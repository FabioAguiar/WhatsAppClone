package fabyuu.whatsappclone.com.br;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.zip.Inflater;

import fabyuu.whatsappclone.com.adapter.TabAccessorAdpter;
import fabyuu.whatsappclone.com.config.ConfiguracaoFirebase;




public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabAccessorAdpter mTabAccessorAdpter;


    private DatabaseReference referenciaFirebase;
    private FirebaseAuth auth;
    private Button botaoSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void deslogarUsuario(){
        auth = ConfiguracaoFirebase.getFirebaseAuth();
        auth.signOut();

        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
