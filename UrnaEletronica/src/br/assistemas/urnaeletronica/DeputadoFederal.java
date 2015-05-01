package br.assistemas.urnaeletronica;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class DeputadoFederal extends Candidatos{
	public static String[] colunas = new String[]{DeputadoFederalColunas._ID, 
		DeputadoFederalColunas.NOME,
		DeputadoFederalColunas.NUMERO,
		DeputadoFederalColunas.PARTIDO,
		DeputadoFederalColunas.FOTO,
		DeputadoFederalColunas.VOTOS};
	
	/*
	 *Pacote contendo provider precisa ser unico 
	 */
	public static final String AUTHORITY = "br.assistemas.urnaeletronica.deputadofederal";

	public DeputadoFederal(){
		
	}
	
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class DeputadoFederalColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private DeputadoFederalColunas(){
			
		}
		
		//content://br.assistemas.urnaeletronica.provider.vereador/vereadores
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/vereadores");
		
		//Mime Tyep para todos os vereadores
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.vereadores";

		//Mime Tyep para um vereador
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.vereadores";
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome";
		public static final String NUMERO = "numero";
		public static final String PARTIDO = "partido";		
		public static final String FOTO = "foto";
		public static final String VOTOS = "votos";
		//Método que constroi uma URI  para um vereador especifico, com o seu di
		//A URI é no formato "content//br.assistemas.urnaeletronica.provider.vereador/id"
		public static Uri getUriId(long id){
			//Adiciona o id na URI default do /vereadores
			Uri uriVereador = ContentUris.withAppendedId(DeputadoFederalColunas.CONTENT_URI, id);
			return uriVereador;
		}
	}
	@Override
	public String toString(){
		return "Nome: "+getNome()+"Número: "+String.valueOf(getNumero())+" Partido: "+getPartido()+" Foto: "+getFoto()+" Votos: "+getVotos();
	}
	
}
