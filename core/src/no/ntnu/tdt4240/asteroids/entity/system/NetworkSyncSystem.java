package no.ntnu.tdt4240.asteroids.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;

import java.nio.ByteBuffer;

import no.ntnu.tdt4240.asteroids.entity.component.MovementComponent;
import no.ntnu.tdt4240.asteroids.entity.component.MultiPlayerClass;
import no.ntnu.tdt4240.asteroids.entity.component.NetworkSyncComponent;
import no.ntnu.tdt4240.asteroids.entity.component.TransformComponent;
import no.ntnu.tdt4240.asteroids.service.network.INetworkService;

import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.movementMapper;
import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.multiPlayerMapper;
import static no.ntnu.tdt4240.asteroids.entity.util.ComponentMappers.transformMapper;


public class NetworkSyncSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(NetworkSyncComponent.class, TransformComponent.class, MovementComponent.class).get();
    private static final String TAG = NetworkSyncSystem.class.getSimpleName();
    private INetworkService networkService;
    private ImmutableArray<Entity> players;

    public NetworkSyncSystem(INetworkService networkService) {
        super(FAMILY);
        this.networkService = networkService;
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        players = engine.getEntitiesFor(Family.all(MultiPlayerClass.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        TransformComponent transform = transformMapper.get(entity);
        MovementComponent movement = movementMapper.get(entity);
        ByteBuffer buffer = ByteBuffer.allocate(4 * 7);
        buffer.putFloat(transform.position.x);
        buffer.putFloat(transform.position.y);
        buffer.putFloat(transform.rotation.angle());
        buffer.putFloat(movement.velocity.x);
        buffer.putFloat(movement.velocity.y);
        buffer.putFloat(movement.acceleration.x);
        buffer.putFloat(movement.acceleration.y);
        networkService.sendUnreliableMessageToOthers(buffer.array());
    }

    public void updateEntity(String playerId, byte[] messageData) {
        Entity entity = null;
        for (Entity player : players) {
            MultiPlayerClass multiPlayerClass = multiPlayerMapper.get(player);
            if (multiPlayerClass.id.equals(playerId)) {
                entity = player;
                break;
            }
        }
        if (entity == null) {
            return;
        }
        ByteBuffer wrap = ByteBuffer.wrap(messageData);
        TransformComponent transformComponent = transformMapper.get(entity);
        MovementComponent movement = movementMapper.get(entity);
        transformComponent.position.x = wrap.getFloat();
        transformComponent.position.y = wrap.getFloat();
        transformComponent.rotation.setAngle(wrap.getFloat());
        movement.velocity.x = wrap.getFloat();
        movement.velocity.y = wrap.getFloat();
        movement.acceleration.x = wrap.getFloat();
        movement.acceleration.y = wrap.getFloat();
    }
}
