package br.com.daciosoftware.meumapa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import br.com.daciosoftware.meumapa.db.Localizacao;
import br.com.daciosoftware.meumapa.db.MeuMapaDatabase;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi")
public class MainActivity extends ActionBarActivity implements LocationListener {

    private Toolbar mToobar;
    //private Toolbar mToobarBottom;
    private MapFragment mapFragment;
    private LocationManager locationManager;
    public GoogleMap mapa;
    private LatLng localizacaoAtual;
    
    public static final int EDITAR = 1; 

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	
	mToobar = (Toolbar) findViewById(R.id.tb_main);
	mToobar.setTitle(R.string.app_name);
	mToobar.setLogo(R.drawable.ic_launcher);
	setSupportActionBar(mToobar);

	/*
	 * Inicializar o banco de dados SQLite
	 */
	MeuMapaDatabase.inicializar(getApplicationContext());
	
	mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,	0, this);

	try{
	    Bundle extras = getIntent().getExtras();
	    if(extras != null){
		Localizacao localizacao = (Localizacao) extras.getSerializable("LOCALIZACAO");
        	goToMap(new LatLng(localizacao.getLatitude(), localizacao.getLongitude()), localizacao.getNomeLocal());
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main_menu, menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

	int id = item.getItemId();

	switch (id) {
	case R.id.action_gravar_local:
	    gravarLocal();
	    return true;
	case R.id.action_registrar_local:
	    registrarLocal();
	    return true;
	case R.id.action_listar_locais:
	    listarLocais();
	    return true;
	case R.id.action_sair:
	    finish();
	    return true;
	}

	return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
	
	localizacaoAtual = new LatLng(location.getLatitude(),location.getLongitude());
	
	goToMap(localizacaoAtual, "Localização Atual");

    }

    @Override
    public void onProviderDisabled(String provide) {
	EnableGPSIfPossible();
    }

    @Override
    public void onProviderEnabled(String provide) {
	
    }

    @Override
    public void onStatusChanged(String provide, int status, Bundle extra) {

    }
    
    
    /**
     * Método para apontar uma localização(latitude e longitude)
     * em Google Map
     * 
     * @param localizacao 
     * @param nomeLocal 
     */

    public void goToMap(LatLng localizacao, String nomeLocal){

	mapa = mapFragment.getMap();
	mapa.setMyLocationEnabled(true);

	MarkerOptions markerOption = new MarkerOptions();

	markerOption.title(nomeLocal);
	markerOption.position(localizacao);

	mapa.addMarker(markerOption);
	mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacao, 15));
	mapa.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	
	locationManager.removeUpdates(this);
	
    }
    @Override
    public void onDestroy() {
	super.onDestroy();
	locationManager.removeUpdates(this);
    }

    @Override
    public void onPause() {
	super.onPause();
	locationManager.removeUpdates(this);
    }

    @Override
    public void onResume() {
	super.onResume();
	
    }
    
    /**
     * Método para habilitar o gps caso esteja desabilitado;
     */
    private void EnableGPSIfPossible() {
	if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
	    buildAlertMessageNoGps();
	}
    }

    /*
     * Método para construir o diálogo para habilitar o GPS
     */
    private void buildAlertMessageNoGps() {
	final AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("O GPS está desabilitado! Deseja habilitá-lo?")
		.setCancelable(false)
		.setPositiveButton("Sim",
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				startActivity(new Intent(
					Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			    }
			})
		.setNegativeButton("Não",
			new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			    }
			});
	final AlertDialog alert = builder.create();
	alert.show();
    }

    private void gravarLocal() {
	if(localizacaoAtual != null){
	    Intent it = new Intent(this,ActivityEditarLocal.class);
	    it.putExtra("LATITUDE", localizacaoAtual.latitude);
	    it.putExtra("LONGITUDE", localizacaoAtual.longitude);
	    startActivity(it);
	}else{
	    Toast.makeText(this, "Aguarde o GPS detectar sua localização atual", Toast.LENGTH_SHORT).show();
	}
    }

    private void registrarLocal() {
	Intent it = new Intent(this,ActivityEditarLocal.class);
	startActivity(it);

    }
 
    private void listarLocais() {
	Intent it = new Intent(this,ListaLocais.class);
	startActivity(it);
	
	
    }
    
}
