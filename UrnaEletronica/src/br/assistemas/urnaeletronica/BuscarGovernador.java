package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o Governador.
 * 
 * @author fdacio
 * 
 */
public class BuscarGovernador extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_buscar_governador);

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
		EditText nomeVice = (EditText) findViewById(R.id.campoNomeVice);
		EditText votos = (EditText) findViewById(R.id.campoVotos);

		// Recupera o nome do Governador
		String nomeGovernador = nome.getText().toString();
		String numeroGovernador = numero.getText().toString();
		Governador p = null;
		if(!(nomeGovernador.equals(""))){
			// Busca o Governador pelo nome
			p = buscarGovernadorNome(nomeGovernador);
		}	

		if(!(numeroGovernador.equals(""))){
			// Busca o vereador pelo nome
			p = buscarGovernadorNumero(numeroGovernador);
		}	
        
		if (p != null) {
			// Atualiza os campos com o resultado
			nome.setText(p.getNome());
			numero.setText(String.valueOf(p.getNumero()));
			partido.setText(p.getPartido());
			nomeVice.setText(p.getNomeViceGovernador());
			votos.setText(String.valueOf(p.getVotos()));
		} else {
			// Limpa os campos
			nome.setText("");
			numero.setText("");
			partido.setText("");
			nomeVice.setText("");
			votos.setText("");
			Toast.makeText(BuscarGovernador.this, "Nenhum Governador encontrado", Toast.LENGTH_SHORT).show();
		}
	}

	// Busca um vereador pelo nome
	protected Governador buscarGovernadorNome(String nomeGovernador) {
		Governador governador = CadastroGovernador.repositorio.buscarGovernadorNome(nomeGovernador);
		return governador;
	}
	protected Governador buscarGovernadorNumero(String numeroGovernador) {
		Governador governador = CadastroGovernador.repositorio.buscarGovernadorNumero(numeroGovernador);
		return governador;
	}
	
}
