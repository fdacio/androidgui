package br.assistemas.urnaeletronica;

import java.util.ArrayList;
import java.util.List;

import br.assistemas.urnaeletronica.DeputadoFederal.DeputadoFederalColunas;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioDeputadoFederal {
	private static final String CATEGORIA = "urna";
	private static final String NOME_BANCO = "eleicoes";
	private static final String NOME_TABELA = "deputadofederal";
	private static final String SCRIPT_CREATE_TABLE = "create table if not exists " +
											NOME_TABELA+"("+
											"_id integer primary key autoincrement," +
											"nome text," +
											"numero integer," +
											"partido text," +
											"foto blob," +
											"votos integer)";
		
	
	protected SQLiteDatabase db;
	
	public RepositorioDeputadoFederal(Context ctx){
		try{
			//Abre o banco de dados ja existente
			db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
			db.execSQL(SCRIPT_CREATE_TABLE);
			
		}catch(Exception e){
			Log.e(CATEGORIA, "Erro ao criar o banco de dados: "+e);
		}
		
		
	}
	
	protected RepositorioDeputadoFederal(){
		//Apenas para criar subclasse ...
	}
	
	public long Salvar(DeputadoFederal d){
		long id = d.getId();
		if(id != 0){
			Atualizar(d);
		}else{
			//Insere novo
			id = Inserir(d);
		}
		return id;
	}
	
	public long Inserir(DeputadoFederal d){
		ContentValues values = new ContentValues();
		values.put(DeputadoFederalColunas.NOME,d.getNome());
		values.put(DeputadoFederalColunas.NUMERO,d.getNumero());
		values.put(DeputadoFederalColunas.PARTIDO,d.getPartido());
		values.put(DeputadoFederalColunas.FOTO,d.getFoto());
		values.put(DeputadoFederalColunas.VOTOS,d.getVotos());
		long id = Inserir(values);
		return id;
	}
	
	public long Inserir(ContentValues valores){
		long id = db.insert(NOME_TABELA, "", valores);
		return id;
	}

	public int Atualizar(DeputadoFederal d){
		ContentValues values = new ContentValues();
		values.put(DeputadoFederalColunas.NOME,d.getNome());
		values.put(DeputadoFederalColunas.NUMERO,d.getNumero());
		values.put(DeputadoFederalColunas.PARTIDO,d.getPartido());
		values.put(DeputadoFederalColunas.FOTO,d.getFoto());
		values.put(DeputadoFederalColunas.VOTOS,d.getVotos());
		String _id = String.valueOf(d.getId());
		String where = DeputadoFederalColunas._ID+"=?";
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
		String where = DeputadoFederalColunas._ID + "=?";
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
	public DeputadoFederal buscarDeputadoFederal(long id){
		// Select * from veredores where _id = ?
		Cursor c = db.query(true,NOME_TABELA, DeputadoFederal.colunas, DeputadoFederalColunas._ID+"="+id,null, null, null, null, null);
		if(c.getCount()>0){
			//Posiciona no primeiro elemento do cursor
			c.moveToFirst();
			DeputadoFederal d = new DeputadoFederal();
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
	
	//Retorna um cursor com todos os DeputadoFederal
	public Cursor getCursor(){
		try{
			//Select * from veredores
			return db.query(NOME_TABELA, DeputadoFederal.colunas, null, null, null, null, null);
		}catch(SQLException e){
			Log.e(CATEGORIA,"Erro ao buscar os Deputados Federal: "+e.toString());
			return null;
		}
	}
	
	//Retorna uma lista de todos os Deputados Federal
	public List<DeputadoFederal> listarDeputadosFederal(){
		Cursor c = getCursor();
		List<DeputadoFederal> deputados = new ArrayList<DeputadoFederal>();
		if(c.moveToFirst()){
			int idxId = c.getColumnIndex(DeputadoFederalColunas._ID);
			int idxNome = c.getColumnIndex(DeputadoFederalColunas.NOME);
			int idxNumero = c.getColumnIndex(DeputadoFederalColunas.NUMERO);
			int idxPartido = c.getColumnIndex(DeputadoFederalColunas.PARTIDO);
			int idxFoto = c.getColumnIndex(DeputadoFederalColunas.FOTO);
			int idxVotos = c.getColumnIndex(DeputadoFederalColunas.VOTOS);
			
			//Loop até o fim
			do{
				DeputadoFederal d = new DeputadoFederal();
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
	
	public DeputadoFederal buscarDeputadoNome(String nome){
		DeputadoFederal d = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, DeputadoFederal.colunas, DeputadoFederalColunas.NOME+" like '%"+nome+"%'",null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				d = new DeputadoFederal();
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

	public DeputadoFederal buscarDeputadoNumero(String numero){
		DeputadoFederal d = null;
		try{
			// Idem a Select _id, nome, numero, partido, foto, votos from veredores where nome = ?
			
			Cursor c = db.query(NOME_TABELA, DeputadoFederal.colunas, DeputadoFederalColunas.NUMERO+"="+numero,null, null, null, null, null);
			//Se encontrou
			
			if(c.moveToNext()){
				d = new DeputadoFederal();
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
