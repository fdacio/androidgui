package br.daciosoftware.loterias;

import java.util.ArrayList;
import java.util.List;

import br.daciosoftware.loterias.Quina.QuinaColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class QuinaRepositorio {
	private static final String CATEGORIA = "loterias";
	private static final String NOME_BANCO = "loterias";
	private static final String NOME_TABELA = "quina";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"numeroconcurso integer," +
											"dataconcurso text," +
											"d1 integer," +
											"d2 integer," +
											"d3 integer," +
											"d4 integer," +
											"d5 integer)";
	//Contantes para ordenar e limitar a listagem
	private static final String ORDER_BY = QuinaColunas.NUMEROCONCURSO+" desc";
	private static final String LIMIT = "limit 0,100";	
	
	protected SQLiteDatabase db;
	
	public QuinaRepositorio(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected QuinaRepositorio(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Quina m){
		long id = m.getId();
		if(id != 0){
			Atualizar(m);
		}else{
			//Insere novo
			id = Inserir(m);
		}
		return id;
	}
	
	public long Inserir(Quina m){
		ContentValues values = new ContentValues();
		values.put(QuinaColunas.NUMEROCONCURSO,m.getNumeroConcurso());
		values.put(QuinaColunas.DATACONCURSO,m.getDataConcurso());
		values.put(QuinaColunas.D1,m.getD1());
		values.put(QuinaColunas.D2,m.getD2());
		values.put(QuinaColunas.D3,m.getD3());
		values.put(QuinaColunas.D4,m.getD4());
		values.put(QuinaColunas.D5,m.getD5());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Quina m){
		ContentValues values = new ContentValues();
		values.put(QuinaColunas.NUMEROCONCURSO,m.getNumeroConcurso());
		values.put(QuinaColunas.DATACONCURSO,m.getDataConcurso());
		values.put(QuinaColunas.D1,m.getD1());
		values.put(QuinaColunas.D2,m.getD2());
		values.put(QuinaColunas.D3,m.getD3());
		values.put(QuinaColunas.D4,m.getD4());
		values.put(QuinaColunas.D5,m.getD5());
		String _id = String.valueOf(m.getId());
		String where = QuinaColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		

		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Atualiza vereador com os valores abaixo
	//A cláusula where é utilizada para identificar o carro a ser atualizado
	public int Atualizar(ContentValues valores, String where, String[] whereArgs){
		int count = db.update(NOME_TABELA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou["+count+"] registros");
		return count;
	}
	
	//Delete o carro com o id fornecido
	public int Deletar(long id){
		String where = QuinaColunas._ID + "=?";
		String _id = String.valueOf(id);
		String[] whereArgs = new String[]{_id};
		int count = Deletar(where, whereArgs);
		return count;
	}
	
	public int Deletar(String where, String[] whereArgs){
		int count = db.delete(NOME_TABELA, where, whereArgs);
		Log.i(CATEGORIA, "Deletou["+count+"] registros");
		return count;
	}
	
	//Busca pelo id do concurso
	public Quina buscarQuinaId(long id){
		// Select * from quina where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Quina.colunas, QuinaColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Quina m = new Quina();
			m.setId(c.getLong(0));
			m.setNumeroConcurso(c.getInt(1));
			m.setDataConcurso(c.getString(2));
			m.setD1(c.getInt(3));
			m.setD2(c.getInt(4));
			m.setD3(c.getInt(5));
			m.setD4(c.getInt(6));
			m.setD5(c.getInt(7));
			return m;
		}
		return null;
	}

	//Retorna um cursor com todos os resultados
	public Cursor getCursor(){
		try{
			//Select * from quina
			return db.query(NOME_TABELA, Quina.colunas, null, null, null, null, ORDER_BY+" "+LIMIT);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar resultado da Quina: "+e.toString());
			return null;
		}
	}
	
	public int getCountReg(){
		int countReg = 0;
		Cursor cursor;
		try{
			//Select * from quina
			cursor = db.query(NOME_TABELA, Quina.colunas, null, null, null, null, null);
			countReg = cursor.getCount();
			return countReg;
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar resultado da Quina: "+e.toString());
			return 0;
		}
	}
	//Retorna uma lista de todos os resultados
	public List<Quina> listarQuina(){
		Cursor c = getCursor();
		List<Quina> listaQuina = new ArrayList<Quina>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(QuinaColunas._ID);
			int idxNumeroConcurso = c.getColumnIndex(QuinaColunas.NUMEROCONCURSO);
			int idxDataConcurso = c.getColumnIndex(QuinaColunas.DATACONCURSO);
			int idxD1 = c.getColumnIndex(QuinaColunas.D1);
			int idxD2 = c.getColumnIndex(QuinaColunas.D2);
			int idxD3 = c.getColumnIndex(QuinaColunas.D3);
			int idxD4 = c.getColumnIndex(QuinaColunas.D4);
			int idxD5 = c.getColumnIndex(QuinaColunas.D5);
			
			//Loop até o fim
			do{
				Quina m = new Quina();
				listaQuina.add(m);
				//recupera os atributos do vereador
				m.setId(c.getLong(idxId));
				m.setNumeroConcurso(c.getInt(idxNumeroConcurso));
				m.setDataConcurso(c.getString(idxDataConcurso));
				m.setD1(c.getInt(idxD1));
				m.setD2(c.getInt(idxD2));
				m.setD3(c.getInt(idxD3));
				m.setD4(c.getInt(idxD4));
				m.setD5(c.getInt(idxD5));
			}while(c.moveToNext());
		}
		return listaQuina;
	}
	
	//Consultar Resultado
	public List<Quina> consultarResultados(String param){
		Cursor c = db.query(true,NOME_TABELA, Quina.colunas, param,null, null, null, null, null);
		List<Quina> listaQuina = new ArrayList<Quina>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(QuinaColunas._ID);
			int idxNumeroConcurso = c.getColumnIndex(QuinaColunas.NUMEROCONCURSO);
			int idxDataConcurso = c.getColumnIndex(QuinaColunas.DATACONCURSO);
			int idxD1 = c.getColumnIndex(QuinaColunas.D1);
			int idxD2 = c.getColumnIndex(QuinaColunas.D2);
			int idxD3 = c.getColumnIndex(QuinaColunas.D3);
			int idxD4 = c.getColumnIndex(QuinaColunas.D4);
			int idxD5 = c.getColumnIndex(QuinaColunas.D5);
			
			//Loop até o fim
			do{
				Quina m = new Quina();
				listaQuina.add(m);
				//recupera os atributos do vereador
				m.setId(c.getLong(idxId));
				m.setNumeroConcurso(c.getInt(idxNumeroConcurso));
				m.setDataConcurso(c.getString(idxDataConcurso));
				m.setD1(c.getInt(idxD1));
				m.setD2(c.getInt(idxD2));
				m.setD3(c.getInt(idxD3));
				m.setD4(c.getInt(idxD4));
				m.setD5(c.getInt(idxD5));
			}while(c.moveToNext());
		}
		return listaQuina;
	}
	
	//Buscar um resultado da quina utilizando as configurações definidas no
	//SQLiteQueryBuilder
	//utilizando pelo Content Provider de partido
	
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
