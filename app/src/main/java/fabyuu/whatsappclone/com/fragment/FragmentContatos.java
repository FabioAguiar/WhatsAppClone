package fabyuu.whatsappclone.com.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import fabyuu.whatsappclone.com.br.R;
import fabyuu.whatsappclone.com.config.ConfiguracaoFirebase;
import fabyuu.whatsappclone.com.helper.Preferencias;
import fabyuu.whatsappclone.com.model.Contato;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentContatos extends Fragment {

    private ListView listView;
    private ArrayAdapter adapter;
    private ArrayList<String> contatos;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerContatos;


    public FragmentContatos() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerContatos);
        Log.i("ValueEventListener", "onStop");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        contatos = new ArrayList<>();

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_fragment_contatos, container, false);

        listView = (ListView) view.findViewById(R.id.lv_contatos);
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, contatos);

        listView.setAdapter( adapter );

        //Recuperar dados com Firebase
        Preferencias preferencias = new Preferencias(getActivity());
        String identificadorUsuario = preferencias.getIdentificador();

        firebase = ConfiguracaoFirebase.getFirebase().child("contatos").child( identificadorUsuario );

        //Listener para recuperar dados

        valueEventListenerContatos = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Limpar lista
                contatos.clear();

                //Listar contatos
                for ( DataSnapshot dados : dataSnapshot.getChildren() ){

                    Contato contato = dados.getValue( Contato .class);
                    contatos.add( contato.getNome() );

                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        return view;
    }



}
