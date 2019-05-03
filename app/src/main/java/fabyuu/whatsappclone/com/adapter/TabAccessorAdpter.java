package fabyuu.whatsappclone.com.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fabyuu.whatsappclone.com.fragment.FragmentContatos;
import fabyuu.whatsappclone.com.fragment.FragmentConversas;

public class TabAccessorAdpter extends FragmentStatePagerAdapter {

    private String[] titulosAbas = {"CONVERSAS", "CONTATOS"};


    public TabAccessorAdpter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new FragmentConversas();
                break;
            case 1:
                fragment = new FragmentContatos();
                break;
            default:
                return fragment;
        }


        return fragment;
    }

    @Override
    public int getCount() {
        return titulosAbas.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulosAbas[position];
    }
}
