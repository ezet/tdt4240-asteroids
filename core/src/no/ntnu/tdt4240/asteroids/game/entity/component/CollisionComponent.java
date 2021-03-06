package no.ntnu.tdt4240.asteroids.game.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import java.util.HashSet;

import no.ntnu.tdt4240.asteroids.game.entity.system.CollisionSystem;

public class CollisionComponent implements Component, Pool.Poolable {

    public final HashSet<Entity> handledCollisions = new HashSet<>();
    public final Vector2 preCollisionPosition = new Vector2();
    public CollisionSystem.ICollisionHandler collisionHandler;
    public Family ignoredEntities;
    public Vector2 preCollisionVelocity;
    public float mass = 1;

    @Override
    public void reset() {
        collisionHandler = null;
        mass = 1;
        ignoredEntities = null;
        preCollisionVelocity = null;
        preCollisionPosition.setZero();
        handledCollisions.clear();
    }
}
