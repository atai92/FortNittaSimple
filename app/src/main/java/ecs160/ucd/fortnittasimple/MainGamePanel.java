package ecs160.ucd.fortnittasimple;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.view.WindowManager;

/**
 * @author impaler
 * This is the main surface that handles the ontouch events and draws
 * the image to the screen.
 */
public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    //TAG used for logging
    private static final String TAG = MainGamePanel.class.getSimpleName();

    //Bitmaps
    private Bitmap fontblack;
    private Bitmap fontwhite;

    //Menu Item Variables
    //Black Font
    private Word BSingleplayer;
    private Word BMultiplayer;
    private Word BOptions;
    private Word BExit;
    private Word BBack;
    //White Font
    private Word WSingleplayer;
    private Word WMultiplayer;
    private Word WOptions;
    private Word WExit;
    private Word WTheGame;
    private Word WSelectMap;
    private Word WNorthSouth;
    private Word TwoPlayers;
    private Word FortyByTwentyFour;
    private Word WBack;

    //final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

    private MainThread thread;
    private int ScreenWidth;
    private int ScreenHeight;

    //state0 = main menu
    private int state = 0;

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
        fontwhite = BitmapFactory.decodeResource(getResources(), R.drawable.fontkingthingswhite);
        /*
         * TODO: Clean up
         * Can consolidate the two lines used for constructing the object and setting positions.
         * Can simply have the position parameters in the object constructor function.
         */
        //Main Menu Resources
        WTheGame = new Word("THE GAME", this, fontwhite);
        WTheGame.setPos(ScreenWidth / 2 - WTheGame.getWordSize() / 2, 50);
        BSingleplayer = new Word("SINGLEPLAYER",this,fontblack);
        BSingleplayer.setPos(ScreenWidth / 2 - BSingleplayer.getWordSize() / 2, ScreenHeight / 7 + 200);
        WSingleplayer = new Word("SINGLEPLAYER",this,fontwhite);
        WSingleplayer.setPos(ScreenWidth / 2 - WSingleplayer.getWordSize() / 2, ScreenHeight / 7 + 200);
        BMultiplayer = new Word("MULTIPLAYER", this, fontblack);
        BMultiplayer.setPos(ScreenWidth / 2 - BMultiplayer.getWordSize() / 2, (ScreenHeight / 7) * 2 + 200);
        WMultiplayer = new Word("MULTIPLAYER", this, fontwhite);
        WMultiplayer.setPos(ScreenWidth / 2 - WMultiplayer.getWordSize() / 2, (ScreenHeight / 7) * 2 + 200);
        BOptions = new Word("OPTIONS", this, fontblack);
        BOptions.setPos(ScreenWidth / 2 - BOptions.getWordSize() / 2, (ScreenHeight / 7) * 3 + 200);
        WOptions = new Word("OPTIONS", this, fontwhite);
        WOptions.setPos(ScreenWidth / 2 - WOptions.getWordSize() / 2, (ScreenHeight / 7) * 3 + 200);
        BExit = new Word("EXIT", this, fontblack);
        BExit.setPos(ScreenWidth / 2 - BExit.getWordSize() / 2, (ScreenHeight / 7) * 4 + 200);
        WExit = new Word("EXIT", this, fontwhite);
        WExit.setPos(ScreenWidth / 2 - WExit.getWordSize() / 2, (ScreenHeight / 7) * 4 + 200);
        //Select Map Resources
        WSelectMap = new Word("SELECT MAP", this, fontwhite);
        WSelectMap.setPos(ScreenWidth / 2 - WSelectMap.getWordSize() / 2, 50);
        BBack = new Word("BACK", this, fontblack);
        BBack.setPos(ScreenWidth * 3 / 4, ScreenHeight - 200);
        WBack = new Word("BACK", this, fontwhite);
        WBack.setPos(ScreenWidth * 3 / 4, ScreenHeight - 200);
        WNorthSouth = new Word("North-South", this, fontwhite);
        WNorthSouth.setPos(ScreenWidth / 10, ScreenHeight / 4);
        TwoPlayers = new Word("2PLAYERS", this, fontwhite);
        TwoPlayers.setPos(ScreenWidth * 3 / 4,ScreenHeight / 2 - 100);
        FortyByTwentyFour = new Word("40x24", this, fontwhite);
        FortyByTwentyFour.setPos(ScreenWidth * 3 / 4 + 50, ScreenHeight / 2 + 50);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and we can safely start the game loop
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // Log that the surface is being destroyed.
        Log.d(TAG, "Surface is being destroyed");
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Keep trying to destroy the surface.
            }
        }
        //If thread was shut down cleanly then log that as well.
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            //Checks where we pressed down on the screen
            case MotionEvent.ACTION_DOWN:
                switch (state) {
                    case 0:
                        BSingleplayer.handleActionDown(event);
                        BMultiplayer.handleActionDown(event);
                        BOptions.handleActionDown(event);
                        if (BExit.handleActionDown(event)) {
                        } else {
                            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
                        }
                        break;
                    case 1:
                        BBack.handleActionDown(event);
                        break;
                    default:
                        break;
                }
                break;
            //Checks where we let go of the screen...oddly does not work for me...
            case MotionEvent.ACTION_UP:
                //doesn't work...
                if (BSingleplayer.isTouched()) BSingleplayer.setTouched(false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        switch (state) {
            //Main Menu State
            case 0:
                canvas.drawColor(Color.GRAY);
                if (BSingleplayer.isTouched()) {
                    WSingleplayer.onDraw(canvas);
                    BSingleplayer.setTouched(false);
                    this.state = 1;
                }
                else BSingleplayer.onDraw(canvas);

                if (BMultiplayer.isTouched()) WMultiplayer.onDraw(canvas);
                else BMultiplayer.onDraw(canvas);

                if (BOptions.isTouched()) WOptions.onDraw(canvas);
                else BOptions.onDraw(canvas);

                if (BExit.isTouched()) {
                    BExit.setTouched(false);
                    WExit.onDraw(canvas);
                    thread.setRunning(false);
                    ((Activity) getContext()).finish();
                } else {
                    BExit.onDraw(canvas);
                }
                WTheGame.onDraw(canvas);
                break;
            //Select Map State
            case 1:
                canvas.drawColor(Color.GRAY);
                WSelectMap.onDraw(canvas);
                WNorthSouth.onDraw(canvas);
                TwoPlayers.onDraw(canvas);
                FortyByTwentyFour.onDraw(canvas);

                if (BBack.isTouched()) {
                    BBack.setTouched(false);
                    WBack.onDraw(canvas);
                    this.state = 0;
                } else {
                    BBack.onDraw(canvas);
                }
                break;
            default:
                break;
        }
        //SpriteArrayList.get(0).onDraw(canvas);
    }

}
