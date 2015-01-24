package ecs160.ucd.fortnittasimple;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private Bitmap fontblack;
    private Word SINGLEPLAYER;
    private Word MULTIPLAYER;
    private Word OPTIONS;
    private Word EXIT;
    private Word THEGAME;
    private MainThread thread;
    private int ScreenWidth;
    private int ScreenHeight;
    //private ArrayList<Sprite> SpriteArrayList = new ArrayList<Sprite>();

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);

        //get Screen Size
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        ScreenWidth = display.getWidth();
        ScreenHeight = display.getHeight();

        //load resources
        fontblack = BitmapFactory.decodeResource(getResources(), R.drawable.fontkingthingsblack);
        THEGAME = new Word("THE GAME", this, fontblack);
        THEGAME.setPos(ScreenWidth/2 - 200,50);
        SINGLEPLAYER = new Word("SINGLEPLAYER",this,fontblack);
        SINGLEPLAYER.setPos(ScreenWidth/2 - 300,ScreenHeight/7 + 200);
        MULTIPLAYER = new Word("MULTIPLAYER", this, fontblack);
        MULTIPLAYER.setPos(ScreenWidth/2 - 275,(ScreenHeight/7)*2 + 200);
        OPTIONS = new Word("OPTIONS", this, fontblack);
        OPTIONS.setPos(ScreenWidth/2 - 180,(ScreenHeight/7)*3 + 200);
        EXIT = new Word("EXIT", this, fontblack);
        EXIT.setPos(ScreenWidth/2 - 120,(ScreenHeight/7)*4 + 200);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > ScreenWidth/2 - 120 && event.getX() < ScreenWidth/2 + 180 && event.getY() > (ScreenHeight/7)*4 + 200 && event.getY() < (ScreenHeight/7)*4 + 300) { //getWidth()/getHeight()
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GRAY);
        SINGLEPLAYER.onDraw(canvas);
        MULTIPLAYER.onDraw(canvas);
        OPTIONS.onDraw(canvas);
        EXIT.onDraw(canvas);
        THEGAME.onDraw(canvas);
        //SpriteArrayList.get(0).onDraw(canvas);
    }

}
