package br.daciosoftware.loterias;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;

public class Utils {
	static ProgressDialog dialog;
	
  
	public static void showAlert(Context context, String title, String text,
      boolean showOk) {
    Builder alertBuilder = new Builder(context);
    alertBuilder.setTitle(title);
    alertBuilder.setMessage(text);
    if (showOk)
      alertBuilder.setNeutralButton("OK", null);
    alertBuilder.create().show();
  }

  public static void showAlert(Context context, String title, String text) {
    showAlert(context, title, text, true);
  }
  
  public static void showProgressDialog(Context context, String title, String msg, boolean cancelable){
	  dialog = new ProgressDialog(context);
	  dialog.setTitle(title);
	  dialog.setMessage(msg);
	  dialog.setIndeterminate(true);
	  dialog.setCancelable(cancelable);
	  dialog.show();
  }

  public static void hiddenProgressDialog(){
	  dialog.dismiss();
  }
}
