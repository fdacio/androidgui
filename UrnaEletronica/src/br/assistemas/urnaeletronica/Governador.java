package br.assistemas.urnaeletronica;


import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class Governador extends Candidatos{
	private String nomeViceGovernador;
	private String fotoViceGovernador;
	
	public void setNomeViceGovernador(String nomeViceGovernador){
		this.nomeViceGovernador = nomeViceGovernador.toUpperCase();
	}
	public String getNomeViceGovernador(){
		return this.nomeViceGovernador;
	}
	
	public void setFotoViceGovernador(String fotoViceGovernador){
		this.fotoViceGovernador = fotoViceGovernador;
	}
	public String getFotoViceGovernador(){
		return this.fotoViceGovernador;
	}
	
	public static String[] colunas = new String[]{GovernadorColunas._ID, 
		GovernadorColunas.NOME,
		GovernadorColunas.NUMERO,
		GovernadorColunas.PARTIDO,
		GovernadorColunas.FOTO,
		GovernadorColunas.NOME_VICE,
		GovernadorColunas.FOTO_VICE,
		GovernadorColunas.VOTOS};
	
	/*
	 *Pacote contendo provider precisa ser unico 
	 */
	public static final String AUTHORITY = "br.assistemas.urnaeletronica.governador";

	public Governador(){
		
	}
	
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class GovernadorColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private GovernadorColunas(){
			
		}
		
		//content://br.assistemas.urnaeletronica.provider.vereador/vereadores
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/governadores");
		
		//Mime Tyep para todos os vereadores
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.governadores";

		//Mime Tyep para um vereador
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.governadores";
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome";
		public static final String NUMERO = "numero";
		public static final String PARTIDO = "partido";		
		public static final String FOTO = "foto";
		public static final String NOME_VICE = "nome_vice";
		public static final String FOTO_VICE = "foto_vice";
		public static final String VOTOS = "votos";
		//Método que constroi uma URI  para um vereador especifico, com o seu di
		//A URI é no formato "content//br.assistemas.urnaeletronica.provider.vereador/id"
		public static Uri getUriId(long id){
			//Adiciona o id na URI default do /vereadores
			Uri uriGovernador = ContentUris.withAppendedId(GovernadorColunas.CONTENT_URI, id);
			return uriGovernador;
		}
	}
	@Override
	public String toString(){
		return "Nome: "+getNome()+"Número: "+String.valueOf(getNumero())+" Partido: "+getPartido()+" Foto: "+getFoto()+" Votos: "+getVotos();
	}
	
}
