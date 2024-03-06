package de.mrg4ming;

import de.mrg4ming.data.Vector2;
import de.mrg4ming.noise.IslandGenerator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

//generate noise with
//scale: 0.008d
//frequency: 1.4
//amplitude: 0.7
//octaves: 10

public class IslandWorldGenerator extends ChunkGenerator {

    public static Vector2<Integer> islandSize = new Vector2<>(16, 16);
    public static int minHeight = 64;
    public static int maxHeight = 128;

    public static IslandGenerator islandGenerator;
    private static double[][] island = new double[islandSize.x * 16][islandSize.y * 16];

    @Override
    public void generateNoise(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        double heightRange = maxHeight - minHeight;

        Vector2<Integer> islandPos = new Vector2<>(chunkX % islandSize.x, chunkZ % islandSize.y);
        Vector2<Integer> islandOffset = new Vector2<>((int) chunkX / islandSize.x, (int)chunkZ / islandSize.y);

        if(islandPos.x == 0 && islandPos.y == 0) {
            islandGenerator = new IslandGenerator(worldInfo.getSeed(), 10, 0.008d, islandSize);
            island = islandGenerator.generateIsland(1.2, 0.7, islandOffset);
        }

        int gridMaxX = (islandPos.x + 1) * 16;
        int gridMaxY = (islandPos.y + 1) * 16;
        int gridMinX = 16 * islandPos.x;
        int gridMinY = 16 * islandPos.y;

        double[][] chunkRaw = new double[16][16];


        for(int i = Math.abs(gridMinX), x = 0; i < gridMaxX; i++, x++) {
            for(int j = Math.abs(gridMinY), y = 0; j < gridMaxY; j++, y++) {
                chunkRaw[x][y] = island[i][j];
            }
        }

        int[][] chunk = new int[16][16];

        for(int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                chunk[x][y] = (int)(chunkRaw[x][y] * heightRange) + minHeight;

                for(int i = -64; i < chunk[x][y]; i++) {
                    if(worldInfo.getEnvironment() == World.Environment.NORMAL) {
                        chunkData.setBlock(x, i, y, Material.STONE);
                    } else {
                        chunkData.setBlock(x, i, y, Material.END_STONE);
                    }
                }
            }
        }


    }

    @Override
    public boolean shouldGenerateSurface() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return true;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return true;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return true;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return true;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return true;
    }
}
