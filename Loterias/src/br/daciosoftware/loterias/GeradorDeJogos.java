package br.daciosoftware.loterias; 

import java.util.ArrayList;

import java.util.Collections;
import java.util.Random;
import android.util.Log;

public class GeradorDeJogos {
	/*
	 * Array que vai guardar a lista de jogos possiveis
	 */
	private ArrayList<String> listaJogos = new ArrayList<String>();
	
	/*
	 * Construtor da class;
	 */
	public GeradorDeJogos(){

	}

	/*
	 * Método que retorna o fatorial de numero
	 */
	public double Fatorial(long n)
	{
		double fat = 1;  
		for(int i = 2; i <= n; i++)
		{   
			fat *= i;  
		}
		return fat;
	}
	
	/*
	 * Método que retorna a quantidade de combinações de universo 
	 * em classe de qtdeDezenas;
	 */
	public long qtdeJogosPossiveis(int qtdeDezenas, int universo){
		//Fomula matemática da Combinação(não é arranjo)
		double r = Fatorial(universo) /(Fatorial(qtdeDezenas) * Fatorial(universo - qtdeDezenas));
		return (long) r;
		 		
	}
	
	/*
	 * Método Principal da classe, ele monta o array com os possíveis jogos
	 */
	public void gerarJogos(ArrayList<Integer> universo, int qtdeDezenas){
	    	long qtdeJogos = qtdeJogosPossiveis(qtdeDezenas, universo.size());
		String jogo = "";
		ArrayList<Integer> arrayJogo = new ArrayList<Integer>();
		int indiceRandom=0;
		int numeroRandom=0;
		ArrayList<Integer> indicesRandomicos = new ArrayList<Integer>();
		Random rd = new Random();
		do{
			do{
				while(indicesRandomicos.indexOf(indiceRandom)>-1){
					indiceRandom = rd.nextInt(universo.size());
				}
				indicesRandomicos.add(indiceRandom);
				if(indicesRandomicos.size()==universo.size()){
					indicesRandomicos.clear();
				}
				numeroRandom = universo.get(indiceRandom);	
				//se o número escolhido aliatoriamente ainda não está no jogo em formação.
				if(arrayJogo.indexOf(numeroRandom)==-1){
					arrayJogo.add(numeroRandom);
				}
			}while(arrayJogo.size() < qtdeDezenas);//loop ate que a quantidade de numeros no jogo seja igual ao parametro de dezenas do jogo
			
			//Ordena os numeros do jogo(ajuda na comparação para saber se já existe na lista de  jogos possíveis)
			Collections.sort(arrayJogo);
			jogo = arrayJogo.toString();
			arrayJogo.clear();
			Log.e("Loterias","Qtde. Dezenas:"+String.valueOf(qtdeDezenas));
			Log.e("Loterias","Qtde. Universo:"+String.valueOf(universo.size())); 
			Log.e("Loterias","jogo="+jogo);
			if(listaJogos.indexOf(jogo)==-1){
				//Adiciona o jogo na lista de jogos.
				listaJogos.add(jogo);
				//System.out.println(jogo.toString());
			}
			//Limpa o array de jogos para adicionar novos jogos.
			
		}while(listaJogos.size() < qtdeJogos);
		//System.out.println(listaJogos.toString());
	}
	
	/*
	 * Método que retorna o array dos jogos possíveis.
	 */
	public ArrayList<String> getListaJogos(){
	   Collections.sort(listaJogos);
	   return listaJogos;
	}
	
}
