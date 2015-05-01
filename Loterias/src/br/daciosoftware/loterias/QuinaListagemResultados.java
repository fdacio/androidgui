package br.daciosoftware.loterias;

import java.util.List;
import br.daciosoftware.loterias.Quina.QuinaColunas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class QuinaListagemResultados extends Activity implements OnItemClickListener{
	protected static final String CATEGORIA = "loteria";
	protected static final int INSERIR_EDITAR = 1;
		
	public static QuinaRepositorio repositorio;
	private List<Quina> listaQuina;
	private ListView listViewDados;
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.activity_listagem_resultados);	
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_quina);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_quina);
		
		repositorio = new QuinaRepositorio(this);
		if(repositorio.listarQuina().size()>0){
			Log.i(CATEGORIA,"Inicializou a Tela de Listagem dos Resultados da Lotofacil");
			atualizaLista();
		}
		else{
			Utils.showAlert(this, getString(R.string.textAtencao), getString(R.string.textInstrucoesQN), true);
		}

        Button btQuinaInserirResultado = (Button) findViewById(R.id.btInserirResultado);
        btQuinaInserirResultado.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(QuinaListagemResultados.this, QuinaEditarResultado.class);
				startActivityForResult(it,INSERIR_EDITAR);
				//startActivity(it,INSERIR_EDITAR);
			}
		});
        
        Button btConsultar = (Button) findViewById(R.id.btConsultar);
        btConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				EditText edtConsultaNumeroConcurso = (EditText) findViewById(R.id.consultarNumeroConcurso);
				EditText edtConsultaDataConcurso = (EditText) findViewById(R.id.consultarDataConcurso);
				EditText edtConsultaDezenasConcurso = (EditText) findViewById(R.id.consultarDezenasConcurso);
				String param = QuinaColunas._ID +">0";
				String textoNumeroConcurso = edtConsultaNumeroConcurso.getText().toString();
				String textoDezenasConcurso = edtConsultaDezenasConcurso.getText().toString();
				String textoDataConcurso = edtConsultaDataConcurso.getText().toString(); 
				
				if(!textoNumeroConcurso.equals("")){
					param += QuinaColunas.NUMEROCONCURSO+"="+textoNumeroConcurso;
				}
				
				if(!textoDataConcurso.equals("")){
					param += " and "+QuinaColunas.DATACONCURSO+"='"+textoDataConcurso+"'";
				}
 				
				if(!textoDezenasConcurso.equals("")){
					param += " and "+QuinaColunas.D1+" in("+textoDezenasConcurso+")"+
					         " and "+QuinaColunas.D2+" in("+textoDezenasConcurso+")"+							 
						     " and "+QuinaColunas.D3+" in("+textoDezenasConcurso+")"+
						     " and "+QuinaColunas.D4+" in("+textoDezenasConcurso+")"+
						     " and "+QuinaColunas.D5+" in("+textoDezenasConcurso+")";
				} 				
				
			
			
				if(!param.equals("")){
					atualizaLista(param);
				}else{
					atualizaLista();
				}
			}
		});
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Resultados da Quina");
	}
	
	protected void atualizaLista(){
		//Pega a lista de partidos  e exibe na tela
		listaQuina = repositorio.listarQuina();
		//Adaptador da lista customizada para cada linha de um partido
		QuinaListAdapter adapter = new QuinaListAdapter(this,listaQuina);
		listViewDados = (ListView) findViewById(R.id.lista_resultados);
		listViewDados.setAdapter(adapter);
		listViewDados.setOnItemClickListener(this);
        Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Quina");
	}

	protected void atualizaLista(String param){
		//Pega a lista de resultados e exibe na tela
		listaQuina = repositorio.consultarResultados(param);
		
		if(listaQuina.size()>0){
			//Adaptador da lista customizada para cada linha de um resultado
			QuinaListAdapter adapter = new QuinaListAdapter(this,listaQuina);
			listViewDados = (ListView) findViewById(R.id.lista_resultados);
			listViewDados.setAdapter(adapter);
			listViewDados.setOnItemClickListener(this);
		    Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Quina");
		}else{
			Toast.makeText(this, "Nenhum resultado encontrado!", Toast.LENGTH_SHORT).show();
		}
	}

	//Recupera o id dos resultados abre a tela de edição
	protected void editarResultadoQuina(int posicao){
		Quina m = listaQuina.get(posicao);
		Log.i(CATEGORIA,"Iniciando Intent Editar");
		Intent it = new Intent(this, QuinaEditarResultado.class);
		it.putExtra(QuinaColunas._ID, m.getId());
		Log.i(CATEGORIA,"Abrindo a Tela de Edição ");
		startActivityForResult(it, INSERIR_EDITAR);
		Log.i(CATEGORIA,"Tela de Edição de aberta");	
	}
	
	@Override
	protected void onActivityResult(int codigo, int codigoRetorno, Intent it){
		super.onActivityResult(codigo, codigoRetorno, it);
		//Quando a activty EditarVereador retornar, seja se foi para editar, atualizar ou excluir
		//Vamos atualizar a lista
		if(codigoRetorno == RESULT_OK){
			//Atualiza a lista
			atualizaLista();
		}
	}

	@Override
	protected void onDestroy(){
		super.onDestroy();
		//Fecha o banco
		repositorio.Fechar();
		Log.i(CATEGORIA,"Destruiu a Tela de Listagem de Resultado da Quina");
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 * Método sobreposto;
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		editarResultadoQuina(position);
	}
	
	public void onItemClick(int position) {
		// TODO Auto-generated method stub
		editarResultadoQuina(position);
	}
	
}
