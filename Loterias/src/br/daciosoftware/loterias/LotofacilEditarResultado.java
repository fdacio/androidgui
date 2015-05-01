package br.daciosoftware.loterias;

import br.daciosoftware.loterias.Lotofacil.LotofacilColunas;
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
 * Activity que utiliza o TableLayout para editar o Resultado da Lotofacil
 * 
 * @author fdacio
 * 
 */
public class LotofacilEditarResultado extends Activity {
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
	private EditText edtD7;
	private EditText edtD8;
	private EditText edtD9;
	private EditText edtD10;
	private EditText edtD11;
	private EditText edtD12;
	private EditText edtD13;
	private EditText edtD14;
	private EditText edtD15;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.activity_editar_resultado_lotofacil);
		LinearLayout tela = (LinearLayout) findViewById(R.id.LinearLayoutBackground);
		tela.setBackgroundResource(R.drawable.background_lotofacil);
		ImageView imgHeader = (ImageView) findViewById(R.id.ImageViewHeader);
		imgHeader.setBackgroundResource(R.drawable.header_lotofacil);


		edtNumeroConcurso = (EditText) findViewById(R.id.edtLotofacilNumeroConscurso);
		edtDataConcurso = (EditText) findViewById(R.id.edtLotofacilDataConcurso);
		edtD1 = (EditText) findViewById(R.id.edtLotofacilD1);
		edtD2 = (EditText) findViewById(R.id.edtLotofacilD2);
		edtD3 = (EditText) findViewById(R.id.edtLotofacilD3);
		edtD4 = (EditText) findViewById(R.id.edtLotofacilD4);
		edtD5 = (EditText) findViewById(R.id.edtLotofacilD5);
		edtD6 = (EditText) findViewById(R.id.edtLotofacilD6);
		edtD7 = (EditText) findViewById(R.id.edtLotofacilD7);
		edtD8 = (EditText) findViewById(R.id.edtLotofacilD8);
		edtD9 = (EditText) findViewById(R.id.edtLotofacilD9);
		edtD10 = (EditText) findViewById(R.id.edtLotofacilD10);
		edtD11 = (EditText) findViewById(R.id.edtLotofacilD11);
		edtD12 = (EditText) findViewById(R.id.edtLotofacilD12);
		edtD13 = (EditText) findViewById(R.id.edtLotofacilD13);
		edtD14 = (EditText) findViewById(R.id.edtLotofacilD14);
		edtD15 = (EditText) findViewById(R.id.edtLotofacilD15);
		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(LotofacilColunas._ID);

			if (id != null) {
				// é uma edição, busca o resutado...
				Lotofacil l = buscarLotofacilId(id);
				edtNumeroConcurso.setText(String.valueOf(l.getNumeroConcurso()));
				edtDataConcurso.setText(String.valueOf(l.getDataConcurso()));
				edtD1.setText(String.valueOf(l.getD1()));
				edtD2.setText(String.valueOf(l.getD2()));
				edtD3.setText(String.valueOf(l.getD3()));
				edtD4.setText(String.valueOf(l.getD4()));
				edtD5.setText(String.valueOf(l.getD5()));
				edtD6.setText(String.valueOf(l.getD6()));
				edtD7.setText(String.valueOf(l.getD7()));
				edtD8.setText(String.valueOf(l.getD8()));
				edtD9.setText(String.valueOf(l.getD9()));
				edtD10.setText(String.valueOf(l.getD10()));
				edtD11.setText(String.valueOf(l.getD11()));
				edtD12.setText(String.valueOf(l.getD12()));
				edtD13.setText(String.valueOf(l.getD13()));
				edtD14.setText(String.valueOf(l.getD14()));
				edtD15.setText(String.valueOf(l.getD15()));

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
		Log.i(CATEGORIA,"Destruiu a Tela de Editar de Resultado Lotofacil");
	}
	
	@SuppressLint("ParserError")
	public boolean salvar() {
		
		if(!Validador.validateNotNull(edtNumeroConcurso, "Informe o numero do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		
		if(!Validador.validateNotNull(edtDataConcurso, "Informe a data do concurso",LotofacilEditarResultado.this)){
			return false;
		}

		if(!Validador.validateDateFormat(edtDataConcurso,"dd/mm/yyyy","Data do concurso inválida",LotofacilEditarResultado.this)){
			return false;
		}

		if(!Validador.validateNotNull(edtD1, "Informe a 1ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}

		if(!Validador.validateNotNull(edtD2, "Informe a 2ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
	
		if(!Validador.validateNotNull(edtD3, "Informe a 3ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD4, "Informe a 4ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD5, "Informe a 5ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD6, "Informe a 6ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD7, "Informe a 7ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD8, "Informe a 8ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD9, "Informe a 9ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD10, "Informe a 10ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD11, "Informe a 11ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD12, "Informe a 12ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD13, "Informe a 13ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD14, "Informe a 14ª dezena do concurso",LotofacilEditarResultado.this)){
			return false;
		}
		if(!Validador.validateNotNull(edtD15, "Informe a 15ª dezena do concurso",LotofacilEditarResultado.this)){
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
		int d7 = Integer.parseInt(edtD7.getText().toString());
		int d8 = Integer.parseInt(edtD8.getText().toString());
		int d9 = Integer.parseInt(edtD9.getText().toString());
		int d10 = Integer.parseInt(edtD10.getText().toString());
		int d11 = Integer.parseInt(edtD11.getText().toString());
		int d12 = Integer.parseInt(edtD12.getText().toString());
		int d13 = Integer.parseInt(edtD13.getText().toString());
		int d14 = Integer.parseInt(edtD14.getText().toString());
		int d15 = Integer.parseInt(edtD15.getText().toString());
		
		Lotofacil l = new Lotofacil();
		if (id != null) {
			// é uma atualização
			l.setId(id);
		}
		l.setNumeroConcurso(numeroconcurso);
		l.setDataConcurso(dataconcurso);
		l.setD1(d1);
		l.setD2(d2);
		l.setD3(d3);
		l.setD4(d4);
		l.setD5(d5);
		l.setD6(d6);
		l.setD7(d7);
		l.setD8(d8);
		l.setD9(d9);
		l.setD10(d10);
		l.setD11(d11);
		l.setD12(d12);
		l.setD13(d13);
		l.setD14(d14);
		l.setD15(d15);

		// Salvar
		salvarLotofacil(l);

		// OK
		setResult(RESULT_OK, new Intent());
		
		

		// Fecha a tela
		finish();
		return true;
	}

	public void excluir() {
		if (id != null) {
			excluirLotofacil(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o resultado lotofacil pelo id
	protected Lotofacil buscarLotofacilId(long id) {
		return LotofacilListagemResultados.repositorio.buscarLotofacilId(id);
	}

	// Salvar o resultado lotofacil
	protected void salvarLotofacil(Lotofacil l) {
		LotofacilListagemResultados.repositorio.Salvar(l);
	}

	// Excluir o resultado lotofacil
	protected void excluirLotofacil(long id) {
		LotofacilListagemResultados.repositorio.Deletar(id);
	}
	
}
