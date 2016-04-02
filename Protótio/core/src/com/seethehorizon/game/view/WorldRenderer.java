package com.seethehorizon.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.seethehorizon.game.model.Block;
import com.seethehorizon.game.model.Will;
import com.seethehorizon.game.model.World;

/**
 * Created by Danilo on 20/02/2016.
 */
public class WorldRenderer {

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 7f;

    private World world;
    private OrthographicCamera cam;

    //para debugar renderização
    ShapeRenderer debugRenderer = new ShapeRenderer();

    //Texturas
    private Texture willTexture;
    private Texture lowBlockTexture;
    private Texture topBlockTexture;

    private SpriteBatch spriteBatch;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX; //pixels per unit do eixo X
    private float ppuY; // pixels per unit do eixo Y

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        ppuX = (float) this.width / CAMERA_WIDTH;
        ppuX = (float) this.height / CAMERA_HEIGHT;
    }

    public WorldRenderer(World world, boolean debug){
        this.world = world;
        this.cam = new OrthographicCamera(10, 7);
        this.cam.position.set(5, 3.5f, 0);
        this.cam.update();
        this.debug = debug;
        spriteBatch = new SpriteBatch();
        loadTextures();
    }

    private void loadTextures(){
        lowBlockTexture = new Texture(Gdx.files.internal("images/lowBlock.png"));
        topBlockTexture = new Texture(Gdx.files.internal("images/topBlock.png"));
    }

    public void render(){
        spriteBatch.begin();
        drawBlocks();
        spriteBatch.end();
        if(debug)
            drawDebug();
    }

    private void drawBlocks(){
        for(Block block : world.getBlocks()){
            spriteBatch.draw(lowBlockTexture, block.getPosition().x * ppuX,
                    block.getPosition().y * ppuY, Block.SIZE * ppuX, Block.SIZE * ppuY);
        }
    }

    private void drawWill(){
        Will will = world.getWill();
        //terminar implementação do desenho do WILL
        //preciso encontrar um desenho pra ele
    }

    private void drawDebug(){
        //renderizando blocos
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        for(Block block : world.getBlocks()){
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x + rect.x;
            float y1 = block.getPosition().y + rect.y;
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
        }
        //renderizando Will
        Will will = world.getWill();
        Rectangle rect = will.getBounds();
        float x1 = will.getPosition().x + rect.x;
        float y1 = will.getPosition().y + rect.y;
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(x1, y1, rect.width, rect.height);
        debugRenderer.end();
    }

}
