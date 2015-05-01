package br.assistemas.urnaeletronica;

import android.app.AlertDialog.Builder;
import android.content.Context;

public class Utils {

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

}
