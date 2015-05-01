package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o DeputadoEstadual.
<<<<<<< HEAD
 * Formulario de Busca
=======
>>>>>>> branch 'master' of https://github.com/fdacio/urnaeletronica.git
 * 
 * @author fdacio
 * 
 */
public class BuscarDeputadoEstadual extends Activity implements OnClickListener {
    @Override
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);

	setContentView(R.layout.form_buscar_deputado_estadual);

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

	// Recupera o nome do deputado
	String nomeDeputadoEstadual = nome.getText().toString();
	String numeroDeputadoEstadual = numero.getText().toString();
	DeputadoEstadual d = null;
	if (!(nomeDeputadoEstadual.equals(""))) {
	    // Busca o deputado pelo nome
	    d = buscarDeputadoEstadualNome(nomeDeputadoEstadual);
	}

	if (!(numeroDeputadoEstadual.equals(""))) {
	    // Busca o deputado pelo nome
	    d = buscarDeputadoEstadualNumero(numeroDeputadoEstadual);
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
	    Toast.makeText(BuscarDeputadoEstadual.this,
		    "Nenhum deputado encontrado", Toast.LENGTH_SHORT).show();
	}
    }

    // Busca um deputado pelo nome
    protected DeputadoEstadual buscarDeputadoEstadualNome(String nomeDeputadoEstadual) {
	DeputadoEstadual deputado = CadastroDeputadoEstadual.repositorio
		.buscarDeputadoNome(nomeDeputadoEstadual);
	return deputado;
    }

    protected DeputadoEstadual buscarDeputadoEstadualNumero(String numeroDeputadoEstadual) {
	DeputadoEstadual deputado = CadastroDeputadoEstadual.repositorio
		.buscarDeputadoNumero(numeroDeputadoEstadual);
	return deputado;
    }

}
