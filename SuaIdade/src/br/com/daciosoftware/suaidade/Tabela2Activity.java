package br.com.daciosoftware.suaidade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class Tabela2Activity extends Activity {
    Context context = this;
    String[] numeros = {"2","3","6","7","10","11",
	    		"14","15","18","19","22","23",
	    		"26","27","30","31","34","35",
	    		"38","39","42","43","46","47",
	    		"50","51","54","55","58","59"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_tabela);

	TableLayout tabela = (TableLayout) findViewById(R.id.tabela);
	tabela.setBackgroundResource(R.drawable.custumerborder3);
	
	for(int i = 0; i<30; i++){
	    String nameView = "textView"+(i+1);
	    int id = getResources().getIdentifier(nameView, "id", getPackageName());
	    TextView textViewNumero = (TextView) findViewById(id);
	    textViewNumero.setText(numeros[i].toString());
	}
	
	Button btnSim = (Button) findViewById(R.id.btnSim);
	btnSim.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		finish();
		Intent it = new Intent(context,Tabela3Activity.class);
		startActivity(it);
		MainActivity.numeroIniciais[1]=Integer.parseInt(numeros[0]);
	    }
	});


	Button btnNao = (Button) findViewById(R.id.btnNao);
	btnNao.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		finish();
		Intent it = new Intent(context,Tabela3Activity.class);
		startActivity(it);
	    }
	});
	
	Button btnVoltar = (Button) findViewById(R.id.btnVoltar);
	btnVoltar.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		goBack();
	    }
	});
    
    }

    @Override
    public void onBackPressed() {
	goBack();
    }
    
    private void goBack(){
	finish();
	MainActivity.numeroIniciais[0] = 0;
	Intent it = new Intent(context,Tabela1Activity.class);
	startActivity(it);
    }
    
     
}
