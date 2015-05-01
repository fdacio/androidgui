package br.assistemas.urnaeletronica;

import java.util.List;

import br.assistemas.urnaeletronica.Senador.SenadorColunas;

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

public class CadastroSenador extends Activity implements
	OnItemClickListener {
    protected static final String CATEGORIA = "urna";
    protected static final int INSERIR_EDITAR = 1;
    protected static final int BUSCAR = 2;

    public static RepositorioSenador repositorio;
    private List<Senador> listaSenadores;
    private ListView listViewDados;

    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	repositorio = new RepositorioSenador(this);
	Log.i(CATEGORIA, "Inicializou a Tela de Listagem de Senador");
	setContentView(R.layout.cadastro_senador);
	atualizaLista();
	Button btnNovo = (Button) findViewById(R.id.btnNovo);
	btnNovo.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroSenador.this,
			EditarSenador.class);
		startActivityForResult(it, INSERIR_EDITAR);
		// startActivity(it,INSERIR_EDITAR);
	    }
	});

	Button btnBuscar = (Button) findViewById(R.id.btnBuscar);
	btnBuscar.setOnClickListener(new OnClickListener() {
	    public void onClick(View view) {
		Intent it = new Intent(CadastroSenador.this,
			BuscarSenador.class);
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
	Log.i(CATEGORIA, "Pausou a Tela de Listagem de Senador");
    }

    protected void atualizaLista() {
	// Pega a lista de Senadores e exibe na tela
	listaSenadores = repositorio.listarSenadores();
	// Adaptador da lista customizada para cada linha de um senador
	SenadorListAdapter adapter = new SenadorListAdapter(
		this, listaSenadores);
	listViewDados = (ListView) findViewById(R.id.listViewSenador);
	listViewDados.setAdapter(adapter);
	listViewDados.setOnItemClickListener(this);
    }

    // Recupera o id do senador  e abre a tela de edição
    protected void editarSenador(int posicao) {
	// usuario clicou em algum senador  da lista
	// Recupera o senador Estadual selecionado
	Senador v = listaSenadores.get(posicao);
	// Cria o Intent da tela de editar
	Log.i(CATEGORIA, "Iniciando Intent Editar");
	Intent it = new Intent(this, EditarSenador.class);

	// passa o id do senador  como parametro
	it.putExtra(SenadorColunas._ID, v.getId());

	Log.i(CATEGORIA, "Abrindo a Tela de Edição");
	// Abre a tela de Edição
	startActivityForResult(it, INSERIR_EDITAR);
	Log.i(CATEGORIA, "Tela de Edição aberta");

    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
	super.onActivityResult(codigo, codigoRetorno, it);
	// Quando a activty EditarSenador retornar, seja se foi para
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
	Log.i(CATEGORIA, "Destroiu a Tela de Listagem de Senador");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	// TODO Auto-generated method stub
	editarSenador(position);

    }
}
