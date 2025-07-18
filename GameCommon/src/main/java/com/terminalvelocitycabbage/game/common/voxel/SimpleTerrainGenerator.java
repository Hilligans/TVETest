package com.terminalvelocitycabbage.game.common.voxel;

public class SimpleTerrainGenerator extends TerrainGenerator{

    @Override
    public void generate(Chunk chunk) {
        for(int x = 0; x < chunk.getWidth(); x++) {
            for(int z = 0; z < chunk.getWidth(); z++) {
                for(int y = 0; y < 55; y++) {
                    chunk.setBlock(Blocks.STONE, x, y, z);
                }
                for(int y = 55; y < 57 + (x + z) % 5; y++) {
                    chunk.setBlock(Blocks.DIRT, x, y, z);
                }
                chunk.setBlock(Blocks.GRASS, x, 57 + (x + z % 5), z);
            }
        }
    }
}
