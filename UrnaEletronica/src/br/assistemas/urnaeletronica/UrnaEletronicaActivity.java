package br.assistemas.urnaeletronica;

import java.util.ArrayList;

import br.assistemas.urnaeletronica.DeputadoFederal.DeputadoFederalColunas;
import br.assistemas.urnaeletronica.DeputadoEstadual.DeputadoEstadualColunas;
import br.assistemas.urnaeletronica.Governador.GovernadorColunas;
import br.assistemas.urnaeletronica.Presidente.PresidenteColunas;
import br.assistemas.urnaeletronica.Senador.SenadorColunas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.MenuItem;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class UrnaEletronicaActivity extends Activity implements
	OnItemClickListener {
    /** Called when the activity is first created. */
    private int tipoVoto = 0;/*
			      * 1-DeputadoEstadual 2-DeputadoFederal 3-Senador
			      * 4-Governador 5-Presidente 6-Partido 7-Nulo
			      */
    private int tipoCargo = 1;/*
			       * 1-DeputadoEstadual 2-DdeputadoFederal 3-Senador
			       * 4-Governador 5-Presidente
			       */
    private boolean habConfirma = false;
    private static final String CATEGORIA = "urna";
    private MediaPlayer mp = null;
    private Context context = this;
    private ListView listViewMenu;

    /* MENU PARA CELULAR */
    private static final int DEPUTADO_ESTADUAL = 0;
    private static final int DEPUTADO_FEDERAL = 1;
    private static final int SENADOR = 2;
    private static final int GOVERNADOR = 3;
    private static final int PRESIDENTE = 4;
    private static final int PARTIDOS = 5;
    private static final int VOTACAO = 6;
    private static final int SAIR = 7;

    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	//setContentView(R.layout.layout_urna_tablet);
	setContentView(R.layout.layout_urna_celular);
	((TextView) findViewById(R.id.cargo)).setText("DEPUTADO(A) ESTADUAL");

	limpaDados();

	Button bt1 = (Button) findViewById(R.id.bt1);
	bt1.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt1();
	    }
	});

	Button bt2 = (Button) findViewById(R.id.bt2);
	bt2.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt2();
	    }
	});

	Button bt3 = (Button) findViewById(R.id.bt3);
	bt3.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt3();
	    }
	});

	Button bt4 = (Button) findViewById(R.id.bt4);
	bt4.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt4();
	    }
	});

	Button bt5 = (Button) findViewById(R.id.bt5);
	bt5.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt5();
	    }
	});

	Button bt6 = (Button) findViewById(R.id.bt6);
	bt6.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt6();
	    }
	});

	Button bt7 = (Button) findViewById(R.id.bt7);
	bt7.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt7();
	    }
	});

	Button bt8 = (Button) findViewById(R.id.bt8);
	bt8.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt8();
	    }
	});

	Button bt9 = (Button) findViewById(R.id.bt9);
	bt9.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt9();
	    }
	});

	Button bt0 = (Button) findViewById(R.id.bt0);
	bt0.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		tocaSom(1);
		onClickBt0();
	    }
	});

	((EditText) findViewById(R.id.edNum1))
		.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start,
			    int before, int count) {
			// TODO Auto-generated method stub
			// ((EditText)
			// findViewById(R.id.edNum2)).requestFocus();
		    }

		    public void beforeTextChanged(CharSequence s, int start,
			    int count, int after) {
			// TODO Auto-generated method stub

		    }

		    public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
			    ((EditText) findViewById(R.id.edNum2))
				    .requestFocus();
			}
		    }
		});

	((EditText) findViewById(R.id.edNum2))
		.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start,
			    int before, int count) {
			// TODO Auto-generated method stub
			// ((EditText)
			// findViewById(R.id.edNum3)).requestFocus();
		    }

		    public void beforeTextChanged(CharSequence s, int start,
			    int count, int after) {
			// TODO Auto-generated method stub
		    }

		    public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
			    // showMessage("Teste","Cargo: " +
			    // Integer.toString(tipoCargo));
			    if ((tipoCargo == 1) || (tipoCargo == 2)
				    || (tipoCargo == 3)) {
				buscaPartido(getNumeroPartido());
			    } else if (tipoCargo == 4) {
				buscaGovernador(getNumeroPartido());
			    } else if (tipoCargo == 5) {
				buscaPresidente(getNumeroPartido());
			    }

			    ((EditText) findViewById(R.id.edNum3))
				    .requestFocus();
			}
		    }
		});

	((EditText) findViewById(R.id.edNum3))
		.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start,
			    int before, int count) {
			// TODO Auto-generated method stub
			// ((EditText)
			// findViewById(R.id.edNum2)).requestFocus();
		    }

		    public void beforeTextChanged(CharSequence s, int start,
			    int count, int after) {
			// TODO Auto-generated method stub

		    }

		    public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
			    if (s.length() > 0) {
				if (tipoCargo == 3) {
				    buscaSenador(getNumeroSenador());
				}
				((EditText) findViewById(R.id.edNum4))
					.requestFocus();
			    }
			}
		    }
		});

	((EditText) findViewById(R.id.edNum4))
		.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start,
			    int before, int count) {
			// TODO Auto-generated method stub
			// ((EditText)
			// findViewById(R.id.edNum2)).requestFocus();
		    }

		    public void beforeTextChanged(CharSequence s, int start,
			    int count, int after) {
			// TODO Auto-generated method stub

		    }

		    public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
			    if (tipoCargo == 2) {
				buscaDeputadoFederal(getNumeroDeputadoFederal());
			    }
			    ((EditText) findViewById(R.id.edNum5))
				    .requestFocus();
			}
		    }
		});

	((EditText) findViewById(R.id.edNum5))
		.addTextChangedListener(new TextWatcher() {

		    public void onTextChanged(CharSequence s, int start,
			    int before, int count) {
			// TODO Auto-generated method stub

		    }

		    public void beforeTextChanged(CharSequence s, int start,
			    int count, int after) {
			// TODO Auto-generated method stub

		    }

		    public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.length() > 0) {
			    if (tipoCargo == 1) {
				buscaDeputadoEstadual(getNumeroDeputadoEstadual());
			    }
			}

		    }
		});

	Button btBranco = (Button) findViewById(R.id.bt_branco);
	btBranco.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		onClickBtBranco();
	    }
	});

	Button btCorrige = (Button) findViewById(R.id.bt_corrige);
	btCorrige.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		onClickBtCorrige();
	    }
	});

	Button btConfirma = (Button) findViewById(R.id.bt_confirma);
	btConfirma.setOnClickListener(new OnClickListener() {
	    public void onClick(View v) {
		onClickBtConfirma();
	    }
	});
	setVolumeControlStream(AudioManager.STREAM_MUSIC);

	ArrayList<String> itensMenu = new ArrayList<String>();
	itensMenu.add("Deputado(a) Estadual");
	itensMenu.add("Deputado(a) Federal");
	itensMenu.add("Senador(a)");
	itensMenu.add("Governador(a)");
	itensMenu.add("Presidente(a)");
	itensMenu.add("Partidos");
	itensMenu.add("Votação");
	itensMenu.add("Sair");

	MenuAdapter adapter = new MenuAdapter(this, itensMenu);
	listViewMenu = (ListView) findViewById(R.id.listViewMenu);
	listViewMenu.setAdapter(adapter);
	listViewMenu.setOnItemClickListener(this);

    }

    public boolean onCreateOptionsMenu(android.view.Menu menu) {
	super.onCreateOptionsMenu(menu);
	menu.add(0, DEPUTADO_ESTADUAL, 0, "Deputado(a) Estadual");
	menu.add(0, DEPUTADO_FEDERAL, 0, "Deputado(a) Federal");
	menu.add(0, SENADOR, 0, "Senador");
	menu.add(0, GOVERNADOR, 0, "Governador");
	menu.add(0, PRESIDENTE, 0, "Presidente");
	menu.add(0, PARTIDOS, 0, "Partidos");
	menu.add(0, VOTACAO, 0, "Votação");
	menu.add(0, SAIR, 0, "Sair");
	return true;
    }

    public boolean onMenuItemSelected(int featured, MenuItem item) {
	switch (item.getItemId()) {
	case DEPUTADO_ESTADUAL:
	    Intent it = new Intent(this, CadastroDeputadoEstadual.class);
	    startActivity(it);
	    return true;
	case DEPUTADO_FEDERAL:
	    Intent it2 = new Intent(this, CadastroDeputadoFederal.class);
	    startActivity(it2);
	    return true;
	case SENADOR:
	    Intent it3 = new Intent(this, CadastroSenador.class);
	    startActivity(it3);
	    return true;

	case GOVERNADOR:
	    Intent it4 = new Intent(this, CadastroGovernador.class);
	    startActivity(it4);
	    return true;

	case PRESIDENTE:
	    Intent it5 = new Intent(this, CadastroPresidente.class);
	    startActivity(it5);
	    return true;

	case PARTIDOS:
	    Intent it6 = new Intent(this, CadastroPartidos.class);
	    startActivity(it6);
	    return true;

	case VOTACAO:
	    Intent it7 = new Intent(this, Votacao.class);
	    startActivity(it7);
	    return true;

	case SAIR:
	    finish();

	}
	return false;
    }

    public EditText getEdtInFocu() {
	EditText edtReturn = null;

	final EditText edNum1 = (EditText) findViewById(R.id.edNum1);
	final EditText edNum2 = (EditText) findViewById(R.id.edNum2);
	final EditText edNum3 = (EditText) findViewById(R.id.edNum3);
	final EditText edNum4 = (EditText) findViewById(R.id.edNum4);
	final EditText edNum5 = (EditText) findViewById(R.id.edNum5);

	String txtNum1 = edNum1.getText().toString();
	String txtNum2 = edNum2.getText().toString();
	String txtNum3 = edNum3.getText().toString();
	String txtNum4 = edNum4.getText().toString();
	String txtNum5 = edNum5.getText().toString();

	if (txtNum1.equals("")) {
	    edtReturn = edNum1;
	} else {
	    if (txtNum2.equals("")) {
		edtReturn = edNum2;
	    } else {
		if (txtNum3.equals("")) {
		    edtReturn = edNum3;
		} else {
		    if (txtNum4.equals("")) {
			edtReturn = edNum4;

		    } else {
			if (txtNum5.equals("")) {
			    edtReturn = edNum5;
			}
		    }
		}
	    }
	}
	return edtReturn;
    }// Fim do metodo de retorno do edt em focu;

    public boolean edtVazio() {
	boolean r = false;
	final EditText edNum1 = (EditText) findViewById(R.id.edNum1);
	final EditText edNum2 = (EditText) findViewById(R.id.edNum2);
	final EditText edNum3 = (EditText) findViewById(R.id.edNum3);
	final EditText edNum4 = (EditText) findViewById(R.id.edNum4);
	final EditText edNum5 = (EditText) findViewById(R.id.edNum5);

	String txtNum1 = edNum1.getText().toString();
	String txtNum2 = edNum2.getText().toString();
	String txtNum3 = edNum3.getText().toString();
	String txtNum4 = edNum4.getText().toString();
	String txtNum5 = edNum5.getText().toString();
	if ((txtNum1.equals("")) || (txtNum2.equals(""))
		|| (txtNum3.equals("")) || (txtNum4.equals(""))
		|| (txtNum5.equals("")))
	    r = true;
	else
	    r = false;
	return r;
    }

    public void limpaDados() {
	((EditText) findViewById(R.id.edNum1)).setText("");
	((EditText) findViewById(R.id.edNum2)).setText("");
	((EditText) findViewById(R.id.edNum3)).setText("");
	((EditText) findViewById(R.id.edNum4)).setText("");
	((EditText) findViewById(R.id.edNum5)).setText("");

	((TextView) findViewById(R.id.label1)).setText("");
	((TextView) findViewById(R.id.label2)).setText("");
	((TextView) findViewById(R.id.label3)).setText("");
	((TextView) findViewById(R.id.label6)).setText("");
	((TextView) findViewById(R.id.nomeCandidato)).setText("");
	((TextView) findViewById(R.id.partidoCandidato)).setText("");
	((TextView) findViewById(R.id.nomeVice)).setText("");

	((TextView) findViewById(R.id.msg1)).setText("");
	((TextView) findViewById(R.id.msg2)).setText("");
	((TextView) findViewById(R.id.msg3)).setText("");
	((TextView) findViewById(R.id.msg4)).setText("");

	((ImageView) findViewById(R.id.foto))
		.setImageResource(R.drawable.foto_modelo);
	((ImageView) findViewById(R.id.fotov))
		.setImageResource(R.drawable.foto_modelo);
	((EditText) findViewById(R.id.edNum1)).requestFocus();
	((TextView) findViewById(R.id.label_numero)).setText("Número:");
	if (tipoCargo == 1) {
	    ((EditText) findViewById(R.id.edNum1)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum2)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum3)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum4)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum5)).setVisibility(View.VISIBLE);
	}
	if (tipoCargo == 2) {
	    ((EditText) findViewById(R.id.edNum1)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum2)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum3)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum4)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum5))
		    .setVisibility(View.INVISIBLE);
	}
	if (tipoCargo == 3) {
	    ((EditText) findViewById(R.id.edNum1)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum2)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum3)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum4))
		    .setVisibility(View.INVISIBLE);
	    ((EditText) findViewById(R.id.edNum5))
		    .setVisibility(View.INVISIBLE);
	}
	if ((tipoCargo == 4) || (tipoCargo == 5)) {
	    ((EditText) findViewById(R.id.edNum1)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum2)).setVisibility(View.VISIBLE);
	    ((EditText) findViewById(R.id.edNum3))
		    .setVisibility(View.INVISIBLE);
	    ((EditText) findViewById(R.id.edNum4))
		    .setVisibility(View.INVISIBLE);
	    ((EditText) findViewById(R.id.edNum5))
		    .setVisibility(View.INVISIBLE);
	}
    }

    public String getNumeroPartido() {
	return ((EditText) findViewById(R.id.edNum1)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum2)).getText().toString();
    }

    public String getNumeroDeputadoEstadual() {
	return ((EditText) findViewById(R.id.edNum1)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum2)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum3)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum4)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum5)).getText().toString();
    }

    public String getNumeroDeputadoFederal() {
	return ((EditText) findViewById(R.id.edNum1)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum2)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum3)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum4)).getText().toString();
    }

    public String getNumeroSenador() {
	return ((EditText) findViewById(R.id.edNum1)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum2)).getText().toString()
		+ ((EditText) findViewById(R.id.edNum3)).getText().toString();

    }

    public void onClickBt1() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("1");

	}
    }

    public void onClickBt2() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("2");

	}
    }

    public void onClickBt3() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("3");

	}
    }

    public void onClickBt4() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("4");

	}
    }

    public void onClickBt5() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("5");

	}
    }

    public void onClickBt6() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("6");

	}
    }

    public void onClickBt7() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("7");

	}
    }

    public void onClickBt8() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("8");

	}
    }

    public void onClickBt9() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("9");

	}
    }

    public void onClickBt0() {
	if (edtVazio()) {
	    final EditText edEmFocu = getEdtInFocu();
	    edEmFocu.setText("0");

	}
    }

    public void onClickBtBranco() {
	tocaSom(1);
	((EditText) findViewById(R.id.edNum1)).setVisibility(View.INVISIBLE);
	((EditText) findViewById(R.id.edNum2)).setVisibility(View.INVISIBLE);
	((EditText) findViewById(R.id.edNum3)).setVisibility(View.INVISIBLE);
	((EditText) findViewById(R.id.edNum4)).setVisibility(View.INVISIBLE);
	((EditText) findViewById(R.id.edNum5)).setVisibility(View.INVISIBLE);
	((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	((TextView) findViewById(R.id.label_numero)).setText("");
	((TextView) findViewById(R.id.msg1)).setText("VOTO EM BRANCO");
	((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	((TextView) findViewById(R.id.msg3))
		.setText("VERDE para CONFIRMAR (Voto em branco)");
	((TextView) findViewById(R.id.msg4)).setText("LARANJA para CORRIGIR");
	habConfirma = true;
	tipoVoto = 8;// Voto em Branco
    }

    public void onClickBtCorrige() {
	tocaSom(1);
	habConfirma = false;
	limpaDados();
    }

    public void onClickBtConfirma() {
	if (habConfirma) {
	    if (tipoCargo == 1) {
		if (tipoVoto == 1) {
		    setVotoDeputadoEstadual(getNumeroDeputadoEstadual());
		} else if (tipoVoto == 6) {
		    setVotoPartido(getNumeroPartido(), "E");
		} else if (tipoVoto == 7) {
		    setVotoNulo("E");
		} else if (tipoVoto == 8) {
		    setVotoBranco("E");
		}
		tipoCargo = 2;
		((TextView) findViewById(R.id.cargo))
			.setText("DEPUTADO(A) FEDERAL");
		((TextView) findViewById(R.id.label_numero)).setText("Número:");
		((EditText) findViewById(R.id.edNum1))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum2))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum3))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum4))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum5))
			.setVisibility(View.INVISIBLE);
		tocaSom(2);
	    } else if (tipoCargo == 2) {
		if (tipoVoto == 2) {
		    setVotoDeputadoFederal(getNumeroDeputadoFederal());
		} else if (tipoVoto == 6) {
		    setVotoPartido(getNumeroPartido(), "F");
		} else if (tipoVoto == 7) {
		    setVotoNulo("F");
		} else if (tipoVoto == 8) {
		    setVotoBranco("F");
		}
		tipoCargo = 3;
		((TextView) findViewById(R.id.cargo)).setText("SENADOR(A)");
		((TextView) findViewById(R.id.label_numero)).setText("Número:");
		((EditText) findViewById(R.id.edNum1))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum2))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum3))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum4))
			.setVisibility(View.INVISIBLE);
		((EditText) findViewById(R.id.edNum5))
			.setVisibility(View.INVISIBLE);
		tocaSom(2);

	    } else if (tipoCargo == 3) {
		if (tipoVoto == 3) {
		    setVotoSenador(getNumeroSenador());
		} else if (tipoVoto == 6) {
		    setVotoPartido(getNumeroPartido(), "S");
		} else if (tipoVoto == 7) {
		    setVotoNulo("S");
		} else if (tipoVoto == 8) {
		    setVotoBranco("S");
		}
		tipoCargo = 4;
		((TextView) findViewById(R.id.cargo)).setText("GOVERNADOR(A)");
		((TextView) findViewById(R.id.label_numero)).setText("Número:");
		((EditText) findViewById(R.id.edNum1))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum2))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum3))
			.setVisibility(View.INVISIBLE);
		((EditText) findViewById(R.id.edNum4))
			.setVisibility(View.INVISIBLE);
		((EditText) findViewById(R.id.edNum5))
			.setVisibility(View.INVISIBLE);
		tocaSom(2);

	    } else if (tipoCargo == 4) {
		if (tipoVoto == 4) {
		    setVotoGovernador(getNumeroPartido());
		} else if (tipoVoto == 6) {
		    setVotoPartido(getNumeroPartido(), "G");
		} else if (tipoVoto == 7) {
		    setVotoNulo("G");
		} else if (tipoVoto == 8) {
		    setVotoBranco("G");
		}
		tipoCargo = 5;
		((TextView) findViewById(R.id.cargo)).setText("PRESIDENTE(A)");
		((TextView) findViewById(R.id.label_numero)).setText("Número:");
		((EditText) findViewById(R.id.edNum1))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum2))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum3))
			.setVisibility(View.INVISIBLE);
		((EditText) findViewById(R.id.edNum4))
			.setVisibility(View.INVISIBLE);
		((EditText) findViewById(R.id.edNum5))
			.setVisibility(View.INVISIBLE);
		tocaSom(2);

	    }

	    else if (tipoCargo == 5) {
		if (tipoVoto == 5) {
		    setVotoPresidente(getNumeroPartido());
		} else if (tipoVoto == 7) {
		    setVotoNulo("P");
		} else if (tipoVoto == 8) {
		    setVotoBranco("P");
		}

		((TextView) findViewById(R.id.cargo))
			.setText("DEPUTADO(A) ESTADUAL");
		((TextView) findViewById(R.id.label_numero)).setText("Número:");
		((EditText) findViewById(R.id.edNum1))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum2))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum3))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum4))
			.setVisibility(View.VISIBLE);
		((EditText) findViewById(R.id.edNum5))
			.setVisibility(View.VISIBLE);
		tipoVoto = 1;
		tipoCargo = 1;
		tocaSom(3);
		showMessage("VOTO", "**** FIM DA VOTAÇÃO **** ");
	    }
	    habConfirma = false;
	    limpaDados();
	}
    }

    // @SuppressLint("ParserError")
    public void tocaSom(final int indice) {
	new Thread() {
	    public void run() {
		int pathMp3 = 0;
		switch (indice) {
		case 1:
		    pathMp3 = R.raw.toque1;
		    break;
		case 2:
		    pathMp3 = R.raw.toque2;
		    break;
		case 3:
		    pathMp3 = R.raw.toque3;
		    break;
		}
		try {
		    if (mp != null) {
			mp.stop();
		    }
		    mp = MediaPlayer.create(context, pathMp3);
		    mp.start();
		} catch (Exception e) {
		    showMessage("Tocar Som", "Error: " + e);
		}

	    }

	}.start();
    }

    public void showMessage(String titulo, String menssagem) {
	AlertDialog.Builder msg = new AlertDialog.Builder(this);
	msg.setTitle(titulo);
	msg.setMessage(menssagem);
	msg.setNeutralButton("OK", null);
	msg.show();
    }

    public void buscaPartido(String numero) {
	RepositorioPartido repositorio = new RepositorioPartido(this);
	Partidos p = repositorio.buscarPartidoNumero(numero);
	if (p != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("");
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(p
		    .getNome());

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla: ");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto de Legenda)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 6;// Voto na legenda

	} else {
	    ((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla: ");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto Nulo)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 7;// Voto nulo

	}
	repositorio.Fechar();
	habConfirma = true;
    }

    public void buscaDeputadoEstadual(String numero) {
	RepositorioDeputadoEstadual repositorio = new RepositorioDeputadoEstadual(
		this);
	DeputadoEstadual d = repositorio.buscarDeputadoNumero(numero);

	if (d != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("Nome: ");
	    ((TextView) findViewById(R.id.nomeCandidato)).setText(d.getNome());
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(d
		    .getPartido());
	    Bitmap foto = BitmapFactory.decodeFile(d.getFoto());
	    ((ImageView) findViewById(R.id.foto)).setImageBitmap(foto);

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 1;

	} else {
	    if (tipoVoto == 4) {// se nao tiver acertado o numero do partido
		((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
		((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
		((TextView) findViewById(R.id.msg3))
			.setText("VERDE para CONFIRMAR (Voto Nulo)");
		((TextView) findViewById(R.id.msg4))
			.setText("LARANJA para CORRIGIR");

	    } else if (tipoVoto == 3) {
		((TextView) findViewById(R.id.msg1)).setText("");
		((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
		((TextView) findViewById(R.id.msg3))
			.setText("VERDE para CONFIRMAR (Voto de Legenda)");
		((TextView) findViewById(R.id.msg4))
			.setText("LARANJA para CORRIGIR");
	    }
	}
	repositorio.Fechar();
	habConfirma = true;
    }

    public void buscaDeputadoFederal(String numero) {
	RepositorioDeputadoFederal repositorio = new RepositorioDeputadoFederal(
		this);
	DeputadoFederal d = repositorio.buscarDeputadoNumero(numero);

	if (d != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("Nome: ");
	    ((TextView) findViewById(R.id.nomeCandidato)).setText(d.getNome());
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(d
		    .getPartido());
	    Bitmap foto = BitmapFactory.decodeFile(d.getFoto());
	    ((ImageView) findViewById(R.id.foto)).setImageBitmap(foto);

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 2;

	} else {
	    ((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto Nulo)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 7;
	}
	repositorio.Fechar();
	habConfirma = true;
    }

    public void buscaSenador(String numero) {

	RepositorioSenador repositorio = new RepositorioSenador(this);
	Senador s = repositorio.buscarSenadorNumero(numero);

	if (s != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("Nome: ");
	    ((TextView) findViewById(R.id.nomeCandidato)).setText(s.getNome());
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(s
		    .getPartido());
	    Bitmap foto = BitmapFactory.decodeFile(s.getFoto());
	    ((ImageView) findViewById(R.id.foto)).setImageBitmap(foto);

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 3;

	} else {
	    ((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto Nulo)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 7;
	}
	repositorio.Fechar();
	habConfirma = true;

    }

    public void buscaGovernador(String numero) {
	RepositorioGovernador repositorio = new RepositorioGovernador(this);
	Governador g = repositorio.buscarGovernadorNumero(numero);

	if (g != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("Nome: ");
	    ((TextView) findViewById(R.id.nomeCandidato)).setText(g.getNome());
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(g
		    .getPartido());
	    Bitmap foto = BitmapFactory.decodeFile(g.getFoto());
	    ((ImageView) findViewById(R.id.foto)).setImageBitmap(foto);

	    ((TextView) findViewById(R.id.label6))
		    .setText("Vice-governador(a): ");
	    ((TextView) findViewById(R.id.nomeVice)).setText(g
		    .getNomeViceGovernador());

	    Bitmap fotov = BitmapFactory.decodeFile(g.getFotoViceGovernador());
	    ((ImageView) findViewById(R.id.fotov)).setImageBitmap(fotov);

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 5;

	} else {
	    ((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto Nulo)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 7;
	}
	repositorio.Fechar();
	habConfirma = true;
    }

    public void buscaPresidente(String numero) {

	RepositorioPresidente repositorio = new RepositorioPresidente(this);
	Presidente p = repositorio.buscarPresidenteNumero(numero);

	if (p != null) {
	    ((TextView) findViewById(R.id.label1)).setText("Seu voto para: ");
	    ((TextView) findViewById(R.id.label2)).setText("Nome: ");
	    ((TextView) findViewById(R.id.nomeCandidato)).setText(p.getNome());
	    ((TextView) findViewById(R.id.label3)).setText("Partido: ");
	    ((TextView) findViewById(R.id.partidoCandidato)).setText(p
		    .getPartido());
	    Bitmap foto = BitmapFactory.decodeFile(p.getFoto());
	    ((ImageView) findViewById(R.id.foto)).setImageBitmap(foto);

	    ((TextView) findViewById(R.id.label6))
		    .setText("Vice-presidente(a): ");
	    ((TextView) findViewById(R.id.nomeVice)).setText(p
		    .getNomeVicePresidente());

	    Bitmap fotov = BitmapFactory.decodeFile(p.getFotoVicePresidente());
	    ((ImageView) findViewById(R.id.fotov)).setImageBitmap(fotov);

	    ((TextView) findViewById(R.id.msg1)).setText("");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 5;

	} else {
	    ((TextView) findViewById(R.id.msg1)).setText("NUMERO ERRADO!");
	    ((TextView) findViewById(R.id.msg2)).setText("Aperte a Tecla:");
	    ((TextView) findViewById(R.id.msg3))
		    .setText("VERDE para CONFIRMAR (Voto Nulo)");
	    ((TextView) findViewById(R.id.msg4))
		    .setText("LARANJA para CORRIGIR");
	    tipoVoto = 7;
	}
	repositorio.Fechar();
	habConfirma = true;

    }

    /** Método para adicionar voto ao Partido **/
    public void setVotoPartido(String partido, String cargo) {

	RepositorioVotosLegenda repositorio = new RepositorioVotosLegenda(this);
	repositorio.setVotosLegenda(partido, cargo);
	repositorio.Fechar();
    }

    /** Método para adicionar voto ao DeputadoEstadual **/
    public void setVotoDeputadoEstadual(String numero) {
	RepositorioDeputadoEstadual repositorio = new RepositorioDeputadoEstadual(
		this);
	DeputadoEstadual v = repositorio.buscarDeputadoNumero(numero);
	int totalVotos = 0;
	if (v != null) {
	    totalVotos = v.getVotos();
	    totalVotos++;
	}
	ContentValues values = new ContentValues();
	values.put(DeputadoEstadualColunas.VOTOS, totalVotos);
	String numeroV = String.valueOf(v.getNumero());
	String where = DeputadoEstadualColunas.NUMERO + "=?";
	String[] whereArgs = new String[] { numeroV };

	repositorio.Atualizar(values, where, whereArgs);
	repositorio.Fechar();

    }

    /** Método para adicionar voto ao DeputadoFederal **/
    public void setVotoDeputadoFederal(String numero) {
	RepositorioDeputadoFederal repositorio = new RepositorioDeputadoFederal(
		this);
	DeputadoFederal d = repositorio.buscarDeputadoNumero(numero);
	int totalVotos = 0;
	if (d != null) {
	    totalVotos = d.getVotos();
	    totalVotos++;
	}
	ContentValues values = new ContentValues();
	values.put(DeputadoFederalColunas.VOTOS, totalVotos);
	String numeroD = String.valueOf(d.getNumero());
	String where = DeputadoFederalColunas.NUMERO + "=?";
	String[] whereArgs = new String[] { numeroD };

	repositorio.Atualizar(values, where, whereArgs);
	repositorio.Fechar();

    }

    /** Método para adicionar voto ao Senador **/
    public void setVotoSenador(String numero) {

	RepositorioSenador repositorio = new RepositorioSenador(this);
	Senador s = repositorio.buscarSenadorNumero(numero);
	int totalVotos = 0;
	if (s != null) {
	    totalVotos = s.getVotos();
	    totalVotos++;
	}
	ContentValues values = new ContentValues();
	values.put(SenadorColunas.VOTOS, totalVotos);
	String numeroS = String.valueOf(s.getNumero());
	String where = SenadorColunas.NUMERO + "=?";
	String[] whereArgs = new String[] { numeroS };

	repositorio.Atualizar(values, where, whereArgs);
	repositorio.Fechar();

    }

    /** Método para adicionar voto ao Presidente **/
    public void setVotoPresidente(String numero) {

	RepositorioPresidente repositorio = new RepositorioPresidente(this);
	Presidente p = repositorio.buscarPresidenteNumero(numero);
	int totalVotos = 0;
	if (p != null) {
	    totalVotos = p.getVotos();
	    totalVotos++;
	}
	ContentValues values = new ContentValues();
	values.put(PresidenteColunas.VOTOS, totalVotos);
	String numeroP = String.valueOf(p.getNumero());
	String where = PresidenteColunas.NUMERO + "=?";
	String[] whereArgs = new String[] { numeroP };

	repositorio.Atualizar(values, where, whereArgs);
	repositorio.Fechar();

    }

    /** Método para adicionar voto nulo **/
    public void setVotoNulo(String cargo) {
	final String tipo = "N";
	RepositorioVotosBrancosNulos repositorio = new RepositorioVotosBrancosNulos(
		this);
	repositorio.setVotosBrancoNulos(tipo, cargo);
	repositorio.Fechar();
    }

    /** Método para adicionar voto branco **/
    public void setVotoBranco(String cargo) {
	final String tipo = "B";
	RepositorioVotosBrancosNulos repositorio = new RepositorioVotosBrancosNulos(
		this);
	repositorio.setVotosBrancoNulos(tipo, cargo);
	repositorio.Fechar();
    }

    /** Método para adicionar voto governador **/
    public void setVotoGovernador(String numero) {
	RepositorioGovernador repositorio = new RepositorioGovernador(this);
	Governador g = repositorio.buscarGovernadorNumero(numero);
	Log.i(CATEGORIA, "Numero do Governador para setar o voto: " + numero);
	Log.i(CATEGORIA, "Nome do Governador para setar o voto: " + g.getNome());
	int totalVotos = 0;
	if (g != null) {
	    totalVotos = g.getVotos();
	    totalVotos++;
	}
	ContentValues values = new ContentValues();
	values.put(GovernadorColunas.VOTOS, totalVotos);
	String numeroP = String.valueOf(g.getNumero());
	String where = GovernadorColunas.NUMERO + "=?";
	String[] whereArgs = new String[] { numeroP };

	repositorio.Atualizar(values, where, whereArgs);
	repositorio.Fechar();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	// Toast.makeText(context, "Posição"+ String.valueOf(position),
	// Toast.LENGTH_SHORT).show();
	switch (position) {
	case 0:
	    Intent it = new Intent(this, CadastroDeputadoEstadual.class);
	    startActivity(it);
	    break;

	case 1:
	    Intent it2 = new Intent(this, CadastroDeputadoFederal.class);
	    startActivity(it2);
	    break;

	case 2:

	    Intent it3 = new Intent(this, CadastroSenador.class);
	    startActivity(it3);
	    break;

	case 3:
	    Intent it4 = new Intent(this, CadastroGovernador.class);
	    startActivity(it4);
	    break;

	case 4:
	    Intent it5 = new Intent(this, CadastroPresidente.class); //
	    startActivity(it5);
	    break;
	case 5:
	    Intent it6 = new Intent(this, CadastroPartidos.class);
	    startActivity(it6);
	    break;

	case 6:
	    Intent it7 = new Intent(this, Votacao.class);
	    startActivity(it7);
	    break;

	case 7:
	    finish();
	}

    }

}
/** Fim da Classe Principal **/
