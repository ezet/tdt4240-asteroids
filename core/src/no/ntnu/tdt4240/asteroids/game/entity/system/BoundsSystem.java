package no.ntnu.tdt4240.asteroids.game.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import no.ntnu.tdt4240.asteroids.game.entity.component.BoundsComponent;
import no.ntnu.tdt4240.asteroids.game.entity.component.CircularBoundsComponent;
import no.ntnu.tdt4240.asteroids.game.entity.component.DrawableComponent;
import no.ntnu.tdt4240.asteroids.game.entity.component.RectangularBoundsComponent;
import no.ntnu.tdt4240.asteroids.game.entity.component.TransformComponent;

import static no.ntnu.tdt4240.asteroids.game.entity.util.ComponentMappers.boundsMapper;
import static no.ntnu.tdt4240.asteroids.game.entity.util.ComponentMappers.drawableMapper;
import static no.ntnu.tdt4240.asteroids.game.entity.util.ComponentMappers.transformMapper;

public class BoundsSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(TransformComponent.class, DrawableComponent.class).one(CircularBoundsComponent.class, RectangularBoundsComponent.class).get();

    public BoundsSystem() {
        super(FAMILY);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        BoundsComponent bounds = boundsMapper.get(entity);
        TransformComponent transform = transformMapper.get(entity);
        DrawableComponent drawable = drawableMapper.get(entity);
        bounds.setSize(drawable.texture.getRegionWidth() * transform.scale.x, drawable.texture.getRegionHeight() * transform.scale.y);
        bounds.setCenter(transform.position);
    }
}
