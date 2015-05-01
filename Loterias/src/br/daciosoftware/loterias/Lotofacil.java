package br.daciosoftware.loterias;

import android.provider.BaseColumns;

public class Lotofacil {
	private long id;
	private int numeroconcurso;
	private String dataconcurso ;
	private int d1;
	private int d2;
	private int d3;
	private int d4;
	private int d5;
	private int d6;
	private int d7;
	private int d8;
	private int d9;
	private int d10;
	private int d11;
	private int d12;
	private int d13;
	private int d14;
	private int d15;

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
	public void setD6(int d6){
		this.d6 = d6;
	}
	public int getD6(){
		return this.d6;
	}

	public void setD7(int d7){
		this.d7 = d7;
	}
	public int getD7(){
		return this.d7;
	}
	
	public void setD8(int d8){
		this.d8 = d8;
	}
	public int getD8(){
		return this.d8;
	}
	
	public void setD9(int d9){
		this.d9 = d9;
	}
	public int getD9(){
		return this.d9;
	}
	
	public void setD10(int d10){
		this.d10 = d10;
	}
	public int getD10(){
		return this.d10;
	}

	public void setD11(int d11){
		this.d11 = d11;
	}
	public int getD11(){
		return this.d11;
	}
	
	public void setD12(int d12){
		this.d12 = d12;
	}
	public int getD12(){
		return this.d12;
	}
	public void setD13(int d13){
		this.d13 = d13;
	}
	public int getD13(){
		return this.d13;
	}

	public void setD14(int d14){
		this.d14 = d14;
	}
	public int getD14(){
		return this.d14;
	}

	public void setD15(int d15){
		this.d15 = d15;
	}
	public int getD15(){
		return this.d15;
	}

	public static String[] colunas = new String[]{LotofacilColunas._ID, 
		LotofacilColunas.NUMEROCONCURSO,
		LotofacilColunas.DATACONCURSO,
		LotofacilColunas.D1,
		LotofacilColunas.D2,
		LotofacilColunas.D3,
		LotofacilColunas.D4,
		LotofacilColunas.D5,
		LotofacilColunas.D6,
		LotofacilColunas.D7,
		LotofacilColunas.D8,
		LotofacilColunas.D9,
		LotofacilColunas.D10,
		LotofacilColunas.D11,
		LotofacilColunas.D12,
		LotofacilColunas.D13,
		LotofacilColunas.D14,
		LotofacilColunas.D15};

	public Lotofacil(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class LotofacilColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private LotofacilColunas(){
			
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
		public static final String D6 = "d6";
		public static final String D7 = "d7";
		public static final String D8 = "d8";
		public static final String D9 = "d9";
		public static final String D10 = "d10";
		public static final String D11 = "d11";
		public static final String D12 = "d12";
		public static final String D13 = "d13";
		public static final String D14 = "d14";
		public static final String D15 = "d15";
		
	}
}
