package br.daciosoftware.loterias;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LotofacilListAdapter extends BaseAdapter{
	private Context context;
	private List<Lotofacil> lista;
	
	public LotofacilListAdapter(Context context, List<Lotofacil> lista){
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
		Lotofacil l = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.activity_listagem_resultados_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView numeroconcurso = (TextView) view1.findViewById(R.id.loterial_numeroconcurso);
		numeroconcurso.setText(String.valueOf(l.getNumeroConcurso()));
		TextView dataconcurso = (TextView) view1.findViewById(R.id.loterial_dataconcurso);
		dataconcurso.setText(String.valueOf(l.getDataConcurso()));
		TextView d1 = (TextView) view1.findViewById(R.id.loterial_d1);
		d1.setText(String.valueOf(l.getD1()));
		TextView d2 = (TextView) view1.findViewById(R.id.loterial_d2);
		d2.setText(String.valueOf(l.getD2()));
		TextView d3 = (TextView) view1.findViewById(R.id.loterial_d3);
		d3.setText(String.valueOf(l.getD3()));
		TextView d4 = (TextView) view1.findViewById(R.id.loterial_d4);
		d4.setText(String.valueOf(l.getD4()));
		TextView d5 = (TextView) view1.findViewById(R.id.loterial_d5);
		d5.setText(String.valueOf(l.getD5()));
		TextView d6 = (TextView) view1.findViewById(R.id.loterial_d6);
		d6.setText(String.valueOf(l.getD6()));
		TextView d7 = (TextView) view1.findViewById(R.id.loterial_d7);
		d7.setText(String.valueOf(l.getD7()));
		TextView d8 = (TextView) view1.findViewById(R.id.loterial_d8);
		d8.setText(String.valueOf(l.getD8()));
		TextView d9 = (TextView) view1.findViewById(R.id.loterial_d9);
		d9.setText(String.valueOf(l.getD9()));
		TextView d10 = (TextView) view1.findViewById(R.id.loterial_d10);
		d10.setText(String.valueOf(l.getD10()));
		TextView d11 = (TextView) view1.findViewById(R.id.loterial_d11);
		d11.setText(String.valueOf(l.getD11()));
		TextView d12 = (TextView) view1.findViewById(R.id.loterial_d12);
		d12.setText(String.valueOf(l.getD12()));
		TextView d13 = (TextView) view1.findViewById(R.id.loterial_d13);
		d13.setText(String.valueOf(l.getD13()));
		TextView d14 = (TextView) view1.findViewById(R.id.loterial_d14);
		d14.setText(String.valueOf(l.getD14()));
		TextView d15 = (TextView) view1.findViewById(R.id.loterial_d15);
		d15.setText(String.valueOf(l.getD15()));
		return view1;
	}

}
