package com.Map;

import com.Sprite.EpicHero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;


import java.util.ArrayList;

/**
 * Classe que implementa la interface de gesti? de contactes
 *
 * @author Marc
 *
 */
public class GestorContactes implements ContactListener {

    private ArrayList<Body> bodyDestroyList;
    private EpicHero hero;
    private int score;

    public GestorContactes(ArrayList<Body> bodyDestroyList, EpicHero hero) {
        this.bodyDestroyList = bodyDestroyList;
        score = 0;
        this.hero = hero;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Gdx.app.log("beginContact", "entre " + fixtureA.getBody().getUserData() + " i "
                + fixtureB.getBody().getUserData().toString());

        if (fixtureA.getBody().getUserData() == null
                || fixtureB.getBody().getUserData() == null) {
            return;
        }

        if((fixtureA.getBody().getUserData().equals("coin") && fixtureB.getBody().getUserData().toString().equals("mario")) ||
        fixtureA.getBody().getUserData().equals("mario") && fixtureB.getBody().getUserData().toString().equals("coin")){
            score++;
            if(fixtureA.getBody().getUserData().equals("coin")){
                fixtureA.getBody().setUserData("dead");
                bodyDestroyList.add(fixtureA.getBody());
            } else {
                fixtureB.getBody().setUserData("dead");
                bodyDestroyList.add(fixtureB.getBody());
            }

        }

        if((fixtureA.getBody().getUserData().equals("deadFall") && fixtureB.getBody().getUserData().toString().equals("mario")) ||
                fixtureA.getBody().getUserData().equals("mario") && fixtureB.getBody().getUserData().toString().equals("deadFall")){
            if(fixtureA.getBody().getUserData().equals("mario")){
                fixtureA.getBody().setUserData("dead");
                bodyDestroyList.add(fixtureA.getBody());
            } else {
                fixtureB.getBody().setUserData("dead");
                bodyDestroyList.add(fixtureB.getBody());
            }

        }

        if((fixtureA.getBody().getUserData().equals("combatZone") && fixtureB.getBody().getUserData().toString().equals("mario")) ||
                fixtureA.getBody().getUserData().equals("mario") && fixtureB.getBody().getUserData().toString().equals("combatZone")){

                hero.setPlatformMode(false);

        }


    }




    @Override
    public void endContact(Contact contact) {
        // TODO Auto-generated method stub

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // TODO Auto-generated method stub
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if((fixtureA.getBody().getUserData().equals("soldier") && fixtureB.getBody().getUserData().toString().equals("mario")) ||
                fixtureA.getBody().getUserData().equals("mario") && fixtureB.getBody().getUserData().toString().equals("soldier")){

            if(fixtureA.getBody().getUserData().equals("mario") && !hero.isDefense()){
                hero.setAlive(false);
                fixtureA.getBody().setGravityScale(0);
                fixtureA.getBody().applyLinearImpulse(new Vector2(-10.0f, 5.0f),
                        fixtureA.getBody().getWorldCenter(), true);
            } else if(!hero.isDefense()) {
                hero.setAlive(false);
                fixtureB.getBody().setGravityScale(0);
                fixtureB.getBody().applyLinearImpulse(new Vector2(-10.0f, 5.0f),
                        fixtureB.getBody().getWorldCenter(), true);
            } else if(fixtureA.getBody().getUserData().equals("soldier")) {
                fixtureA.getBody().setUserData("dead");
                fixtureA.getBody().setGravityScale(0);
                fixtureA.getBody().applyLinearImpulse(new Vector2(10.0f, 4.0f), fixtureA.getBody().getWorldCenter(), true);
            } else {
                fixtureB.getBody().setUserData("dead");
                fixtureB.getBody().setGravityScale(0);
                fixtureB.getBody().applyLinearImpulse(new Vector2(10.0f, 4.0f), fixtureB.getBody().getWorldCenter(), true);
            }

        }
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
