package com.example.gametimev1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class levelSelectMenu extends AppCompatActivity
{
    Button back,l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselectmenu);

        back = findViewById(R.id.back);
        l1 = findViewById(R.id.level1);
        l2 = findViewById(R.id.level2);
        l3 = findViewById(R.id.level3);
        l4 = findViewById(R.id.level4);
        l5 = findViewById(R.id.level5);
        l6 = findViewById(R.id.level6);
        l7 = findViewById(R.id.level7);
        l8 = findViewById(R.id.level8);
        l9 = findViewById(R.id.level9);
        l10 = findViewById(R.id.level10);
        l11 = findViewById(R.id.level11);
        l12 = findViewById(R.id.level12);
        l13 = findViewById(R.id.level13);
        l14 = findViewById(R.id.level14);
        l15 = findViewById(R.id.level15);
        l16 = findViewById(R.id.level16);
    }

    public void backStart(View view)
    {
        Intent startIntent = new Intent(levelSelectMenu.this, StartMenu.class);
        startActivity(startIntent);
    }

    public void levelOneStart(View view)
    {
        Intent level1Intent = new Intent(levelSelectMenu.this, MainActivity.class);
        startActivity(level1Intent);
    }

        public void levelTwoStart(View view)
    {
        Intent level2Intent = new Intent(levelSelectMenu.this, MainActivity2.class);
        startActivity(level2Intent);
    }

    public void levelThreeStart(View view)
    {
        Intent level3Intent = new Intent(levelSelectMenu.this, MainActivity3.class);
        startActivity(level3Intent);
    }

    public void levelFourStart(View view)
    {
        Intent level4Intent = new Intent(levelSelectMenu.this, MainActivity4.class);
        startActivity(level4Intent);
    }
    public void levelFiveStart(View view)
    {
        Intent level5Intent = new Intent(levelSelectMenu.this, MainActivity5.class);
        startActivity(level5Intent);
    }
    public void levelSixStart(View view)
    {
        Intent level6Intent = new Intent(levelSelectMenu.this, MainActivity6.class);
        startActivity(level6Intent);
    }
        public void levelSevenStart(View view)
    {
        Intent level7Intent = new Intent(levelSelectMenu.this, MainActivity7.class);
        startActivity(level7Intent);
    }
        public void levelEightStart(View view)
    {
        Intent level8Intent = new Intent(levelSelectMenu.this, MainActivity8.class);
        startActivity(level8Intent);
    }
        public void levelNineStart(View view)
    {
        Intent level9Intent = new Intent(levelSelectMenu.this, MainActivity9.class);
        startActivity(level9Intent);
    }
        public void levelTenStart(View view)
    {
        Intent level10Intent = new Intent(levelSelectMenu.this, MainActivity10.class);
        startActivity(level10Intent);
    }
    //
        public void levelElevenStart(View view)
    {
        Intent level11Intent = new Intent(levelSelectMenu.this, MainActivity11.class);
        startActivity(level11Intent);
    }
        public void levelTwelveStart(View view)
    {
        Intent level12Intent = new Intent(levelSelectMenu.this, MainActivity12.class);
        startActivity(level12Intent);
    }
        public void levelThirteenStart(View view)
    {
        Intent level13Intent = new Intent(levelSelectMenu.this, MainActivity13.class);
        startActivity(level13Intent);
    }
        public void levelFourteenStart(View view)
    {
        Intent level14Intent = new Intent(levelSelectMenu.this, MainActivity14.class);
        startActivity(level14Intent);
    }
        public void levelFifteenStart(View view)
    {
        Intent level15Intent = new Intent(levelSelectMenu.this, MainActivity15.class);
        startActivity(level15Intent);
    }
        public void levelSixteenStart(View view)
    {
        Intent level16Intent = new Intent(levelSelectMenu.this, MainActivity16.class);
        startActivity(level16Intent);
    }
}
