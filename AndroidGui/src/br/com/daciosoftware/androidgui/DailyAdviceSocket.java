package br.com.daciosoftware.androidgui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DailyAdviceSocket extends Activity {

    private EditText edtIp;
    private EditText edtPorta;
    private TextView textViewAdvice;
    private SharedPreferences sp;

    private Socket socket;
    private String ip;
    private int porta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_daily_advice_socket);

	edtIp = (EditText) findViewById(R.id.edtIpServer);
	edtPorta = (EditText) findViewById(R.id.edtPortServer);
	textViewAdvice = (TextView) findViewById(R.id.textViewAdvice);
	Button btnGetAdvice = (Button) findViewById(R.id.btnGetAdvice);

	sp = getSharedPreferences("DAILE_ADVICE", MODE_PRIVATE);
	edtIp.setText(sp.getString("IP", ""));
	edtPorta.setText(sp.getString("PORTA", ""));
	
	btnGetAdvice.setOnClickListener(new OnClickListener() {
	    @Override
	    public void onClick(View v) {

		ip = edtIp.getText().toString();
		porta = Integer.parseInt(edtPorta.getText().toString());

		try {
		    
		    Log.e("ADVICE", ip + " " + porta);
		    socket = new Socket(ip, porta);

		    InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
		    BufferedReader reader = new BufferedReader(streamReader);
		    String advice = reader.readLine();
		    textViewAdvice.setText(advice);

		} catch (UnknownHostException e1) {
		    e1.printStackTrace();
		} catch (IOException e1) {
		    e1.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

	    }
	});

    }

 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.daily_advice_socket, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.action_settings) {
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }
}
