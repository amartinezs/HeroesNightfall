package com.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
/**
 * Classe que implementa la interface de gesti? de contactes
 *
 * @author Marc
 *
 */
public class GestorContactes implements ContactListener {

    private Array<Body> bodyDestroyList;
    private World world;

    public GestorContactes(Array<Body> bodyDestroyList) {
        this.bodyDestroyList = bodyDestroyList;
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
        // TODO Auto-generated method stu
    }



}
