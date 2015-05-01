package br.assistemas.urnaeletronica;

import java.util.List;

import br.assistemas.urnaeletronica.DeputadoFederal.DeputadoFederalColunas;

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

public class CadastroDeputadoFederal extends Activity implements
	OnItemClickListener {
    protected static final String CATEGORIA = "urna";
    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;

    public static RepositorioDeputadoFederal repositorio;
    private List<DeputadoFederal> listaDeputadosFederal;
    private ListView listViewDados;

    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	repositorio = new RepositorioDeputadoFederal(this);
	Log.i(CATEGORIA, "Inicializou a Tela de Listagem de Deputado Federal");
	setContentView(R.layout.cadastro_deputado_federal);
	atualizaLista();
	Button btnNovo = (Button) findViewById(R.id.btnNovo);
	btnNovo.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroDeputadoFederal.this,
			EditarDeputadoFederal.class);
		startActivityForResult(it, INSERIR_EDITAR);
		// startActivity(it,INSERIR_EDITAR);
	    }
	});

	Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
	btnBuscar.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroDeputadoFederal.this,
			BuscarDeputadoFederal.class);
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
    protected void onPause() {
	super.onPause();
	Log.i(CATEGORIA, "Pausou a Tela de Listagem de Deputado Federal");
    }

    protected void atualizaLista() {
	// Pega a lista de DeputadosFederal e exibe na tela
	listaDeputadosFederal = repositorio.listarDeputadosFederal();
	// Adaptador da lista customizada para cada linha de um Deputado
	// Federal
	DeputadoFederalListAdapter adapter = new DeputadoFederalListAdapter(
		this, listaDeputadosFederal);
	listViewDados = (ListView) findViewById(R.id.listViewDeputadoFederal);
	listViewDados.setAdapter(adapter);
	listViewDados.setOnItemClickListener(this);
    }

    // Recupera o id do Deputado Federal e abre a tela de edição
    protected void editarDeputadoFederal(int posicao) {
	// usuario clicou em algum Deputado Federal da lista
	// Recupera o Deputado Federal selecionado
	DeputadoFederal v = listaDeputadosFederal.get(posicao);
	// Cria o Intent da tela de editar
	Log.i(CATEGORIA, "Iniciando Intent Editar");
	Intent it = new Intent(this, EditarDeputadoFederal.class);

	// passa o id do Deputado Federal como parametro
	it.putExtra(DeputadoFederalColunas._ID, v.getId());

	Log.i(CATEGORIA, "Abrindo a Tela de Edição");
	// Abre a tela de Edição
	startActivityForResult(it, INSERIR_EDITAR);
	Log.i(CATEGORIA, "Tela de Edição aberta");

    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
	super.onActivityResult(codigo, codigoRetorno, it);
	// Quando a activty EditarDeputadoFederal retornar, seja se foi para
	// editar, atualizar ou excluir
	// Vamos atualizar a lista
	if (codigoRetorno == RESULT_OK) {
	    // Atualiza a lista
	    atualizaLista();
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	// Fecha o banco
	repositorio.Fechar();
	Log.i(CATEGORIA, "Destroiu a Tela de Listagem de Deputado Federal");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	// TODO Auto-generated method stub
	editarDeputadoFederal(position);

    }
}
