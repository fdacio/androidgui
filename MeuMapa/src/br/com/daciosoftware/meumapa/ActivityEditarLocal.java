package br.com.daciosoftware.meumapa;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import br.com.daciosoftware.meumapa.db.Localizacao;
import br.com.daciosoftware.meumapa.db.MeuMapaDatabase;

@SuppressWarnings("deprecation")
public class ActivityEditarLocal extends ActionBarActivity {

    EditText edtLatitude;
    EditText edtLongitude;
    EditText edtNomeLocal;
    
    private double latitude;
    private double longitude;
    private String nomeLocal;
    private Localizacao localizacao;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_editar_local);
	edtLatitude = (EditText) findViewById(R.id.edtLatitude);
	edtLongitude = (EditText) findViewById(R.id.edtLongitude);
	edtNomeLocal = (EditText) findViewById(R.id.edtNomeLocal);
	
	Bundle extras = getIntent().getExtras();
	if(extras != null){
	    this.latitude = extras.getDouble("LATITUDE");
	    this.longitude = extras.getDouble("LONGITUDE");
	    this.localizacao = (Localizacao) extras.getSerializable("LOCALIZACAO");

	    if(this.localizacao != null){
		///localizacao = MeuMapaDatabase.getLocalizacao(this._id);
		edtLatitude.setText(String.valueOf(localizacao.getLatitude()));
		edtLongitude.setText(String.valueOf(localizacao.getLongitude()));
		edtNomeLocal.setText(localizacao.getNomeLocal());
	    }else{
		edtLatitude.setText(String.valueOf(this.latitude));
		edtLongitude.setText(String.valueOf(this.longitude));
	    }
	}
	
	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.menu_editar, menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();

	switch (id) {
	case R.id.action_save:
	    salvarLocal();
	    
	    return true;
	case R.id.action_cancel:
	    finish();
	    return true;
	}

	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
	super.onDestroy();
    }

    @Override
    public void onPause() {
	super.onPause();
    }

    @Override
    public void onResume() {
	super.onResume();
	
    }


    private void salvarLocal() {
	
	if(
	(!edtLatitude.getText().toString().equals(""))&&
	(!edtLongitude.getText().toString().equals(""))&&
	(!edtNomeLocal.getText().toString().equals(""))
	){
	    this.latitude = Double.valueOf(edtLatitude.getText().toString());
	    this.longitude = Double.valueOf(edtLongitude.getText().toString());
	    this.nomeLocal = edtNomeLocal.getText().toString();

	    if(this.localizacao != null){
		try{
		    localizacao.setLatitude(latitude);
		    localizacao.setLongitude(longitude);
		    localizacao.setNomeLocal(nomeLocal);
		    MeuMapaDatabase.updateLocalizacao(localizacao);
		    Toast.makeText(this, "Localização Alterada!", Toast.LENGTH_SHORT).show();
		    finish();
		}catch(Exception e){ 
		    Toast.makeText(this, "Erro ao Alterar! "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}

	    }else{
		this.localizacao = new Localizacao();
        	    
		this.localizacao.setLatitude(this.latitude);
		this.localizacao.setLongitude(this.longitude);
		this.localizacao.setNomeLocal(nomeLocal);
        	    
		try{
		    MeuMapaDatabase.insertLocalizacao(this.localizacao);
		    Toast.makeText(this, "Localização Salvar!", Toast.LENGTH_SHORT).show();
		    finish();
		}catch(Exception e){ 
		    Toast.makeText(this, "Erro ao Salvar! "+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		
	    }
	}else{
	    Toast.makeText(this, "Informe todos os campos para localização!", Toast.LENGTH_SHORT).show();
	}
	
    }

    
}
