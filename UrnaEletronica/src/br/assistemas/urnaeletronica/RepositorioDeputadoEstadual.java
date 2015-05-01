package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.DeputadoEstadual.DeputadoEstadualColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioDeputadoEstadual {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "deputadoestadual";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"nome text," +
											"numero integer," +
											"partido text," +
											"foto blob," +
											"votos integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioDeputadoEstadual(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioDeputadoEstadual(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(DeputadoEstadual d){
		long id = d.getId();
		if(id != 0){
			Atualizar(d);
		}else{
			//Insere novo
			id = Inserir(d);
		}
		return id;
	}
	
	public long Inserir(DeputadoEstadual d){
		ContentValues values = new ContentValues();
		values.put(DeputadoEstadualColunas.NOME,d.getNome());
		values.put(DeputadoEstadualColunas.NUMERO,d.getNumero());
		values.put(DeputadoEstadualColunas.PARTIDO,d.getPartido());
		values.put(DeputadoEstadualColunas.FOTO,d.getFoto());
		values.put(DeputadoEstadualColunas.VOTOS,d.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(DeputadoEstadual d){
		ContentValues values = new ContentValues();
		values.put(DeputadoEstadualColunas.NOME,d.getNome());
		values.put(DeputadoEstadualColunas.NUMERO,d.getNumero());
		values.put(DeputadoEstadualColunas.PARTIDO,d.getPartido());
		values.put(DeputadoEstadualColunas.FOTO,d.getFoto());
		values.put(DeputadoEstadualColunas.VOTOS,d.getVotos());
		String _id = String.valueOf(d.getId());
		String where = DeputadoEstadualColunas._ID+"=?";
		String[] whereArgs = new String[]{_id};
		

		int count = Atualizar(values, where, whereArgs);
		return count;
	}
	
	//Atualiza deputado com os valores abaixo
	//A cláusula where é utilizada para identificar o carro a ser atualizado
	public int Atualizar(ContentValues valores, String where, String[] whereArgs){
		int count = db.update(NOME_TABELA, valores, where, whereArgs);
		Log.i(CATEGORIA, "Atualizou["+count+"] registros");
		return count;
	}
	
	//Delete o carro com o id fornecido
	public int Deletar(long id){
		String where = DeputadoEstadualColunas._ID + "=?";
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
	
	//Busca deputado pelo id
	public DeputadoEstadual buscarDeputadoEstadual(long id){
		// Select * from veredores where _id = ?
		Cursor c = db.query(true,NOME_TABELA, DeputadoEstadual.colunas, DeputadoEstadualColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			DeputadoEstadual d = new DeputadoEstadual();
			d.setId(c.getLong(0));
			d.setNome(c.getString(1));
			d.setNumero(c.getInt(2));
			d.setPartido(c.getString(3));
			d.setFoto(c.getString(4));
			d.setVotos(c.getInt(5));
			return d;
		}
		return null;
	}
	
	//Retorna um cursor com todos os DeputadosEstadual
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, DeputadoEstadual.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os Deputados Estadual: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os Deputados Estadual
	public List<DeputadoEstadual> listarDeputadosEstadual(){
		Cursor c = getCursor();
		List<DeputadoEstadual> deputados = new ArrayList<DeputadoEstadual>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(DeputadoEstadualColunas._ID);
			int idxNome = c.getColumnIndex(DeputadoEstadualColunas.NOME);
			int idxNumero = c.getColumnIndex(DeputadoEstadualColunas.NUMERO);
			int idxPartido = c.getColumnIndex(DeputadoEstadualColunas.PARTIDO);
			int idxFoto = c.getColumnIndex(DeputadoEstadualColunas.FOTO);
			int idxVotos = c.getColumnIndex(DeputadoEstadualColunas.VOTOS);
			
			//Loop até o fim
			do{
				DeputadoEstadual d = new DeputadoEstadual();
				deputados.add(d);
				//recupera os atributos do Deputado 
				d.setId(c.getLong(idxId));
				d.setNome(c.getString(idxNome));
				d.setNumero(c.getInt(idxNumero));
				d.setPartido(c.getString(idxPartido));
				d.setFoto(c.getString(idxFoto));
				d.setVotos(c.getInt(idxVotos));
			}while(c.moveToNext());
		}
		return deputados;
	}
	
	public DeputadoEstadual buscarDeputadoNome(String nome){
		DeputadoEstadual d = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, DeputadoEstadual.colunas, DeputadoEstadualColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				d = new DeputadoEstadual();
				d.setId(c.getLong(0));
				d.setNome(c.getString(1));
				d.setNumero(c.getInt(2));
				d.setPartido(c.getString(3));
				d.setFoto(c.getString(4));
				d.setVotos(c.getInt(5));
				return d;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o  deputado pelo nome "+e.toString());
			return null;
		}
		return d;
		
	}

	public DeputadoEstadual buscarDeputadoNumero(String numero){
		DeputadoEstadual d = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, DeputadoEstadual.colunas, DeputadoEstadualColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				d = new DeputadoEstadual();
				d.setId(c.getLong(0));
				d.setNome(c.getString(1));
				d.setNumero(c.getInt(2));
				d.setPartido(c.getString(3));
				d.setFoto(c.getString(4));
				d.setVotos(c.getInt(5));
				return d;
			}
			
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar o deputado pelo numero "+e.toString());
			return null;
		}
		return d;
		
	}
	
	//Buscar um carro utilizando as configurações definidas no
	//SQLiteQueryBuilder
	//utilizando pelo Content Provider de deputado
	
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
