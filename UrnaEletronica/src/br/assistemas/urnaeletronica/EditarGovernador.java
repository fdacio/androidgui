package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.Governador.GovernadorColunas;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

/**
 * Activity que utiliza o TableLayout para editar o Vereador
 * 
 * @author fdacio
 * 
 */
public class EditarGovernador extends Activity {
	protected static final String CATEGORIA = "urna";
	static final int RESULT_SALVAR = 3;
	static final int RESULT_EXCLUIR = 4;
	static final int PESQ_FOTO = 5;
	static final int PESQ_FOTO_VICE = 6;

	// Campos texto
	private EditText campoNome;
	private EditText campoNumero;
	private Spinner campoPartido;
	private EditText campoFoto;
	private EditText campoNomeVice;
	private EditText campoFotoVice;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_editar_governador);

		campoNome = (EditText) findViewById(R.id.campoNome);
		campoNumero = (EditText) findViewById(R.id.campoNumero);
		
		//campoPartido = (EditText) findViewById(R.id.campoPartido);
		campoPartido = (Spinner) findViewById(R.id.campoPartido);
		RepositorioPartido repositorio = new RepositorioPartido(this);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,repositorio.listarPartidosSimples());
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		campoPartido.setAdapter(adaptador);

		campoFoto = (EditText) findViewById(R.id.campoFoto);
		campoNomeVice = (EditText) findViewById(R.id.campoNomeVice);
		campoFotoVice = (EditText) findViewById(R.id.campoFotoVice);
		campoFoto = (EditText) findViewById(R.id.campoFoto);

		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(GovernadorColunas._ID);

			if (id != null) {
				// é uma edição, busca o Governador...
				Governador p = buscarGovernador(id);
				campoNome.setText(p.getNome());
				campoNumero.setText(String.valueOf(p.getNumero()));
				
				for(int i = 0; i < campoPartido.getCount(); i++){
					if(campoPartido.getItemAtPosition(i).toString().equals(p.getPartido())){
						campoPartido.setSelection(i);
					}
				}
				
				campoFoto.setText(p.getFoto());
				campoNomeVice.setText(p.getNomeViceGovernador());
				campoFotoVice.setText(p.getFotoViceGovernador());
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
	
		
		// Listener para pesquisar foto Governador
		ImageButton btPesqFoto = (ImageButton) findViewById(R.id.btPesqFoto);
		btPesqFoto.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				listarFotos();
			}
		});
		
		// Listener para pesquisar foto Governador
		ImageButton btPesqFotoVice = (ImageButton) findViewById(R.id.btPesqFotoVice);
		btPesqFotoVice.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				listarFotosVice();
			}
		});

		
		Log.i(CATEGORIA,"Iniciou a Tela de Editar de Governador");
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Cancela para nao ficar nada na tela pendente
		setResult(RESULT_CANCELED);
		// Fecha a tela
		//finish();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		Log.i(CATEGORIA,"Destroiu a Tela de Editar de Vereador");
	}
	
	public void salvar() {

		int numero = 0;
		try {
			numero = Integer.parseInt(campoNumero.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		Governador p = new Governador();
		if (id != null) {
			// é uma atualização
			p.setId(id);
		}
		p.setNome(campoNome.getText().toString());
		p.setNumero(numero);
		//v.setPartido(campoPartido.getText().toString());
		p.setPartido(campoPartido.getItemAtPosition(campoPartido.getSelectedItemPosition()).toString());
		p.setFoto(campoFoto.getText().toString());
		p.setNomeViceGovernador(campoNomeVice.getText().toString());
		p.setFotoViceGovernador(campoFotoVice.getText().toString());
		// Salvar
		salvarGovernador(p);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirGovernador(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o governador pelo id
	protected Governador buscarGovernador(long id) {
		return CadastroGovernador.repositorio.buscarGovernador(id);
	}

	// Salvar o governador
	protected void salvarGovernador(Governador p) {
		CadastroGovernador.repositorio.Salvar(p);
	}

	// Excluir o governador
	protected void excluirGovernador(long id) {
		CadastroGovernador.repositorio.Deletar(id);
	}
	
	protected void listarFotos(){
		Intent intent = new Intent(Intent.ACTION_PICK);  
		intent.setType("image/*");
		//("image/*");  
		startActivityForResult(intent, PESQ_FOTO);
	}

	protected void listarFotosVice(){
		Intent intent = new Intent(Intent.ACTION_PICK);  
		intent.setType("image/*");
		//("image/*");  
		startActivityForResult(intent, PESQ_FOTO_VICE);
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
    	super.onActivityResult(requestCode, resultCode, data);  
    	if (resultCode == RESULT_OK && requestCode == PESQ_FOTO) {
    		Uri selectedImage = data.getData();  
    		String[] filePathColumn = {MediaStore.Images.Media.DATA };  
    		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);  
    		cursor.moveToFirst();  
    		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
    		String filePath = cursor.getString(columnIndex);
    		Log.i("FOTO",filePath);
    		
    		campoFoto.setText(filePath);
    		cursor.close();
    	}
    	
    	if (resultCode == RESULT_OK && requestCode == PESQ_FOTO_VICE) {
    		Uri selectedImage = data.getData();  
    		String[] filePathColumn = {MediaStore.Images.Media.DATA };  
    		Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);  
    		cursor.moveToFirst();  
    		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
    		String filePath = cursor.getString(columnIndex);
    		Log.i("FOTO",filePath);
    		
    		campoFotoVice.setText(filePath);
    		cursor.close();
    	}
    	
   }  	


}
