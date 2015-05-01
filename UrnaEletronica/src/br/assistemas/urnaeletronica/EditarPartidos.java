package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.Partidos.PartidoColunas;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;


/**
 * Activity que utiliza o TableLayout para editar o Vereador
 * 
 * @author fdacio
 * 
 */
public class EditarPartidos extends Activity {
	protected static final String CATEGORIA = "urna";
	static final int RESULT_SALVAR = 3;
	static final int RESULT_EXCLUIR = 4;
	
	// Campos texto
	private EditText campoNome;
	private EditText campoNumero;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_editar_partidos);

		campoNome = (EditText) findViewById(R.id.campoNome);
		campoNumero = (EditText) findViewById(R.id.campoNumero);
		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(PartidoColunas._ID);

			if (id != null) {
				// é uma edição, busca o Partido...
				Partidos p = buscarPartidos(id);
				campoNome.setText(p.getNome());
				campoNumero.setText(String.valueOf(p.getNumero()));

			}
		}

		Button btCancelar = (Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				setResult(RESULT_CANCELED);
				//Fecha a tela
				finish();
			}
		});

		// Listener para salvar o Vereador
		Button btSalvar = (Button) findViewById(R.id.btSalvar);
		btSalvar.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				salvar();
			}
		});

		Button btExcluir = (Button) findViewById(R.id.btExcluir);

		if (id == null) {
			// Se id está nulo, não pode excluir
			btExcluir.setVisibility(View.INVISIBLE);
		} else {
			// Listener para excluir o Vereador
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}
	
		Log.i(CATEGORIA,"Iniciou a Tela de Editar de Partidos");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para nao ficar nada na tela pendente
		setResult(RESULT_CANCELED);
		// Fecha a tela
		finish();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(CATEGORIA,"Destroiu a Tela de Editar de Partidos");
	}
	
	public void salvar() {

		int numero = 0;
		try {
			numero = Integer.parseInt(campoNumero.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		Partidos p = new Partidos();
		if (id != null) {
			// é uma atualização
			p.setId(id);
		}
		p.setNome(campoNome.getText().toString());
		p.setNumero(numero);

		// Salvar
		salvarPartidos(p);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirPartidos(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o vereador pelo id
	protected Partidos buscarPartidos(long id) {
		return CadastroPartidos.repositorio.buscarPartido(id);
	}

	// Salvar o Vereador
	protected void salvarPartidos(Partidos p) {
		CadastroPartidos.repositorio.Salvar(p);
	}

	// Excluir o Vereador
	protected void excluirPartidos(long id) {
		CadastroPartidos.repositorio.Deletar(id);
	}
	
}
