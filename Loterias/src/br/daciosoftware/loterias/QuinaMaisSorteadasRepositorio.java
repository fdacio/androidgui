package br.daciosoftware.loterias;

import java.util.ArrayList;
import java.util.List;

import br.daciosoftware.loterias.Quina.QuinaColunas;
import br.daciosoftware.loterias.QuinaMaisSorteadas.QuinaMaisSorteadasColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class QuinaMaisSorteadasRepositorio {
	private static final String CATEGORIA = "loterias";
	private static final String NOME_BANCO = "loterias";
	private static final String NOME_TABELA = "quina_mais_sorteadas";
	private static final String NOME_TABELA_2 = "quina";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
			NOME_TABELA+"("+
			"_id integer primary key autoincrement," +
			"dezena integer," +
			"frequencia integer," +
			"selecionada integer)";
	
	//Contantes para ordenar e limitar a listagem
	private static final String ORDER_BY = QuinaMaisSorteadasColunas.FREQUENCIA+" desc";
	private static final int QTDE_DEZENAS = 60;	
	protected SQLiteDatabase db;
	
	public QuinaMaisSorteadasRepositorio(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
	}
	
	protected QuinaMaisSorteadasRepositorio(){
		//Apenas para criar subclasse ...
	}
	

	public long Inserir(QuinaMaisSorteadas q){
		ContentValues values = new ContentValues();
		values.put(QuinaMaisSorteadasColunas.DEZENA,q.getDezena());
		values.put(QuinaMaisSorteadasColunas.FREQUENCIA,q.getFrequencia());
		values.put(QuinaMaisSorteadasColunas.SELECIONADA,q.getSelecionada());
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
		
		String condicao1 = "(("+QuinaColunas.D1+"="+dezena+")or" +
							"("+QuinaColunas.D2+"="+dezena+")or" +
							"("+QuinaColunas.D3+"="+dezena+")or" +
							"("+QuinaColunas.D4+"="+dezena+")or" +
							"("+QuinaColunas.D5+"="+dezena+"))";
		
		try{
			//Select * from quina QuinaMaisSorteadas.colunas, QuinaMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA_2, Quina.colunas, condicao1+" "+condicao2, null, null, null,null,null);
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
			cursor = db.rawQuery("select numeroconcurso from quina order by numeroconcurso desc;", null);
			//query(NOME_TABELA_2, (new String[]{QuinaColunas.NUMEROCONCURSO}), QuinaColunas.NUMEROCONCURSO+"=(select max("+QuinaColunas.NUMEROCONCURSO+"))", null, null, null,null);
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
			condicao = "and ("+QuinaColunas.NUMEROCONCURSO+" between "+limite+" and "+maxConcurso+")";
			Log.i(CATEGORIA,"Condicao: "+condicao);
		}

		QuinaMaisSorteadas q = new QuinaMaisSorteadas();
		for(i = 1; i<= QTDE_DEZENAS;i++){
			q.setDezena(i);
			q.setFrequencia(getFrequencia(i, condicao));
			q.setSelecionada(0);
			Inserir(q);
		}
	}
	
	public int AtualizarSelecionada(QuinaMaisSorteadas q){
		ContentValues values = new ContentValues();
		values.put(QuinaMaisSorteadasColunas.SELECIONADA,q.getSelecionada());
		String _id = String.valueOf(q.getId());
		String where = QuinaMaisSorteadasColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Metodo para obter as dezenas selecionada
	public Cursor getDezenasSelecionadas(){
		Cursor cursor = null;
		try{
			//Select * from quina QuinaMaisSorteadas.colunas, QuinaMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA, (new String[]{QuinaMaisSorteadasColunas.DEZENA}), QuinaMaisSorteadasColunas.SELECIONADA+"=1", null, null, null,null,null);
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
		String where = QuinaMaisSorteadasColunas._ID + "=?";
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
	public QuinaMaisSorteadas buscarQuinaMaisSorteadasDezena(int dezena){
		// Select * from quina where numeroconcurso = ?
		Cursor c = db.query(true,NOME_TABELA, QuinaMaisSorteadas.colunas, QuinaMaisSorteadasColunas.DEZENA+"="+dezena,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			QuinaMaisSorteadas q = new QuinaMaisSorteadas();
			q.setId(c.getLong(0));
			q.setDezena(c.getInt(1));
			q.setFrequencia(c.getInt(2));
			q.setSelecionada(c.getInt(3));
			return q;
		}
		return null;
	}
	
	//Retorna um cursor com todos os resultados
	public Cursor getCursor(){
		try{
			//Select * from quina
			return db.query(NOME_TABELA, QuinaMaisSorteadas.colunas, null, null, null, null, ORDER_BY);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar quina mais sorteadas: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos 
	public List<QuinaMaisSorteadas> listarQuinaMaisSorteadas(){
		Cursor c = getCursor();
		
		List<QuinaMaisSorteadas> quinamaissorteadas = new ArrayList<QuinaMaisSorteadas>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(QuinaMaisSorteadasColunas._ID);
			int idxDezena = c.getColumnIndex(QuinaMaisSorteadasColunas.DEZENA);
			int idxFrequencia = c.getColumnIndex(QuinaMaisSorteadasColunas.FREQUENCIA);
			int idxSelecionada = c.getColumnIndex(QuinaMaisSorteadasColunas.SELECIONADA);
			
			//Loop até o fim 
			do{
				QuinaMaisSorteadas q = new QuinaMaisSorteadas();
				quinamaissorteadas.add(q);
				//recupera os atributos do vereador
				q.setId(c.getLong(idxId));
				q.setDezena(c.getInt(idxDezena));
				q.setFrequencia(c.getInt(idxFrequencia));
				q.setSelecionada(c.getInt(idxSelecionada));
			}while(c.moveToNext());
		}
		return quinamaissorteadas;
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
