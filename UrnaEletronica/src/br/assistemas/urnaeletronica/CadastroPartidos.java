package br.assistemas.urnaeletronica;

import java.util.List;

import br.assistemas.urnaeletronica.Partidos.PartidoColunas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CadastroPartidos extends Activity implements OnItemClickListener {
    protected static final String CATEGORIA = "urna";
    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;

    public static RepositorioPartido repositorio;
    private List<Partidos> listaPartidos;
    private ListView listViewDados;

    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	repositorio = new RepositorioPartido(this);
	Log.i(CATEGORIA, "Inicializou a Tela de Listagem de Partidos");
	setContentView(R.layout.cadastro_partidos);
	atualizaLista();
	Button btnNovo = (Button) findViewById(R.id.btnNovo);
	btnNovo.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroPartidos.this,
			EditarPartidos.class);
		startActivityForResult(it, INSERIR_EDITAR);
		// startActivity(it,INSERIR_EDITAR);
	    }
	});

	Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
	btnBuscar.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroPartidos.this,
			BuscarPartidos.class);
		startActivityForResult(it, INSERIR_EDITAR);
		// startActivity(it,INSERIR_EDITAR);
	    }
	});

	Button btnFechar = (Button) findViewById(R.id.btnFechar);
	btnFechar.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		// Fecha a tela
		finish();

	    }
	});
    }

    @Override
    protected void onPause() {
	super.onPause();
	Log.i(CATEGORIA, "Pausou a Tela de Listagem de Partidos");
    }

    protected void atualizaLista() {
	// Pega a lista de partidos e exibe na tela
	listaPartidos = repositorio.listarPartidos();
	// Adaptador da lista customizada para cada linha de um partido

	PartidosListAdapter adapter = new PartidosListAdapter(this,
		listaPartidos);
	listViewDados = (ListView) findViewById(R.id.listViewPartidos);
	listViewDados.setAdapter(adapter);
	listViewDados.setOnItemClickListener(this);
	Log.i(CATEGORIA, "Atualizou a Listagem de Partidos");
    }

    // Recupera o id do partidos e abre a tela de edição
    protected void editarPartidos(int posicao) {
	Partidos p = listaPartidos.get(posicao);
	Log.i(CATEGORIA, "Iniciando Intent Editar");
	Intent it = new Intent(this, EditarPartidos.class);
	it.putExtra(PartidoColunas._ID, p.getId());
	Log.i(CATEGORIA, "Abrindo a Tela de Edição de Partido");
	startActivityForResult(it, INSERIR_EDITAR);
	Log.i(CATEGORIA, "Tela de Edição de Partido aberta");
    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
	super.onActivityResult(codigo, codigoRetorno, it);
	// Quando a activty EditarVereador retornar, seja se foi para editar,
	// atualizar ou excluir
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
	Log.i(CATEGORIA, "Destroiu a Tela de Listagem de Partido");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	editarPartidos(position);

    }
}
