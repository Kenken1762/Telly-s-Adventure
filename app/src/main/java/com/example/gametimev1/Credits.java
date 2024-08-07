package com.example.gametimev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Credits extends AppCompatActivity {

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);

        back = findViewById(R.id.back);
    }

    public void backStart(View view)
    {
        Intent startIntent = new Intent(Credits.this, StartMenu.class);
        startActivity(startIntent);
    }
}
