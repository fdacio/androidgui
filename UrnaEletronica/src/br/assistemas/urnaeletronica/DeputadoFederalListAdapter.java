package br.assistemas.urnaeletronica;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DeputadoFederalListAdapter extends BaseAdapter{
	private Context context;
	private List<DeputadoFederal> lista;
	
	public DeputadoFederalListAdapter(Context context, List<DeputadoFederal> lista){
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
		DeputadoFederal l = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.deputado_federal_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView nome = (TextView) view1.findViewById(R.id.nome);
		nome.setText(String.valueOf(l.getNome()));
		TextView numero = (TextView) view1.findViewById(R.id.numero);
		numero.setText(String.valueOf(l.getNumero()));
		TextView partido = (TextView) view1.findViewById(R.id.partido);
		partido.setText(String.valueOf(l.getPartido()));
		TextView votos = (TextView) view1.findViewById(R.id.votos);
		votos.setText(String.valueOf(l.getVotos()));



		return view1;
	}

}
