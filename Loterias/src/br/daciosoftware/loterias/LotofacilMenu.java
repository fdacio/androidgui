package br.daciosoftware.loterias;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.os.Bundle;


public class LotofacilMenu extends Activity implements OnItemClickListener{
	private ListView listViewMenu;
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.activity_menu_loterias);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_lotofacil);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_lotofacil);

		
		ArrayList<String> itensMenu = new ArrayList<String>();
		itensMenu.add("Arquivo com resultados");
		itensMenu.add("Dezenas mais sorteadas");
		itensMenu.add("Concursos");
		itensMenu.add("Sair");
		
		MenuAdapter adapter = new MenuAdapter(this,itensMenu,R.drawable.icon_lotofacil);
		listViewMenu = (ListView) findViewById(R.id.lista_menu_loteria);
		listViewMenu.setAdapter(adapter);
		listViewMenu.setOnItemClickListener(this);
		
		
	}

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		switch(position){
		case 0:
			startActivity(new Intent(this, LotofacilCarregarArquivo.class));			
			break;
		case 1:
			startActivity(new Intent(this, LotofacilDezenasMaisSorteadas.class));			
			break;
		case 2:
			startActivity(new Intent(this, LotofacilListagemResultados.class));
			break;
		default:
			// Encerra a activity (encerra o ciclo de vida)
			finish();
		}
		
	}
}
