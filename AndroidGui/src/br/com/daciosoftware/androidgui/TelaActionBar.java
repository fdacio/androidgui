package br.com.daciosoftware.androidgui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class TelaActionBar extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_action_bar);
	ActionBar actionBar = getSupportActionBar();
	actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu items for use in the action bar
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.menu_action_bar, menu);
	return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
	// Handle presses on the action bar items
	switch (item.getItemId()) {
	case R.id.action_search:
	    Toast.makeText(getApplicationContext(), "Abrir Consulta",
		    Toast.LENGTH_SHORT).show();
	    return true;
	case R.id.action_add:
	    Toast.makeText(getApplicationContext(), "Chamar Tela de Adição",
		    Toast.LENGTH_SHORT).show();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
}
