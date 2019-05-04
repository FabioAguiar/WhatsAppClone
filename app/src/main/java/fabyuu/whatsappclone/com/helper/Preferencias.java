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
    private final static String CHAVE_IDENTIFICADOR = "identificadorUsuarioLogado";

    public Preferencias( Context contextoParametro ){

        sharedPreferences = contextoParametro.getSharedPreferences(NOME_ARQUIVO, MODE);
        editor = sharedPreferences.edit();

    }

    public void salvarDados(String identificador){
        editor.putString(CHAVE_IDENTIFICADOR, identificador);

        editor.commit();
    }

    public String getIdentificador(){
        return sharedPreferences.getString(CHAVE_IDENTIFICADOR, null);
    }


}
