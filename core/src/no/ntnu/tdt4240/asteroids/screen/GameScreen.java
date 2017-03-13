package no.ntnu.tdt4240.asteroids.screen;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import no.ntnu.tdt4240.asteroids.Asteroids;
import no.ntnu.tdt4240.asteroids.entity.component.DrawableComponent;
import no.ntnu.tdt4240.asteroids.entity.component.PositionComponent;
import no.ntnu.tdt4240.asteroids.entity.component.VelocityComponent;
import no.ntnu.tdt4240.asteroids.entity.system.MovementSystem;
import no.ntnu.tdt4240.asteroids.entity.system.RenderSystem;
import no.ntnu.tdt4240.asteroids.input.GamepadButtonListener;
import no.ntnu.tdt4240.asteroids.input.GamepadJoystickListener;
import no.ntnu.tdt4240.asteroids.input.InputHandler;
import no.ntnu.tdt4240.asteroids.input.VirtualGamepad;

public class GameScreen extends ScreenAdapter {

    private static final String TAG = GameScreen.class.getSimpleName();
    private static final int MAX_VELOCITY = 500;

    private final Asteroids game;
    private final Camera guiCam;
    private final SpriteBatch batch;
    private final Stage guiStage;
    private final PooledEngine engine;
    private boolean running;
    private Entity player;

    public GameScreen(Asteroids game) {
        this.game = game;

        // TODO: figure out camera/viewport/stage stuff
        batch = game.getBatch();

        guiCam = new OrthographicCamera();
        Viewport guiViewport = new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiStage = new Stage(guiViewport, batch);
        Gdx.input.setInputProcessor(guiStage);



        engine = new PooledEngine();
        initEngine(engine, batch);

        initGamepad(guiStage);
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
        guiStage.draw();

        // TODO: draw UI
    }

    private void initGamepad(Stage guiStage) {
        InputHandler inputHandler = new InputHandler(player, engine);
        VirtualGamepad gamepad = new VirtualGamepad();
        gamepad.addButtonListener(new GamepadButtonListener(inputHandler));
        gamepad.addJoystickListener(new GamepadJoystickListener(inputHandler));
        guiStage.addActor(gamepad);
    }

    private void initEngine(PooledEngine engine, SpriteBatch batch) {
        Texture texture = new Texture("slide_1.png");
        engine.addSystem(new RenderSystem(batch));
        engine.addSystem(new MovementSystem());
        // TODO: player should be local var, change it when touch listener is refactored
        player = engine.createEntity();
        player.add(new PositionComponent(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2));
        player.add(new VelocityComponent(0, 0));
        player.add(new DrawableComponent(new TextureRegion(texture)));
        engine.addEntity(player);
    }

}
