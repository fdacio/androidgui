package br.daciosoftware.loterias;

import java.util.List;
import br.daciosoftware.loterias.Lotofacil.LotofacilColunas;
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

public class LotofacilListagemResultados extends Activity implements OnItemClickListener{
	protected static final String CATEGORIA = "loteria";
	protected static final int INSERIR_EDITAR = 1;
		
	public static LotofacilRepositorio repositorio;
	private List<Lotofacil> listaLotofacil;
	private ListView listViewDados;

	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		setContentView(R.layout.activity_listagem_resultados);	
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_lotofacil);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_lotofacil);

		repositorio = new LotofacilRepositorio(this);
		if(repositorio.listarLotofacil().size()>0){
			Log.i(CATEGORIA,"Inicializou a Tela de Listagem dos Resultados da Lotofacil");
			atualizaLista();
		}
		else{
			Utils.showAlert(this, getString(R.string.textAtencao), getString(R.string.textInstrucoesLF), true);
		}

        Button btLotofacilInserirResultado = (Button) findViewById(R.id.btInserirResultado);
        btLotofacilInserirResultado.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it = new Intent(LotofacilListagemResultados.this, LotofacilEditarResultado.class);
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
					param = LotofacilColunas.NUMEROCONCURSO+"="+textoNumeroConcurso;
				}
				if(!textoDataConcurso.equals("")){
					if(param.equals("")){
						param = LotofacilColunas.DATACONCURSO+"='"+textoDataConcurso+"'";
					}
					else{
						param = param +" and "+LotofacilColunas.DATACONCURSO+"='"+textoDataConcurso+"'";
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
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Resultados da Lotofacil");
	}
	
	protected void atualizaLista(){
		//Pega a lista de partidos  e exibe na tela
		listaLotofacil = repositorio.listarLotofacil();
		//Adaptador da lista customizada para cada linha de um partido
		LotofacilListAdapter adapter = new LotofacilListAdapter(this,listaLotofacil);
		listViewDados = (ListView) findViewById(R.id.lista_resultados);
		listViewDados.setAdapter(adapter);
		listViewDados.setOnItemClickListener(this);
        Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Lotofacil");
	}

	protected void atualizaLista(String param){
		//Pega a lista de resultados e exibe na tela
		listaLotofacil = repositorio.consultarResultados(param);
		
		if(listaLotofacil.size()>0){
			//Adaptador da lista customizada para cada linha de um resultado
			LotofacilListAdapter adapter = new LotofacilListAdapter(this,listaLotofacil);
			listViewDados = (ListView) findViewById(R.id.lista_resultados);
			listViewDados.setAdapter(adapter);
			listViewDados.setOnItemClickListener(this);
		    Log.i(CATEGORIA,"Atualizou a Listagem de Resultados Lotofacil");
		}else{
			Toast.makeText(this, "Nenhum resultado encontrado!", Toast.LENGTH_SHORT).show();
		}
	}

	//Recupera o id dos resultados abre a tela de edição
	protected void editarResultadoLotofacil(int posicao){
		Lotofacil l = listaLotofacil.get(posicao);
		Log.i(CATEGORIA,"Iniciando Intent Editar");
		Intent it = new Intent(this, LotofacilEditarResultado.class);
		it.putExtra(LotofacilColunas._ID, l.getId());
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
		Log.i(CATEGORIA,"Destruiu a Tela de Listagem de Resultado da Lotofacil");
	}

	/*
	 * (non-Javadoc)
	 * @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long)
	 * Método sobreposto;
	 */
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		editarResultadoLotofacil(position);
	}
	
	public void onItemClick(int position) {
		// TODO Auto-generated method stub
		editarResultadoLotofacil(position);
	}
	
}
