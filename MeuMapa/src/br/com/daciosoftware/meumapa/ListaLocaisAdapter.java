package br.com.daciosoftware.meumapa;

import java.util.List;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import br.com.daciosoftware.meumapa.db.Localizacao;
import br.com.daciosoftware.meumapa.db.MeuMapaDatabase;

public class ListaLocaisAdapter extends BaseAdapter {

    private Context context;
    private List<Localizacao> lista;

    public ListaLocaisAdapter(Context context, List<Localizacao> lista) {
	this.context = context;
	this.lista = lista;
    }

    @Override
    public int getCount() {
	return lista.size();
    }

    @Override
    public Object getItem(int position) {
	return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
	return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	Localizacao l = lista.get(position);
	LayoutInflater inflater = (LayoutInflater) context
		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View view1 = inflater.inflate(R.layout.activity_lista_locais_linha,
		null);
	TextView nomeLocal = (TextView) view1
		.findViewById(R.id.textViewNomeLocal);
	ImageButton btnEdit = (ImageButton) view1.findViewById(R.id.btnEdit);
	ImageButton btnDelete = (ImageButton) view1
		.findViewById(R.id.btnDelete);
	ImageButton btnGoMap = (ImageButton) view1.findViewById(R.id.btnGoMap);
	nomeLocal.setText(String.valueOf(l.getNomeLocal()));

	btnEdit.setOnClickListener(new OnEditLocalListener(l));
	btnDelete.setOnClickListener(new OnDeleteLocalListener(l));
	btnGoMap.setOnClickListener(new OnGoMapLocalListener(l));
	buttonEffect(btnEdit);
	buttonEffect(btnDelete);
	buttonEffect(btnGoMap);
	return view1;
    }

    private class OnDeleteLocalListener implements OnClickListener {

	private Localizacao l;

	public OnDeleteLocalListener(Localizacao l) {
	    this.l = l;
	}

	@Override
	public void onClick(View v) {
	    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
	    builder.setMessage("Deseja deletar este registro?")
		    .setCancelable(false)
		    .setPositiveButton("Sim",
			    new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
					int id) {
				    MeuMapaDatabase.deleteLocalizacao(l);
				}
			    })
		    .setNegativeButton("Não",
			    new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
					int id) {
				    dialog.cancel();
				}
			    });
	    final AlertDialog alert = builder.create();
	    alert.show();

	}

    }

    private class OnEditLocalListener implements OnClickListener {

	private Localizacao l;

	public OnEditLocalListener(Localizacao l) {
	    this.l = l;
	}

	@Override
	public void onClick(View v) {
	    Intent it = new Intent(context, ActivityEditarLocal.class);
	    it.putExtra("LOCALIZACAO", l);
	    context.startActivity(it);
	}
    }

    private class OnGoMapLocalListener implements OnClickListener {

	private Localizacao l;

	public OnGoMapLocalListener(Localizacao l) {
	    this.l = l;
	}

	@Override
	public void onClick(View v) {
	    Intent it = new Intent(context, MainActivity.class);
	    it.putExtra("LOCALIZACAO", l);
	    context.startActivity(it);

	}
    }

    /**
     * Método que coloca efeito de clicado nos ImageButton da ListaView
     * 
     * @param button
     */
    public static void buttonEffect(View button) {
	button.setOnTouchListener(new OnTouchListener() {

	    public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
		    v.getBackground().setColorFilter(0x77000000,
			    PorterDuff.Mode.SRC_ATOP);
		    v.invalidate();
		    break;
		}
		case MotionEvent.ACTION_UP: {
		    v.getBackground().clearColorFilter();
		    v.invalidate();
		    break;
		}
		}
		return false;
	    }
	});
    }

}
