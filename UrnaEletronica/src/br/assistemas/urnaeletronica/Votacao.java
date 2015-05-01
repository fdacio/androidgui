package br.assistemas.urnaeletronica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;

public class Votacao extends Activity {
    private RepositorioDeputadoEstadual repositorioDeputadoEstadual;
    private RepositorioDeputadoFederal repositorioDeputadoFederal;
    private RepositorioSenador repositorioSenador;
    private RepositorioGovernador repositorioGovernador;
    private RepositorioPresidente repositorioPresidente;
    private RepositorioVotosBrancosNulos repositorioVotosBrancosNulos;
    //private RepositorioVotosLegenda repositorioVotosLegenda;

    private List<DeputadoEstadual> listaDeputadosEstadual;
    private List<DeputadoFederal> listaDeputadosFederal;
    private List<Senador> listaSenadores;
    private List<Governador> listaGovernadores;
    private List<Presidente> listaPresidentes;

    private String directoryFile;
    private static final String ARQUIVO = "VOTACAO.txt";
    private static final int DIRECTORY_BROWSER = 1;

    @Override
    public void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	setContentView(R.layout.form_votacao);

	repositorioVotosBrancosNulos = new RepositorioVotosBrancosNulos(this);
	//repositorioVotosLegenda = new RepositorioVotosLegenda();

	/**
	 * Votação de Deputado Estadual
	 */
	repositorioDeputadoEstadual = new RepositorioDeputadoEstadual(this);
	listaDeputadosEstadual = repositorioDeputadoEstadual
		.listarDeputadosEstadual();
	DeputadoEstadualVotacaoListAdapter adapterDeputadoEstadual = new DeputadoEstadualVotacaoListAdapter(
		this, listaDeputadosEstadual);
	ListView listViewDadosDeputadoEstadual = (ListView) findViewById(R.id.listaDeputadoEstadual);
	listViewDadosDeputadoEstadual.setAdapter(adapterDeputadoEstadual);

	TextView qtdeVotosBrancosDeputadoEstadual = (TextView) findViewById(R.id.qtdeVotosBrancosDeputadoEstadual);
	TextView qtdeVotosNulosDeputadoEstadual = (TextView) findViewById(R.id.qtdeVotosNulosDeputadoEstadual);
	qtdeVotosBrancosDeputadoEstadual.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"B", "E")));
	qtdeVotosNulosDeputadoEstadual.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"N", "E")));

	/**
	 * Votação de Deputado Federal
	 */
	repositorioDeputadoFederal = new RepositorioDeputadoFederal(this);
	listaDeputadosFederal = repositorioDeputadoFederal
		.listarDeputadosFederal();
	DeputadoFederalVotacaoListAdapter adapterDeputadoFederal = new DeputadoFederalVotacaoListAdapter(
		this, listaDeputadosFederal);
	ListView listViewDadosDeputadoFederal = (ListView) findViewById(R.id.listaDeputadoFederal);
	listViewDadosDeputadoFederal.setAdapter(adapterDeputadoFederal);

	TextView qtdeVotosBrancosDeputadoFederal = (TextView) findViewById(R.id.qtdeVotosBrancosDeputadoFederal);
	TextView qtdeVotosNulosDeputadoFederal = (TextView) findViewById(R.id.qtdeVotosNulosDeputadoFederal);
	qtdeVotosBrancosDeputadoFederal.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"B", "F")));
	qtdeVotosNulosDeputadoFederal.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"N", "F")));

	/**
	 * Votação de Senador
	 */
	repositorioSenador = new RepositorioSenador(this);
	listaSenadores = repositorioSenador.listarSenadores();
	SenadorVotacaoListAdapter adapterSenador = new SenadorVotacaoListAdapter(
		this, listaSenadores);
	ListView listViewDadosSenador = (ListView) findViewById(R.id.listaSenador);
	listViewDadosSenador.setAdapter(adapterSenador);

	TextView qtdeVotosBrancosSenador = (TextView) findViewById(R.id.qtdeVotosBrancosSenador);
	TextView qtdeVotosNulosSenador = (TextView) findViewById(R.id.qtdeVotosNulosSenador);
	qtdeVotosBrancosSenador.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"B", "S")));
	qtdeVotosNulosSenador.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"N", "S")));

	/**
	 * Votação de Governador
	 */
	repositorioGovernador = new RepositorioGovernador(this);
	listaGovernadores = repositorioGovernador.listarGovernadores();
	GovernadorVotacaoListAdapter adapterGovernador = new GovernadorVotacaoListAdapter(
		this, listaGovernadores);
	ListView listViewDadosGovernador = (ListView) findViewById(R.id.listaGovernador);
	listViewDadosGovernador.setAdapter(adapterGovernador);

	TextView qtdeVotosBrancosGovernador = (TextView) findViewById(R.id.qtdeVotosBrancosGovernador);
	TextView qtdeVotosNulosGovernador = (TextView) findViewById(R.id.qtdeVotosNulosGovernador);
	qtdeVotosBrancosGovernador.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"B", "G")));
	qtdeVotosNulosGovernador.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"N", "G")));

	/**
	 * Votação de Presidente
	 */
	repositorioPresidente = new RepositorioPresidente(this);
	listaPresidentes = repositorioPresidente.listarPresidentes();
	PresidenteVotacaoListAdapter adapterPresidente = new PresidenteVotacaoListAdapter(
		this, listaPresidentes);
	ListView listViewDadosPresidente = (ListView) findViewById(R.id.listaPresidente);
	listViewDadosPresidente.setAdapter(adapterPresidente);

	TextView qtdeVotosBrancosPresidente = (TextView) findViewById(R.id.qtdeVotosBrancosPresidente);
	TextView qtdeVotosNulosPresidente = (TextView) findViewById(R.id.qtdeVotosNulosPresidente);
	qtdeVotosBrancosPresidente.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"B", "P")));
	qtdeVotosNulosPresidente.setText(String
		.valueOf(repositorioVotosBrancosNulos.totalVotosBrancosNulos(
			"N", "P")));

	Button btnArquivo = (Button) findViewById(R.id.btnArquivo);
	btnArquivo.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		/*
		 * Rotinas para chamar um dialogo para selecao de diretorio.
		 */
		Intent it = new Intent(Votacao.this, FileDialog.class);
		String starPath = android.os.Environment
			.getExternalStorageDirectory().toString();
		it.putExtra(FileDialog.START_PATH, starPath);
		it.putExtra(FileDialog.CAN_SELECT_DIR, true);
		it.putExtra(FileDialog.ONLY_SELECT_DIR, true);
		it.putExtra(FileDialog.SELECTION_MODE,
			FileDialog.MODE_OPEN);
		startActivityForResult(it, DIRECTORY_BROWSER);

		/*
		 * FIM DA ROTINA
		 */

	    }

	});

	Button btnFechar = (Button) findViewById(R.id.btnFechar);
	btnFechar.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();

	    }

	});
    }

    protected void montaArquivo() {
	String newLine = System.getProperty("line.separator");
	File arquivo = null;
	int totalVotos = 0;
	try {
	    arquivo = new File(directoryFile, ARQUIVO);

	    FileOutputStream out = new FileOutputStream(arquivo);
	    out.write(("VOTAÇÃO" + newLine).getBytes());
	    out.write(("=======" + newLine).getBytes());
	    out.write(("DEPUTADO(A) ESTADUAL" + newLine).getBytes());
	    out.write(("=================" + newLine).getBytes());

	    listaDeputadosEstadual = repositorioDeputadoEstadual
		    .listarDeputadosEstadual();
	    for (int i = 0; i < listaDeputadosEstadual.size(); i++) {
		int numero = listaDeputadosEstadual.get(i).getNumero();
		String nome = listaDeputadosEstadual.get(i).getNome();
		int votos = listaDeputadosEstadual.get(i).getVotos();
		totalVotos = totalVotos + votos;
		String linha = String.format("%5d %-20.20s %4d voto(s)",
			numero, nome, votos);
		linha = linha + newLine;
		out.write(linha.getBytes());
	    }
	    int votosBrancos = repositorioVotosBrancosNulos
		    .totalVotosBrancosNulos("B", "E");
	    int votosNulos = repositorioVotosBrancosNulos
		    .totalVotosBrancosNulos("N", "E");
	    totalVotos = totalVotos + votosBrancos + votosNulos;
	    String linha2 = String.format("%-26.26s %4d",
		    "Total de Votos Brancos:", votosBrancos) + newLine;
	    String linha3 = String.format("%-26.26s %4d",
		    "Total de Votos Nulos:", votosNulos) + newLine;
	    String linha4 = String.format("%-26.26s %4d", "Total de Votos:",
		    totalVotos) + newLine;

	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha2.getBytes());
	    out.write(linha3.getBytes());
	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha4.getBytes());

	    out.write(("DEPUTADO(A) FEDERAL" + newLine).getBytes());
	    out.write(("=================" + newLine).getBytes());

	    listaDeputadosFederal = repositorioDeputadoFederal
		    .listarDeputadosFederal();
	    for (int i = 0; i < listaDeputadosFederal.size(); i++) {
		int numero = listaDeputadosFederal.get(i).getNumero();
		String nome = listaDeputadosFederal.get(i).getNome();
		int votos = listaDeputadosFederal.get(i).getVotos();
		totalVotos = totalVotos + votos;
		String linha = String.format("%5d %-20.20s %4d voto(s)",
			numero, nome, votos);
		linha = linha + newLine;
		out.write(linha.getBytes());
	    }
	    votosBrancos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "B", "F");
	    votosNulos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "N", "F");
	    totalVotos = totalVotos + votosBrancos + votosNulos;
	    linha2 = String.format("%-26.26s %4d", "Total de Votos Brancos:",
		    votosBrancos) + newLine;
	    linha3 = String.format("%-26.26s %4d", "Total de Votos Nulos:",
		    votosNulos) + newLine;
	    linha4 = String.format("%-26.26s %4d", "Total de Votos:",
		    totalVotos) + newLine;

	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha2.getBytes());
	    out.write(linha3.getBytes());
	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha4.getBytes());

	    out.write(("SENADOR(A)" + newLine).getBytes());
	    out.write(("=================" + newLine).getBytes());

	    listaSenadores = repositorioSenador.listarSenadores();
	    for (int i = 0; i < listaSenadores.size(); i++) {
		int numero = listaSenadores.get(i).getNumero();
		String nome = listaSenadores.get(i).getNome();
		int votos = listaSenadores.get(i).getVotos();
		totalVotos = totalVotos + votos;
		String linha = String.format("%5d %-20.20s %4d voto(s)",
			numero, nome, votos);
		linha = linha + newLine;
		out.write(linha.getBytes());
	    }
	    votosBrancos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "B", "S");
	    votosNulos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "N", "S");
	    totalVotos = totalVotos + votosBrancos + votosNulos;
	    linha2 = String.format("%-26.26s %4d", "Total de Votos Brancos:",
		    votosBrancos) + newLine;
	    linha3 = String.format("%-26.26s %4d", "Total de Votos Nulos:",
		    votosNulos) + newLine;
	    linha4 = String.format("%-26.26s %4d", "Total de Votos:",
		    totalVotos) + newLine;

	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha2.getBytes());
	    out.write(linha3.getBytes());
	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha4.getBytes());

	    out.write(("GOVERNADOR(A)" + newLine).getBytes());
	    out.write(("=================" + newLine).getBytes());

	    listaGovernadores = repositorioGovernador.listarGovernadores();
	    for (int i = 0; i < listaGovernadores.size(); i++) {
		int numero = listaGovernadores.get(i).getNumero();
		String nome = listaGovernadores.get(i).getNome();
		int votos = listaGovernadores.get(i).getVotos();
		totalVotos = totalVotos + votos;
		String linha = String.format("%5d %-20.20s %4d voto(s)",
			numero, nome, votos);
		linha = linha + newLine;
		out.write(linha.getBytes());
	    }
	    votosBrancos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "B", "G");
	    votosNulos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "N", "G");
	    totalVotos = totalVotos + votosBrancos + votosNulos;
	    linha2 = String.format("%-26.26s %4d", "Total de Votos Brancos:",
		    votosBrancos) + newLine;
	    linha3 = String.format("%-26.26s %4d", "Total de Votos Nulos:",
		    votosNulos) + newLine;
	    linha4 = String.format("%-26.26s %4d", "Total de Votos:",
		    totalVotos) + newLine;

	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha2.getBytes());
	    out.write(linha3.getBytes());
	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha4.getBytes());

	    out.write(("PRESIDENTE(A)" + newLine).getBytes());
	    out.write(("=================" + newLine).getBytes());

	    listaPresidentes = repositorioPresidente.listarPresidentes();
	    for (int i = 0; i < listaPresidentes.size(); i++) {
		int numero = listaPresidentes.get(i).getNumero();
		String nome = listaPresidentes.get(i).getNome();
		int votos = listaPresidentes.get(i).getVotos();
		totalVotos = totalVotos + votos;
		String linha = String.format("%5d %-20.20s %4d voto(s)",
			numero, nome, votos);
		linha = linha + newLine;
		out.write(linha.getBytes());
	    }
	    votosBrancos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "B", "P");
	    votosNulos = repositorioVotosBrancosNulos.totalVotosBrancosNulos(
		    "N", "P");
	    totalVotos = totalVotos + votosBrancos + votosNulos;
	    linha2 = String.format("%-26.26s %4d", "Total de Votos Brancos:",
		    votosBrancos) + newLine;
	    linha3 = String.format("%-26.26s %4d", "Total de Votos Nulos:",
		    votosNulos) + newLine;
	    linha4 = String.format("%-26.26s %4d", "Total de Votos:",
		    totalVotos) + newLine;

	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha2.getBytes());
	    out.write(linha3.getBytes());
	    out.write(("====================================" + newLine)
		    .getBytes());
	    out.write(linha4.getBytes());

	    out.flush();
	    out.close();

	    Toast.makeText(Votacao.this,
		    "Arquivo: " + ARQUIVO + " gerado com sucesso",
		    Toast.LENGTH_SHORT).show();

	} catch (FileNotFoundException e) {
	    // TODO: handle exception
	    Log.e("URNA", e.getMessage(), e);

	} catch (IOException e) {
	    // TODO: handle exception
	    Log.e("URNA", e.getMessage(), e);
	}
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == DIRECTORY_BROWSER) {
	    if (resultCode == RESULT_OK) {
		directoryFile = data.getStringExtra(FileDialog.RESULT_PATH);
		Log.i("URNA", "Diretorio Selecionado:" + directoryFile);
		montaArquivo();
	    } else if (resultCode == RESULT_CANCELED) {
		directoryFile = "";
	    }
	}
    }

    @Override
    protected void onDestroy() {
	super.onDestroy();
	repositorioDeputadoEstadual.Fechar();
	repositorioDeputadoFederal.Fechar();
	repositorioSenador.Fechar();
	repositorioGovernador.Fechar();
	repositorioPresidente.Fechar();
    }
}
