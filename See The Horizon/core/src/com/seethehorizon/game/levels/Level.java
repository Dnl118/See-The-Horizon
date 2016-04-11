package com.seethehorizon.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.seethehorizon.game.model.AbstractGameObject;
import com.seethehorizon.game.model.Esgoto;
import com.seethehorizon.game.model.Predios;
import com.seethehorizon.game.model.Solo;

/**
 * Created by Francisco on 10/04/2016.
 */
public class Level {

    public static final String TAG = Level.class.getName();

    public enum BlockType {
        EMPTY(0, 0, 0), //preto
        SOLO(0, 255, 0), //verde
        PLAYER_SPAWNPOINT(255, 255, 255), //branco
        ITEM_COLETAVEL_1(255, 0, 255), //rosa
        ITEM_COLETAVEL_2(255, 255, 0); //amarelo

        private int color;

        private BlockType(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor(int color) {
            return this.color == color;
        }

        public int getColor() {
            return color;
        }
    }

    public Array<Solo> solos;
    public Predios predios;
    public Esgoto esgoto;

    public Level(String fileName) {
        init(fileName);
    }

    private void init(String fileName) {
        solos = new Array<Solo>();
        //carregando arquivo que representa um level
        Pixmap pixmap = new Pixmap(Gdx.files.internal(fileName));
        //scaneia pixmap (de cimapa esquerda para baixo direita)
        int lastPixel = -1;
        for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
                AbstractGameObject object = null;
                float offsetHeight;
                float baseHeight = pixmap.getHeight() - pixelY;
                //pega cor do pixel em rgba
                int currentPixel = pixmap.getPixel(pixelX, pixelY);
                //procura cor no enum para identificar o tipo do objeto

                //caso espaco vazio
                if (BlockType.EMPTY.sameColor(currentPixel)) {
                    //faz nada
                } else if (BlockType.SOLO.sameColor(currentPixel)) { //caso solo
                    if (lastPixel != currentPixel) {
                        object = new Solo();
                        float heightIncreaseFactor = 0.25f;
                        offsetHeight = -2.5f;
                        object.position.set(pixelX, baseHeight * object.dimension.x *
                                heightIncreaseFactor + offsetHeight);
                        solos.add((Solo) object);
                    } else {
                        solos.get(solos.size - 1).increaseLenght(1);
                    }
                } else if (BlockType.PLAYER_SPAWNPOINT.sameColor(currentPixel)) { //player sapwn point
                    //faz nada
                } else if(BlockType.ITEM_COLETAVEL_1.sameColor(currentPixel)){ //para coletavel 1
                    //faz nada
                } else if(BlockType.ITEM_COLETAVEL_2.sameColor(currentPixel)){
                    //faz nada
                } else { //objeto desconhecido
                    //obtem rgb
                    int r = 0xff & (currentPixel >>> 24);
                    int g = 0xff & (currentPixel >>> 16);
                    int b = 0xff & (currentPixel >>> 8);
                    int a = 0xff & currentPixel;
                    Gdx.app.error(TAG, "Objeto desconhecido x<" + pixelX + "> y<"
                            + pixelY + ">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
                }
                lastPixel = currentPixel;
            }
        }

        predios = new Predios(pixmap.getWidth());
        predios.position.set(-1, -1);
        esgoto = new Esgoto(pixmap.getWidth());
        esgoto.position.set(0, -3.75f);

        //libera memoria
        pixmap.dispose();
        Gdx.app.debug(TAG, "Level '" + fileName + "' carregado!");

    }

    public void render(SpriteBatch batch) {
        predios.render(batch);
        for(Solo solo : solos){
            solo.render(batch);
        }
        esgoto.render(batch);
    }
}
