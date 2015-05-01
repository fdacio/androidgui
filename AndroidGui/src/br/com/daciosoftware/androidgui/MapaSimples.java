package br.com.daciosoftware.androidgui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapaSimples extends Activity implements OnMapReadyCallback {
    
    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mapa_simples);

	MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
	mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

	map.setMyLocationEnabled(true);
	map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 13));

	map.addMarker(new MarkerOptions().title("Hamburgo")
		.snippet("Cidade de Hamburgo.")
		.position(HAMBURG));
	
    }

}