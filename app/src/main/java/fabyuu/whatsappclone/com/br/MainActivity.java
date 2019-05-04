package fabyuu.whatsappclone.com.br;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

import fabyuu.whatsappclone.com.adapter.TabAccessorAdpter;
import fabyuu.whatsappclone.com.config.ConfiguracaoFirebase;
import fabyuu.whatsappclone.com.helper.Base64Custom;
import fabyuu.whatsappclone.com.helper.Preferencias;
import fabyuu.whatsappclone.com.model.Contato;
import fabyuu.whatsappclone.com.model.Usuario;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAccessorAdpter mTabAccessorAdpter;
    private String identificadoContato;

    private DatabaseReference referenciaFirebase;
    private FirebaseAuth auth;
    private Button botaoSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = ConfiguracaoFirebase.getFirebaseAuth();

        mToolbar = (Toolbar)  findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("WhatsApp");

        mViewPager = (ViewPager) findViewById(R.id.main_tabs_pager);
        mTabAccessorAdpter = new TabAccessorAdpter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAccessorAdpter);

        mTabLayout = (TabLayout) findViewById(R.id.main_tab);
        mTabLayout.setupWithViewPager(mViewPager);

        referenciaFirebase = ConfiguracaoFirebase.getFirebase();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_add:
                abrirCadastroUsuario();
                break;
            case R.id.item_configuracoes:
                break;
            case R.id.item_pesquisa:
                break;
            case R.id.item_deslogar:
                deslogarUsuario();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void abrirCadastroUsuario(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Novo Contato");
        alertDialog.setMessage("Email do usuário");
        alertDialog.setCancelable(false);

        final EditText editText = new EditText(MainActivity.this);
        alertDialog.setView(editText);

        alertDialog.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailContato = editText.getText().toString();

                if( emailContato.isEmpty() ){
                    Toast.makeText(MainActivity.this,"Preencha o email", Toast.LENGTH_SHORT).show();
                }else{
                    identificadoContato = Base64Custom.codificarBase64( emailContato );

                    //recuperar instancia Firebase com a codificação do email digitado
                    referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("usuario").child( identificadoContato );

                    referenciaFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if( dataSnapshot != null ){

                                //Recuperar de contato adicionado
                                Usuario usuarioContato = dataSnapshot.getValue( Usuario.class );


                                //Recuperar identificado de usuario logado (Base64)
                                Preferencias preferencias = new Preferencias(MainActivity.this);
                                String identificadorUsuarioLogado = preferencias.getIdentificador();


                                referenciaFirebase = ConfiguracaoFirebase.getFirebase();
                                referenciaFirebase = referenciaFirebase.child("contatos")
                                        .child(identificadorUsuarioLogado).child( identificadoContato );

                                Contato contato = new Contato();
                                contato.setIdentificadorUsuario( identificadoContato );
                                contato.setEmail( usuarioContato.getEmail() );
                                contato.setNome( usuarioContato.getNome() );


                                referenciaFirebase.setValue( contato );

                            }else{
                                Toast.makeText(MainActivity.this, "Usuário não possui cadastro",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.create();
        alertDialog.show();

    }

    private void deslogarUsuario(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
