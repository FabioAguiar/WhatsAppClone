package fabyuu.whatsappclone.com.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;

import java.util.HashMap;

public class Preferencias {

    private Context contexto;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor  editor;
    private final static String NOME_ARQUIVO = "WHATSAPP.PREFERENCIAS";
    private final static int MODE = 0;
    private final static String CHAVE_NOME = "NOME";
    private final static String CHAVE_TELEFONE = "TELEFONE";
    private final static String CHAVE_TOKEN = "TOKEN";

    public Preferencias( Context contextoParametro ){

        sharedPreferences = contextoParametro.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();

    }

    public void salvarPreferenciasUsuario(String nome, String telefone, String token){
        editor.putString(CHAVE_NOME, nome);
        editor.putString(CHAVE_TELEFONE, telefone);
        editor.putString(CHAVE_TOKEN, token);
        editor.commit();
    }

    public HashMap<String, String> getDadosUsuario(){

        HashMap <String, String> dadosUsuario = new HashMap<>();

        dadosUsuario.put(CHAVE_NOME, sharedPreferences.getString(CHAVE_NOME, null));
        dadosUsuario.put(CHAVE_TELEFONE, sharedPreferences.getString(CHAVE_TELEFONE, null));
        dadosUsuario.put(CHAVE_TOKEN, sharedPreferences.getString(CHAVE_TOKEN, null));

        return dadosUsuario;
    }

}
