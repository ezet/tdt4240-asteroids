package no.ntnu.tdt4240.asteroids.presenter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

import no.ntnu.tdt4240.asteroids.Asteroids;
import no.ntnu.tdt4240.asteroids.game.World;
import no.ntnu.tdt4240.asteroids.game.entity.system.NetworkSystem;
import no.ntnu.tdt4240.asteroids.model.PlayerData;
import no.ntnu.tdt4240.asteroids.service.ServiceLocator;
import no.ntnu.tdt4240.asteroids.service.network.INetworkService;

public class MpGamePresenter extends BaseGamePresenter implements World.IWorldListener, INetworkService.INetworkListener {

    @SuppressWarnings("unused")
    private static final String TAG = MpGamePresenter.class.getSimpleName();
    private static final int ROUNDS = 3;
    private final INetworkService networkService;
    private int roundsPlayed = 0;

    public MpGamePresenter(Asteroids game, Screen parent) {
        super(game, parent);
        networkService = ServiceLocator.getAppComponent().getNetworkService();
        networkService.setNetworkListener(this);
    }

    @Override
    protected void initializeEntityComponent(PooledEngine engine) {
        ServiceLocator.initializeMultiPlayerEntityComponent(engine);
    }

    @Override
    protected void setupEngine(PooledEngine engine, SpriteBatch batch) {
        super.setupEngine(engine, batch);
        engine.addSystem(new NetworkSystem(ServiceLocator.getAppComponent().getNetworkService()));
    }

    @Override
    public void handle(World model, int event) {
        switch (event) {
            case World.EVENT_WORLD_RESET: {
                addPlayers(players.values(), true);
                break;
            }
        }
    }

    @Override
    public void notifyScoreChanged(Entity entity, int oldScore, int score) {
        //        if (Objects.equals(id, playerId)) {
//            view.updateScore(score);
//        }
        // TODO: 05-Apr-17 handle opponent scores
    }

    @Override
    public void notifyPlayerRemoved(Entity entity) {
        super.notifyPlayerRemoved(entity);
        if (remainingPlayers.size() == 1) {
            PlayerData playerData = players.get(remainingPlayers.iterator().next());
            playerData.totalScore += 1;
            view.updateScore(playerData.totalScore);
        }
        if (remainingPlayers.size() <= 1) {
            roundsPlayed++;
            if (roundsPlayed < ROUNDS) {
                world.reset();
            } else {
                onGameEnd();
            }
        }
    }

    @Override
    public void onPause() {
        // disable default pause behaviour
    }

    @Override
    public void onResume() {
        // disable default resume behaviour
    }

    @Override
    public void onQuitLevel() {
        super.onQuitLevel();
        networkService.quitGame();
    }

    @Override
    public void onQuit() {
        networkService.quitGame();
        super.onQuit();
    }

    @Override
    public void onReliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        Gdx.app.debug(TAG, "onReliableMessageReceived: " + senderParticipantId + "," + describeContents);
    }

    @Override
    public void onUnreliableMessageReceived(String senderParticipantId, int describeContents, byte[] messageData) {
        engine.getSystem(NetworkSystem.class).processPackage(senderParticipantId, messageData);
    }

    @Override
    public void onRoomReady(List<PlayerData> players) {
        Gdx.app.debug(TAG, "onRoomReady: ");
        addPlayers(players, true);
        world.initialize();
    }
}
