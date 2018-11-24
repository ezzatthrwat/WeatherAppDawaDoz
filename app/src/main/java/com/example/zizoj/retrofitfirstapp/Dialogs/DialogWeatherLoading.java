package com.example.zizoj.retrofitfirstapp.Dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zizoj.retrofitfirstapp.R;

public class DialogWeatherLoading extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_loading_weather, container, false);

        return view;
    }

}
