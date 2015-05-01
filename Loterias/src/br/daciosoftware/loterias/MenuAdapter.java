package br.daciosoftware.loterias;

import java.util.ArrayList;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> lista;
	private int icone;
	
	public MenuAdapter(Context context, ArrayList<String> lista, int icone){
		this.context = context;
		this.lista = lista;
		this.icone = icone;
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
		View v = inflater.inflate(R.layout.activity_menu_adapter_loterias, null);
		TextView textMenu = (TextView) v.findViewById(R.id.textMenu);
		textMenu.setText(lista.get(position));
		ImageView img = (ImageView) v.findViewById(R.id.iconMenu);
		img.setImageResource(icone);
		
		return v;
	}
}