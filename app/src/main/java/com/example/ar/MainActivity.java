package com.example.ar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.ar.sceneform.ux.ArFragment;

public class MainActivity extends AppCompatActivity {
    private ArFragment arCoreFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arCoreFragment = (ArFragment)
        getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
    }
}
