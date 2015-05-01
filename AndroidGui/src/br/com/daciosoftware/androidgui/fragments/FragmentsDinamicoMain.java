package br.com.daciosoftware.androidgui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import br.com.daciosoftware.androidgui.R;

public class FragmentsDinamicoMain extends FragmentActivity {

    Fragment fragmentUm = new FragmentsDinamicoUm();
    Fragment fragmentDois = new FragmentsDinamicoDois();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_fragments_dinamico_main);
	//addFragment(fragmentUm);
    }

    public void onClickFragmentOne(View view) {
	replaceFragment(fragmentUm);
    }

    public void onClickFragmentTwo(View view) {
	replaceFragment(fragmentDois);
    }

    /*
    private void addFragment(Fragment fragment) {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	transaction.add(R.id.linearLayoutFragment, fragment);
	transaction.addToBackStack(null);

	transaction.commit();
    }
    */

    private void replaceFragment(Fragment fragment) {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	transaction.replace(R.id.linearLayoutFragment, fragment);
	transaction.addToBackStack(null);

	transaction.commit();
    }
}
