package com.example.laborator2_3;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {//de retinut AppCompactActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {//se genereaza cu bundle
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2); //asta treb adaugata
    }
}
