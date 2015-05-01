package br.daciosoftware.loterias;

public class LerHtml {
	public String getValorHtml(String linha){
		String s;
		int i=0;
		int count = linha.length()-1;
		char C;
		s = "";
		for(i = 0; i < count;i++){
			C = linha.charAt(i); 
			if( C == '>'){
				i++;
				while(linha.charAt(i) != '<'){
					s = s + linha.charAt(i);
					i++;
				}	
			}
			
		}
		return s;
	}
	
	public String getTagValor(String linha){
		if(linha.length() > 3){
			return linha.substring(0,4);
		}else{
			return linha;
		}
	}

	public String getTagInicio(String linha){
		if(linha.length() > 2){
			return linha.substring(0,3);
		}else{
			return linha;
		}
	}
	
	public String getTagFim(String linha){
		//return linha;
		if(linha.length() > 4){
			return linha.substring(0,5);
		}else{	
			return linha;
		}
		
	}


}
