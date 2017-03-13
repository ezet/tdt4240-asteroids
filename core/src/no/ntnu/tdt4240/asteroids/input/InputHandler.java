package no.ntnu.tdt4240.asteroids.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

import no.ntnu.tdt4240.asteroids.entity.component.PositionComponent;
import no.ntnu.tdt4240.asteroids.entity.component.VelocityComponent;
import no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers;

public class InputHandler {

    public static final int MAX_VELOCITY = 500;
    private final Entity player;
    private final PooledEngine engine;

    public InputHandler(Entity player, PooledEngine engine) {

        this.player = player;
        this.engine = engine;
    }

    public void move(float knobPercentX, float knobPercentY) {
        VelocityComponent velocityComponent = ComponentMappers.velocityMapper.get(player);
        velocityComponent.setX(knobPercentX * MAX_VELOCITY);
        velocityComponent.setY(knobPercentY * MAX_VELOCITY);
    }

    public void fire() {
        PositionComponent playerPosition = ComponentMappers.positionMapper.get(player);
        PositionComponent bulletPosition = engine.createComponent(PositionComponent.class);
        bulletPosition.setX(playerPosition.getX());
        bulletPosition.setY(playerPosition.getY());

        VelocityComponent playerVelocity = ComponentMappers.velocityMapper.get(player);
        VelocityComponent bulletVelocity = engine.createComponent(VelocityComponent.class);
        bulletVelocity.setX(playerVelocity.getX() * 10);
        bulletVelocity.setY(playerVelocity.getY() * 10);


        Entity bullet = engine.createEntity();
        bullet.add(bulletPosition);
        bullet.add(bulletVelocity);
        engine.addEntity(bullet);
    }
}
