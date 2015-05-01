package br.daciosoftware.loterias;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MegasenaJogosGeradosListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> lista;
	
	public MegasenaJogosGeradosListAdapter(Context context, ArrayList<String> lista){
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
	
	public View getView(int position, View convertView, ViewGroup parent){
		//Recupera o partido da posição atual
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.activity_jogos_gerados_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView jogoGerado = (TextView) view1.findViewById(R.id.textDezenasJogos);
		jogoGerado.setText(lista.get(position).toString());
		return view1;
	}
}
