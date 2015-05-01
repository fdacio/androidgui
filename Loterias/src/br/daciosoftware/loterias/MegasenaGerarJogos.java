package br.daciosoftware.loterias;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.daciosoftware.loterias.MegasenaMaisSorteadas.MegasenaMaisSorteadasColunas;

public class MegasenaGerarJogos extends Activity {
	protected static final String CATEGORIA = "loterias";
	private static final int DIRECTORY_BROWSER = 1;
	private static final String NOME_ARQUIVO = "jogos_megasena.txt";
	
	public static MegasenaMaisSorteadasRepositorio repositorio;
	private ArrayList<Integer> listaDezenasSelecionadas;
	private ListView listViewDados;
	private ArrayList<String> listaJogosGerados;
	private Spinner spinnerConcursos;
	private TextView jogosPossiveis;
	private long qtdeJogos;
	private int qtdeDezenas;
	private GeradorDeJogos geradorDeJogos;
	private ProgressDialog dialog;
	private Handler handler = new Handler();
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerar_jogos_megasena);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_megasena);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_megasena);
		
       	
        EditText edtNumerosSelecionados = (EditText) findViewById(R.id.edtNumerosSelecionados);
       	listaDezenasSelecionadas = new ArrayList<Integer>();
       	repositorio = new MegasenaMaisSorteadasRepositorio(this);
       	Cursor c = repositorio.getDezenasSelecionadas();
       	if(c.moveToFirst()){
       		int idxDezena = c.getColumnIndex(MegasenaMaisSorteadasColunas.DEZENA);
		   	String dezenasSelecionadas = "";
			do{
				if(dezenasSelecionadas.equals("")){
					dezenasSelecionadas = String.valueOf(c.getInt(idxDezena));
				}
				else{
					dezenasSelecionadas = dezenasSelecionadas+", "+String.valueOf(c.getInt(idxDezena));
				}
				listaDezenasSelecionadas.add(c.getInt(idxDezena));
			}while(c.moveToNext());
		   	edtNumerosSelecionados.setText(dezenasSelecionadas);
       	}
       	TextView textNumeroSelecionados = (TextView) findViewById(R.id.textNumeroSelecionados);
       	textNumeroSelecionados.setText(textNumeroSelecionados.getText()+": "+String.valueOf(listaDezenasSelecionadas.size())+" dezenas");
       	
       	spinnerConcursos = (Spinner) findViewById(R.id.spinnerConcursos);
		
       	jogosPossiveis = (TextView) findViewById(R.id.textJogosPossiveis);
       	
       	Button btGerarJogos = (Button) findViewById(R.id.btGerarJogos);
        btGerarJogos.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				int n;
				n = spinnerConcursos.getSelectedItemPosition();
				switch(n){
					case 0:
						qtdeDezenas = 6;
						break;
					case 1:
						qtdeDezenas = 7;
						break;
					case 2:
						qtdeDezenas = 8;
						break;
					case 3:
						qtdeDezenas = 9;
						break;
					case 4:
						qtdeDezenas = 10;
						break;
					case 5:
						qtdeDezenas = 11;
						break;
					case 6:
						qtdeDezenas = 12;
						break;
					case 7:
						qtdeDezenas = 13;
						break;
					case 8:
						qtdeDezenas = 14;
						break;
					case 9:
						qtdeDezenas = 15;
						break;
				}
				if(qtdeDezenas > listaDezenasSelecionadas.size()){
					Toast.makeText(MegasenaGerarJogos.this, "Erro! Quantidade "+spinnerConcursos.getSelectedItem().toString()+" maior que dezenas selecionadas", Toast.LENGTH_SHORT).show();
				}
				else{
					dialog = ProgressDialog.show(MegasenaGerarJogos.this, "", "Aguarde...",false,true);
					carregaJogos();
				}
			}
		});
        
       	Button btSalvarJogosTxt = (Button) findViewById(R.id.btSalvarJogosTxt);
       	btSalvarJogosTxt.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(MegasenaGerarJogos.this, FileDialog.class);
				String starPath = android.os.Environment.getExternalStorageDirectory().toString();
			    it.putExtra(FileDialog.START_PATH,starPath);
				it.putExtra(FileDialog.CAN_SELECT_DIR, true);
				it.putExtra(FileDialog.ONLY_SELECT_DIR, true);
				it.putExtra(FileDialog.SELECTION_MODE,FileSelectionMode.MODE_OPEN);
                startActivityForResult(it, DIRECTORY_BROWSER);

			}
       	});
    }
    
	protected void atualizaListaJogos(){
		jogosPossiveis.setText("Jogos Possíveis: "+String.valueOf(qtdeJogos));
		//Pega a lista de jogos gerados e exibe na tela
		listaJogosGerados = geradorDeJogos.getListaJogos();
		//Adaptador da lista customizada para cada linha de um partido
		MegasenaJogosGeradosListAdapter adapter = new MegasenaJogosGeradosListAdapter(this,listaJogosGerados);
		listViewDados = (ListView) findViewById(R.id.lista_jogos_gerados);
		listViewDados.setAdapter(adapter);
		Log.i(CATEGORIA,"Atualizou a Listagem de Jogos Gerados");
	}
    
	private void carregaJogos(){
		new Thread(){
			@Override
			public void run(){
				geradorDeJogos = new GeradorDeJogos();
				qtdeJogos = geradorDeJogos.qtdeJogosPossiveis(qtdeDezenas, listaDezenasSelecionadas.size());
				Log.i(CATEGORIA,"Qtde Dezenas Selecionadas: "+listaDezenasSelecionadas.size());
				Log.i(CATEGORIA,"Qtde Dezenas pro Jogo: "+qtdeDezenas);
				geradorDeJogos.gerarJogos(listaDezenasSelecionadas, qtdeDezenas);
				
				finalizaCarga();
			}
		}.start();
	}
	
	private void finalizaCarga(){
		handler.post(new Runnable(){
			public void run(){
				//Fecha a janela de progresso
				dialog.dismiss();
				atualizaListaJogos();				
			}
		});
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		//Fecha o banco
		repositorio.Fechar();
		Log.i(CATEGORIA,"Destruiu a Tela de Listagem de Jogos Gerados");
	}
	protected void montaArquivo(String directoryFile, String nomeArquivo){
		String newLine = System.getProperty("line.separator");
		File arquivo = null;
		try {
			//arquivo = new File(android.os.Environment.getExternalStorageDirectory(),ARQUIVO);
			arquivo = new File(directoryFile,nomeArquivo);
			
			FileOutputStream out =  new FileOutputStream(arquivo);
			String texto1 = "JOGOS GERADOS"+newLine;
			String texto2 = "===================================="+newLine;
			out.write(texto1.getBytes());
			out.write(texto2.getBytes());
			
			for(int i = 0; i < listaJogosGerados.size(); i++){
				String jogoTexto = listaJogosGerados.get(i).toString();
				jogoTexto = jogoTexto+newLine;
				out.write(jogoTexto.getBytes());
			}
			
			out.flush();//para garantir que os dados sejam realmente gravados no arquivo.
			out.close();
			Toast.makeText(MegasenaGerarJogos.this, "Arquivo: "+directoryFile+"/"+nomeArquivo +" salvo com sucesso", Toast.LENGTH_SHORT).show();
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			Log.e(CATEGORIA,e.getMessage(), e);
			
		} catch (IOException e){
			// TODO: handle exception
			Log.e(CATEGORIA,e.getMessage(), e);
		}
		
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data);  
    	if (requestCode == DIRECTORY_BROWSER){
    		if (resultCode == RESULT_OK) {
    			montaArquivo(data.getStringExtra(FileDialog.RESULT_PATH),NOME_ARQUIVO);
    		}
    	}
	}	
 	
}