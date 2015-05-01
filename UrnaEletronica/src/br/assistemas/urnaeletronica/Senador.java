package br.assistemas.urnaeletronica;

import android.provider.BaseColumns;

public class Senador extends Candidatos{
	public static String[] colunas = new String[]{SenadorColunas._ID, 
		SenadorColunas.NOME,
		SenadorColunas.NUMERO,
		SenadorColunas.PARTIDO,
		SenadorColunas.FOTO,
		SenadorColunas.VOTOS};
	

	public Senador(){}
	
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class SenadorColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private SenadorColunas(){}
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome";
		public static final String NUMERO = "numero";
		public static final String PARTIDO = "partido";		
		public static final String FOTO = "foto";
		public static final String VOTOS = "votos";

	}
	@Override
	public String toString(){
		return "Nome: "+getNome()+"Número: "+String.valueOf(getNumero())+" Partido: "+getPartido()+" Foto: "+getFoto()+" Votos: "+getVotos();
	}
	
}
