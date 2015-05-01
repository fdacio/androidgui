package br.com.daciosoftware.suaidade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends Activity {
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_resultado);
	
	TextView textViewIdade = (TextView) findViewById(R.id.textViewIdade);
	
	int soma = MainActivity.somaNumerosIniciais();
	
	if(soma > 0){
	    textViewIdade.setText(soma+" ano(s)");
	}else{
	    textViewIdade.setText("Você tem acima de 60 ano(s)");
	}
	MainActivity.zeraNumerosIniciais();
	
	Button btnReniciar = (Button) findViewById(R.id.btnReniciar);
	btnReniciar.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		finish();
		Intent it = new Intent(context,Tabela1Activity.class);
		startActivity(it);
		
	    }
	});
    }

    @Override
    public void onBackPressed(){
	finish();
    }
}
