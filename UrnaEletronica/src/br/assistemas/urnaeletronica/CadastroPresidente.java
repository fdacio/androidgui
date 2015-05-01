package br.assistemas.urnaeletronica;

import java.util.List;

import br.assistemas.urnaeletronica.Presidente.PresidenteColunas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CadastroPresidente extends Activity implements OnItemClickListener{
	protected static final String CATEGORIA = "urna";
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
		
	public static RepositorioPresidente repositorio;
	private List<Presidente> listaPresidentes;
	private ListView listViewDados;
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		repositorio = new RepositorioPresidente(this);
		Log.i(CATEGORIA,"Inicializou a Tela de Listagem de Presidentees");
		setContentView(R.layout.cadastro_presidente);
		atualizaLista();
		Button btnNovo = (Button) findViewById(R.id.btnNovo);
		btnNovo.setOnClickListener(new OnClickListener() {
		    public void onClick(View view) {
			Intent it = new Intent(CadastroPresidente.this,
				EditarPresidente.class);
			startActivityForResult(it, INSERIR_EDITAR);
			// startActivity(it,INSERIR_EDITAR);
		    }
		});

		Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
		btnBuscar.setOnClickListener(new OnClickListener() {
		    public void onClick(View view) {
			Intent it = new Intent(CadastroPresidente.this,
				BuscarPresidente.class);
			startActivityForResult(it, INSERIR_EDITAR);
			// startActivity(it,INSERIR_EDITAR);
		    }
		});

		Button btnFechar = (Button) findViewById(R.id.btnFechar);
		btnFechar.setOnClickListener(new OnClickListener() {
		    public void onClick(View view) {
			//Fecha a tela
			finish();

		    }
		});
		
	}

	@Override
	protected void onPause(){
		super.onPause();
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Presidentees");
	}
	
	protected void atualizaLista(){
		//Pega a lista de vereadores  e exibe na tela
		listaPresidentes = repositorio.listarPresidentes();
		PresidenteListAdapter adapter = new PresidenteListAdapter(this,
			listaPresidentes);
		listViewDados = (ListView) findViewById(R.id.listViewPresidentes);
		listViewDados.setAdapter(adapter);
		listViewDados.setOnItemClickListener(this);
		Log.i(CATEGORIA, "Atualizou a Listagem de Presidentes");
	}
	
	
	//Recupera o id do vereador e abre a tela de edição
	protected void editarPresidente(int posicao){
		//usuario clicou em algum vereador da lista
		//Recupera o vereador selecionado
		Presidente p = listaPresidentes.get(posicao);
		//Cria o Intent da tela de editar
		Log.i(CATEGORIA,"Iniciando Intent Editar");
		Intent it = new Intent(this, EditarPresidente.class);
		
		//passa o id do vereador como parametro
		it.putExtra(PresidenteColunas._ID, p.getId());
		
		Log.i(CATEGORIA,"Abrindo a Tela de Edição");
		//Abre a tela de Edição
		startActivityForResult(it, INSERIR_EDITAR);
		Log.i(CATEGORIA,"Tela de Edição aberta");
		
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
		Log.i(CATEGORIA,"Destroiu a Tela de Listagem de Presidente");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    // TODO Auto-generated method stub
	    editarPresidente(position);
	    
	}
}
