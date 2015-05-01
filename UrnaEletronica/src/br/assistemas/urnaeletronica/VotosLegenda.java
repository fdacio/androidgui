package br.assistemas.urnaeletronica;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class VotosLegenda {
	private long id;
	private String partido;
	private String cargo;
	private int voto;
	
	public void setId(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	public String getPartido() {
	    return partido;
	}
	public void setPartido(String partido) {
	    this.partido = partido;
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

	public static String[] colunas = new String[]{VotosLegendaColunas._ID, 
	    VotosLegendaColunas.PARTIDO,
	    VotosLegendaColunas.CARGO,
		VotosLegendaColunas.VOTO};
	public static final String AUTHORITY = "br.assistemas.urnaeletronica.votosbrancosnulos";

	public VotosLegenda(){
		
	}
	/**
	 * Classe interna para representa colunas que serao inseridas por um Content
	 * Provider
	 * @author fdacio
	 * Filha de BaseColumns que ja define (_id e _count), para seguir o padrao android
	 *
	 */
	public static final class VotosLegendaColunas implements BaseColumns{
		//Não pode estanciar essa classe
		private VotosLegendaColunas(){
			
		}
		
		//content://br.assistemas.urnaeletronica.provider.vereador/vereadores
		public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/votoslegenda");
		
		//Mime Tyep para todos os vereadores
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.votoslegenda";

		//Mime Tyep para um vereador
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.votoslegenda";
		
		//Ordenação default para inserir no order by
		public static final String DEFAULT_SORT_ORDER = "_id ASC";
		
		public static final String PARTIDO = "partido";
		public static final String CARGO = "cargo";
		public static final String VOTO = "voto";
		//Método que constroi uma URI  para um vereador especifico, com o seu di
		//A URI é no formato "content//br.assistemas.urnaeletronica.provider.vereador/id"
		public static Uri getUriId(long id){
			//Adiciona o id na URI default do /vereadores
			Uri uriVotosLegendaColunas = ContentUris.withAppendedId(VotosLegendaColunas.CONTENT_URI, id);
			return uriVotosLegendaColunas;
		}
	}
	@Override
	public String toString(){
		return "Partido: "+getPartido()+" Cargo: "+getCargo()+" Voto: "+getVoto();
	}
	

}
