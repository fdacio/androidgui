package br.daciosoftware.loterias;

import java.util.ArrayList;
import java.util.List;

import br.daciosoftware.loterias.Lotofacil.LotofacilColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class LotofacilRepositorio {
	private static final String CATEGORIA = "loterias";
	private static final String NOME_BANCO = "loterias";
	private static final String NOME_TABELA = "lotofacil";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"numeroconcurso integer," +
											"dataconcurso text," +
											"d1 integer," +
											"d2 integer," +
											"d3 integer," +
											"d4 integer," +
											"d5 integer," +
											"d6 integer," +
											"d7 integer," +
											"d8 integer," +
											"d9 integer," +
											"d10 integer," +
											"d11 integer," +
											"d12 integer," +
											"d13 integer," +
											"d14 integer," +
											"d15 integer)";
	//Contantes para ordenar e limitar a listagem
	private static final String ORDER_BY = LotofacilColunas.NUMEROCONCURSO+" desc";
	private static final String LIMIT = "limit 0,100";	
	
	protected SQLiteDatabase db;
	
	public LotofacilRepositorio(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected LotofacilRepositorio(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Lotofacil l){
		long id = l.getId();
		if(id != 0){
			Atualizar(l);
		}else{
			//Insere novo
			id = Inserir(l);
		}
		return id;
	}
	
	public long Inserir(Lotofacil l){
		ContentValues values = new ContentValues();
		values.put(LotofacilColunas.NUMEROCONCURSO,l.getNumeroConcurso());
		values.put(LotofacilColunas.DATACONCURSO,l.getDataConcurso());
		values.put(LotofacilColunas.D1,l.getD1());
		values.put(LotofacilColunas.D2,l.getD2());
		values.put(LotofacilColunas.D3,l.getD3());
		values.put(LotofacilColunas.D4,l.getD4());
		values.put(LotofacilColunas.D5,l.getD5());
		values.put(LotofacilColunas.D6,l.getD6());
		values.put(LotofacilColunas.D7,l.getD7());
		values.put(LotofacilColunas.D8,l.getD8());
		values.put(LotofacilColunas.D9,l.getD9());
		values.put(LotofacilColunas.D10,l.getD10());
		values.put(LotofacilColunas.D11,l.getD11());
		values.put(LotofacilColunas.D12,l.getD12());
		values.put(LotofacilColunas.D13,l.getD13());
		values.put(LotofacilColunas.D14,l.getD14());
		values.put(LotofacilColunas.D15,l.getD15());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Lotofacil l){
		ContentValues values = new ContentValues();
		values.put(LotofacilColunas.NUMEROCONCURSO,l.getNumeroConcurso());
		values.put(LotofacilColunas.DATACONCURSO,l.getDataConcurso());
		values.put(LotofacilColunas.D1,l.getD1());
		values.put(LotofacilColunas.D2,l.getD2());
		values.put(LotofacilColunas.D3,l.getD3());
		values.put(LotofacilColunas.D4,l.getD4());
		values.put(LotofacilColunas.D5,l.getD5());
		values.put(LotofacilColunas.D6,l.getD6());
		values.put(LotofacilColunas.D7,l.getD7());
		values.put(LotofacilColunas.D8,l.getD8());
		values.put(LotofacilColunas.D9,l.getD9());
		values.put(LotofacilColunas.D10,l.getD10());
		values.put(LotofacilColunas.D11,l.getD11());
		values.put(LotofacilColunas.D12,l.getD12());
		values.put(LotofacilColunas.D13,l.getD13());
		values.put(LotofacilColunas.D14,l.getD14());
		values.put(LotofacilColunas.D15,l.getD15());
		String _id = String.valueOf(l.getId());
		String where = LotofacilColunas._ID+"=?";
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
		String where = LotofacilColunas._ID + "=?";
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
	public Lotofacil buscarLotofacilId(long id){
		// Select * from lotofacil where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Lotofacil.colunas, LotofacilColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Lotofacil l = new Lotofacil();
			l.setId(c.getLong(0));
			l.setNumeroConcurso(c.getInt(1));
			l.setDataConcurso(c.getString(2));
			l.setD1(c.getInt(3));
			l.setD2(c.getInt(4));
			l.setD3(c.getInt(5));
			l.setD4(c.getInt(6));
			l.setD5(c.getInt(7));
			l.setD6(c.getInt(8));
			l.setD7(c.getInt(9));
			l.setD8(c.getInt(10));
			l.setD9(c.getInt(11));
			l.setD10(c.getInt(12));
			l.setD11(c.getInt(13));
			l.setD12(c.getInt(14));
			l.setD13(c.getInt(15));
			l.setD14(c.getInt(16));
			l.setD15(c.getInt(17));
			return l;
		}
		return null;
	}

	//Retorna um cursor com todos os resultados
	public Cursor getCursor(){
		try{
			//Select * from lotofacil
			return db.query(NOME_TABELA, Lotofacil.colunas, null, null, null, null, ORDER_BY+" "+LIMIT);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar resultado da Lotofacil: "+e.toString());
			return null;
		}
	}
	
	public int getCountReg(){
		int countReg = 0;
		Cursor cursor;
		try{
			//Select * from lotofacil
			cursor = db.query(NOME_TABELA, Lotofacil.colunas, null, null, null, null, null);
			countReg = cursor.getCount();
			return countReg;
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar resultado da Lotofacil: "+e.toString());
			return 0;
		}
	}
	//Retorna uma lista de todos os resultados
	public List<Lotofacil> listarLotofacil(){
		Cursor c = getCursor();
		List<Lotofacil> listaLotofacil = new ArrayList<Lotofacil>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(LotofacilColunas._ID);
			int idxNumeroConcurso = c.getColumnIndex(LotofacilColunas.NUMEROCONCURSO);
			int idxDataConcurso = c.getColumnIndex(LotofacilColunas.DATACONCURSO);
			int idxD1 = c.getColumnIndex(LotofacilColunas.D1);
			int idxD2 = c.getColumnIndex(LotofacilColunas.D2);
			int idxD3 = c.getColumnIndex(LotofacilColunas.D3);
			int idxD4 = c.getColumnIndex(LotofacilColunas.D4);
			int idxD5 = c.getColumnIndex(LotofacilColunas.D5);
			int idxD6 = c.getColumnIndex(LotofacilColunas.D6);
			int idxD7 = c.getColumnIndex(LotofacilColunas.D7);
			int idxD8 = c.getColumnIndex(LotofacilColunas.D8);
			int idxD9 = c.getColumnIndex(LotofacilColunas.D9);
			int idxD10 = c.getColumnIndex(LotofacilColunas.D10);
			int idxD11 = c.getColumnIndex(LotofacilColunas.D11);
			int idxD12 = c.getColumnIndex(LotofacilColunas.D12);
			int idxD13 = c.getColumnIndex(LotofacilColunas.D13);
			int idxD14 = c.getColumnIndex(LotofacilColunas.D14);
			int idxD15 = c.getColumnIndex(LotofacilColunas.D15);
			
			//Loop até o fim
			do{
				Lotofacil l = new Lotofacil();
				listaLotofacil.add(l);
				//recupera os atributos do vereador
				l.setId(c.getLong(idxId));
				l.setNumeroConcurso(c.getInt(idxNumeroConcurso));
				l.setDataConcurso(c.getString(idxDataConcurso));
				l.setD1(c.getInt(idxD1));
				l.setD2(c.getInt(idxD2));
				l.setD3(c.getInt(idxD3));
				l.setD4(c.getInt(idxD4));
				l.setD5(c.getInt(idxD5));
				l.setD6(c.getInt(idxD6));
				l.setD7(c.getInt(idxD7));
				l.setD8(c.getInt(idxD8));
				l.setD9(c.getInt(idxD9));
				l.setD10(c.getInt(idxD10));
				l.setD11(c.getInt(idxD11));
				l.setD12(c.getInt(idxD12));
				l.setD13(c.getInt(idxD13));
				l.setD14(c.getInt(idxD14));
				l.setD15(c.getInt(idxD15));
			}while(c.moveToNext());
		}
		return listaLotofacil;
	}
	
	//Consultar Resultado
	public List<Lotofacil> consultarResultados(String param){
		Cursor c = db.query(true,NOME_TABELA, Lotofacil.colunas, param,null, null, null, null, null);
		List<Lotofacil> listaLotofacil = new ArrayList<Lotofacil>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(LotofacilColunas._ID);
			int idxNumeroConcurso = c.getColumnIndex(LotofacilColunas.NUMEROCONCURSO);
			int idxDataConcurso = c.getColumnIndex(LotofacilColunas.DATACONCURSO);
			int idxD1 = c.getColumnIndex(LotofacilColunas.D1);
			int idxD2 = c.getColumnIndex(LotofacilColunas.D2);
			int idxD3 = c.getColumnIndex(LotofacilColunas.D3);
			int idxD4 = c.getColumnIndex(LotofacilColunas.D4);
			int idxD5 = c.getColumnIndex(LotofacilColunas.D5);
			int idxD6 = c.getColumnIndex(LotofacilColunas.D6);
			int idxD7 = c.getColumnIndex(LotofacilColunas.D7);
			int idxD8 = c.getColumnIndex(LotofacilColunas.D8);
			int idxD9 = c.getColumnIndex(LotofacilColunas.D9);
			int idxD10 = c.getColumnIndex(LotofacilColunas.D10);
			int idxD11 = c.getColumnIndex(LotofacilColunas.D11);
			int idxD12 = c.getColumnIndex(LotofacilColunas.D12);
			int idxD13 = c.getColumnIndex(LotofacilColunas.D13);
			int idxD14 = c.getColumnIndex(LotofacilColunas.D14);
			int idxD15= c.getColumnIndex(LotofacilColunas.D15);
			
			//Loop até o fim
			do{
				Lotofacil l = new Lotofacil();
				listaLotofacil.add(l);
				//recupera os atributos do vereador
				l.setId(c.getLong(idxId));
				l.setNumeroConcurso(c.getInt(idxNumeroConcurso));
				l.setDataConcurso(c.getString(idxDataConcurso));
				l.setD1(c.getInt(idxD1));
				l.setD2(c.getInt(idxD2));
				l.setD3(c.getInt(idxD3));
				l.setD4(c.getInt(idxD4));
				l.setD5(c.getInt(idxD5));
				l.setD6(c.getInt(idxD6));
				l.setD7(c.getInt(idxD7));
				l.setD8(c.getInt(idxD8));
				l.setD9(c.getInt(idxD9));
				l.setD10(c.getInt(idxD10));
				l.setD11(c.getInt(idxD11));
				l.setD12(c.getInt(idxD12));
				l.setD13(c.getInt(idxD13));
				l.setD14(c.getInt(idxD14));
				l.setD15(c.getInt(idxD15));
				
				
			}while(c.moveToNext());
		}
		return listaLotofacil;
	}
	
	//Buscar um resultado da lotofacil utilizando as configurações definidas no
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
