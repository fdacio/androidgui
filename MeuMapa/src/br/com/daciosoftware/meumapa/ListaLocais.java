package br.com.daciosoftware.meumapa;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.daciosoftware.meumapa.db.Localizacao;
import br.com.daciosoftware.meumapa.db.MeuMapaDatabase;

@SuppressWarnings("deprecation")
public class ListaLocais extends ActionBarActivity implements
	OnItemClickListener {

    private ListView listView;
    private List<Localizacao> localizacoes;
    private String nomeLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_lista_locais);
	//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	setNomeLocal("");
	atualizaLista();
    }

    protected void atualizaLista() {
	localizacoes = MeuMapaDatabase.carregarLocalizacoes(this.nomeLocal);
	ListaLocaisAdapter adapter = new ListaLocaisAdapter(this, localizacoes);
	listView = (ListView) findViewById(R.id.listViewLocais);
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu_listagem, menu);

	
	MenuItem searchItem = menu.findItem(R.id.action_search);
	SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
	searchView.setOnQueryTextListener(new OnQueryTextListener() {
	    
	    @Override
	    public boolean onQueryTextSubmit(String nomeLocal) {
		setNomeLocal(nomeLocal);
		atualizaLista();
		setNomeLocal("");
		return false;
	    }
	    
	    @Override
	    public boolean onQueryTextChange(String arg0) {
		// TODO Auto-generated method stub
		return false;
	    }
	});
	return super.onCreateOptionsMenu(menu);
    }
    
    private void setNomeLocal(String nomeLocal){
	this.nomeLocal = nomeLocal;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();

	switch (id) {
	case R.id.action_search:
	    //Toast.makeText(getApplicationContext(), "Aqui Implementar Consulta", Toast.LENGTH_SHORT).show();
	    return true;
	case R.id.action_reload:
	    atualizaLista();
	    return true;
	}

	return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	atualizaLista();

    }

    @Override
    protected void onActivityResult(int codigo, int codigoRetorno, Intent it) {
	super.onActivityResult(codigo, codigoRetorno, it);
	if (codigoRetorno == RESULT_OK) {
	    atualizaLista();
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	MeuMapaDatabase.encerrarSessao();
    }
    
    @Override
    public void onPause(){
	super.onPause();
    }

    @Override
    public void onResume(){
	super.onResume();
    }
}
