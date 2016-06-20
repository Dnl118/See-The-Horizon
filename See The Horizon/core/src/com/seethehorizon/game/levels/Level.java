package com.seethehorizon.game.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.seethehorizon.game.model.AbstractGameObject;
import com.seethehorizon.game.model.Coin;
import com.seethehorizon.game.model.Cristal1;
import com.seethehorizon.game.model.Cristal2;
import com.seethehorizon.game.model.End;
import com.seethehorizon.game.model.Esgoto;
import com.seethehorizon.game.model.Live;
import com.seethehorizon.game.model.Predios;
import com.seethehorizon.game.model.Solo;
import com.seethehorizon.game.model.Will;

/**
 * Created by Danilo on 10/04/2016.
 */
public class Level {

    public static final String TAG = Level.class.getName();

    public enum BlockType {
        EMPTY(0, 0, 0), //preto
        SOLO(128, 0, 0), //marrom
        PLAYER_SPAWNPOINT(255, 255, 255), //branco
        COIN(255, 255, 0), //amarelo
        CRISTAL1(0, 255, 0), //verde lima
        CRISTAL2(255, 0, 0), //vermelho
        EXTRA_LIVE(255, 0, 255), //rosa
        END(0, 255, 255);

        private int color;

        BlockType(int r, int g, int b) {
            color = r << 24 | g << 16 | b << 8 | 0xff;
        }

        public boolean sameColor(int color) {
            return this.color == color;
        }

        public int getColor() {
            return color;
        }
    }

    public Will will;
    public Array<Coin> coins;
    public Array<Cristal1> cristais1;
    public Array<Cristal2> cristais2;
    public Array<Live> extraLives;
    public Array<Solo> solos;
    public Array<End> ends;
    public Predios predios;
    public Esgoto esgoto;

    public Level(String fileName) {
        init(fileName);
    }

    private void init(String fileName) {
        //personagem
        will = null;
        //objetos coletaveis
        coins = new Array<Coin>();
        cristais1 = new Array<Cristal1>();
        cristais2 = new Array<Cristal2>();
        extraLives = new Array<Live>();
        //objetos do cenario
        solos = new Array<Solo>();
        //cria barra invisivel para final
        ends = new Array<End>();
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
                    object = new Will();
                    offsetHeight = -3.0f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    will = (Will) object;
                } else if (BlockType.COIN.sameColor(currentPixel)) { //para coin
                    object = new Coin();
                    offsetHeight = -1.5f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    coins.add((Coin) object);
                } else if (BlockType.CRISTAL1.sameColor(currentPixel)) { //para cristal1
                    object = new Cristal1();
                    offsetHeight = -1.5f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    cristais1.add((Cristal1) object);
                } else if (BlockType.CRISTAL2.sameColor(currentPixel)) { //para cristal2
                    object = new Cristal2();
                    offsetHeight = -1.5f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    cristais2.add((Cristal2) object);
                } else if(BlockType.EXTRA_LIVE.sameColor(currentPixel)) {
                    object = new Live();
                    offsetHeight = -1.5f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    extraLives.add((Live) object);
                } else if(BlockType.END.sameColor(currentPixel)) {
                    object = new End();
                    offsetHeight = -1.5f;
                    object.position.set(pixelX, baseHeight * object.dimension.y + offsetHeight);
                    ends.add((End) object);
                } else { //objeto desconhecido
                    //obtem rgb
                    int r = 0xff & (currentPixel >>> 24);
                    int g = 0xff & (currentPixel >>> 16);
                    int b = 0xff & (currentPixel >>> 8);
                    int a = 0xff & currentPixel;
                    Gdx.app.error(TAG, "Objeto desconhecido x<" + pixelX + "> y<"
                            + pixelY + ">: r<" + r + "> g<" + g + "> b<" + b + "> a<" + a + ">");
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

    public void update(float deltaTime){
        will.update(deltaTime);
        for(Solo solo : solos){
            solo.update(deltaTime);
        }
        for(Coin c : coins){
            c.update(deltaTime);
        }
        for(Cristal1 c1 : cristais1){
            c1.update(deltaTime);
        }
        for(Cristal2 c2 : cristais2){
            c2.update(deltaTime);
        }
        for(Live l : extraLives){
            l.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        predios.render(batch);
        esgoto.render(batch);
        for (Solo solo : solos) {
            solo.render(batch);
        }
        for(Coin c : coins){
            c.render(batch);
        }
        for(Cristal1 c1 : cristais1){
            c1.render(batch);
        }
        for(Cristal2 c2 : cristais2){
            c2.render(batch);
        }
        for(Live l : extraLives){
            l.render(batch);
        }
        will.render(batch);
    }
}
