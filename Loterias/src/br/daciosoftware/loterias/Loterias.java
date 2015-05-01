package br.daciosoftware.loterias;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class Loterias extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menumain);
		
        ImageButton btMegasena = (ImageButton) findViewById(R.id.btMenuMainMegasena);
		btMegasena.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(Loterias.this, MegasenaMenu.class));
			}
		});
		
        ImageButton btQuina = (ImageButton) findViewById(R.id.btMenuMainQuina);
		btQuina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(Loterias.this, QuinaMenu.class));
			}
		});
		
        ImageButton btLotofacil = (ImageButton) findViewById(R.id.btMenuMainLotofacil);
		btLotofacil.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(Loterias.this, LotofacilMenu.class));
			}
		});

        ImageButton btSair = (ImageButton) findViewById(R.id.btMenuMainBtSair);
		btSair.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
		
    }

}
