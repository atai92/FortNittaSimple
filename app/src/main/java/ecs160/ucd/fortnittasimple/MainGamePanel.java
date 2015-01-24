package ecs160.ucd.fortnittasimple;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();

    private Bitmap fontblack;
    private Sprite sprite;
    private Word SINGLEPLAYER;
    private MainThread thread;
    //private ArrayList<Sprite> SpriteArrayList = new ArrayList<Sprite>();

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);

        //load resources
        fontblack = BitmapFactory.decodeResource(getResources(), R.drawable.fontkingthingsblack);
        sprite = new Sprite(this,fontblack);
        sprite.setBmpVals(95,1,((int)'A'- (int)' '));
        sprite.setPos(100,100);
        //SpriteArrayList.add(new Sprite(this,fontblack));
        //SpriteArrayList.get(0).setBmpVals(95,1, ((int)'S' - (int)' '));
        //SpriteArrayList.get(0).setPos(120,100);
        SINGLEPLAYER = new Word("SINGLEPLAYER",this,fontblack);
        SINGLEPLAYER.setPos(100,300);
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
            if (event.getY() > 50) {
                sprite.setPos(200,200);
            }
            if (event.getX() < 50) { //getWidth()/getHeight()
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
        sprite.onDraw(canvas);
        SINGLEPLAYER.onDraw(canvas);
        //SpriteArrayList.get(0).onDraw(canvas);
    }

}
