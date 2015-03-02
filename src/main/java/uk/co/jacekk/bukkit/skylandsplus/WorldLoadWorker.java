/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.jacekk.bukkit.skylandsplus;

import java.util.ArrayList;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator;

/**
 *
 * @author sl
 */
public class WorldLoadWorker implements Runnable {
    private SkylandsPlus plugin;

    public WorldLoadWorker(SkylandsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (World world : new ArrayList<World>(this.plugin.getServer().getWorlds())) {
            if (world.getGenerator() instanceof ChunkGenerator) {
                return;
            }

            if (world.getEnvironment() != World.Environment.NORMAL) {
                return;
            }

            String level = world.getName();
            WorldCreator wc = new WorldCreator(level+"_skylands");
            wc = wc.seed(this.plugin.getServer().getWorld(level).getSeed());
            wc = wc.generator(new uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator("offset=128,village,canyon,mineshaft,village,largefeatures,mushroom,swampland"));
            wc = wc.environment(World.Environment.NORMAL);
            wc = wc.type(WorldType.NORMAL);
            wc = wc.generateStructures(true);
            this.plugin.getServer().createWorld(wc);
        }
    }
}
