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

public class Tabela1Activity extends Activity {
    Context context = this;
    String[] numeros = {"1","3","5","7","9","11",
	    		"13","15","17","19","21","23",
	    		"25","27","29","31","33","35",
	    		"37","39","41","43","45","47",
	    		"49","51","53","55","57","59"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_tabela);

	TableLayout tabela = (TableLayout) findViewById(R.id.tabela);
	tabela.setBackgroundResource(R.drawable.custumerborder2);

	
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
		Intent it = new Intent(context,Tabela2Activity.class);
		startActivity(it);
		MainActivity.numeroIniciais[0]=Integer.parseInt(numeros[0]);
	    }
	});


	Button btnNao = (Button) findViewById(R.id.btnNao);
	btnNao.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		finish();
		Intent it = new Intent(context,Tabela2Activity.class);
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
	//Intent it = new Intent(context,MainActivity.class);
	//startActivity(it);
    }
}
