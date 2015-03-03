/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.jacekk.bukkit.skylandsplusminus;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.World;

/**
 *
 * @author sl
 */
public class TimeSyncWorker implements Runnable {
    private SkylandsPlusMinus plugin;

    public TimeSyncWorker(SkylandsPlusMinus plugin) {
        this.plugin = plugin;
    }
    
    public void run() {
        Map<World, World> table = new HashMap<World, World>(this.plugin.timeSyncTable);
        for (World world : table.keySet()) {
            World world_skylands = table.get(world);
            
            world_skylands.setTime(world.getTime());
        }
    }
}
