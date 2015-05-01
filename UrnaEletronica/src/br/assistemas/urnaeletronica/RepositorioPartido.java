package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.Partidos.PartidoColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioPartido {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "partidos";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"nome text," +
											"numero integer," +
											"votos integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioPartido(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioPartido(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Partidos p){
		long id = p.getId();
		if(id != 0){
			Atualizar(p);
		}else{
			//Insere novo
			id = Inserir(p);
		}
		return id;
	}
	
	public long Inserir(Partidos p){
		ContentValues values = new ContentValues();
		values.put(PartidoColunas.NOME,p.getNome());
		values.put(PartidoColunas.NUMERO,p.getNumero());
		values.put(PartidoColunas.VOTOS,p.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Partidos p){
		ContentValues values = new ContentValues();
		values.put(PartidoColunas.NOME,p.getNome());
		values.put(PartidoColunas.NUMERO,p.getNumero());
		values.put(PartidoColunas.VOTOS,p.getVotos());
		String _id = String.valueOf(p.getId());
		String where = PartidoColunas._ID+"=?";
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
		String where = PartidoColunas._ID + "=?";
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
	
	//Busca partido pelo id
	public Partidos buscarPartido(long id){
		// Select * from partidos where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Partidos.colunas, PartidoColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Partidos p = new Partidos();
			p.setId(c.getLong(0));
			p.setNome(c.getString(1));
			p.setNumero(c.getInt(2));
			p.setVotos(c.getInt(3));
			return p;
		}
		return null;
	}
	
	//Retorna um cursor com todos os vereadores
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, Partidos.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os carros: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os partidos
	public List<Partidos> listarPartidos(){
		Cursor c = getCursor();
		List<Partidos> partidos = new ArrayList<Partidos>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(PartidoColunas._ID);
			int idxNome = c.getColumnIndex(PartidoColunas.NOME);
			int idxNumero = c.getColumnIndex(PartidoColunas.NUMERO);
			int idxVotos = c.getColumnIndex(PartidoColunas.VOTOS);
			
			//Loop até o fim
			do{
				Partidos p = new Partidos();
				partidos.add(p);
				//recupera os atributos do vereador
				p.setId(c.getLong(idxId));
				p.setNome(c.getString(idxNome));
				p.setNumero(c.getInt(idxNumero));
				p.setVotos(c.getInt(idxVotos));
			}while(c.moveToNext());
		}
		return partidos;
	}
	
	//Retorna uma lista de todos os partidos para combo
	public List<String> listarPartidosSimples(){
		Cursor c = getCursor();
		List<String> partidos = new ArrayList<String>();
		if(c.moveToFirst()){
			int idxNome = c.getColumnIndex(PartidoColunas.NOME);
			//Loop até o fim
			do{
				partidos.add(c.getString(idxNome));
				//recupera os atributos do vereador
			}while(c.moveToNext());
		}
		return partidos;
	}

	public Partidos buscarPartidoNome(String nome){
		Partidos p = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Partidos.colunas, PartidoColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				p = new Partidos();
				p.setId(c.getLong(0));
				p.setNome(c.getString(1));
				p.setNumero(c.getInt(2));
				p.setVotos(c.getInt(3));
				return p;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o partido pelo nome "+e.toString());
			return null;
		}
		return p;
		
	}

	public Partidos buscarPartidoNumero(String numero){
		Log.i(CATEGORIA,"Partido: " + numero);
		Partidos p = null;
		try{
			Cursor c = db.query(NOME_TABELA, Partidos.colunas, PartidoColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			if(c.moveToNext()){
				p = new Partidos();
				p.setId(c.getLong(0));
				p.setNome(c.getString(1));
				p.setNumero(c.getInt(2));
				p.setVotos(c.getInt(3));
				return p;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o partido pelo numero "+e.toString());
			return null;
		}
		return p;
		
	}
	
	//Buscar um partido utilizando as configurações definidas no
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
