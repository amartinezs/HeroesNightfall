package com.Sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Albert on 28/05/2015.
 */
public abstract class Enemy {

    //Does al related painting/load sprite tasks
    protected AtlasAnimation atlas;

    protected Animation currentAnimation;

    protected Array<TextureRegion> stoppedAnimation;
    protected Array<TextureRegion> attackAction;
    protected Array<TextureRegion> hitAction;

    private float height;
    private float width;

    public Enemy(String spriteSheetPath, float positionX, float positionY, World world, float fps, String tag, float width, float height) {
        this.height = height;
        this.width = width;
        atlas = new AtlasAnimation(spriteSheetPath, positionX, positionY, world, fps, tag, width, height);
    }

    public void dispose() {
        atlas.dispose();
    }

    public void draw(SpriteBatch batch, float delta) {
        atlas.draw(batch, delta, width, height);
    }

    public AtlasAnimation getAtlas(){
        return atlas;
    }

    public void setAtlas(AtlasAnimation atlas){
        this.atlas = atlas;
    }


}
