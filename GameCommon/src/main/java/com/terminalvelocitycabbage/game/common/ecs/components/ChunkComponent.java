package com.terminalvelocitycabbage.game.common.ecs.components;

import com.terminalvelocitycabbage.engine.ecs.Component;
import com.terminalvelocitycabbage.game.common.voxel.Chunk;

public class ChunkComponent implements Component {

    public Chunk chunk;

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public int getChunkX() {
        return chunk.getChunkX();
    }

    public int getChunkZ() {
        return chunk.getChunkZ();
    }

    public int distanceTo(int x, int z) {
        if(chunk == null) {
            return Integer.MAX_VALUE;
        }
        return Math.max(Math.abs(x - chunk.getChunkX()), Math.abs(z - chunk.getChunkZ()));
    }

    @Override
    public void setDefaults() {
        this.chunk = null;
    }
}
