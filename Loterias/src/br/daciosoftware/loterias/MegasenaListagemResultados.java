package br.daciosoftware.loterias;

import java.util.List;
import br.daciosoftware.loterias.Megasena.MegasenaColunas;
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

public class MegasenaListagemResultados extends Activity implements OnItemClickListener{
	protected static final String CATEGORIA = "loteria";
	protected static final int INSERIR_EDITAR = 1;
		
	public static MegasenaRepositorio repositorio;
	private List<Megasena> listaMegasena;
	private ListView listViewDados;
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.activity_listagem_resultados);	
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_megasena);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_megasena);

		repositorio = new MegasenaRepositorio(this);
		if(repositorio.listarMegasena().size()>0){
			Log.i(CATEGORIA,"Inicializou a Tela de Listagem dos Resultados da Lotofacil");
			atualizaLista();
		}
		else{
			Utils.showAlert(this, getString(R.string.textAtencao), getString(R.string.textInstrucoesMS), true);
		}
		
        Button btMegasenaInserirResultado = (Button) findViewById(R.id.btInserirResultado);
        btMegasenaInserirResultado.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(MegasenaListagemResultados.this, MegasenaEditarResultado.class);
				startActivityForResult(it,INSERIR_EDITAR);
				//startActivity(it,INSERIR_EDITAR);
			}
		});
        
        Button btConsultar = (Button) findViewById(R.id.btConsultar);
        btConsultar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				EditText edtConsultaNumeroConcurso = (EditText) findViewById(R.id.consultarNumeroConcurso);
				EditText edtConsultaDataConcurso = (EditText) findViewById(R.id.consultarDataConcurso);
				String param = "";
				String textoNumeroConcurso = edtConsultaNumeroConcurso.getText().toString();
				String textoDataConcurso = edtConsultaDataConcurso.getText().toString();
				if(!textoNumeroConcurso.equals("")){
					param = MegasenaColunas.NUMEROCONCURSO+"="+textoNumeroConcurso;
				}
				if(!textoDataConcurso.equals("")){
					if(param.equals("")){
						param = MegasenaColunas.DATACONCURSO+"='"+textoDataConcurso+"'";
					}
					else{
						param = param +" and "+MegasenaColunas.DATACONCURSO+"='"+textoDataConcurso+"'";
					}
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
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Resultados da Megasena");
	}
	
	protected void atualizaLista(){
		//Pega a lista de partidos  e exibe na tela
		listaMegasena = repositorio.listarMegasena();
		//Adaptador da lista customizada para cada linha de um partido
		MegasenaListAdapter adapter = new MegasenaListAdapter(this,listaMegasena);
		listViewDados = (ListView) findViewById(R.id.lista_resultados);
		listViewDados.setAdapter(adapter);
		listViewDados.setOnItemClickListener(this);
        Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Megasena");
	}

	protected void atualizaLista(String param){
		//Pega a lista de resultados e exibe na tela
		listaMegasena = repositorio.consultarResultados(param);
		
		if(listaMegasena.size()>0){
			//Adaptador da lista customizada para cada linha de um resultado
			MegasenaListAdapter adapter = new MegasenaListAdapter(this,listaMegasena);
			listViewDados = (ListView) findViewById(R.id.lista_resultados);
			listViewDados.setAdapter(adapter);
			listViewDados.setOnItemClickListener(this);
		    Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Megasena");
		}else{
			Toast.makeText(this, "Nenhum resultado encontrado!", Toast.LENGTH_SHORT).show();
		}
	}

	//Recupera o id dos resultados abre a tela de edição
	protected void editarResultadoMegasena(int posicao){
		Megasena m = listaMegasena.get(posicao);
		Log.i(CATEGORIA,"Iniciando Intent Editar");
		Intent it = new Intent(this, MegasenaEditarResultado.class);
		it.putExtra(MegasenaColunas._ID, m.getId());
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
		Log.i(CATEGORIA,"Destruiu a Tela de Listagem de Resultado da Megasena");
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 * Método sobreposto;
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		editarResultadoMegasena(position);
	}
	
	public void onItemClick(int position) {
		// TODO Auto-generated method stub
		editarResultadoMegasena(position);
	}
	
}
