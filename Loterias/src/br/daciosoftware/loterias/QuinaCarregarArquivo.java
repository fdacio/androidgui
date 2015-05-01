package br.daciosoftware.loterias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import br.daciosoftware.loterias.Quina.QuinaColunas;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class QuinaCarregarArquivo extends Activity {

	private static final String CATEGORIA = "loterias";
	private static final String TITLE = "Arquivo de Resultados";
	private static final String MSG_PROCESSAMENTO  = "Processando Arquivo, aguarde...";
	private static final String MSG_VALIDACAO = "Erro. Informe o arquivo de resultados!";
	private static final String MSG_PROCESSO_CONCLUIDO = "Processamento Concluído . Concursos Cadastrados";
	private static final String TAG_VALOR = "<td>";
	private static final int DIRECTORY_BROWSER = 1;
	
	private EditText edArquivo;
	private TextView textResultado; 
	
	private Context context = QuinaCarregarArquivo.this;
	private ProgressDialog pDialog;
	private Handler handler = new Handler();
	
	public static QuinaRepositorio repositorio;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar_arquivo);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_quina);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_quina);
		
        
        edArquivo = (EditText) findViewById(R.id.edtArquivoResultados);
		Button btArquivo = (Button) findViewById(R.id.btArquivo);
		Button btProcessar = (Button) findViewById(R.id.btProcessar);
		textResultado = (TextView) findViewById(R.id.textMsgCarregarArquivo);
		TextView textInstrucoes = (TextView) findViewById(R.id.textInstrucoes);
		textInstrucoes.setText(getText(R.string.textInstrucoesQNAr));
		
		
		repositorio = new QuinaRepositorio(this);
		btArquivo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				/*
				 * Rotinas para chamar um dialogo para selecao de diretorio.
				 */
				Intent it = new Intent(QuinaCarregarArquivo.this, FileDialog.class);
				String starPath = android.os.Environment.getExternalStorageDirectory().toString();
			    it.putExtra(FileDialog.START_PATH,starPath);
				it.putExtra(FileDialog.CAN_SELECT_DIR, false);
				it.putExtra(FileDialog.FORMAT_FILTER,(new String[]{"htm","html"}));
				it.putExtra(FileDialog.SELECTION_MODE,FileSelectionMode.MODE_OPEN);
                startActivityForResult(it, DIRECTORY_BROWSER);
				/*
				 * FIM DA ROTINA
				 */
			}
		});
		
		btProcessar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (!edArquivo.getText().toString().equals("")){
					processarArquivo(edArquivo.getText().toString());
					pDialog = ProgressDialog.show(context,TITLE,MSG_PROCESSAMENTO,true,false);
				}
				else{
					Toast.makeText(context, MSG_VALIDACAO,Toast.LENGTH_SHORT).show();
				}
			}
		});	
	}

	//Atualiza o arquivo pro EditText do arquivo quando selecionado
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data);  
    	if (requestCode == DIRECTORY_BROWSER){
    		if (resultCode == RESULT_OK) {
    			edArquivo.setText(data.getStringExtra(FileDialog.RESULT_PATH));
    		}
    		else if (resultCode == RESULT_CANCELED) {
    			edArquivo.setText("");
    		}
    	}	
  	
	}
	
	protected void processarArquivo(final String arquivo){
		new Thread(){
			@Override
			public void run(){
				Log.i(CATEGORIA,"Arquivo LIDO: "+arquivo);
				String linha = "";
				String tagValor = "";
				String DataConcurso="";
				ArrayList<String> quinaResultados = new ArrayList<String>();
				LerHtml html = new LerHtml();
				
				try
				{
					File f = new File(arquivo);
					
					if(f.exists())
					{
						repositorio.Deletar(QuinaColunas._ID + ">?", new String[]{"0"});
						BufferedReader br = new BufferedReader(new FileReader(arquivo));
						//Loop para encontrar o ponto de partida dos resultado <td>1</td> concurso 1;
						while((linha = br.readLine()) != null)
						{
							linha = linha.trim();
							tagValor = html.getTagValor(linha);
							linha = linha.replace("/", "");
							
							if(tagValor.equals(TAG_VALOR)){
								quinaResultados.add(html.getValorHtml(linha));
								Log.i(CATEGORIA,"Valor pro array LISTA ARRAY: "+html.getValorHtml(linha));
							}
							
							if(quinaResultados.size() == 18){
								Log.i(CATEGORIA,"Array para gravacao: "+quinaResultados.toString());
								/*
								 * Gravação dos Dados 
								 */
								Quina q = new Quina();
								q.setNumeroConcurso(Integer.parseInt(quinaResultados.get(0)));
								DataConcurso = quinaResultados.get(1);
								DataConcurso = DataConcurso.substring(0, 2) + "/"+DataConcurso.substring(2, 4)+"/"+DataConcurso.substring(4, 8);
								q.setDataConcurso(DataConcurso);
								q.setD1(Integer.parseInt(quinaResultados.get(2)));
								q.setD2(Integer.parseInt(quinaResultados.get(3)));
								q.setD3(Integer.parseInt(quinaResultados.get(4)));
								q.setD4(Integer.parseInt(quinaResultados.get(5)));
								q.setD5(Integer.parseInt(quinaResultados.get(6)));
								
								repositorio.Salvar(q);
								q = null;
								quinaResultados.clear();
								/*
								 * Fim da gravação
								 */
							}
							
						}
						br.close();
						/*
						 * FINAL DO PROCESSAMENTO DO ARQUIVO E FECHA O DIALOGO DE PROGRESSO
						 */
						finalizaProcessamento();
					}
				}catch(FileNotFoundException e){
				
				}catch(IOException e){
					
				}
			}	
		}.start();	
	}
	
	private void finalizaProcessamento(){
		handler.post(new Runnable(){
			public void run(){
				pDialog.dismiss();
				Toast.makeText(context, MSG_PROCESSO_CONCLUIDO,Toast.LENGTH_SHORT).show();
				textResultado.setText("Adicionou "+String.valueOf(repositorio.getCountReg())+" resultados");
			}});
	}
}