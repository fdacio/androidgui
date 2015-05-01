package br.assistemas.urnaeletronica;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class VotosBrancosNulos {
	private long id;
	private String tipo;
	private String cargo;
	private int voto;
	
	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}

	public void setTipo(String tipo){
		this.tipo = tipo.toUpperCase();
	}
	public String getTipo(){
		return this.tipo;
	}

	public void setCargo(String cargo){
		this.cargo = cargo.toUpperCase();
	}
	public String getCargo(){
		return this.cargo;
	}

	public void setVotos(int voto){
		this.voto = voto;
	}
	public int getVoto(){
		return this.voto;
	}

	public static String[] colunas = new String[]{VotosBrancosNulosColunas._ID, 
		VotosBrancosNulosColunas.TIPO,
		VotosBrancosNulosColunas.CARGO,
		VotosBrancosNulosColunas.VOTO};
	public static final String AUTHORITY = "br.assistemas.urnaeletronica.votosbrancosnulos";

	public VotosBrancosNulos(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class VotosBrancosNulosColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private VotosBrancosNulosColunas(){
			
		}
		
		//content://br.assistemas.urnaeletronica.provider.vereador/vereadores
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/votosbrancosnulos");
		
		//Mime Tyep para todos os vereadores
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.votosbrancosnulos";

		//Mime Tyep para um vereador
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.votosbrancosnulos";
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String NOME = "nome";
		public static final String TIPO = "tipo";
		public static final String CARGO = "cargo";
		public static final String VOTO = "voto";
		//Método que constroi uma URI  para um vereador especifico, com o seu di
		//A URI é no formato "content//br.assistemas.urnaeletronica.provider.vereador/id"
		public static Uri getUriId(long id){
			//Adiciona o id na URI default do /vereadores
			Uri uriVotosBrancosNulosColunas = ContentUris.withAppendedId(VotosBrancosNulosColunas.CONTENT_URI, id);
			return uriVotosBrancosNulosColunas;
		}
	}
	@Override
	public String toString(){
		return "Tipo: "+getTipo()+"Cargo: "+getCargo()+" Voto: "+getVoto();
	}
	

}
