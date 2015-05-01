package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.Senador.SenadorColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioSenador {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "senadores";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"nome text," +
											"numero integer," +
											"partido text," +
											"foto blob," +
											"votos integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioSenador(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioSenador(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Senador s){
		long id = s.getId();
		if(id != 0){
			Atualizar(s);
		}else{
			//Insere novo
			id = Inserir(s);
		}
		return id;
	}
	
	public long Inserir(Senador s){
		ContentValues values = new ContentValues();
		values.put(SenadorColunas.NOME,s.getNome());
		values.put(SenadorColunas.NUMERO,s.getNumero());
		values.put(SenadorColunas.PARTIDO,s.getPartido());
		values.put(SenadorColunas.FOTO,s.getFoto());
		values.put(SenadorColunas.VOTOS,s.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Senador s){
		ContentValues values = new ContentValues();
		values.put(SenadorColunas.NOME,s.getNome());
		values.put(SenadorColunas.NUMERO,s.getNumero());
		values.put(SenadorColunas.PARTIDO,s.getPartido());
		values.put(SenadorColunas.FOTO,s.getFoto());
		values.put(SenadorColunas.VOTOS,s.getVotos());
		String _id = String.valueOf(s.getId());
		String where = SenadorColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		

		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Atualiza senador com os valores abaixo
	//A cláusula where é utilizada para identificar o carro a ser atualizado
	public int Atualizar(ContentValues valores, String where, String[] whereArgs){
		int count = db.update(NOME_TABELA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou["+count+"] registros");
		return count;
	}
	
	//Delete o carro com o id fornecido
	public int Deletar(long id){
		String where = SenadorColunas._ID + "=?";
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
	
	//Busca senador pelo id
	public Senador buscarSenador(long id){
		// Select * from veredores where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Senador.colunas, SenadorColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Senador s = new Senador();
			s.setId(c.getLong(0));
			s.setNome(c.getString(1));
			s.setNumero(c.getInt(2));
			s.setPartido(c.getString(3));
			s.setFoto(c.getString(4));
			s.setVotos(c.getInt(5));
			return s;
		}
		return null;
	}
	
	//Retorna um cursor com todos os SenadorsEstadual
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, Senador.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os Senador: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os Senadores
	public List<Senador> listarSenadores(){
		Cursor c = getCursor();
		List<Senador> senadores = new ArrayList<Senador>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(SenadorColunas._ID);
			int idxNome = c.getColumnIndex(SenadorColunas.NOME);
			int idxNumero = c.getColumnIndex(SenadorColunas.NUMERO);
			int idxPartido = c.getColumnIndex(SenadorColunas.PARTIDO);
			int idxFoto = c.getColumnIndex(SenadorColunas.FOTO);
			int idxVotos = c.getColumnIndex(SenadorColunas.VOTOS);
			
			//Loop até o fim
			do{
				Senador s = new Senador();
				senadores.add(s);
				//recupera os atributos do Senador 
				s.setId(c.getLong(idxId));
				s.setNome(c.getString(idxNome));
				s.setNumero(c.getInt(idxNumero));
				s.setPartido(c.getString(idxPartido));
				s.setFoto(c.getString(idxFoto));
				s.setVotos(c.getInt(idxVotos));
			}while(c.moveToNext());
		}
		return senadores;
	}
	
	public Senador buscarSenadorNome(String nome){
		Senador s = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Senador.colunas, SenadorColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				s = new Senador();
				s.setId(c.getLong(0));
				s.setNome(c.getString(1));
				s.setNumero(c.getInt(2));
				s.setPartido(c.getString(3));
				s.setFoto(c.getString(4));
				s.setVotos(c.getInt(5));
				return s;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o  senadores pelo nome "+e.toString());
			return null;
		}
		return s;
		
	}

	public Senador buscarSenadorNumero(String numero){
		Senador s = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Senador.colunas, SenadorColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				s = new Senador();
				s.setId(c.getLong(0));
				s.setNome(c.getString(1));
				s.setNumero(c.getInt(2));
				s.setPartido(c.getString(3));
				s.setFoto(c.getString(4));
				s.setVotos(c.getInt(5));
				return s;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o senador pelo numero "+e.toString());
			return null;
		}
		return s;
		
	}
	
	//Buscar um carro utilizando as configurações definidas no
	//SQLiteQueryBuilder
	//utilizando pelo Content Provider de senador
	
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
