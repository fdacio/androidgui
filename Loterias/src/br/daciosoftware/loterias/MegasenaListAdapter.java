package br.daciosoftware.loterias;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MegasenaListAdapter extends BaseAdapter{
	private Context context;
	private List<Megasena> lista;
	
	public MegasenaListAdapter(Context context, List<Megasena> lista){
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
		Megasena m = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.activity_listagem_resultados_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView numeroconcurso = (TextView) view1.findViewById(R.id.loterial_numeroconcurso);
		numeroconcurso.setText(String.valueOf(m.getNumeroConcurso()));
		TextView dataconcurso = (TextView) view1.findViewById(R.id.loterial_dataconcurso);
		dataconcurso.setText(String.valueOf(m.getDataConcurso()));
		TextView d1 = (TextView) view1.findViewById(R.id.loterial_d1);
		d1.setText(String.valueOf(m.getD1()));
		TextView d2 = (TextView) view1.findViewById(R.id.loterial_d2);
		d2.setText(String.valueOf(m.getD2()));
		TextView d3 = (TextView) view1.findViewById(R.id.loterial_d3);
		d3.setText(String.valueOf(m.getD3()));
		TextView d4 = (TextView) view1.findViewById(R.id.loterial_d4);
		d4.setText(String.valueOf(m.getD4()));
		TextView d5 = (TextView) view1.findViewById(R.id.loterial_d5);
		d5.setText(String.valueOf(m.getD5()));
		TextView d6 = (TextView) view1.findViewById(R.id.loterial_d6);
		d6.setText(String.valueOf(m.getD6()));
		return view1;
	}

}
