package com.example.gametimev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class helpMenu extends AppCompatActivity
{
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        start = findViewById(R.id.start);
    }

            public void backStart(View view)
    {
        Intent startIntent = new Intent(helpMenu.this, StartMenu.class);
        startActivity(startIntent);
    }
}
