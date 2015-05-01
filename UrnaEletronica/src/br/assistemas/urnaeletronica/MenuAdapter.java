package br.assistemas.urnaeletronica;

import java.util.ArrayList;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> lista;

	
	public MenuAdapter(Context context, ArrayList<String> lista){
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
	
	public View getView(int position, View contentView, ViewGroup parent){
		LayoutInflater  inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.menu_adapter, null);
		TextView textMenu = (TextView) v.findViewById(R.id.textMenu);
		textMenu.setText(lista.get(position));
		return v;
	}
}