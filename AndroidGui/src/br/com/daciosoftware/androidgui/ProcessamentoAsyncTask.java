package br.com.daciosoftware.androidgui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ProcessamentoAsyncTask extends Activity implements
	OnItemClickListener {

    private static final int DIALOG_KEY = 0;
    private Button btnProcessamento1;
    private Button btnProcessamento2;
    private Button btnLimpar;
    private ListView listView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.activity_processamento_async_task);

	btnProcessamento1 = (Button) findViewById(R.id.btnProcessamento1);
	btnProcessamento1.setOnClickListener(new OnClickListener() {

	    @SuppressWarnings("deprecation")
	    @Override
	    public void onClick(View v) {
		TarefaProcessamento1 tarefa = new TarefaProcessamento1();
		String url = "http://androidcookbook.com/seam/resource/rest/recipe/list";
		showDialog(DIALOG_KEY);
		tarefa.execute(url, url, url, url, url);
		
	    }
	});

	btnProcessamento2 = (Button) findViewById(R.id.btnProcessamento2);
	btnProcessamento2.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		TarefaProcessamento2 tarefa = new TarefaProcessamento2();
		tarefa.execute("http://androidcookbook.com/seam/resource/rest/recipe/list");
	    }
	});

	listView = (ListView) findViewById(R.id.listView1);
	listView.setTextFilterEnabled(true);
	listView.setOnItemClickListener(this);

	btnLimpar = (Button) findViewById(R.id.btnLimpar);
	btnLimpar.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		listView.setAdapter(null);

	    }

	});

    }

    /**
     * Método executa quando se click/toque em um item da listage das receitas
     * de código do livro Android Cookbook
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
	    long id) {
	Datum datum = (Datum) listView.getItemAtPosition(position);
	Uri uri = Uri.parse("http://androidcookbook.com/Recipe.seam?recipeId="
		+ datum.getId());
	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	this.startActivity(intent);

    }

    /**
     * Método para processar o xml contendo a lista de links das receitas do
     * livro Android CookBook
     * 
     * @param url
     * @return ArrayList contedo id e titulo da receita
     * @throws IOException
     * @throws XmlPullParserException
     */
    public ArrayList<Datum> parse(String url) throws IOException,
	    XmlPullParserException {

	final ArrayList<Datum> results = new ArrayList<Datum>();

	XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	factory.setNamespaceAware(true);
	XmlPullParser xpp = factory.newPullParser();
	URL input = new URL(url);
	xpp.setInput(input.openStream(), null);
	int eventType = xpp.getEventType();
	String currentTag = null;
	Integer id = null;
	String title = null;
	while (eventType != XmlPullParser.END_DOCUMENT) {
	    if (eventType == XmlPullParser.START_TAG) {
		currentTag = xpp.getName();
	    } else if (eventType == XmlPullParser.TEXT) {
		if ("id".equals(currentTag)) {
		    id = Integer.valueOf(xpp.getText());
		}
		if ("title".equals(currentTag)) {
		    title = xpp.getText();
		}

	    } else if (eventType == XmlPullParser.END_TAG) {
		if ("recipe".equals(xpp.getName())) {
		    Datum datum = new Datum(id, title);
		    results.add(datum);
		}
	    }

	    eventType = xpp.next();
	}

	return results;
    }

    /**
     * Classe interna que representa uma estrutura de dados com campos Id e
     * Titulo
     * 
     * @author fdacio
     *
     */
    protected class Datum {
	int id;
	String title;

	public Datum(int id, String title) {
	    this.id = id;
	    this.title = title;
	}

	public String toString() {
	    return title;
	}

	public int getId() {
	    return id;
	}

	public String getTitle() {
	    return title;
	}
    }

    protected class TarefaProcessamento2 extends
	    AsyncTask<String, Void, ArrayList<Datum>> {

	@Override
	protected ArrayList<Datum> doInBackground(String... urls) {
	    ArrayList<Datum> datumList = new ArrayList<Datum>();

	    try {
		datumList = parse(urls[0]);

	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (XmlPullParserException e) {
		e.printStackTrace();
	    }

	    return datumList;
	}

	protected void onPostExecute(ArrayList<Datum> lista) {
	    listView.setAdapter(new ArrayAdapter<Datum>(
		    ProcessamentoAsyncTask.this,
		    android.R.layout.simple_list_item_1, lista));
	    ProcessamentoAsyncTask.this
		    .setProgressBarIndeterminateVisibility(false);
	}

	protected void onPreExecute() {
	    ProcessamentoAsyncTask.this
		    .setProgressBarIndeterminateVisibility(true);
	}

    }

    protected Dialog onCreateDialog(int id) {
	switch (id) {
		case DIALOG_KEY:
		    progressDialog = new ProgressDialog(this);
		    progressDialog.setProgress(0);
		    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		    progressDialog.setMessage("Recebendo dados...");
		    progressDialog.setCanceledOnTouchOutside(false);
		    return progressDialog;
	}
	return null;
    }

    protected class TarefaProcessamento1 extends
	    AsyncTask<String, Integer, ArrayList<Datum>> {

	@Override
	protected ArrayList<Datum> doInBackground(String... urls) {
	    ArrayList<Datum> datumList = new ArrayList<Datum>();
	    for (int i = 0; i < urls.length; i++) {

		try {
		    datumList = parse(urls[i]);
		    publishProgress((int) (((i+1)/(float)urls.length)*100));

		} catch (IOException e) {
		    e.printStackTrace();
		} catch (XmlPullParserException e) {
		    e.printStackTrace();
		}
	    }

	    return datumList;
	}

	protected void onPostExecute(ArrayList<Datum> lista) {
	    listView.setAdapter(new ArrayAdapter<Datum>(
		    ProcessamentoAsyncTask.this,
		    android.R.layout.simple_list_item_1, lista));
	    progressDialog.dismiss();
	}

	protected void onPreExecute() {
	    progressDialog.show();
	}
	
	protected void onProgressUpdate(Integer... values){
	    progressDialog.setProgress(values[0]);
	}

    }

}
