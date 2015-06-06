package br.com.daciosoftware.meumapa.db;

import android.provider.BaseColumns;

public class MeuMapaContratoDatabase {
	public static final String NOME_BANCO = "meumapa.db";
	public static final int VERSAO = 1;
	
	// Informações da tabela 'aula'
	public static abstract class Localizacao implements BaseColumns{
		
		// Colunas da tabela 'localizacao'
		public static final String NOME_TABELA = "localizacao";
		public static final String COLUNA_LATITUDE = "latitude";
		public static final String COLUNA_LONGITUDE = "longitude";
		public static final String COLUNA_NOME_LOCAL = "nome_local";
	
		
		// Query de criação da tabela
		public static final String SQL_CRIAR_TABELA_LOCALIZACAO = 
				"CREATE TABLE " + NOME_TABELA + "(" +
				Localizacao._ID + " INTEGER PRIMARY KEY, " +
				Localizacao.COLUNA_LATITUDE + " FLOAT, " +
				Localizacao.COLUNA_LONGITUDE + " FLOAT, " +
				Localizacao.COLUNA_NOME_LOCAL + " TEXT" + ")";
	}

}
