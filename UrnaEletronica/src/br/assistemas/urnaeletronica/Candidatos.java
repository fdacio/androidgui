package br.assistemas.urnaeletronica;

public class Candidatos {
	private long id = 0;
	private String nome;
	private int numero;
	private String partido;
	private String foto;
	private int votos = 0;
	
	
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

	public void setPartido(String partido){
		this.partido = partido.toUpperCase();
	}
	
	public String getPartido(){
		return this.partido;
	}
	
	public void setFoto(String foto){
		this.foto = foto;
	}
	public String getFoto(){
		return this.foto;
	}
	
	public void setVotos(int votos){
		this.votos = votos;
	}
	public int getVotos(){
		return this.votos;
	}

	
}
