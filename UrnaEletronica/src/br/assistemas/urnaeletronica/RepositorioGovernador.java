package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.Governador.GovernadorColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioGovernador {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "governador";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"nome text," +
											"numero integer," +
											"partido text," +
											"foto blob," +
											"nome_vice text, "+
											"foto_vice blob, "+
											"votos integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioGovernador(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioGovernador(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Governador g){
		long id = g.getId();
		if(id != 0){
			Atualizar(g);
		}else{
			//Insere novo
			id = Inserir(g);
		}
		return id;
	}
	
	public long Inserir(Governador g){
		ContentValues values = new ContentValues();
		values.put(GovernadorColunas.NOME,g.getNome());
		values.put(GovernadorColunas.NUMERO,g.getNumero());
		values.put(GovernadorColunas.PARTIDO,g.getPartido());
		values.put(GovernadorColunas.FOTO,g.getFoto());
		values.put(GovernadorColunas.NOME_VICE,g.getNomeViceGovernador());
		values.put(GovernadorColunas.FOTO_VICE,g.getFotoViceGovernador());
		values.put(GovernadorColunas.VOTOS,g.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Governador g){
		ContentValues values = new ContentValues();
		values.put(GovernadorColunas.NOME,g.getNome());
		values.put(GovernadorColunas.NUMERO,g.getNumero());
		values.put(GovernadorColunas.PARTIDO,g.getPartido());
		values.put(GovernadorColunas.FOTO,g.getFoto());
		values.put(GovernadorColunas.VOTOS,g.getVotos());
		values.put(GovernadorColunas.NOME_VICE,g.getNomeViceGovernador());
		values.put(GovernadorColunas.FOTO_VICE,g.getFotoViceGovernador());
		String _id = String.valueOf(g.getId());
		String where = GovernadorColunas._ID+"=?";
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
		String where = GovernadorColunas._ID + "=?";
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
	
	//Busca vereador pelo id
	public Governador buscarGovernador(long id){
		// Select * from veredores where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Governador.colunas, GovernadorColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Governador g = new Governador();
			g.setId(c.getLong(0));
			g.setNome(c.getString(1));
			g.setNumero(c.getInt(2));
			g.setPartido(c.getString(3));
			g.setFoto(c.getString(4));
			g.setNomeViceGovernador(c.getString(5));
			g.setFotoViceGovernador(c.getString(6));
			g.setVotos(c.getInt(7));
			return g;
		}
		return null;
	}
	
	//Retorna um cursor com todos os vereadores
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, Governador.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os Governadors: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os Governadors
	public List<Governador> listarGovernadores(){
		Cursor c = getCursor();
		List<Governador> governadores = new ArrayList<Governador>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(GovernadorColunas._ID);
			int idxNome = c.getColumnIndex(GovernadorColunas.NOME);
			int idxNumero = c.getColumnIndex(GovernadorColunas.NUMERO);
			int idxPartido = c.getColumnIndex(GovernadorColunas.PARTIDO);
			int idxFoto = c.getColumnIndex(GovernadorColunas.FOTO);
			int idxNomeViceGovernador = c.getColumnIndex(GovernadorColunas.NOME_VICE);
			int idxFotoViceGovernador = c.getColumnIndex(GovernadorColunas.FOTO_VICE);
			int idxVotos = c.getColumnIndex(GovernadorColunas.VOTOS);
			
			//Loop até o fim
			do{
				Governador g = new Governador();
				governadores.add(g);
				//recupera os atributos do Governadores
				g.setId(c.getLong(idxId));
				g.setNome(c.getString(idxNome));
				g.setNumero(c.getInt(idxNumero));
				g.setPartido(c.getString(idxPartido));
				g.setFoto(c.getString(idxFoto));
				g.setNomeViceGovernador(c.getString(idxNomeViceGovernador));
				g.setFotoViceGovernador(c.getString(idxFotoViceGovernador));
				g.setVotos(c.getInt(idxVotos));
			}while(c.moveToNext());
		}
		return governadores;
	}
	
	public Governador buscarGovernadorNome(String nome){
		Governador g = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Governador.colunas, GovernadorColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				g = new Governador();
				g.setId(c.getLong(0));
				g.setNome(c.getString(1));
				g.setNumero(c.getInt(2));
				g.setPartido(c.getString(3));
				g.setFoto(c.getString(4));
				g.setNomeViceGovernador(c.getString(5));
				g.setFotoViceGovernador(c.getString(6));
				g.setVotos(c.getInt(7));
				return g;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o Governador pelo nome "+e.toString());
			return null;
		}
		return g;
		
	}

	public Governador buscarGovernadorNumero(String numero){
		Governador g = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Governador.colunas, GovernadorColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				g = new Governador();
				g.setId(c.getLong(0));
				g.setNome(c.getString(1));
				g.setNumero(c.getInt(2));
				g.setPartido(c.getString(3));
				g.setFoto(c.getString(4));
				g.setNomeViceGovernador(c.getString(5));
				g.setFotoViceGovernador(c.getString(6));
				g.setVotos(c.getInt(7));
				return g;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o Governador pelo numero "+e.toString());
			return null;
		}
		return g;
		
	}
	
	//Buscar um carro utilizando as configurações definidas no
	//SQLiteQueryBuilder
	//utilizando pelo Content Provider de vereador
	
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
