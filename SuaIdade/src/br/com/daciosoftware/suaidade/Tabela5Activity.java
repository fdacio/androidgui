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

public class Tabela5Activity extends Activity {

    Context context = this;
    String[] numeros = {"16","17","18","19","20","21",
	    		"22","23","24","25","26","27",
	    		"28","29","30","31","48","49",
	    		"50","51","52","53","54","55",
	    		"56","57","58","59","60"," "};
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
		Intent it = new Intent(context,Tabela6Activity.class);
		startActivity(it);
		MainActivity.numeroIniciais[4] = Integer.parseInt(numeros[0]);
	    }
	});


	Button btnNao = (Button) findViewById(R.id.btnNao);
	btnNao.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		finish();
		Intent it = new Intent(context,Tabela6Activity.class);
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
	MainActivity.numeroIniciais[3] = 0;
	Intent it = new Intent(context,Tabela4Activity.class);
	startActivity(it);
    }
}
