package no.ntnu.tdt4240.asteroids.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import no.ntnu.tdt4240.asteroids.entity.component.PositionComponent;
import no.ntnu.tdt4240.asteroids.entity.component.VelocityComponent;

import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.positionMapper;
import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.velocityMapper;


public class MovementSystem extends IteratingSystem {

    private static final String TAG = MovementSystem.class.getSimpleName();

    private ImmutableArray<Entity> entities;

    public MovementSystem() {
        super(Family.all(PositionComponent.class, VelocityComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        VelocityComponent velocity = velocityMapper.get(entity);
        position.x = velocity.x * deltaTime;
        position.y = velocity.y * deltaTime;
    }
}
