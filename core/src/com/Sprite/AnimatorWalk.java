package com.Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Albert on 11/05/2015.
 */
public class AnimatorWalk {

    /**
     * Enumeració per les direccions
     */
    public enum Direction {LEFT, RIGHT, STOPPED, JUMP, FALLING};

    private Sprite sprite;
    private Animation animation;

    private TextureRegion[] framesLeft, framesRight;
    private Texture frame;
    private TextureRegion frameJumpUpRight, framesFallingDownRight;

    private int textureCols, textureRows;
    private Direction direction;

    private float stateTime;
    private float fps;

    /**
     * Constructor
     *
     * @param sprite        sprite associat al personatge
     * @param textureCols   columnes de la textura
     * @param textureRows   files de la textura
     * @param stoppedTexture textura a utilitzar quan el personatge està aturat
     */
    public AnimatorWalk(Sprite sprite, int textureCols, int textureRows, Texture stoppedTexture, float fps) {

        this.sprite = sprite;
        this.textureCols = textureCols;
        this.textureRows = textureRows;
        frame = stoppedTexture;
        direction = Direction.STOPPED;
        this.fps = fps;

        Texture framesTexture = sprite.getTexture();
        TextureRegion[][] tmp = TextureRegion.split(framesTexture,
                framesTexture.getWidth() / this.textureCols,
                framesTexture.getHeight() / this.textureRows);

        framesLeft = new TextureRegion[textureCols];
        for (int j = 0; j < textureCols; j++) {
            framesLeft[j] = tmp[1][j];
        }

        framesRight = new TextureRegion[textureCols];
        for (int j = 0; j < textureCols; j++) {
            framesRight[j] = tmp[0][j];
        }

        frameJumpUpRight = tmp[2][0];
        framesFallingDownRight = tmp[2][1];

        animation = new Animation(fps, framesRight);
        stateTime = 0f;
    }

    /**
     * Dibuixar l'sprite
     *
     * @param spriteBatch
     */
    public void draw(SpriteBatch spriteBatch) {
        if (direction == Direction.STOPPED) {
            spriteBatch.draw(frame, sprite.getX(), sprite.getY());
        } else if(direction == Direction.LEFT || direction == Direction.RIGHT) {
            stateTime += Gdx.graphics.getDeltaTime() * 2;
            spriteBatch.draw(animation.getKeyFrame(stateTime, true), sprite.getX(), sprite.getY());
        } else if(direction == Direction.JUMP) {
            spriteBatch.draw(frameJumpUpRight, sprite.getX(), sprite.getY());
        }
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public Enum<Direction> getDirection(){
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
        if (direction == Direction.LEFT) {
            animation = new Animation(fps, framesLeft);
        } else if(direction == Direction.RIGHT) {
            animation = new Animation(fps, framesRight);
        }/* else if (direction == Direction.JUMP) {
            animation = new Animation (fps,frameJumpUpRight);
            Gdx.app.log("jump", "adsf");
        } else if(direction == Direction.FALLING){
            animation = new Animation(fps,framesFallingDownRight);
        }*/
    }


    public void setStoppedTexture(Texture texture) {
        this.frame = texture;
    }
}
