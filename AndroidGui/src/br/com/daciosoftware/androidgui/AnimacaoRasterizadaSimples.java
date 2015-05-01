package br.com.daciosoftware.androidgui;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimacaoRasterizadaSimples extends Activity {
    private AnimationDrawable semafaroAnimation;
    private TextView tempo;
    private boolean continua = false;
    private int count = -1;
    private Handler handler;
    private static final long DELAY = 1000;
    private static final long LIMITE = 21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_animacao_rasterizada_simples);
	ImageView luzes = (ImageView) findViewById(R.id.imageView1);
	semafaroAnimation = (AnimationDrawable) luzes.getDrawable();
	tempo = (TextView) findViewById(R.id.tempo);
	handler = new Handler();
    }

    @Override
    public void onStart() {
	super.onStart();
	semafaroAnimation.start();
	continua = true;
	new Thread() {
	    @Override
	    public void run() {
		if (continua) {
		    atualizaTempo();
		    handler.postDelayed(this, DELAY);
		    count++;
		    if (count == LIMITE) {
			count = 1;
		    }

		}
	    }
	}.start();

    }

    @Override
    public void onDestroy() {
	super.onDestroy();
	continua = false;
    }

    protected void atualizaTempo() {
	handler.post(new Runnable() {

	    @Override
	    public void run() {
		tempo.setText(count + " seg.");

	    }

	});
    }
}
