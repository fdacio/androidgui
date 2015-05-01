package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.Partidos.PartidoColunas;
import br.assistemas.urnaeletronica.VotosBrancosNulos.VotosBrancosNulosColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioVotosBrancosNulos {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "votos_brancos_nulos";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"tipo text," +
											"cargo text," +
											"voto integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioVotosBrancosNulos(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.i(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
	}
	
	protected RepositorioVotosBrancosNulos(){
		//Apenas para criar subclasse ...
	}
	
	public long Inserir(VotosBrancosNulos vbn){
		ContentValues values = new ContentValues();
		values.put(VotosBrancosNulosColunas.TIPO,vbn.getTipo());
		values.put(VotosBrancosNulosColunas.CARGO,vbn.getCargo());
		values.put(VotosBrancosNulosColunas.VOTO,vbn.getVoto());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	//Retorna um cursor com todos os votos branco e nulos
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, VotosBrancosNulos.colunas, null, null, null, null, null);
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os votos brancos e nulos: "+e.toString());
			return null;
		}
	}
	
	public void setVotosBrancoNulos(String tipo, String cargo){
		VotosBrancosNulos vbn = new VotosBrancosNulos();
		vbn.setTipo(tipo);
		vbn.setCargo(cargo);
		vbn.setVotos(1);
		Inserir(vbn);
	}
	
	public int totalVotosBrancosNulos(String tipo, String cargo){
		int total = 0;
		Cursor c = db.query(true,NOME_TABELA, VotosBrancosNulos.colunas, VotosBrancosNulosColunas.TIPO+"='"+tipo+"' AND "+VotosBrancosNulosColunas.CARGO+"='"+cargo+"'",null, null, null, null, null);
		Log.i(CATEGORIA,"Total de Votos("+tipo+")("+cargo+"): "+String.valueOf(total));
		Log.i(CATEGORIA,"Total de Resgistros da Tabela VBN: "+String.valueOf(c.getCount()));
		return c.getCount();
	}
	
	//Delete todos votos brancos e nulos;
	public int Deletar(){
		String where = PartidoColunas._ID + ">?";
		String _id = String.valueOf(-1);
		String[] whereArgs = new String[]{_id};
		int count = Deletar(where, whereArgs);
		return count;
	}
	
	public int Deletar(String where, String[] whereArgs){
		int count = db.delete(NOME_TABELA, where, whereArgs);
		Log.i(CATEGORIA, "Deletou["+count+"] registros");
		return count;
	}

	//Buscar um partido utilizando as configurações definidas no
	//SQLiteQueryBuilder
	//utilizando pelo Content Provider de votos brancos de nulos
	
	public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, String selection, 
			String[] selectionArgs,String groupBy, String having, String orderBy){
		Cursor c = queryBuilder.query(this.db, projection, selection, selectionArgs, groupBy, having, orderBy);
		return c;
	}
	
	//Fecha o banco
	public void Fechar(){
		if(db != null){
			db.close();
		}
	}
			
}
