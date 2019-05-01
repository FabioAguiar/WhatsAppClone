package fabyuu.whatsappclone.com.br;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import fabyuu.whatsappclone.com.helper.Preferencias;

public class ValidadorActivity extends AppCompatActivity {

    private EditText codigoValidacao;
    private Button botaoValidar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        codigoValidacao = (EditText) findViewById(R.id.codValidarId);
        botaoValidar = (Button) findViewById(R.id.botaoValidarId);

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(codigoValidacao, smf);
        codigoValidacao.addTextChangedListener(mtw);

        botaoValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                String tokenGerado = usuario.get("TOKEN");
                String tokenDigitado = codigoValidacao.getText().toString();

                if(tokenDigitado.equals(tokenGerado)){
                    Toast.makeText(ValidadorActivity.this, "VALIDADO", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ValidadorActivity.this, "NÃO VALIDADO", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
