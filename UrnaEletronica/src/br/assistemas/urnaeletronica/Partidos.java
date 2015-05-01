package br.assistemas.urnaeletronica;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Partidos {
	private long id;
	private String nome ;
	private int numero;
	private int votos;
	
	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}

	public void setNome(String nome){
		this.nome = nome.toUpperCase();
	}
	public String getNome(){
		return this.nome;
	}

	public void setNumero(int numero){
		this.numero = numero;
	}
	public int getNumero(){
		return this.numero;
	}

	
	public void setVotos(int votos){
		this.votos = votos;
	}
	public int getVotos(){
		return this.votos;
	}



	public static String[] colunas = new String[]{PartidoColunas._ID, 
		PartidoColunas.NOME,
		PartidoColunas.NUMERO,
		PartidoColunas.VOTOS};
	public static final String AUTHORITY = "br.assistemas.urnaeletronica.partido";

	public Partidos(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class PartidoColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private PartidoColunas(){
			
		}
		
		//content://br.assistemas.urnaeletronica.provider.vereador/vereadores
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/partidos");
		
		//Mime Tyep para todos os vereadores
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.partidos";

		//Mime Tyep para um vereador
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.partidos";
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome";
		public static final String NUMERO = "numero";
		public static final String VOTOS = "votos";
	
		//Método que constroi uma URI  para um vereador especifico, com o seu di
		//A URI é no formato "content//br.assistemas.urnaeletronica.provider.vereador/id"
		public static Uri getUriId(long id){
			//Adiciona o id na URI default do /vereadores
			Uri uriPartido = ContentUris.withAppendedId(PartidoColunas.CONTENT_URI, id);
			return uriPartido;
		}
	}
	@Override
	public String toString(){
		return "Nome: "+getNome()+"Número: "+String.valueOf(getNumero())+" Votos: "+getVotos();
	}
	

}
