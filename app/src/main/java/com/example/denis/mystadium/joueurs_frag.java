package com.example.denis.mystadium;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by denis on 30-11-16.
 */

public class joueurs_frag extends android.support.v4.app.Fragment{
    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.joueurs, container, false);
        return myView;
    }
}
