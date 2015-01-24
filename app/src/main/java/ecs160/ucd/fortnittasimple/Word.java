package ecs160.ucd.fortnittasimple;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Alan on 1/24/2015.
 */
public class Word {
    //need list of sprites
    private int WordSize = 0;
    private int x = 0;
    private int y = 0;
    private int spacing = 45;
    private ArrayList<Sprite> SpriteArrayList = new ArrayList<Sprite>();

    public Word(String word, MainGamePanel gameView, Bitmap bmp) {
        this.WordSize = word.length();
        for (int WordIndex = 0; WordIndex < word.length(); WordIndex++) {
            SpriteArrayList.add(new Sprite(gameView,bmp));
            SpriteArrayList.get(WordIndex).setBmpVals(95,1, ((int)word.charAt(WordIndex) - (int)' '));
            SpriteArrayList.get(WordIndex).setPos(x + WordIndex * spacing, y);
        }
    }

    public void setPos(int xpos, int ypos) {
        this.x = xpos;
        this.y = ypos;
        for (int i = 0; i < WordSize; i++) {
            SpriteArrayList.get(i).setPos(x + i * spacing, y);
        }
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < WordSize; i++) {
            SpriteArrayList.get(i).onDraw(canvas);
        }
    }
}
