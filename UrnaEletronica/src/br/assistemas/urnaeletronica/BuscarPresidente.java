package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o Presidente.
 * 
 * @author fdacio
 * 
 */
public class BuscarPresidente extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_buscar_presidente);

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

		// Recupera o nome do Presidente
		String nomePresidente = nome.getText().toString();
		String numeroPresidente = numero.getText().toString();
		Presidente p = null;
		if(!(nomePresidente.equals(""))){
			// Busca o Presidente pelo nome
			p = buscarPresidenteNome(nomePresidente);
		}	

		if(!(numeroPresidente.equals(""))){
			// Busca o vereador pelo nome
			p = buscarPresidenteNumero(numeroPresidente);
		}	
        
		if (p != null) {
			// Atualiza os campos com o resultado
			nome.setText(p.getNome());
			numero.setText(String.valueOf(p.getNumero()));
			partido.setText(p.getPartido());
			nomeVice.setText(p.getNomeVicePresidente());
			votos.setText(String.valueOf(p.getVotos()));
		} else {
			// Limpa os campos
			nome.setText("");
			numero.setText("");
			partido.setText("");
			nomeVice.setText("");
			votos.setText("");
			Toast.makeText(BuscarPresidente.this, "Nenhum Presidente encontrado", Toast.LENGTH_SHORT).show();
		}
	}

	// Busca um vereador pelo nome
	protected Presidente buscarPresidenteNome(String nomePresidente) {
		Presidente presidente = CadastroPresidente.repositorio.buscarPresidenteNome(nomePresidente);
		return presidente;
	}
	protected Presidente buscarPresidenteNumero(String numeroPresidente) {
		Presidente presidente = CadastroPresidente.repositorio.buscarPresidenteNumero(numeroPresidente);
		return presidente;
	}
	
}
