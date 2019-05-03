package fabyuu.whatsappclone.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fabyuu.whatsappclone.com.br.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConversas extends Fragment {


    public FragmentConversas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_conversas, container, false);
    }

}
