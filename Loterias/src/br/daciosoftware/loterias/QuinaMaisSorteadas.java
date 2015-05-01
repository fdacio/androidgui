package br.daciosoftware.loterias;

import android.provider.BaseColumns;

public class QuinaMaisSorteadas {
	private long id;
	private int dezena;
	private int frequencia;
	private int selecionada;

	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	public void setDezena(int dezena){
		this.dezena = dezena;
	}
	public int getDezena(){
		return this.dezena;
	}

	public void setFrequencia(int frequencia){
		this.frequencia = frequencia;
	}
	public int getFrequencia(){
		return this.frequencia;
	}
	
	public void setSelecionada(int selecionada){
		this.selecionada = selecionada;
	}

	public int getSelecionada(){
		return this.selecionada;
	}

	public static String[] colunas = new String[]{QuinaMaisSorteadasColunas._ID, 
		QuinaMaisSorteadasColunas.DEZENA,
		QuinaMaisSorteadasColunas.FREQUENCIA,
		QuinaMaisSorteadasColunas.SELECIONADA};

	public QuinaMaisSorteadas(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class QuinaMaisSorteadasColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private QuinaMaisSorteadasColunas(){
			
		}
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		public static final String DEZENA = "dezena";
		public static final String FREQUENCIA = "frequencia";
		public static final String SELECIONADA = "selecionada";
	}
}
