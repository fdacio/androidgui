package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o DeputadoFederal.
 * 
 * @author fdacio
 * 
 */
public class BuscarDeputadoFederal extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);

	setContentView(R.layout.form_buscar_deputado_federal);

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

	// Recupera o nome do deputadofederal
	String nomeDeputadoFederal = nome.getText().toString();
	String numeroDeputadoFederal = numero.getText().toString();
	DeputadoFederal d = null;
	if (!(nomeDeputadoFederal.equals(""))) {
	    // Busca o deputadofederal pelo nome
	    d = buscarDeputadoFederalNome(nomeDeputadoFederal);
	}

	if (!(numeroDeputadoFederal.equals(""))) {
	    // Busca o deputadofederal pelo nome
	    d = buscarDeputadoFederalNumero(numeroDeputadoFederal);
	}

	if (d != null) {
	    // Atualiza os campos com o resultado
	    nome.setText(d.getNome());
	    numero.setText(String.valueOf(d.getNumero()));
	    partido.setText(d.getPartido());
	    votos.setText(String.valueOf(d.getVotos()));
	} else {
	    // Limpa os campos
	    nome.setText("");
	    numero.setText("");
	    partido.setText("");
	    votos.setText("");
	    Toast.makeText(BuscarDeputadoFederal.this,
		    "Nenhum Deputado Federal encontrado", Toast.LENGTH_SHORT).show();
	}
    }

    // Busca um deputadofederal pelo nome
    protected DeputadoFederal buscarDeputadoFederalNome(String nomeDeputadoFederal) {
	DeputadoFederal deputadofederal = CadastroDeputadoFederal.repositorio
		.buscarDeputadoNome(nomeDeputadoFederal);
	return deputadofederal;
    }

    protected DeputadoFederal buscarDeputadoFederalNumero(String numeroDeputadoFederal) {
	DeputadoFederal deputadofederal = CadastroDeputadoFederal.repositorio
		.buscarDeputadoNumero(numeroDeputadoFederal);
	return deputadofederal;
    }

}
