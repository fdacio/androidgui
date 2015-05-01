package br.daciosoftware.loterias;

import android.provider.BaseColumns;

public class Quina {
	private long id;
	private int numeroconcurso;
	private String dataconcurso ;
	private int d1;
	private int d2;
	private int d3;
	private int d4;
	private int d5;


	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	public void setNumeroConcurso(int numeroconcurso){
		this.numeroconcurso = numeroconcurso;
	}
	public int getNumeroConcurso(){
		return this.numeroconcurso;
	}
	public void setDataConcurso(String dataconcurso){
		this.dataconcurso = dataconcurso;
	}
	public String getDataConcurso(){
		return this.dataconcurso;
	}
	public void setD1(int d1){
		this.d1 = d1;
	}
	public int getD1(){
		return this.d1;
	}
	public void setD2(int d2){
		this.d2 = d2;
	}
	public int getD2(){
		return this.d2;
	}
	public void setD3(int d3){
		this.d3 = d3;
	}
	public int getD3(){
		return this.d3;
	}
	public void setD4(int d4){
		this.d4 = d4;
	}
	public int getD4(){
		return this.d4;
	}
	public void setD5(int d5){
		this.d5 = d5;
	}
	public int getD5(){
		return this.d5;
	}

	public static String[] colunas = new String[]{QuinaColunas._ID, 
		QuinaColunas.NUMEROCONCURSO,
		QuinaColunas.DATACONCURSO,
		QuinaColunas.D1,
		QuinaColunas.D2,
		QuinaColunas.D3,
		QuinaColunas.D4,
		QuinaColunas.D5};

	public Quina(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class QuinaColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private QuinaColunas(){
			
		}
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		public static final String NUMEROCONCURSO = "numeroconcurso";
		public static final String DATACONCURSO = "dataconcurso";
		public static final String D1 = "d1";
		public static final String D2 = "d2";
		public static final String D3 = "d3";
		public static final String D4 = "d4";
		public static final String D5 = "d5";		
	}
}
