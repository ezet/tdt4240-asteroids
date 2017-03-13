package no.ntnu.tdt4240.asteroids;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import no.ntnu.tdt4240.asteroids.entity.component.DrawableComponent;
import no.ntnu.tdt4240.asteroids.entity.component.PositionComponent;
import no.ntnu.tdt4240.asteroids.entity.component.VelocityComponent;
import no.ntnu.tdt4240.asteroids.entity.system.MovementSystem;
import no.ntnu.tdt4240.asteroids.entity.system.RenderSystem;

public class GameScreen extends ScreenAdapter {

    private final Asteroids game;
    private final Stage stage;
    private PooledEngine engine;
    private Camera camera;
    private SpriteBatch batch;
    private boolean running;

    public GameScreen(Asteroids game) {
        this.game = game;
        batch = game.getBatch();


        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

//        Skin touchPadSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        Skin touchPadSkin = new Skin(new FileHandle("data/uiskin.json"));

        Skin touchPadSkin = new Skin();
        touchPadSkin.add("touchBackground", new Texture("data/touchBackground.png"));
        touchPadSkin.add("touchKnob", new Texture("data/touchKnob.png"));
        // TODO: use skin.json
        Touchpad.TouchpadStyle style = new Touchpad.TouchpadStyle();
        style.background = touchPadSkin.getDrawable("touchBackground");
        style.knob = touchPadSkin.getDrawable("touchKnob");

        Touchpad touchPad = new Touchpad(20, style);
        touchPad.setBounds(15, 15, 100, 100);
        camera = stage.getCamera();
        stage.addActor(touchPad);

        initEngine();

        running = true;

    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    private void update(float delta) {
        if (delta > 0.1f) delta = 0.1f;
        if (running) engine.update(delta);
        // TODO: update everything else
    }

    private void draw() {
        stage.draw();

        // TODO: draw UI
    }

    private void initEngine() {
        Texture texture = new Texture("badlogic.jpg");
        engine = new PooledEngine();
        engine.addSystem(new RenderSystem(camera));
        engine.addSystem(new MovementSystem());

        Entity player = engine.createEntity();
        player.add(new PositionComponent(50, 50));
        player.add(new VelocityComponent(5, 5));
        player.add(new DrawableComponent(new TextureRegion(texture)));
        engine.addEntity(player);
    }

}
