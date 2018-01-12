package com.seethehorizon.game.util;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * Created by dansp on 12/01/2018.
 */

public class Utils {

    /**
     * Flip a pixmap.
     *
     * @param pixmap the flipped pix map.
     */
    public static void flipPixmap(Pixmap pixmap) {
        int w = pixmap.getWidth();
        int h = pixmap.getHeight();
        int hold;

        //change blending to 'none' so that alpha areas will not show
        //previous orientation of image
        Pixmap.setBlending(Pixmap.Blending.None);
        for (int y = 0; y < h / 2; y++) {
            for (int x = 0; x < w / 2; x++) {
                //get color of current pixel
                hold = pixmap.getPixel(x, y);
                //draw color of pixel from opposite side of pixmap to current position
                pixmap.drawPixel(x, y, pixmap.getPixel(w - x - 1, y));
                //draw saved color to other side of pixmap
                pixmap.drawPixel(w - x - 1, y, hold);
                //repeat for height/width inverted pixels
                hold = pixmap.getPixel(x, h - y - 1);
                pixmap.drawPixel(x, h - y - 1, pixmap.getPixel(w - x - 1, h - y - 1));
                pixmap.drawPixel(w - x - 1, h - y - 1, hold);
            }
        }
        //set blending back to default
        Pixmap.setBlending(Pixmap.Blending.SourceOver);
    }

}
