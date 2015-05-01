package br.assistemas.urnaeletronica;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Buscar o Vereador.
 * 
 * @author fdacio
 * 
 */
public class BuscarPartidos extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_buscar_partidos);

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
		EditText votos = (EditText) findViewById(R.id.campoVotos);

		// Recupera o nome do vereador
		String nomePartido = nome.getText().toString();
		String numeroPartido = numero.getText().toString();
		Partidos p = null;
		if(!(nomePartido.equals(""))){
			// Busca o partido pelo nome
			p = buscarPartidosNome(nomePartido);
		}	

		if(!(numeroPartido.equals(""))){
			// Busca o partido pelo numero
			p = buscarPartidoNumero(numeroPartido);
		}	
        
		if (p != null) {
			// Atualiza os campos com o resultado
			nome.setText(p.getNome());
			numero.setText(String.valueOf(p.getNumero()));
			votos.setText(String.valueOf(p.getVotos()));
		} else {
			// Limpa os campos
			nome.setText("");
			numero.setText("");
			votos.setText("");
			Toast.makeText(BuscarPartidos.this, "Nenhum partido encontrado", Toast.LENGTH_SHORT).show();
		}
	}

	// Busca um partido pelo nome
	protected Partidos buscarPartidosNome(String nomePartido) {
		Partidos partido = CadastroPartidos.repositorio.buscarPartidoNome(nomePartido);
		return partido;
	}
	protected Partidos buscarPartidoNumero(String numeroPartido) {
		Partidos partido = CadastroPartidos.repositorio.buscarPartidoNumero(numeroPartido);
		return partido;
	}
	
}
