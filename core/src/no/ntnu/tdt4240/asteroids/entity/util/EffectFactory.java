package no.ntnu.tdt4240.asteroids.entity.util;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import no.ntnu.tdt4240.asteroids.game.effect.IEffect;
import no.ntnu.tdt4240.asteroids.game.effect.InvulnerabilityEffect;

public class EffectFactory<F extends IEffect> {

    private final Array<Class<F>> effects = new Array<>();

    public void registerEffect(Class<F> effect) {
        effects.add(effect);
    }


    public IEffect getRandomEffect() {
        if (true) return new InvulnerabilityEffect();
        if (effects.size == 0) return null;
        int random = MathUtils.random(0, effects.size - 1);
        try {
            return effects.get(random).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
