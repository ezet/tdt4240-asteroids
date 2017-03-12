package no.ntnu.tdt4240.asteroids.component.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.sun.istack.internal.Nullable;

import no.ntnu.tdt4240.asteroids.component.PositionComponent;
import no.ntnu.tdt4240.asteroids.component.VelocityComponent;

import static com.badlogic.gdx.Gdx.app;


public class MovementSystem extends EntitySystem {

    private static final String TAG = MovementSystem.class.getSimpleName();

    private ImmutableArray<Entity> entities;
    private ComponentMapper<PositionComponent> positionMapper = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> velocityMapper = ComponentMapper.getFor(VelocityComponent.class);

    public MovementSystem() {
    }

    @Override
    public void addedToEngine(@Nullable Engine engine) {
        if (engine == null) {
            app.error(TAG, "addedToEngine: Engine is null");
            return;
        }
        @SuppressWarnings("unchecked")
        Family family = Family.all(PositionComponent.class, VelocityComponent.class).get();
        entities = engine.getEntitiesFor(family);
    }

    @Override
    public void update(float deltaTime) {
        for (Entity entity : entities) {
            PositionComponent position = positionMapper.get(entity);
            VelocityComponent velocity = velocityMapper.get(entity);

            position.x = velocity.x * deltaTime;
            position.y = velocity.y * deltaTime;

        }
    }
}
