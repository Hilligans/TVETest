package com.terminalvelocitycabbage.game.client.ecs;

import com.terminalvelocitycabbage.engine.ecs.Entity;
import com.terminalvelocitycabbage.engine.ecs.Manager;
import com.terminalvelocitycabbage.engine.ecs.System;
import com.terminalvelocitycabbage.game.common.ecs.components.ChunkComponent;
import com.terminalvelocitycabbage.game.common.voxel.World;
import com.terminalvelocitycabbage.templates.ecs.components.MeshComponent;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class BuildWorldSystem extends System {

    @Override
    public void update(Manager manager, float deltaTime) {
        // get the world from the ecs;
        World world = new World();
        // get the player from the system;
        int renderDistance = 5;

        int playerChunkX = 0;
        int playerChunkZ = 0;

        // split chunks into ones within render distance and ones outside
        Map<Boolean, List<Entity>> entities = manager.getEntitiesWith(MeshComponent.class, ChunkComponent.class).stream()
                .collect(Collectors.partitioningBy(entity -> entity.getComponent(ChunkComponent.class).distanceTo(playerChunkX, playerChunkZ) <= renderDistance));

        // remove all chunks outside of render distance
        entities.get(false).forEach(manager::freeEntity);

        // figure out what chunk positions are within our render distance
        SortedSet<Long> chunks = new TreeSet<>();
        LongStream.range(-renderDistance, renderDistance)
                .forEach(i -> LongStream.range(-renderDistance, renderDistance)
                        .forEach(j -> chunks.add((i + playerChunkX) << 32 | (j + playerChunkZ))));

        // remove all chunks that already have meshes
        entities.get(true).forEach(entity -> {
            ChunkComponent chunkComponent = entity.getComponent(ChunkComponent.class);
            chunks.remove(chunkComponent.getChunkX() | (long)chunkComponent.getChunkZ() << 32);
        });

        // make a mesh for any of the chunks that dont have a mesh
        chunks.forEach(chunk -> {
            Entity newEntity = manager.createEntity();
            newEntity.addComponent(MeshComponent.class);
            newEntity.addComponent(ChunkComponent.class).setChunk(world.getChunk((int)(long)chunk, (int)(chunk >>> 32)));
        });
    }
}
