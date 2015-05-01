package br.daciosoftware.loterias;

import java.util.ArrayList;
import java.util.List;

import br.daciosoftware.loterias.Megasena.MegasenaColunas;
import br.daciosoftware.loterias.MegasenaMaisSorteadas.MegasenaMaisSorteadasColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class MegasenaMaisSorteadasRepositorio {
	private static final String CATEGORIA = "loterias";
	private static final String NOME_BANCO = "loterias";
	private static final String NOME_TABELA = "megasena_mais_sorteadas";
	private static final String NOME_TABELA_2 = "megasena";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
			NOME_TABELA+"("+
			"_id integer primary key autoincrement," +
			"dezena integer," +
			"frequencia integer," +
			"selecionada integer)";
	
	//Contantes para ordenar e limitar a listagem
	private static final String ORDER_BY = MegasenaMaisSorteadasColunas.FREQUENCIA+" desc";
	private static final int QTDE_DEZENAS = 60;	
	protected SQLiteDatabase db;
	
	public MegasenaMaisSorteadasRepositorio(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
	}
	
	protected MegasenaMaisSorteadasRepositorio(){
		//Apenas para criar subclasse ...
	}
	

	public long Inserir(MegasenaMaisSorteadas m){
		ContentValues values = new ContentValues();
		values.put(MegasenaMaisSorteadasColunas.DEZENA,m.getDezena());
		values.put(MegasenaMaisSorteadasColunas.FREQUENCIA,m.getFrequencia());
		values.put(MegasenaMaisSorteadasColunas.SELECIONADA,m.getSelecionada());
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
		
		String condicao1 = "(("+MegasenaColunas.D1+"="+dezena+")or" +
							"("+MegasenaColunas.D2+"="+dezena+")or" +
							"("+MegasenaColunas.D3+"="+dezena+")or" +
							"("+MegasenaColunas.D4+"="+dezena+")or" +
							"("+MegasenaColunas.D5+"="+dezena+")or" +
							"("+MegasenaColunas.D6+"="+dezena+"))";
		
		try{
			//Select * from megasena MegasenaMaisSorteadas.colunas, MegasenaMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA_2, Megasena.colunas, condicao1+" "+condicao2, null, null, null,null,null);
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
			cursor = db.rawQuery("select numeroconcurso from megasena order by numeroconcurso desc;", null);
			//query(NOME_TABELA_2, (new String[]{MegasenaColunas.NUMEROCONCURSO}), MegasenaColunas.NUMEROCONCURSO+"=(select max("+MegasenaColunas.NUMEROCONCURSO+"))", null, null, null,null);
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
			condicao = "and ("+MegasenaColunas.NUMEROCONCURSO+" between "+limite+" and "+maxConcurso+")";
			Log.i(CATEGORIA,"Condicao: "+condicao);
		}

		MegasenaMaisSorteadas m = new MegasenaMaisSorteadas();
		for(i = 1; i<= QTDE_DEZENAS;i++){
			m.setDezena(i);
			m.setFrequencia(getFrequencia(i, condicao));
			m.setSelecionada(0);
			Inserir(m);
		}
	}
	
	public int AtualizarSelecionada(MegasenaMaisSorteadas m){
		ContentValues values = new ContentValues();
		values.put(MegasenaMaisSorteadasColunas.SELECIONADA,m.getSelecionada());
		String _id = String.valueOf(m.getId());
		String where = MegasenaMaisSorteadasColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Metodo para obter as dezenas selecionada
	public Cursor getDezenasSelecionadas(){
		Cursor cursor = null;
		try{
			//Select * from megasena MegasenaMaisSorteadas.colunas, MegasenaMaisSorteadasColunas._ID+"="+id
			cursor = db.query(NOME_TABELA, (new String[]{MegasenaMaisSorteadasColunas.DEZENA}), MegasenaMaisSorteadasColunas.SELECIONADA+"=1", null, null, null,null,null);
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
		String where = MegasenaMaisSorteadasColunas._ID + "=?";
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
	public MegasenaMaisSorteadas buscarMegasenaMaisSorteadasDezena(int dezena){
		// Select * from megasena where numeroconcurso = ?
		Cursor c = db.query(true,NOME_TABELA, MegasenaMaisSorteadas.colunas, MegasenaMaisSorteadasColunas.DEZENA+"="+dezena,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			MegasenaMaisSorteadas m = new MegasenaMaisSorteadas();
			m.setId(c.getLong(0));
			m.setDezena(c.getInt(1));
			m.setFrequencia(c.getInt(2));
			m.setSelecionada(c.getInt(3));
			return m;
		}
		return null;
	}
	
	//Retorna um cursor com todos os resultados
	public Cursor getCursor(){
		try{
			//Select * from megasena
			return db.query(NOME_TABELA, MegasenaMaisSorteadas.colunas, null, null, null, null, ORDER_BY);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar megasena mais sorteadas: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos 
	public List<MegasenaMaisSorteadas> listarMegasenaMaisSorteadas(){
		Cursor c = getCursor();
		
		List<MegasenaMaisSorteadas> megasenamaissorteadas = new ArrayList<MegasenaMaisSorteadas>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(MegasenaMaisSorteadasColunas._ID);
			int idxDezena = c.getColumnIndex(MegasenaMaisSorteadasColunas.DEZENA);
			int idxFrequencia = c.getColumnIndex(MegasenaMaisSorteadasColunas.FREQUENCIA);
			int idxSelecionada = c.getColumnIndex(MegasenaMaisSorteadasColunas.SELECIONADA);
			
			//Loop até o fim 
			do{
				MegasenaMaisSorteadas m = new MegasenaMaisSorteadas();
				megasenamaissorteadas.add(m);
				//recupera os atributos do vereador
				m.setId(c.getLong(idxId));
				m.setDezena(c.getInt(idxDezena));
				m.setFrequencia(c.getInt(idxFrequencia));
				m.setSelecionada(c.getInt(idxSelecionada));
			}while(c.moveToNext());
		}
		return megasenamaissorteadas;
	}
	
	//Buscar um resultado da megasena utilizando as configurações definidas no
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
