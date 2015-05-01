package br.com.daciosoftware.androidgui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.daciosoftware.androidgui.R;

public class FragmentsDinamicoUm extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	View view = inflater.inflate(R.layout.activity_fragments_dinamico_um, container, false);
	return view;
    }
}
