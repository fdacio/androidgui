package br.com.daciosoftware.androidgui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.daciosoftware.androidgui.R;

public class FragmentsPropagandaMain extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_fragments_propaganda_main);
	
	Button btnTela2Fragments = (Button) findViewById(R.id.btnTela2Fragments);
	
	btnTela2Fragments.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		Intent it = new Intent(getApplicationContext(),FragmentsPropagandaTela2.class);
		startActivity(it); 
		
	    }
	});
    }
}
