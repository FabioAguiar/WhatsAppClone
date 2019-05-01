package fabyuu.whatsappclone.com.br;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.opengl.EGLExt;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Random;

import fabyuu.whatsappclone.com.config.ConfiguracaoFirebase;
import fabyuu.whatsappclone.com.helper.Permissoes;
import fabyuu.whatsappclone.com.helper.Preferencias;
import fabyuu.whatsappclone.com.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference referenciaFirebase;

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private TextView cadastre_se;
    private Usuario usuario;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.emailId);
        senha = (EditText) findViewById(R.id.senhaId);
        botaoLogar = (Button) findViewById(R.id.botaoLogarId);
        cadastre_se = (TextView) findViewById(R.id.cadastreseId);


        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText() != null && senha.getText() != null){
                    usuario = new Usuario();
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                }
                validarLogin();

            }
        });


        cadastre_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                startActivity(intent);
            }
        });

    }

    private void verificarUsuarioLogado(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();

        if(auth.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }


    private void validarLogin(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if( task.isSuccessful() ){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Logado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Erro ao logar!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void abrirTelaPrincipal(){

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}
