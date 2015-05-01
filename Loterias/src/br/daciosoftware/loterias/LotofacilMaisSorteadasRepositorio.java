package br.daciosoftware.loterias;

import java.util.ArrayList;
import java.util.List;

import br.daciosoftware.loterias.Lotofacil.LotofacilColunas;
import br.daciosoftware.loterias.LotofacilMaisSorteadas.LotofacilMaisSorteadasColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class LotofacilMaisSorteadasRepositorio {
	private static final String CATEGORIA = "loterias";
	private static final String NOME_BANCO = "loterias";
	private static final String NOME_TABELA = "lotofacil_mais_sorteadas";
	private static final String NOME_TABELA_2 = "lotofacil";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
			NOME_TABELA+"("+
			"_id integer primary key autoincrement," +
			"dezena integer," +
			"frequencia integer," +
			"selecionada integer)";
	
	//Contantes para ordenar e limitar a listagem
	private static final String ORDER_BY = LotofacilMaisSorteadasColunas.FREQUENCIA+" desc";
	private static final int QTDE_DEZENAS = 25;	
	protected SQLiteDatabase db;
	
	public LotofacilMaisSorteadasRepositorio(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
	}
	
	protected LotofacilMaisSorteadasRepositorio(){
		//Apenas para criar subclasse ...
	}
	

	public long Inserir(LotofacilMaisSorteadas l){
		ContentValues values = new ContentValues();
		values.put(LotofacilMaisSorteadasColunas.DEZENA,l.getDezena());
		values.put(LotofacilMaisSorteadasColunas.FREQUENCIA,l.getFrequencia());
		values.put(LotofacilMaisSorteadasColunas.SELECIONADA,l.getSelecionada());
		long id = Inserir(values);
		return id;
	}

	public long Inserir(ContentValues valores){
		//Insert objeto
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int getFrequencia(int dezena, String condicao2){
		/* "concursos" é uma variavel que informa os concuros a serem pesquisado a dezena(10 ultimos, 20 ultimos)*/
		Cursor cursor;
		int countReg = 0;
		
		String condicao1 = "(("+LotofacilColunas.D1+"="+dezena+")or" +
							"("+LotofacilColunas.D2+"="+dezena+")or" +
							"("+LotofacilColunas.D3+"="+dezena+")or" +
							"("+LotofacilColunas.D4+"="+dezena+")or" +
							"("+LotofacilColunas.D5+"="+dezena+")or" +
							"("+LotofacilColunas.D6+"="+dezena+")or" +
							"("+LotofacilColunas.D7+"="+dezena+")or" +
							"("+LotofacilColunas.D8+"="+dezena+")or" +
							"("+LotofacilColunas.D9+"="+dezena+")or" +
							"("+LotofacilColunas.D10+"="+dezena+")or" +
							"("+LotofacilColunas.D11+"="+dezena+")or" +
							"("+LotofacilColunas.D12+"="+dezena+")or" +
							"("+LotofacilColunas.D13+"="+dezena+")or" +
							"("+LotofacilColunas.D14+"="+dezena+")or" +
							"("+LotofacilColunas.D15+"="+dezena+"))";
		
		try{
			//Select * from lotofacil LotofacilMaisSorteadas.colunas, LotofacilMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA_2, Lotofacil.colunas, condicao1+" "+condicao2, null, null, null,null,null);
			countReg = cursor.getCount();
			return countReg;
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar frequencia: "+e.toString());
			return 0;
		}
	}

	public void InserirDezenas(int concursos){
		int i;
		int maxConcurso = 0;
		int countConcurso = 0;
		int limite = 0;
		Cursor cursor;
		String condicao = "";
		
		try{
			cursor = db.rawQuery("select numeroconcurso from lotofacil order by numeroconcurso desc;", null);
			//query(NOME_TABELA_2, (new String[]{LotofacilColunas.NUMEROCONCURSO}), LotofacilColunas.NUMEROCONCURSO+"=(select max("+LotofacilColunas.NUMEROCONCURSO+"))", null, null, null,null);
			countConcurso = cursor.getCount();
			if(cursor.getCount() >0){
				cursor.moveToFirst();
				maxConcurso = cursor.getInt(0);
			} else{
				countConcurso = 0;
			}
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar max concurso: "+e.toString());
		}
		
		Log.i(CATEGORIA,"Concursos: "+concursos+" countConcurso: "+countConcurso);
		if((countConcurso > concursos)&&(concursos > 1)){
			limite = (maxConcurso - concursos)+1;
			condicao = "and ("+LotofacilColunas.NUMEROCONCURSO+" between "+limite+" and "+maxConcurso+")";
			Log.i(CATEGORIA,"Condicao: "+condicao);
		}

		LotofacilMaisSorteadas l = new LotofacilMaisSorteadas();
		for(i = 1; i<= QTDE_DEZENAS;i++){
			l.setDezena(i);
			l.setFrequencia(getFrequencia(i, condicao));
			l.setSelecionada(0);
			Inserir(l);
		}
	}
	
	public int AtualizarSelecionada(LotofacilMaisSorteadas l){
		ContentValues values = new ContentValues();
		values.put(LotofacilMaisSorteadasColunas.SELECIONADA,l.getSelecionada());
		String _id = String.valueOf(l.getId());
		String where = LotofacilMaisSorteadasColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Metodo para obter as dezenas selecionada
	public Cursor getDezenasSelecionadas(){
		Cursor cursor = null;
		try{
			//Select * from lotofacil LotofacilMaisSorteadas.colunas, LotofacilMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA, (new String[]{LotofacilMaisSorteadasColunas.DEZENA}), LotofacilMaisSorteadasColunas.SELECIONADA+"=1", null, null, null,null,null);
			return cursor;
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar obter a qtde selecionada: "+e.toString());
			return null;
		}
		
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
		String where = LotofacilMaisSorteadasColunas._ID + "=?";
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
	
	//Busca pela dezena
	public LotofacilMaisSorteadas buscarLotofacilMaisSorteadasDezena(int dezena){
		// Select * from lotofacil where numeroconcurso = ?
		Cursor c = db.query(true,NOME_TABELA, LotofacilMaisSorteadas.colunas, LotofacilMaisSorteadasColunas.DEZENA+"="+dezena,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			LotofacilMaisSorteadas l = new LotofacilMaisSorteadas();
			l.setId(c.getLong(0));
			l.setDezena(c.getInt(1));
			l.setFrequencia(c.getInt(2));
			l.setSelecionada(c.getInt(3));
			return l;
		}
		return null;
	}
	
	//Retorna um cursor com todos os resultados
	public Cursor getCursor(){
		try{
			//Select * from lotofacil
			return db.query(NOME_TABELA, LotofacilMaisSorteadas.colunas, null, null, null, null, ORDER_BY);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar lotofacil mais sorteadas: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos 
	public List<LotofacilMaisSorteadas> listarLotofacilMaisSorteadas(){
		Cursor c = getCursor();
		
		List<LotofacilMaisSorteadas> lotofacilmaissorteadas = new ArrayList<LotofacilMaisSorteadas>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(LotofacilMaisSorteadasColunas._ID);
			int idxDezena = c.getColumnIndex(LotofacilMaisSorteadasColunas.DEZENA);
			int idxFrequencia = c.getColumnIndex(LotofacilMaisSorteadasColunas.FREQUENCIA);
			int idxSelecionada = c.getColumnIndex(LotofacilMaisSorteadasColunas.SELECIONADA);
			
			//Loop até o fim 
			do{
				LotofacilMaisSorteadas l = new LotofacilMaisSorteadas();
				lotofacilmaissorteadas.add(l);
				//recupera os atributos do vereador
				l.setId(c.getLong(idxId));
				l.setDezena(c.getInt(idxDezena));
				l.setFrequencia(c.getInt(idxFrequencia));
				l.setSelecionada(c.getInt(idxSelecionada));
			}while(c.moveToNext());
		}
		return lotofacilmaissorteadas;
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
