package ru.smole.automine.util;

import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cuboid {

    public static Cuboid getCuboidFromString(String data) {
        var splitData = data.split("\\s");

        var world = Bukkit.getWorld(splitData[0]);

        return new Cuboid(
                world,
                Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]), Integer.parseInt(splitData[3]),
                Integer.parseInt(splitData[4]), Integer.parseInt(splitData[5]), Integer.parseInt(splitData[6])
        );
    }

    private final List<Block> blocks = new ArrayList<>();

    public Cuboid(World world, int x1, int y1, int z1, int x2, int y2, int z2) {
        var maxX = Math.max(x1, x2);
        var maxY = Math.max(y1, y2);
        var maxZ = Math.max(z1, z2);
        var minX = Math.min(x1, x2);
        var minY = Math.min(y1, y2);
        var minZ = Math.min(z1, z2);

        for (int x = minX; x < maxX; x++)
            for (int y = minY; y < maxY; y++)
                for (int z = minZ; z < maxZ; z++) {
                    blocks.add(world.getBlockAt(x, y, z));
                }
    }

    @Unmodifiable
    public List<Block> getBlocks() {
        return Collections.unmodifiableList(blocks);
    }
}
