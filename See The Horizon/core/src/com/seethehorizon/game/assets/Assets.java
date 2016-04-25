package com.seethehorizon.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.seethehorizon.game.util.Constants;

/**
 * Created by Francisco on 10/04/2016.
 */
public class Assets implements Disposable, AssetErrorListener {

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AssetWill will;
    public AssetSolo solo;
    public AssetCoin coin;
    public AssetCristal1 cristal1;
    public AssetCristal2 cristal2;
    public AssetLevelDecoration levelDecoration;
    public AssetFonts fonts;

    //para que nao seja instanciada em outras classes
    private Assets(){}

    public void init(AssetManager assetManager){
        this.assetManager = assetManager;
        //para gerenciar erros
        assetManager.setErrorListener(this);
        //carregando texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        //aguarda ate carregar todos os assets
        assetManager.finishLoading();
        //para imprimir os assets carregados no log
        Gdx.app.debug(TAG, "# assets carregados: " + assetManager.getAssetNames().size);
        for(String assetName : assetManager.getAssetNames()){
            Gdx.app.debug(TAG, "Asset: " + assetName);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);

        for(Texture t : atlas.getTextures()){
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        //inicia objetos assets
        levelDecoration = new AssetLevelDecoration(atlas);
        fonts = new AssetFonts();
        will = new AssetWill(atlas);
        solo = new AssetSolo(atlas);
        coin = new AssetCoin(atlas);
        cristal1 = new AssetCristal1(atlas);
        cristal2 = new AssetCristal2(atlas);

    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Nao foi possivel carregar o asset: '" +
        asset.fileName + "'", (Exception) throwable);
    }


    public class AssetWill{
        public final TextureAtlas.AtlasRegion willAsset;

        public AssetWill(TextureAtlas textureAtlas){
            willAsset = textureAtlas.findRegion("will");
        }
    }

    public class AssetSolo{
        public final TextureAtlas.AtlasRegion edge;
        public final TextureAtlas.AtlasRegion middle;

        public AssetSolo(TextureAtlas textureAtlas) {
            edge = textureAtlas.findRegion("solo_edge");
            middle = textureAtlas.findRegion("solo_middle");
        }
    }

    public class AssetCoin{
        public final TextureAtlas.AtlasRegion coin;

        public AssetCoin(TextureAtlas textureAtlas){
            coin = textureAtlas.findRegion("coin");
        }
    }

    public class AssetCristal1 {
        public final TextureAtlas.AtlasRegion cristal1;

        public AssetCristal1(TextureAtlas textureAtlas){
            cristal1 = textureAtlas.findRegion("cristal1");
        }
    }

    public class AssetCristal2 {
        public final TextureAtlas.AtlasRegion cristal2;

        public AssetCristal2(TextureAtlas textureAtlas){
            cristal2 = textureAtlas.findRegion("cristal2");
        }
    }

    public class AssetLevelDecoration{
        public final TextureAtlas.AtlasRegion predios;
        public final TextureAtlas.AtlasRegion esgoto;

        public AssetLevelDecoration(TextureAtlas textureAtlas){
            predios = textureAtlas.findRegion("predios");
            esgoto = textureAtlas.findRegion("esgoto");
        }
    }

    public class AssetFonts{
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts(){
            // cria 3fontes  usando 15px bitmap
            defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
            defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
            defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
            // sseta tamanho das fontes
            defaultSmall.getData().setScale(0.75f); //getData armazena dados do bitdfontmap
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.0f);
            // enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
    }

}
