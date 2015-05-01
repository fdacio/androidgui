package br.daciosoftware.loterias;

import br.daciosoftware.loterias.Megasena.MegasenaColunas;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * Activity que utiliza o TableLayout para editar o Resultado da Megasena
 * 
 * @author fdacio
 * 
 */
public class MegasenaEditarResultado extends Activity {
	protected static final String CATEGORIA = "loteria";
	static final int RESULT_SALVAR = 3;
	static final int RESULT_EXCLUIR = 4;
	
	// Campos texto
	private EditText edtNumeroConcurso;
	private EditText edtDataConcurso;
	private EditText edtD1;
	private EditText edtD2;
	private EditText edtD3;
	private EditText edtD4;
	private EditText edtD5;
	private EditText edtD6;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_editar_resultado_megasena);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_megasena);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_megasena);


		edtNumeroConcurso = (EditText) findViewById(R.id.edtMegasenaNumeroConscurso);
		edtDataConcurso = (EditText) findViewById(R.id.edtMegasenaDataConcurso);
		edtD1 = (EditText) findViewById(R.id.edtMegasenaD1);
		edtD2 = (EditText) findViewById(R.id.edtMegasenaD2);
		edtD3 = (EditText) findViewById(R.id.edtMegasenaD3);
		edtD4 = (EditText) findViewById(R.id.edtMegasenaD4);
		edtD5 = (EditText) findViewById(R.id.edtMegasenaD5);
		edtD6 = (EditText) findViewById(R.id.edtMegasenaD6);
		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(MegasenaColunas._ID);

			if (id != null) {
				// é uma edição, busca o resutado...
				Megasena m = buscarMegasenaId(id);
				edtNumeroConcurso.setText(String.valueOf(m.getNumeroConcurso()));
				edtDataConcurso.setText(String.valueOf(m.getDataConcurso()));
				edtD1.setText(String.valueOf(m.getD1()));
				edtD2.setText(String.valueOf(m.getD2()));
				edtD3.setText(String.valueOf(m.getD3()));
				edtD4.setText(String.valueOf(m.getD4()));
				edtD5.setText(String.valueOf(m.getD5()));
				edtD6.setText(String.valueOf(m.getD6()));

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
			//btExcluir.setVisibility(View.INVISIBLE);
			btExcluir.setEnabled(false);
		} else {
			// Listener para excluir o resultado
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
		Log.i(CATEGORIA,"Destruiu a Tela de Editar de Resultado Megasena");
	}
	
	@SuppressLint("ParserError")
	public boolean salvar() {
		
		if(!Validador.validateNotNull(edtNumeroConcurso, "Informe o numero do concurso",MegasenaEditarResultado.this)){
			return false;
		}
		
		if(!Validador.validateNotNull(edtDataConcurso, "Informe a data do concurso",MegasenaEditarResultado.this)){
			return false;
		}

		if(!Validador.validateDateFormat(edtDataConcurso,"dd/mm/yyyy","Data do concurso inválida",MegasenaEditarResultado.this)){
			return false;
		}

		if(!Validador.validateNotNull(edtD1, "Informe a 1ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}

		if(!Validador.validateNotNull(edtD2, "Informe a 2ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}
	
		if(!Validador.validateNotNull(edtD3, "Informe a 3ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD4, "Informe a 4ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD5, "Informe a 5ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD6, "Informe a 6ª dezena do concurso",MegasenaEditarResultado.this)){
			return false;
		}

		int numeroconcurso = Integer.parseInt(edtNumeroConcurso.getText().toString());
		String dataconcurso = edtDataConcurso.getText().toString(); 
		int d1 = Integer.parseInt(edtD1.getText().toString());
		int d2 = Integer.parseInt(edtD2.getText().toString());
		int d3 = Integer.parseInt(edtD3.getText().toString());
		int d4 = Integer.parseInt(edtD4.getText().toString());
		int d5 = Integer.parseInt(edtD5.getText().toString());
		int d6 = Integer.parseInt(edtD6.getText().toString());
		
		Megasena m = new Megasena();
		if (id != null) {
			// é uma atualização
			m.setId(id);
		}
		m.setNumeroConcurso(numeroconcurso);
		m.setDataConcurso(dataconcurso);
		m.setD1(d1);
		m.setD2(d2);
		m.setD3(d3);
		m.setD4(d4);
		m.setD5(d5);
		m.setD6(d6);

		// Salvar
		salvarMegasena(m);

		// OK
		setResult(RESULT_OK, new Intent());
		
		

		// Fecha a tela
		finish();
		return true;
	}

	public void excluir() {
		if (id != null) {
			excluirMegasena(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o resultado megasena pelo id
	protected Megasena buscarMegasenaId(long id) {
		return MegasenaListagemResultados.repositorio.buscarMegasenaId(id);
	}

	// Salvar o resultado megasena
	protected void salvarMegasena(Megasena m) {
		MegasenaListagemResultados.repositorio.Salvar(m);
	}

	// Excluir o resultado megasena
	protected void excluirMegasena(long id) {
		MegasenaListagemResultados.repositorio.Deletar(id);
	}
	
}
