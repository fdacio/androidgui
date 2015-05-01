package br.com.daciosoftware.androidgui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MapaSimples2 extends Activity {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_mapa_simples2);
	map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	
	map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
	
	map.addMarker(new MarkerOptions()
		.position(KIEL)
		.title("Kiel")
		.snippet("Kiel is cool")
		.icon(BitmapDescriptorFactory
			.fromResource(R.drawable.ic_launcher)));

	// Move the camera instantly to hamburg with a zoom of 15.
	map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

	// Zoom in, animating the camera.
	map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
}
