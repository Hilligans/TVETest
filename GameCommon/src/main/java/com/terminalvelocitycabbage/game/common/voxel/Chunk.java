package com.terminalvelocitycabbage.game.common.voxel;

public class Chunk {

    /* this is a pretty inefficient implementation of chunks, but is basically as simple as it gets
    *
    * References are between 4-8 bytes, a single 16*16*256 chunk will use 262KB. Engines often use compressed id lookups
    * and segmenting into subchunks to help with this problem.
    * */

    public int width;
    public int height;

    public int chunkX;
    public int chunkZ;

    public Block[] blocks;
    public Block defaultBlock;

    public Chunk(int chunkX, int chunkZ, int width, int height, Block defaultBlock) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        blocks = new Block[width * width * height];
        this.defaultBlock = defaultBlock;
    }

    public void setBlock(Block block, int x, int y, int z) {
        blocks[getIndex(x % width, y % height, z % width)] = block;
    }

    public Block getBlock(int x, int y, int z) {
        Block block = blocks[getIndex(x % width, y % height, z % width)];
        return block == null ? defaultBlock : block;
    }

    protected int getIndex(int x, int y, int z) {
        return (x * width + z) * width + y;
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }
}
