package br.com.daciosoftware.androidgui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.daciosoftware.androidgui.fragments.FragmentsDinamicoMain;
import br.com.daciosoftware.androidgui.fragments.FragmentsPropagandaMain;

public class MenuMain extends ListActivity {

    private static final String[] itensMenu = new String[] {
	    "Cantos Arredondados", "Estrela RatinBar", "Visão Balançar",
	    "Detecção de Gestos", "Animação Rasterizada", "Mapa", "Mapa2",
	    "Processamento AsyncTask", "Fragments 1", "Fragments Dinâmico",
	    "ActionBar","Sair" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	this.setListAdapter(new ArrayAdapter<String>(this,
		android.R.layout.simple_list_item_1, itensMenu));

    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
	switch (position) {
	case 0:
	    startActivity(new Intent(this, CantosArredondados.class));
	    break;

	case 1:
	    startActivity(new Intent(this, EstrelasRatingBar.class));
	    break;
	case 2:
	    startActivity(new Intent(this, VisaoBalancar.class));
	    break;
	case 3:
	    startActivity(new Intent(this, DeteccaoGestos.class));
	    break;
	case 4:
	    startActivity(new Intent(this, AnimacaoRasterizadaSimples.class));
	    break;
	case 5:
	    startActivity(new Intent(this, MapaSimples.class));
	    break;
	case 6:
	    startActivity(new Intent(this, MapaSimples2.class));
	    break;
	case 7:
	    startActivity(new Intent(this, ProcessamentoAsyncTask.class));
	    break;
	case 8:
	    startActivity(new Intent(this, FragmentsPropagandaMain.class));
	    break;
	case 9:
	    startActivity(new Intent(this, FragmentsDinamicoMain.class));
	    break;
	case 10:
	    startActivity(new Intent(this, TelaActionBar.class));
	    break;
	default:
	    finish();
	}
    }

}
