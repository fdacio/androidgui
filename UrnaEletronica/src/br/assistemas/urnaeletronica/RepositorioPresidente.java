package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.Presidente.PresidenteColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioPresidente {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "presidentes";
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
	
	public RepositorioPresidente(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioPresidente(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(Presidente p){
		long id = p.getId();
		if(id != 0){
			Atualizar(p);
		}else{
			//Insere novo
			id = Inserir(p);
		}
		return id;
	}
	
	public long Inserir(Presidente p){
		ContentValues values = new ContentValues();
		values.put(PresidenteColunas.NOME,p.getNome());
		values.put(PresidenteColunas.NUMERO,p.getNumero());
		values.put(PresidenteColunas.PARTIDO,p.getPartido());
		values.put(PresidenteColunas.FOTO,p.getFoto());
		values.put(PresidenteColunas.NOME_VICE,p.getNomeVicePresidente());
		values.put(PresidenteColunas.FOTO_VICE,p.getFotoVicePresidente());
		values.put(PresidenteColunas.VOTOS,p.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(Presidente p){
		ContentValues values = new ContentValues();
		values.put(PresidenteColunas.NOME,p.getNome());
		values.put(PresidenteColunas.NUMERO,p.getNumero());
		values.put(PresidenteColunas.PARTIDO,p.getPartido());
		values.put(PresidenteColunas.FOTO,p.getFoto());
		values.put(PresidenteColunas.VOTOS,p.getVotos());
		values.put(PresidenteColunas.NOME_VICE,p.getNomeVicePresidente());
		values.put(PresidenteColunas.FOTO_VICE,p.getFotoVicePresidente());
		String _id = String.valueOf(p.getId());
		String where = PresidenteColunas._ID+"=?";
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
		String where = PresidenteColunas._ID + "=?";
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
	public Presidente buscarPresidente(long id){
		// Select * from veredores where _id = ?
		Cursor c = db.query(true,NOME_TABELA, Presidente.colunas, PresidenteColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			Presidente p = new Presidente();
			p.setId(c.getLong(0));
			p.setNome(c.getString(1));
			p.setNumero(c.getInt(2));
			p.setPartido(c.getString(3));
			p.setFoto(c.getString(4));
			p.setNomeVicePresidente(c.getString(5));
			p.setFotoVicePresidente(c.getString(6));
			p.setVotos(c.getInt(7));
			return p;
		}
		return null;
	}
	
	//Retorna um cursor com todos os vereadores
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, Presidente.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os Presidentes: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os Presidentes
	public List<Presidente> listarPresidentes(){
		Cursor c = getCursor();
		List<Presidente> governadores = new ArrayList<Presidente>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(PresidenteColunas._ID);
			int idxNome = c.getColumnIndex(PresidenteColunas.NOME);
			int idxNumero = c.getColumnIndex(PresidenteColunas.NUMERO);
			int idxPartido = c.getColumnIndex(PresidenteColunas.PARTIDO);
			int idxFoto = c.getColumnIndex(PresidenteColunas.FOTO);
			int idxNomeVicePresidente = c.getColumnIndex(PresidenteColunas.NOME_VICE);
			int idxFotoVicePresidente = c.getColumnIndex(PresidenteColunas.FOTO_VICE);
			int idxVotos = c.getColumnIndex(PresidenteColunas.VOTOS);
			
			//Loop até o fim
			do{
				Presidente p = new Presidente();
				governadores.add(p);
				//recupera os atributos do Presidentees
				p.setId(c.getLong(idxId));
				p.setNome(c.getString(idxNome));
				p.setNumero(c.getInt(idxNumero));
				p.setPartido(c.getString(idxPartido));
				p.setFoto(c.getString(idxFoto));
				p.setNomeVicePresidente(c.getString(idxNomeVicePresidente));
				p.setFotoVicePresidente(c.getString(idxFotoVicePresidente));
				p.setVotos(c.getInt(idxVotos));
			}while(c.moveToNext());
		}
		return governadores;
	}
	
	public Presidente buscarPresidenteNome(String nome){
		Presidente p = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Presidente.colunas, PresidenteColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				p = new Presidente();
				p.setId(c.getLong(0));
				p.setNome(c.getString(1));
				p.setNumero(c.getInt(2));
				p.setPartido(c.getString(3));
				p.setFoto(c.getString(4));
				p.setNomeVicePresidente(c.getString(5));
				p.setFotoVicePresidente(c.getString(6));
				p.setVotos(c.getInt(7));
				return p;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o Presidente pelo nome "+e.toString());
			return null;
		}
		return p;
		
	}

	public Presidente buscarPresidenteNumero(String numero){
		Presidente p = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, Presidente.colunas, PresidenteColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				p = new Presidente();
				p.setId(c.getLong(0));
				p.setNome(c.getString(1));
				p.setNumero(c.getInt(2));
				p.setPartido(c.getString(3));
				p.setFoto(c.getString(4));
				p.setNomeVicePresidente(c.getString(5));
				p.setFotoVicePresidente(c.getString(6));
				p.setVotos(c.getInt(7));
				return p;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o Presidente pelo numero "+e.toString());
			return null;
		}
		return p;
		
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
