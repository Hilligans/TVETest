package com.terminalvelocitycabbage.game.common.voxel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class World {

    public TerrainGenerator terrainGenerator;
    public List<FeatureGenerator> featureGeneratorList = new ArrayList<>();
    public HashMap<Long, Chunk> chunkMap = new HashMap<>();


    public World() {
        terrainGenerator = new SimpleTerrainGenerator();
    }

    public Chunk getChunk(int chunkX, int chunkZ) {
        Chunk chunk = getPartialChunk(chunkX, chunkZ);
        if(chunk == null || !chunk.getFullyGenerated()) {
            return generateChunk(chunkX, chunkZ);
        }
        return chunk;
    }

    public Chunk getPartialChunk(int chunkX, int chunkZ) {
        return chunkMap.get((long)chunkX << 32 | (long)chunkZ);
    }

    public void setChunk(int chunkX, int chunkZ, Chunk chunk) {
        chunkMap.put((long)chunkX << 32 | (long)chunkZ, chunk);
    }

    public Chunk generateChunk(int chunkX, int chunkZ) {
        for(int x = chunkX - 1; x < chunkX + 1; x++) {
            for(int z = chunkZ - 1; z < chunkZ + 1; z++) {
                Chunk chunk = getPartialChunk(x, z);
                if(chunk == null) {
                    chunk = new Chunk(x, z, 32, 256, null);
                    terrainGenerator.generate(chunk);
                    setChunk(x, z, chunk);
                }
            }
        }
        Chunk chunk = getPartialChunk(chunkX, chunkZ);
        if(chunk == null) {
            chunk = new Chunk(chunkX, chunkZ, 32, 256, null);
            terrainGenerator.generate(chunk);
        }

        for(FeatureGenerator featureGenerator : featureGeneratorList) {
            featureGenerator.generate(chunk);
        }

        return chunk;
    }
}
