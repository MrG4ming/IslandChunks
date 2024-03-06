package de.mrg4ming.noise;

import de.mrg4ming.data.Vector2;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class IslandGenerator {

    private SimplexOctaveGenerator gen;

    private Vector2<Integer> islandSize;

    public IslandGenerator(long seed, int octaves, double scale, Vector2<Integer> islandSize) {
        gen = new SimplexOctaveGenerator(seed, octaves);
        gen.setScale(scale);

        this.islandSize = new Vector2<>(islandSize.x, islandSize.y);
    }

    public double[][] generateIsland(double frequency, double amplitude, Vector2<Integer> offset) {
        double[][] map = new double[islandSize.x * 16][islandSize.y* 16];

        for(int x = 0; x < islandSize.x * 16; x++) {
            for (int y = 0; y < islandSize.y * 16; y++) {
                //generate noise with
                //scale: 0.008d
                //frequency: 1.4
                //amplitude: 0.7
                //octaves: 10

                map[x][y] = (this.gen.noise((x + offset.x * 16), (y + offset.y * 16), frequency, amplitude, true) + 1) / 2;
            }
        }



        for(int x = 0; x < islandSize.x * 16; x++) {
            for(int y = 0; y < islandSize.y * 16; y++) {


                //TODO: fix falloff map

                map[x][y] = clamp(map[x][y] * (1 - generateFalloffMap(this.islandSize, 1f)[x][y]), 0f, 1f);
            }
        }

        return map;
    }

    private float[][] generateFalloffMap(Vector2<Integer> islandSize, float intensity) {
        float[][] values = new float[islandSize.x * 16][islandSize.y * 16];

        for(int i = 0; i < islandSize.x * 16; i++) {
            for(int j = 0; j < islandSize.y * 16; j++) {
                float x = i / (float)(islandSize.x * 16);
                float y = j / (float)(islandSize.y * 16);

                float value = Math.max(Math.abs(x), Math.abs(y));

                values[i][j] = value * intensity;
            }
        }

        return values;
    }


    public static double clamp(double val, double min, double max) {
        return Math.min(max, Math.max(min, val));
    }
}
