package br.daciosoftware.loterias;

import java.util.List;

import br.daciosoftware.loterias.MegasenaMaisSorteadas.MegasenaMaisSorteadasColunas;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class MegasenaDezenasMaisSorteadas extends Activity{
	protected static final String CATEGORIA = "loterias";
	public static MegasenaMaisSorteadasRepositorio repositorio;
	public static MegasenaRepositorio mr;
	private List<MegasenaMaisSorteadas> listaMegasenaMaisSorteadas;
	private ListView listViewDados;	
	private ProgressDialog dialog;
	private Spinner spinnerConcursos;
	private Handler handler = new Handler();
		   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dezenas_mais_sorteadas);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_megasena);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_megasena);
		
		mr = new MegasenaRepositorio(this);
		repositorio = new MegasenaMaisSorteadasRepositorio(this);
		if(mr.getCursor().getCount() > 0){
			Log.i(CATEGORIA,"Inicializou a Tela de Listagem dos Resultados da Mega-Sena");
			
			dialog = ProgressDialog.show(this, "", "Aguarde...",false,true);
			carregaDados(0);
			atualizaLista();
		}
		else{
			Utils.showAlert(this, getString(R.string.textAtencao), getString(R.string.textInstrucoesMS), true);
		}
		
        spinnerConcursos = (Spinner) findViewById(R.id.spinnerConcursos);
        
        Button btListar = (Button) findViewById(R.id.btListar);
        btListar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int n;
				n = spinnerConcursos.getSelectedItemPosition();
				switch(n){
					case 1:
						n = 10;
						break;
					case 2:
						n = 30;
						break;
						
					case 3:
						n = 30;
						break;
					case 4:
						n = 40;
						break;
					case 5:
						n = 50;
						break;
					case 6:
						n = 100;
						break;
					
				}
				if(mr.getCursor().getCount() > 0){
					dialog = ProgressDialog.show(MegasenaDezenasMaisSorteadas.this, "", "Aguarde...",false,true);
					carregaDados(n);
				}
			}
		});

        Button btGerarJogos = (Button) findViewById(R.id.btGerarJogos);
        btGerarJogos.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(repositorio.getDezenasSelecionadas().getCount()<6){
					Toast.makeText(MegasenaDezenasMaisSorteadas.this, "Selecione pelo menos 6 dezenas", Toast.LENGTH_SHORT).show();
				}else{
					gerarJogos();
				}
			}
		});
	}
    
    
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Resultados da Megasena");
	}
	
	protected void atualizaLista(){
		//Pega a lista de partidos  e exibe na tela
		listaMegasenaMaisSorteadas = repositorio.listarMegasenaMaisSorteadas();
		//Adaptador da lista customizada para cada linha de um partido
		MegasenaMaisSorteadasListAdapter adapter = new MegasenaMaisSorteadasListAdapter(this,listaMegasenaMaisSorteadas);
		listViewDados = (ListView) findViewById(R.id.lista_dezenas_mais_sorteadas);
		listViewDados.setAdapter(adapter);
		Log.i(CATEGORIA,"Atualizou a Listagem de Dezenas Mais Sorteadas");
	}

	protected void gerarJogos(){
		//Aqui fazer as conbinações com as dezenas marcadas
		Intent it = new Intent(this, MegasenaGerarJogos.class);
		startActivity(it);
	}
	
	
	private void carregaDados(final int concursos){
		new Thread(){
			@Override
			public void run(){
					repositorio.Deletar(MegasenaMaisSorteadasColunas._ID + ">?", new String[]{"0"});
			        repositorio.InserirDezenas(concursos);
			        finalizaCarga();
			}
		}.start();
	}
	
	private void finalizaCarga(){
		handler.post(new Runnable(){
			public void run(){
				//Fecha a janela de progresso
				dialog.dismiss();
				atualizaLista();
				
			}
		});
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		//Fecha o banco
		repositorio.Fechar();
		Log.i(CATEGORIA,"Destruiu a Tela de Listagem de Resultado da Megasena");
	}
}
