package br.com.daciosoftware.meumapa.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class MeuMapaHelperDatabase extends SQLiteOpenHelper{

    public MeuMapaHelperDatabase(Context context){
	super(context, MeuMapaContratoDatabase.NOME_BANCO, null, MeuMapaContratoDatabase.VERSAO);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
	db.execSQL(MeuMapaContratoDatabase.Localizacao.SQL_CRIAR_TABELA_LOCALIZACAO);
	
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	// TODO Auto-generated method stub
	
    }

}
