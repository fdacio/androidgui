package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o Senador.
 * 
 * @author fdacio
 * 
 */
public class BuscarSenador extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);

	setContentView(R.layout.form_buscar_senador);

	ImageButton btBuscar = (ImageButton) findViewById(R.id.btBuscar);
	btBuscar.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
	super.onPause();
	// Cancela para nao ficar nada pendente na tela
	setResult(RESULT_CANCELED);

	// Fecha a tela
	finish();
    }

    /**
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    public void onClick(View view) {

	EditText nome = (EditText) findViewById(R.id.campoNome);
	EditText numero = (EditText) findViewById(R.id.campoNumero);
	EditText partido = (EditText) findViewById(R.id.campoPartido);
	EditText votos = (EditText) findViewById(R.id.campoVotos);

	// Recupera o nome do senador
	String nomeSenador = nome.getText().toString();
	String numeroSenador = numero.getText().toString();
	Senador v = null;
	if (!(nomeSenador.equals(""))) {
	    // Busca o senador pelo nome
	    v = buscarSenadorNome(nomeSenador);
	}

	if (!(numeroSenador.equals(""))) {
	    // Busca o senador pelo nome
	    v = buscarSenadorNumero(numeroSenador);
	}

	if (v != null) {
	    // Atualiza os campos com o resultado
	    nome.setText(v.getNome());
	    numero.setText(String.valueOf(v.getNumero()));
	    partido.setText(v.getPartido());
	    votos.setText(String.valueOf(v.getVotos()));
	} else {
	    // Limpa os campos
	    nome.setText("");
	    numero.setText("");
	    partido.setText("");
	    votos.setText("");
	    Toast.makeText(BuscarSenador.this,
		    "Nenhum senador encontrado", Toast.LENGTH_SHORT).show();
	}
    }

    // Busca um senador pelo nome
    protected Senador buscarSenadorNome(String nomeSenador) {
	Senador senador = CadastroSenador.repositorio
		.buscarSenadorNome(nomeSenador);
	return senador;
    }

    protected Senador buscarSenadorNumero(String numeroSenador) {
	Senador senador = CadastroSenador.repositorio
		.buscarSenadorNumero(numeroSenador);
	return senador;
    }

}
