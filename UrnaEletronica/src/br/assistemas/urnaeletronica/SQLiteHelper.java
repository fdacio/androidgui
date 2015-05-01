package br.assistemas.urnaeletronica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class SQLiteHelper extends SQLiteOpenHelper{
	private static final String CATEGORIA = "urna";
	private String[] scriptSQLCreate;
	private String scriptSQLDelete;
	
	
	/**
	 * Cria uma instância de SQLiteHelper
	 * 
	 * @param nomeBanco nome do banco de dados
	 * @param versaoBanco versão do banco de dados(se for diferente é pra atualizar)
	 * @param scriptSQLCreate SQL com create table
	 * @param scriptSQLDelete SQL com drop table
	 * 
	 */
	SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete) {
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	//Cria o novo banco
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(CATEGORIA, "Criando banco com sql");
		int qtdeScript = scriptSQLCreate.length;
		
		//Executa cada SQL passada como parametro
		for(int i = 0; i < qtdeScript; i++){
			String sql = scriptSQLCreate[i];
			Log.i(CATEGORIA, sql );
			//Cria o banco de dados executando o script de criação
			db.execSQL(sql);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int versaoAntiga, int novaVersao) {
		// TODO Auto-generated method stub
		Log.w(CATEGORIA, "Atualizando a versão "+ versaoAntiga + " para " + novaVersao +". Todos os registros serão deletados");
		Log.i(CATEGORIA, scriptSQLDelete);
		//Delete as tabela
		db.execSQL(scriptSQLDelete);
		//Cria novamente
		onCreate(db);
		
	}

}
