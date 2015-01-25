package ecs160.ucd.fortnittasimple; /**
 * Created by Alan on 1/23/2015.
 */
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import ecs160.ucd.fortnittasimple.MainGamePanel;

public class Sprite {

    private static final String TAG = Sprite.class.getSimpleName();

    private int BMP_ROWS = 5;
    private int BMP_COLUMNS = 1;
    private int y = 100;
    private int x = 100;
    private int xSpeed = 5;
    private MainGamePanel gameView;
    private Bitmap bmp;
    private Rect srcrect;
    private int currentFrame = 0;
    private int width;
    private int height;

    public Sprite(MainGamePanel gameView, Bitmap bmp) {
        this.gameView=gameView;
        this.bmp=bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    private void update() {
        if (y > gameView.getWidth() - bmp.getWidth() - xSpeed) {
            xSpeed = -5;
        }
        if (y + xSpeed < 0) {
            xSpeed = 5;
        }
        y = y + xSpeed;
        currentFrame = ++currentFrame % BMP_ROWS;
    }

    public void onDraw(Canvas canvas) {
        //update();
        int srcX = 0;
        //modded with BMP_ROWS so we will never go out of the max number of frames
        int srcY = (currentFrame % BMP_ROWS)*height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public void setBmpVals(int rows, int cols, int frame) {
        this.BMP_ROWS = rows;
        this.BMP_COLUMNS = cols;
        this.currentFrame = frame;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    public void setPos(int xpos, int ypos) {
        this.x = xpos;
        this.y = ypos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
