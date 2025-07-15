package com.terminalvelocitycabbage.game.common.voxel;

public class World {

    public Chunk getChunk(int chunkX, int chunkZ) {
        return new Chunk(chunkX, chunkZ, 16, 256, null);
    }
}
