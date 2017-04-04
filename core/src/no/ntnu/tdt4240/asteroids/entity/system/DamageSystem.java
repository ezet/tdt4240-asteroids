package no.ntnu.tdt4240.asteroids.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.asteroids.entity.component.DamageComponent;
import no.ntnu.tdt4240.asteroids.entity.component.HealthComponent;

import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.damageMapper;
import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.healthMapper;


public class DamageSystem extends EntitySystem implements CollisionSystem.ICollisionHandler {

    private Array<IDamageHandler> damageListeners = new Array<>();

    public DamageSystem(CollisionSystem collisionSystem) {
        collisionSystem.listeners.add(this);
        setProcessing(false);
    }

    @Override
    public boolean onCollision(PooledEngine engine, Entity source, Entity target) {
        HealthComponent healthComponent = healthMapper.get(target);
        DamageComponent damageComponent = damageMapper.get(source);
        if (healthComponent == null || damageComponent == null) return false;
        if (damageComponent.ignoredEntities != null && damageComponent.ignoredEntities.matches(target))
            return false;
        if (healthComponent.ignoredEntities != null && healthComponent.ignoredEntities.matches(source))
            return false;
        healthComponent.hitPoints -= damageComponent.damage;
        if (healthComponent.damageHandler != null) {
            healthComponent.damageHandler.onDamageTaken(getEngine(), source, damageComponent.damage);
        }
        notifyDamageListeners(target, healthComponent.hitPoints);

        if (healthComponent.hitPoints <= 0) {
            if (healthComponent.damageHandler != null)
                healthComponent.damageHandler.onEntityDestroyed(getEngine(), source, target);
        }
        notifyDestroyedListeners(source, target);
        return true;
    }

    private void notifyDamageListeners(Entity entity, int hitpoints) {
        for (IDamageHandler listener : damageListeners) {
            listener.onDamageTaken(getEngine(), entity, hitpoints);
        }
    }

    private void notifyDestroyedListeners(Entity source, Entity target) {
        for (IDamageHandler listener : damageListeners) {
            listener.onEntityDestroyed(getEngine(), source, target);
        }
    }

    public interface IDamageHandler {
        void onDamageTaken(Engine engine, Entity entity, int damageTaken);

        void onEntityDestroyed(Engine engine, Entity source, Entity target);
    }

}
