package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.Senador.SenadorColunas;
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
 * Activity que utiliza o TableLayout para editar o Senador
 * 
 * @author fdacio
 * 
 */
public class EditarSenador extends Activity {
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

		setContentView(R.layout.form_editar_senador);

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
			id = extras.getLong(SenadorColunas._ID);

			if (id != null) {
				// é uma edição, busca o Senador...
				Senador s = buscarSenador(id);
				campoNome.setText(s.getNome());
				campoNumero.setText(String.valueOf(s.getNumero()));
				
				for(int i = 0; i < campoPartido.getCount(); i++){
					if(campoPartido.getItemAtPosition(i).toString().equals(s.getPartido())){
						campoPartido.setSelection(i);
					}
				}
				
				campoFoto.setText(s.getFoto());
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

		// Listener para salvar o Senador
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
			// Listener para excluir o Senador
			btExcluir.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					excluir();
				}
			});
		}
	
		
		// Listener para pesquisar foto Senador
		ImageButton btPesqFoto = (ImageButton) findViewById(R.id.btPesqFoto);
		btPesqFoto.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				listarFotos();
			}
		});
		Log.i(CATEGORIA,"Iniciou a Tela de Editar de Senador");
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
		Log.i(CATEGORIA,"Destroiu a Tela de Editar de Senador");
	}
	
	public void salvar() {

		int numero = 0;
		try {
			numero = Integer.parseInt(campoNumero.getText().toString());
		} catch (NumberFormatException e) {
			// ok neste exemplo, tratar isto em aplicações reais
		}

		Senador s = new Senador();
		if (id != null) {
			// é uma atualização
			s.setId(id);
		}
		s.setNome(campoNome.getText().toString());
		s.setNumero(numero);
		//s.setPartido(campoPartido.getText().toString());
		s.setPartido(campoPartido.getItemAtPosition(campoPartido.getSelectedItemPosition()).toString());
		s.setFoto(campoFoto.getText().toString());

		// Salvar
		salvarSenador(s);

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	public void excluir() {
		if (id != null) {
			excluirSenador(id);
		}

		// OK
		setResult(RESULT_OK, new Intent());

		// Fecha a tela
		finish();
	}

	// Buscar o Senador pelo id
	protected Senador buscarSenador(long id) {
		return CadastroSenador.repositorio.buscarSenador(id);
	}

	// Salvar o Senador
	protected void salvarSenador(Senador s) {
		CadastroSenador.repositorio.Salvar(s);
	}

	// Excluir o Senador
	protected void excluirSenador(long id) {
		CadastroSenador.repositorio.Deletar(id);
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
