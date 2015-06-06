package br.com.daciosoftware.meumapa.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class MeuMapaDatabase {
    private static MeuMapaHelperDatabase helper;
    private static SQLiteDatabase db;

    public static void inicializar(Context contexto) {
	if (helper == null) {
	    helper = new MeuMapaHelperDatabase(contexto);
	}
    }

    public static void encerrarSessao() {
	if (db != null)
	    db.close();
    }

    public static List<Localizacao> carregarLocalizacoes(String nomeLocal) {

	db = helper.getReadableDatabase();
	String where = null;

	String[] colunas = { MeuMapaContratoDatabase.Localizacao._ID,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL};

	if(!nomeLocal.equals("")){
	    where = MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL + " like " + "'%"+ nomeLocal +"%'";
	}
	
	String criterioOrdenacao = MeuMapaContratoDatabase.Localizacao._ID;

	Cursor c = db.query(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, colunas, where, null, null, null, criterioOrdenacao);
	
	
	
	List<Localizacao> localizacoes = new ArrayList<Localizacao>();

	if (c != null && c.getCount() > 0) {
	    c.moveToFirst();

	    int indiceId = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao._ID);
	    
	    int indiceLatitude = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE);
	    int indiceLongitude = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE);
	    int indiceNomeLocal = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL);

	    do {
		Localizacao l = new Localizacao();
		l.setId(c.getInt(indiceId));
		l.setLatitude(c.getFloat(indiceLatitude));
		l.setLongitude(c.getFloat(indiceLongitude));
		l.setNomeLocal(c.getString(indiceNomeLocal));
		localizacoes.add(l);
	    } while (c.moveToNext());
	}

	return localizacoes;

    }

    public static Localizacao getLocalizacao(int _id){
	
	db = helper.getReadableDatabase();

	String[] colunas = { MeuMapaContratoDatabase.Localizacao._ID,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL};

	String where = MeuMapaContratoDatabase.Localizacao._ID + "=?";
	String[] whereArgs = new String[] {String.valueOf(_id)};


	Cursor c = db.query(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, colunas, where, whereArgs, null, null, null);

	List<Localizacao> localizacoes = new ArrayList<Localizacao>();
	
	Localizacao l = new Localizacao();
	
	if (c != null && c.getCount() > 0) {
	    c.moveToFirst();

	    int indiceId = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao._ID);
	    
	    int indiceLatitude = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE);
	    int indiceLongitude = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE);
	    int indiceNomeLocal = c.getColumnIndex(MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL);

	   
	    l.setId(c.getInt(indiceId));
	    l.setLatitude(c.getFloat(indiceLatitude));
	    l.setLongitude(c.getFloat(indiceLongitude));
	    l.setNomeLocal(c.getString(indiceNomeLocal));
	    localizacoes.add(l);
	   
	}
	
	return l;
    }
    public static Cursor getCursorLocalizacoes() {

	db = helper.getReadableDatabase();

	String[] colunas = { MeuMapaContratoDatabase.Localizacao._ID,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE,
		MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL};

	String criterioOrdenacao = MeuMapaContratoDatabase.Localizacao._ID;

	Cursor c = db.query(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, colunas, null, null, null, null, criterioOrdenacao);
	
	return c;
    }
    
    public static void insertLocalizacao(Localizacao localizacao){
	ContentValues values = new ContentValues();
	
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE, localizacao.getLatitude());
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE, localizacao.getLongitude());
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL, localizacao.getNomeLocal());
	db = helper.getReadableDatabase();
	db.insert(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, "", values);
	
    }

    
    public static void updateLocalizacao(Localizacao localizacao){
	ContentValues values = new ContentValues();
	
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_LATITUDE, localizacao.getLatitude());
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_LONGITUDE, localizacao.getLongitude());
	values.put(MeuMapaContratoDatabase.Localizacao.COLUNA_NOME_LOCAL, localizacao.getNomeLocal());
	
	String _id  = String.valueOf(localizacao.getId());
	String where = MeuMapaContratoDatabase.Localizacao._ID + "=?";
	String[] whereArgs = new String[] {_id};
	
	db = helper.getReadableDatabase();
	db.update(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, values, where, whereArgs);
	
    }
    
    public static void deleteLocalizacao(Localizacao localizacao){
	
	String _id  = String.valueOf(localizacao.getId());
	String where = MeuMapaContratoDatabase.Localizacao._ID + "=?";
	String[] whereArgs = new String[] {_id};
	
	db = helper.getReadableDatabase();
	db.delete(MeuMapaContratoDatabase.Localizacao.NOME_TABELA, where, whereArgs);
    }
    
}
