package br.com.daciosoftware.suaidade;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
    private Context context = this;
    public static int[] numeroIniciais = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Button btnIniciar = (Button) findViewById(R.id.btnIniciar);
	btnIniciar.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {
		Intent it = new Intent(context, Tabela1Activity.class);
		startActivity(it);
	    }
	});
    }

    /**
     * Ao entrara o estado de espera, zera as dezenas do calculo
     *
     */
    @Override
    protected void onResume() {
	super.onResume();
	zeraNumerosIniciais();
    }

    /**
     * metodo que soma os numeros iniciais das tabelas
     *
     * @return a idade da pessoa
     */
    public static int somaNumerosIniciais() {
	int soma = 0;
	for (int i = 0; i < numeroIniciais.length; i++) {
	    soma += numeroIniciais[i];
	}
	return soma;
    }

    /**
     * Array que represeta os numeros iniciais de 
     * cada tabela a serem somados
     */
    
    public static void zeraNumerosIniciais() {
	numeroIniciais[0] = 0;
	numeroIniciais[1] = 0;
	numeroIniciais[2] = 0;
	numeroIniciais[3] = 0;
	numeroIniciais[4] = 0;
	numeroIniciais[5] = 0;
    }

    public static String mostraNumerosIniciais() {
	String t = "";
	for (int i = 0; i < numeroIniciais.length; i++) {
	    t += "[" + numeroIniciais[i] + "]";
	}
	return t;
    }
}
