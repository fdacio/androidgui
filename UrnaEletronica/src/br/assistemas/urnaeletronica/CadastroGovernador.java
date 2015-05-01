package br.assistemas.urnaeletronica;

import java.util.List;

import br.assistemas.urnaeletronica.Governador.GovernadorColunas;

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

public class CadastroGovernador extends Activity implements OnItemClickListener{
	protected static final String CATEGORIA = "urna";
	protected static final int INSERIR_EDITAR = 1;
	protected static final int BUSCAR = 2;
		
	public static RepositorioGovernador repositorio;
	private List<Governador> listaGovernadores;
	private ListView listViewDados;
	
	@Override
	public void onCreate(Bundle icicle){
		super.onCreate(icicle);
		repositorio = new RepositorioGovernador(this);
		Log.i(CATEGORIA,"Inicializou a Tela de Listagem de Governadores");
		setContentView(R.layout.cadastro_governador);
		atualizaLista();
		Button btnNovo = (Button) findViewById(R.id.btnNovo);
		btnNovo.setOnClickListener(new OnClickListener() {
		    public void onClick(View view) {
			Intent it = new Intent(CadastroGovernador.this,
				EditarGovernador.class);
			startActivityForResult(it, INSERIR_EDITAR);
			// startActivity(it,INSERIR_EDITAR);
		    }
		});

		Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
		btnBuscar.setOnClickListener(new OnClickListener() {
		    public void onClick(View view) {
			Intent it = new Intent(CadastroGovernador.this,
				BuscarGovernador.class);
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
		Log.i(CATEGORIA,"Pausou a Tela de Listagem de Governadores");
	}
	
	protected void atualizaLista(){
		//Pega a lista de vereadores  e exibe na tela
		listaGovernadores = repositorio.listarGovernadores();
		GovernadorListAdapter adapter = new GovernadorListAdapter(this,
			listaGovernadores);
		listViewDados = (ListView) findViewById(R.id.listViewGovernadores);
		listViewDados.setAdapter(adapter);
		listViewDados.setOnItemClickListener(this);
		Log.i(CATEGORIA, "Atualizou a Listagem de Governadores");
	}
	
	
	//Recupera o id do vereador e abre a tela de edição
	protected void editarGovernador(int posicao){
		//usuario clicou em algum vereador da lista
		//Recupera o vereador selecionado
		Governador p = listaGovernadores.get(posicao);
		//Cria o Intent da tela de editar
		Log.i(CATEGORIA,"Iniciando Intent Editar");
		Intent it = new Intent(this, EditarGovernador.class);
		
		//passa o id do vereador como parametro
		it.putExtra(GovernadorColunas._ID, p.getId());
		
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
		Log.i(CATEGORIA,"Destroiu a Tela de Listagem de Governador");
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
		long id) {
	    // TODO Auto-generated method stub
	    editarGovernador(position);
	    
	}
}
