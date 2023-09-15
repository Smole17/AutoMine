package ru.smole.automine;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import ru.smole.automine.util.Cuboid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AutoMine extends JavaPlugin {

    private final List<Material> blocks = new ArrayList<>();
    private Cuboid cuboid;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        var config = getConfig();

        cuboid = Cuboid.getCuboidFromString(Objects.requireNonNull(config.getString("position")));
        config.getStringList("blocks")
                .stream()
                .map(s -> {
                    var data = s.split(":");

                    return Pair.of(Material.getMaterial(data[0]), Integer.parseInt(data[1]));
                })
                .forEach(pair -> blocks.addAll(Collections.nCopies(pair.right(), pair.left())));

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            cuboid.getBlocks()
                    .stream()
                    .filter(block -> block.getType() == Material.AIR)
                    .forEach(block -> {
                        var findMaterial = blocks.get(ThreadLocalRandom.current().nextInt(blocks.size()));

                        block.setType(findMaterial, false);
                    });
        }, 0L, config.getLong("update-period"));
    }
}
