package br.assistemas.urnaeletronica;

import br.assistemas.urnaeletronica.Partidos.PartidoColunas;
import br.assistemas.urnaeletronica.VotosLegenda.VotosLegendaColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioVotosLegenda {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "votos_legenda";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"partido text, "+
											"cargo text," +
											"voto integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioVotosLegenda(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.i(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
	}
	
	protected RepositorioVotosLegenda(){
		//Apenas para criar subclasse ...
	}
	
	public long Inserir(VotosLegenda vl){
		ContentValues values = new ContentValues();
		values.put(VotosLegendaColunas.PARTIDO,vl.getPartido());
		values.put(VotosLegendaColunas.CARGO,vl.getCargo());
		values.put(VotosLegendaColunas.VOTO,vl.getVoto());
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
			return db.query(NOME_TABELA, VotosLegenda.colunas, null, null, null, null, null);
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os votos de legenda: "+e.toString());
			return null;
		}
	}
	
	public void setVotosLegenda(String partido, String cargo){
		VotosLegenda vl = new VotosLegenda();
		vl.setCargo(partido);
		vl.setCargo(cargo);
		vl.setVotos(1);
		Inserir(vl);
	}
	
	public int totalVotosLegenda(String partido, String cargo){
		int total = 0;
		Cursor c = db.query(true,NOME_TABELA, VotosLegenda.colunas, VotosLegendaColunas.PARTIDO+"='"+partido+"'"+" and "+VotosLegendaColunas.CARGO+"='"+cargo+"'",null, null, null, null, null);
		Log.i(CATEGORIA,"Total de Votos("+partido+"/"+cargo+"): "+String.valueOf(total));
		Log.i(CATEGORIA,"Total de Resgistros da Tabela Votos de Legenda: "+String.valueOf(c.getCount()));
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
