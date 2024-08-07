package com.example.gametimev1;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity7 extends AppCompatActivity
{
    ImageView heroIdle,heroJump,heroRun,coin1,coin2,coin3,tint,collected1,collected2,collected3,rockSpike1,rockSpike2;
    FrameLayout frame;
    TextView story;
    AnimationDrawable idleAnimation, jumpAnimation,runAnimation,coin1Animation,coin2Animation,coin3Animation;
    float heroIdleX, heroIdleY,heroJumpX,heroJumpY, heroRunX, heroRunY, density, lastJumpY;
    Rect heroRect,coin1Rect,coin2Rect,coin3Rect,rockSpike1Rect,rockSpike2Rect;
    Thread jump,jumpTimer,thread,hitBox,gravity;
    boolean actionUp,actionDown,actionRight,actionLeft, lastVerticalPressed,coin1Collected,coin2Collected,coin3Collected,alive;
    ImageButton upButton, downButton, leftButton, rightButton;
    Button pause,returnToGame,retryLevel,mainMenu,nextLevelButton;
    ArrayList<Platforms5> platforms;
    Ground5 gro = new Ground5();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level7);
        density = getResources().getDisplayMetrics().density;
        heroIdle = findViewById(R.id.heroIdle);
        heroJump = findViewById(R.id.heroJump);
        heroRun = findViewById(R.id.heroRun);
        story = findViewById(R.id.story);
        rockSpike1 = findViewById(R.id.rockspike1);
        rockSpike2 = findViewById(R.id.rockspike2);
        frame = findViewById(R.id.frame);
        coin1 = findViewById(R.id.coin1);
        coin2 = findViewById(R.id.coin2);
        coin3 = findViewById(R.id.coin3);
        pause = findViewById(R.id.pauseButton);
        retryLevel = findViewById(R.id.retry);
        nextLevelButton = findViewById(R.id.nextLevelButton);
        mainMenu = findViewById(R.id.menu);
        tint = findViewById(R.id.tint);
        collected1 = findViewById(R.id.collected1);
        collected2 = findViewById(R.id.collected2);
        collected3 = findViewById(R.id.collected3);
        returnToGame = findViewById(R.id.play);
        platforms = new ArrayList<Platforms5>();
        coin1Collected = false;
        coin2Collected = false;
        coin3Collected = false;
        alive = true;
        lastJumpY = 0;

        coin1.setBackgroundResource(R.drawable.coinanimation);
        coin1Animation = (AnimationDrawable) coin1.getBackground();
        coin2.setBackgroundResource(R.drawable.coinanimation);
        coin2Animation = (AnimationDrawable) coin2.getBackground();
        coin3.setBackgroundResource(R.drawable.coinanimation);
        coin3Animation = (AnimationDrawable) coin3.getBackground();

        heroJump.setBackgroundResource(R.drawable.jumpanimation);
        jumpAnimation = (AnimationDrawable) heroJump.getBackground();
        heroIdle.setBackgroundResource(R.drawable.idleanimation);
        idleAnimation = (AnimationDrawable) heroIdle.getBackground();
        heroRun.setBackgroundResource(R.drawable.runanimation);
        runAnimation = (AnimationDrawable)heroRun.getBackground();
        idleAnimation.start();
        coin1Animation.start();
        coin2Animation.start();
        coin3Animation.start();
        jumpAnimation.setOneShot(true);
        heroIdle.setVisibility(View.VISIBLE);
        mainMenu.setVisibility(View.INVISIBLE);
        returnToGame.setVisibility(View.INVISIBLE);
        retryLevel.setVisibility(View.INVISIBLE);
        nextLevelButton.setVisibility(View.INVISIBLE);
        platforms.clear();

        for(int x = 0;x < 5;x++)
        {
            String tempID = "platform" + (x+1);
            int sourceID = getResources().getIdentifier(tempID, "id", getPackageName());
            platforms.add(new Platforms5(findViewById(sourceID)));
        }
    }

    @Override
    protected void onStart(){
        super.onStart();

        thread = new Thread(){
            public void run(){
                while(alive)
                {
                    changePos();
                    try {
                        thread.sleep(20);
                    }catch(Exception e){}
                }
        }
    };

        gravity = new Thread(){
            public void run(){
                while(true)
                {
                    grav();
                }
            }
        };

        hitBox = new Thread(){
            public void run(){
                while(true)
                {
                    collectionTime();
                    if(coin1Collected && coin2Collected && coin3Collected)
                    {
                        nextLevel();
                    }
                }
            }
        };

    thread.start();
    hitBox.start();
    gravity.start();

    }
    @Override
    protected void onPause(){super.onPause(); thread.interrupt();}

    @Override
    protected void onStop(){super.onStop(); thread.interrupt();}

    @Override
    protected void onResume() {
        super.onResume();

    upButton = findViewById(R.id.upButton);
        upButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_DOWN && actionDown == false)
                {
                        jump = new Thread(){
                            public void run(){
                                lastVerticalPressed = true;
                                if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                                    actionUp = true;
                                try {heroIdle.setVisibility(View.INVISIBLE);heroRun.setVisibility(View.INVISIBLE);heroJump.setVisibility(View.VISIBLE);} catch (Exception e) {}
                                try{Thread.sleep(250);}catch(Exception e){}
                                actionUp = false;
                                while(!gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                                    try {Thread.sleep(1);} catch (InterruptedException e){}
                                try{idleAnimation.start();heroIdle.setVisibility(View.VISIBLE);}catch (Exception e){}
                                try{heroJump.setVisibility(View.INVISIBLE);}catch (Exception e){}
                                jump.interrupt();
                            }
                        };
                        jump.start();

                    jumpTimer = new Thread(){
                        public void run(){
                            for(int x = 0;x < 3;x++)
                            {
                                    try {
                                        Thread.sleep(5);
                                        jumpAnimation.selectDrawable(x);
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {}
                                    if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                                        jumpTimer.interrupt();
                            }
                        }
                    };
                    jumpTimer.start();
                }
            return true;
            }
        });

        downButton = findViewById(R.id.downButton);
        downButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                {
                        lastVerticalPressed = false;
                        actionDown = true;
                        if(!gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                        {
                        try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
                        try{heroJump.setVisibility(View.VISIBLE);}catch(Exception e){}
                        }
                        jumpAnimation.selectDrawable(2);
                }
                if(motionEvent.getAction() == motionEvent.ACTION_UP)
                {
                    actionDown = false;
                }
            return true;
            }
        });

        leftButton = findViewById(R.id.leftButton);
        leftButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                {
                        actionLeft = true;
                        runAnimation.start();
                        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                        {
                        try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
                        try{heroJump.setVisibility(View.INVISIBLE);}catch(Exception e){}
                        try{heroRun.setVisibility(View.VISIBLE);}catch(Exception e){}
                        }
                }
                if(motionEvent.getAction() == motionEvent.ACTION_UP)
                {
                    runAnimation.stop();
                        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                        {
                        try{heroIdle.setVisibility(View.VISIBLE);}catch(Exception e){}
                        try{heroRun.setVisibility(View.INVISIBLE);}catch(Exception e){}
                        try{heroJump.setVisibility(View.INVISIBLE);}catch (Exception e){}
                        }
                    actionLeft = false;
                }
            return true;
            }
        });

        rightButton = findViewById(R.id.rightButton);
        rightButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                {
                        actionRight = true;
                        runAnimation.start();
                        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                        {
                            try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
                            try{heroRun.setVisibility(View.VISIBLE);}catch(Exception e){}
                            try{heroJump.setVisibility(View.INVISIBLE);}catch (Exception e){}
                        }

                }
                if(motionEvent.getAction() == motionEvent.ACTION_UP)
                {
                    runAnimation.stop();
                    if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
                    {
                        try{heroIdle.setVisibility(View.VISIBLE);}catch(Exception e){}
                        try{heroRun.setVisibility(View.INVISIBLE);}catch(Exception e){}
                        try{heroJump.setVisibility(View.INVISIBLE);}catch (Exception e){}
                    }
                    actionRight = false;
                }
            return true;
            }
        });
    }
    public void grav()
    {
        try{Thread.sleep(1);}
        catch(InterruptedException e){}

        if(!alive)
            try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms) && !(actionUp || actionLeft || actionRight || actionDown) && alive) {
            try{heroIdle.setVisibility(View.VISIBLE);}catch(Exception e){}
            try{heroJump.setVisibility(View.INVISIBLE);}catch(Exception e){}
            try{heroRun.setVisibility(View.INVISIBLE);}catch(Exception e){}
        }
        if(!gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
        {
            if(actionRight || actionLeft)
            {
                if(actionUp)
                {
                    try{heroRun.setVisibility(View.INVISIBLE);}catch(Exception e){}
                    try{heroJump.setVisibility(View.VISIBLE);}catch(Exception e){}
                }
                else
                {
                    try{heroRun.setVisibility(View.VISIBLE);}catch(Exception e){}
                    try{heroJump.setVisibility(View.INVISIBLE);}catch(Exception e){}
                }
            }
            heroJumpY += 1.3;
            heroIdleY = heroJumpY;
            heroRunY = heroIdleY;
            heroJump.setY(heroJumpY);
            heroIdle.setY(heroIdleY);
            heroRun.setY(heroRunY);
        }
    }

    public void mainMenuStart(View view)
    {
       try{thread.interrupt();}catch(Exception e){}
        try{hitBox.interrupt();}catch(Exception e){}
        try{gravity.interrupt();}catch(Exception e){}
        try{idleAnimation.stop();}catch(Exception e){}
        try{runAnimation.stop();}catch(Exception e){}
        try{jumpAnimation.stop();}catch(Exception e){}
        Intent mainMenuIntent = new Intent(MainActivity7.this, StartMenu.class);
        startActivity(mainMenuIntent);
    }

    public void levelEightStart(View view)
    {
        try{thread.interrupt();}catch(Exception e){}
        try{hitBox.interrupt();}catch(Exception e){}
        try{gravity.interrupt();}catch(Exception e){}
        try{idleAnimation.stop();}catch(Exception e){}
        try{runAnimation.stop();}catch(Exception e){}
        try{jumpAnimation.stop();}catch(Exception e){}
        Intent levelEightIntent = new Intent(MainActivity7.this, MainActivity8.class);
        startActivity(levelEightIntent);
    }

    public void retryStart(View view)
    {
        recreate();
    }

    public void pauseMenu(View view)
    {
        try{story.setVisibility(View.INVISIBLE);}catch (Exception e){}
        try{upButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{downButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{leftButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{rightButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{pause.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{returnToGame.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{mainMenu.setVisibility(View.VISIBLE);}catch (Exception e){}
        try{tint.setVisibility(View.VISIBLE);}catch (Exception e){}
        idleAnimation.stop();
        coin3Animation.stop();
        coin2Animation.stop();
        coin1Animation.stop();
    }

    public void return2Game(View view)
    {
        try{story.setVisibility(View.VISIBLE);}catch (Exception e){}
        try{upButton.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{downButton.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{leftButton.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{rightButton.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{pause.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{returnToGame.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{mainMenu.setVisibility(View.INVISIBLE);}catch (Exception e){}
        try{tint.setVisibility(View.INVISIBLE);}catch (Exception e){}
        idleAnimation.start();
        coin3Animation.start();
        coin2Animation.start();
        coin1Animation.start();
    }

    public void nextLevel()
    {
        try{upButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{downButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{leftButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{rightButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{pause.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{nextLevelButton.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{mainMenu.setVisibility(View.VISIBLE);}catch (Exception e){}
        try{tint.setVisibility(View.VISIBLE);}catch (Exception e){}
        thread.interrupt();
        hitBox.interrupt();
        gravity.interrupt();
        idleAnimation.stop();
        coin3Animation.stop();
        coin2Animation.stop();
        coin1Animation.stop();
    }

    public void deathScreen()
    {
        hitBox.interrupt();
        try{story.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{upButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{downButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{leftButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{rightButton.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{pause.setVisibility(View.INVISIBLE);}catch(Exception e){}
        try{retryLevel.setVisibility(View.VISIBLE);}catch(Exception e){}
        try{mainMenu.setVisibility(View.VISIBLE);}catch (Exception e){}
        try{tint.setVisibility(View.VISIBLE);}catch (Exception e){}
        coin3Animation.stop();
        coin2Animation.stop();
        coin1Animation.stop();
    }

    public void collectionTime()
    {

        heroRect = new Rect((int)(heroIdle.getX()/density),(int)(heroIdle.getY()/density),(int)((heroIdle.getX() + heroIdle.getWidth())/density),(int)((heroIdle.getY() + heroIdle.getHeight())/density));
        coin1Rect = new Rect((int)(coin1.getX()/density), (int)(coin1.getY()/density), (int)((coin1.getX() + coin1.getWidth())/density), (int)((coin1.getY() + coin1.getHeight())/density));
        coin2Rect = new Rect((int)(coin2.getX()/density), (int)(coin2.getY()/density), (int)((coin2.getX() + coin2.getWidth())/density), (int)((coin2.getY() + coin2.getHeight())/density));
        coin3Rect = new Rect((int)(coin3.getX()/density), (int)(coin3.getY()/density), (int)((coin3.getX() + coin3.getWidth())/density), (int)((coin3.getY() + coin3.getHeight())/density));
        rockSpike1Rect = new Rect((int)(rockSpike1.getX()/density), (int)(rockSpike1.getY()/density), (int)((rockSpike1.getX() + rockSpike1.getWidth())/density), (int)((rockSpike1.getY() + rockSpike1.getHeight())/density));
        rockSpike2Rect = new Rect((int)(rockSpike2.getX()/density), (int)(rockSpike2.getY()/density), (int)((rockSpike2.getX() + rockSpike2.getWidth())/density), (int)((rockSpike2.getY() + rockSpike2.getHeight())/density));

        if(Rect.intersects(heroRect,coin1Rect))
            {
                try{collected1.setVisibility(View.VISIBLE);}catch(Exception e){}
                try{coin1.setVisibility(View.INVISIBLE);}catch(Exception e){}
                coin1Collected = true;
            }

            if(Rect.intersects(heroRect,coin2Rect))
            {
                try{collected2.setVisibility(View.VISIBLE);}catch(Exception e){}
                try{coin2.setVisibility(View.INVISIBLE);}catch(Exception e){}
                coin2Collected = true;
            }

            if(Rect.intersects(heroRect,coin3Rect))
            {
                try{collected3.setVisibility(View.VISIBLE);}catch(Exception e){}
                try{coin3.setVisibility(View.INVISIBLE);}catch(Exception e){}
                coin3Collected = true;
            }

            if(Rect.intersects(heroRect,rockSpike1Rect) || Rect.intersects(heroRect,rockSpike2Rect))
            {
                alive = false;
                deathScreen();
            }
    }

    public void verticalLimiter() {
        //SkyLimit
        if (heroIdleY < 1){
            heroIdleY = 1;
            heroJumpY = 1;
            heroRunY = 1;
    }
        //Ground
        if (heroIdleY > frame.getHeight() - heroIdle.getHeight() - 55){
            heroIdleY = frame.getHeight() - heroIdle.getHeight() - 56;
            heroJumpY = frame.getHeight() - heroJump.getHeight() - 56;
            heroRunY = frame.getHeight() - heroRun.getHeight() - 56;
    }

        //Platform Ground

         for(int x = 0;x < platforms.size();x++)
        {
            if((heroIdleY >= platforms.get(x).getId().getY() - heroIdle.getHeight() - 1  && heroIdleY <= platforms.get(x).getId().getY()) &&
                    ((heroIdleX >= platforms.get(x).getId().getX() - heroIdle.getWidth()) && (heroIdleX <= platforms.get(x).getId().getX() + platforms.get(x).getId().getWidth()))
                    && lastVerticalPressed)
            {
                heroIdleY = platforms.get(x).getId().getY() - heroIdle.getHeight();
                heroJumpY = platforms.get(x).getId().getY() - heroIdle.getHeight();
                heroRunY = platforms.get(x).getId().getY() - heroIdle.getHeight();
            }


        }

         //walls
    }

    public void changePos(){
        heroIdleX = heroIdle.getX();
        heroIdleY = heroIdle.getY();
        heroJumpX = heroJump.getX();
        heroJumpY = heroJump.getY();
        heroRunX = heroRun.getX();
        heroRunY = heroRun.getY();


        //up
        if(actionUp)
        {
            moveUp();
        }

        //down
        if(actionDown)
        {
            moveDown();
        }

        //left
        if(actionLeft)
        {
            moveLeft();
        }

        //right
        if(actionRight)
        {
            moveRight();
        }

        //vertical limiters
        verticalLimiter();

        //horizontal limiters
        if(heroJumpX < 0)
            heroJumpX = 0;

        if(heroIdleX < 0)
            heroIdleX = 0;

        if(heroRunX < 0)
            heroRunX = 0;

        if(heroIdleX > frame.getWidth() - heroIdle.getWidth())
            heroIdleX = frame.getWidth() - heroIdle.getWidth();

        if(heroJumpX > frame.getWidth() - heroJump.getWidth())
            heroJumpX = frame.getWidth() - heroJump.getWidth();

        if(heroRunX > frame.getWidth() - heroRun.getWidth())
            heroRunX = frame.getWidth() - heroRun.getWidth();

        heroIdle.setX(heroIdleX);
        heroIdle.setY(heroIdleY);
        heroJump.setX(heroJumpX);
        heroJump.setY(heroJumpY);
        heroRun.setX(heroRunX);
        heroRun.setY(heroRunY);
    }

    public void moveUp(){
        heroIdleY = heroIdle.getY();
        heroJumpY = heroJump.getY();
        heroRunY = heroRun.getY();

    Thread up = new Thread(){
        public void run(){
           while(actionUp)
           {
                heroIdleY -= 1;
                heroJumpY -= 1;
                heroRunY -= 1;
                try{Thread.sleep(2);}
                catch(InterruptedException e){}
           }
        }

    };
    up.start();
    heroIdle.setY(heroIdleY);
    heroJump.setY(heroJumpY);
    heroRun.setY(heroRunY);
    }

    public void moveDown(){
        heroIdleY += 15;
        heroJumpY += 15;
        heroRunY += 15;
    }
    public void moveLeft(){
        heroIdleX -= 15;
        heroJumpX -= 15;
        heroRunX -= 15;

        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
        {
            try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
            try{heroRun.setVisibility(View.VISIBLE);}catch(Exception e){}
            runAnimation.start();
        }

        heroIdle.setScaleX(Float.valueOf("-1f"));
        heroJump.setScaleX(Float.valueOf("-1f"));
        heroRun.setScaleX(Float.valueOf("-1f"));
    }
    public void moveRight(){
        heroIdleX += 15;
        heroJumpX += 15;
        heroRunX += 15;

        if(gro.onGround(lastVerticalPressed,heroJumpY, heroJumpX, heroJump,frame,platforms))
        {
            try{heroIdle.setVisibility(View.INVISIBLE);}catch(Exception e){}
            try{heroRun.setVisibility(View.VISIBLE);}catch(Exception e){}
            runAnimation.start();
        }

        heroIdle.setScaleX(Float.valueOf("1f"));
        heroJump.setScaleX(Float.valueOf("1f"));
        heroRun.setScaleX(Float.valueOf("1f"));
    }
}
