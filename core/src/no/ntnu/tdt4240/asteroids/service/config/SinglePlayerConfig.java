package no.ntnu.tdt4240.asteroids.service.config;

@SuppressWarnings("FieldCanBeLocal")
public class SinglePlayerConfig implements IGameConfig {

    private static double obstacleSpawnChance = 0.3;
    private static int maxObstacles = 8;
    private static int minObstacles = 3;
    private static double powerupSpawnChance = 0.5;
    private static float playerGravity = 0.01f;
    private static int obstacleMaxSpeed = 200;
    private static float accelerationScalar = 500;
    private static int playerNoSpawnRadius = 50;


    @Override
    public double getObstacleSpawnChance() {
        return obstacleSpawnChance;
    }

    @Override
    public int getMaxObstacles() {
        return maxObstacles;
    }

    @Override
    public int getMinObstacles() {
        return minObstacles;
    }

    @Override
    public double getPowerupSpawnChance() {
        return powerupSpawnChance;
    }

    @Override
    public float getPlayerGravity() {
        return playerGravity;
    }

    @Override
    public int getObstacleMaxSpeed() {
        return obstacleMaxSpeed;
    }

    @Override
    public float getAccelerationScalar() {
        return accelerationScalar;
    }



    @Override
    public int getPlayerNoSpawnRadius() {
        return playerNoSpawnRadius;
    }

    @Override
    public float getBombDelay() {
        return 2f;
    }

    @Override
    public float getMissileDelay() {
        return 3f;
    }
}
