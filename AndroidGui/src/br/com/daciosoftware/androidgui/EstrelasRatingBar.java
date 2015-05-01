package br.com.daciosoftware.androidgui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Toast;

public class EstrelasRatingBar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_estrelas_rating_bar);
	OnRatingBarChangeListener barChangeListener = new OnRatingBarChangeListener(){

	    @Override
	    public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2) {
		int rating = (int) arg1;
		String message = null;
		switch(rating){
			case 1:message = "Sorry you're really upset wint us";break;
			case 2:message = "Sorry you're not happy";break;
			case 3:message = "Good enough is not good enough";break;
			case 4:message = "Thanks, we're glad you liked it.";break;
			case 5:message = "Awesome - thanks!";break;
		}
		
		Toast.makeText(EstrelasRatingBar.this, message, Toast.LENGTH_LONG).show();
		
	    }
	    
	};
	
	final RatingBar ratingBarService = (RatingBar)findViewById(R.id.ratingBarService);
	ratingBarService.setOnRatingBarChangeListener(barChangeListener);
	final RatingBar ratingBarPrice = (RatingBar)findViewById(R.id.ratingBarPrice);
	ratingBarPrice.setOnRatingBarChangeListener(barChangeListener);
	
	Button buttonDone = (Button)findViewById(R.id.buttonDone);
	
	OnClickListener buttonDoneClick = new OnClickListener() {
	    
	    @Override
	    public void onClick(View arg0) {
		String message = String.format("Final Answer: Service %.0f/%d, Price %.0f/%d%nThank you!",ratingBarService.getRating(), ratingBarService.getNumStars(),ratingBarPrice.getRating(), ratingBarPrice.getNumStars() );
		Toast.makeText(EstrelasRatingBar.this, message, Toast.LENGTH_SHORT).show();
		finish();
	    }
	};

	buttonDone.setOnClickListener(buttonDoneClick);
    }
}
