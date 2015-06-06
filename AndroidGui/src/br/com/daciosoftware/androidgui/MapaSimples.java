package br.com.daciosoftware.androidgui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapaSimples extends Activity implements LocationListener {
    
    private LocationManager locationManager;
    private GoogleMap mapa;
    MapFragment mapFragment;
    private EditText edtLatitude;
    private EditText edtLongitude;
    private double valorLatitude;
    private double valorLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mapa_simples);
	
	edtLatitude = (EditText) findViewById(R.id.edtLatitude);
	edtLongitude = (EditText) findViewById(R.id.edtLongitude);
	mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
			
	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    

    
    @Override
    public void onLocationChanged(Location location) {
	valorLatitude = location.getLatitude();
	valorLongitude = location.getLongitude();
	edtLongitude.setText(String.valueOf(valorLongitude));
	edtLatitude.setText(String.valueOf(valorLatitude));
	
	mapa = mapFragment.getMap();
	mapa.setMyLocationEnabled(true);
	LatLng localizacaoAtual = new LatLng(valorLatitude, valorLongitude);
	MarkerOptions markerOption = new MarkerOptions();
	
	markerOption.title("LOCALIZAÇÃO ATUAL");
	markerOption.position(localizacaoAtual);
	
	mapa.addMarker(markerOption);
	mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(localizacaoAtual, 15));
	
    }
    
    
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
	Toast.makeText(this, "Status de "+provider+": "+status, Toast.LENGTH_SHORT).show();
	
    }

    @Override
    public void onProviderEnabled(String provider) {
	Toast.makeText(this, provider+": Habilitado", Toast.LENGTH_SHORT).show();
	
    }

    @Override
    public void onProviderDisabled(String provider) {
	Toast.makeText(this, provider+": Desabilitado", Toast.LENGTH_SHORT).show();
	
    }

    

}