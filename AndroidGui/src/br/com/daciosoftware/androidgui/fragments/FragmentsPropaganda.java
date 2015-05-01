package br.com.daciosoftware.androidgui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import br.com.daciosoftware.androidgui.R;

public class FragmentsPropaganda extends Fragment{

    private static final String URL = "http://www.gerentemunicipal.com.br";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
	View view = inflater.inflate(R.layout.activity_fragments_propaganda, container);
        
	Button btnIrPropaganda = (Button) view.findViewById(R.id.btnIrPropaganda);
	
	btnIrPropaganda.setOnClickListener(new OnClickListener(){

	    @Override
	    public void onClick(View v) {
		Uri url = Uri.parse(URL);
	        Intent intent = new Intent(Intent.ACTION_VIEW, url);
	        startActivity(intent);

		
	    }
	    
	});
	
	return view;
	
    }
}
