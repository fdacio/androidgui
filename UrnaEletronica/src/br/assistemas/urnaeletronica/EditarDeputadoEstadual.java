package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.DeputadoEstadual.DeputadoEstadualColunas;
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
 * Activity que utiliza o TableLayout para editar o DeputadoEstadual
 * 
 * @author fdacio
 * 
 */
public class EditarDeputadoEstadual extends Activity {
	protected static final String CATEGORIA = "urna";
	static final int RESULT_SALVAR = 3;
	static final int RESULT_EXCLUIR = 4;
	static final int PESQ_FOTO = 5;

	// Campos texto
	private EditText campoNome;
	private EditText campoNumero;
	//private EditText campoPartido;
	private Spinner campoPartido;
	private EditText campoFoto;
	private Long id;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		setContentView(R.layout.form_editar_deputado_estadual);

		campoNome = (EditText) findViewById(R.id.campoNome);
		campoNumero = (EditText) findViewById(R.id.campoNumero);
		
		//campoPartido = (EditText) findViewById(R.id.campoPartido);
		campoPartido = (Spinner) findViewById(R.id.campoPartido);
		RepositorioPartido repositorio = new RepositorioPartido(this);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,repositorio.listarPartidosSimples());
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		campoPartido.setAdapter(adaptador);

		campoFoto = (EditText) findViewById(R.id.campoFoto);

		id = null;

		Bundle extras = getIntent().getExtras();
		// Se for para Editar, recuperar os valores ...
		if (extras != null) {
			id = extras.getLong(DeputadoEstadualColunas._ID);

			if (id != null) {
				// é uma edição, busca o DeputadoEstadual...
				DeputadoEstadual v = buscarDeputadoEstadual(id);
				campoNome.setText(v.getNome());
				campoNumero.setText(String.valueOf(v.getNumero()));
				
				for(int i = 0; i < campoPartido.getCount(); i++){
					if(campoPartido.getItemAtPosition(i).toString().equals(v.getPartido())){
						campoPartido.setSelection(i);
					}
				}
				
				campoFoto.setText(v.getFoto());
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

		// Listener para salvar o DeputadoEstadual
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
			// Listener para excluir o DeputadoEstadual
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}
	
		
		// Listener para pesquisar foto DeputadoEstadual
		ImageButton btPesqFoto = (ImageButton) findViewById(R.id.btPesqFoto);
		btPesqFoto.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				listarFotos();
			}
		});
		Log.i(CATEGORIA,"Iniciou a Tela de Editar de DeputadoEstadual");
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
		Log.i(CATEGORIA,"Destroiu a Tela de Editar de DeputadoEstadual");
	}
	
	public void salvar() {

		int numero = 0;
		try {
			numero = Integer.parseInt(campoNumero.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		DeputadoEstadual v = new DeputadoEstadual();
		if (id != null) {
			// é uma atualização
			v.setId(id);
		}
		v.setNome(campoNome.getText().toString());
		v.setNumero(numero);
		//v.setPartido(campoPartido.getText().toString());
		v.setPartido(campoPartido.getItemAtPosition(campoPartido.getSelectedItemPosition()).toString());
		v.setFoto(campoFoto.getText().toString());

		// Salvar
		salvarDeputadoEstadual(v);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirDeputadoEstadual(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o DeputadoEstadual pelo id
	protected DeputadoEstadual buscarDeputadoEstadual(long id) {
		return CadastroDeputadoEstadual.repositorio.buscarDeputadoEstadual(id);
	}

	// Salvar o DeputadoEstadual
	protected void salvarDeputadoEstadual(DeputadoEstadual v) {
		CadastroDeputadoEstadual.repositorio.Salvar(v);
	}

	// Excluir o DeputadoEstadual
	protected void excluirDeputadoEstadual(long id) {
		CadastroDeputadoEstadual.repositorio.Deletar(id);
	}
	
	protected void listarFotos(){
		Intent intent = new Intent(Intent.ACTION_PICK);  
		intent.setType("image/*");
		//("image/*");  
		startActivityForResult(intent, PESQ_FOTO);
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
    	Log.i("FOTO","Nao seleciounou foto");
   }  	
}
