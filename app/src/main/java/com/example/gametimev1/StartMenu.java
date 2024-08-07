package com.example.gametimev1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartMenu extends AppCompatActivity
{
    Button levelSelectButton, helpButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startmenu);

        levelSelectButton = findViewById(R.id.levelSelect);
        helpButton = findViewById(R.id.help);
    }

        public void levelSelectStart(View view)
    {
        Intent levelSelectIntent = new Intent(StartMenu.this, levelSelectMenu.class);
        startActivity(levelSelectIntent);
    }

        public void helpStart(View view)
    {
        Intent helpIntent = new Intent(StartMenu.this, helpMenu.class);
        startActivity(helpIntent);
    }
}
