package br.daciosoftware.loterias;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class QuinaMaisSorteadasListAdapter extends BaseAdapter{
	protected static final String CATEGORIA = "loterias";
	private Context context;
	private List<QuinaMaisSorteadas> lista;
		
	public QuinaMaisSorteadasListAdapter(Context context, List<QuinaMaisSorteadas> lista){
		this.context = context;
		this.lista = lista;
	}
	public int getCount(){
		return lista.size();
	}
	public Object getItem(int position){
		return lista.get(position);
	}
	public long getItemId(int position){
		return position;
	}
	
	public View getView(final int position, View convertView, ViewGroup parent){
		//Recupera o partido da posição atual
		QuinaMaisSorteadas q = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.activity_mais_sorteadas_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView ordem = (TextView) view1.findViewById(R.id.textOrdem);
		ordem.setText(String.valueOf(((position)+1)+"º"));
		TextView dezena = (TextView) view1.findViewById(R.id.textDezena);
		dezena.setText(String.valueOf(q.getDezena()));
		TextView frequencia = (TextView) view1.findViewById(R.id.textFrequencia);
		frequencia.setText(String.valueOf(q.getFrequencia())+" vezes");
		final CheckBox cbxSelecao = (CheckBox) view1.findViewById(R.id.checkBoxSelecao);
		
		if(q.getSelecionada()==1){
			cbxSelecao.setChecked(true);
		}else if(q.getSelecionada()==0){
			cbxSelecao.setChecked(false);
		}

		cbxSelecao.setOnClickListener(new OnClickListener(){
			public void onClick(View view) {
				atualizaSelecionada(cbxSelecao,position);
			}
		});
		
		return view1;
	}
	
    protected void atualizaSelecionada(CheckBox cbxSelecao, int position){
    	QuinaMaisSorteadas q = lista.get(position);
    	if(cbxSelecao.isChecked()){
    		q.setSelecionada(1);
    		Log.i(CATEGORIA,"Marcou a dezena: "+q.getDezena());
    	}
    	else{
    		q.setSelecionada(0);
    		Log.i(CATEGORIA,"Desmacou a dezena: "+q.getDezena());
    	}
    	
    	QuinaMaisSorteadasRepositorio repositorio = new QuinaMaisSorteadasRepositorio(context);
    	repositorio.AtualizarSelecionada(q);
    	/*
    	if(repositorio.getDezenasSelecionadas().getCount() == 1){
    		Toast.makeText(context, String.valueOf(repositorio.getDezenasSelecionadas().getCount())+" dezena selecionada", Toast.LENGTH_SHORT).show();
    	}else{
    		Toast.makeText(context, String.valueOf(repositorio.getDezenasSelecionadas().getCount())+" dezenas selecionadas", Toast.LENGTH_SHORT).show();
    	}
    	*/
    	repositorio.Fechar();
    	
    }
}
