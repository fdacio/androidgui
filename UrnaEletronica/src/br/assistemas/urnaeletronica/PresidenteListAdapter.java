package br.assistemas.urnaeletronica;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PresidenteListAdapter extends BaseAdapter{
	private Context context;
	private List<Presidente> lista;
	
	public PresidenteListAdapter(Context context, List<Presidente> lista){
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
		//Recupera o prefeito da posição atual
		Presidente p = lista.get(position);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view1 = inflater.inflate(R.layout.presidente_linha_adapter, null);
		//Atualiza o valor do TextView
		TextView nome = (TextView) view1.findViewById(R.id.nome);
		nome.setText(p.getNome());
		TextView numero = (TextView) view1.findViewById(R.id.numero);
		numero.setText(String.valueOf(p.getNumero()));
		TextView partido = (TextView) view1.findViewById(R.id.partido);
		partido.setText(p.getPartido());
		TextView nomeVice = (TextView) view1.findViewById(R.id.nome_vice);
		nomeVice.setText(p.getNomeVicePresidente());
		TextView votos = (TextView) view1.findViewById(R.id.votos);
		votos.setText(String.valueOf(p.getVotos()));
		return view1;
	}

}
