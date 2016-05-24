package uk.co.jacekk.bukkit.skylandsplusminus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import org.bukkit.generator.ChunkGenerator;

import org.bukkit.plugin.java.JavaPlugin;
import uk.co.jacekk.bukkit.skylandsplusminus.listeners.MobSpawnListener;
import uk.co.jacekk.bukkit.skylandsplusminus.listeners.PhysicsListener;

public class SkylandsPlusMinus extends JavaPlugin {

    public List<Entity> fallingEntities = new ArrayList<Entity>();
    public Map<World, World> timeSyncTable = new HashMap<World, World>();

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new PhysicsListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
////        Temporal Crafting server 
//        this.getServer().getScheduler().scheduleSyncRepeatingTask(this,  new PlayerMovementWorker(this), 0, 1);
//        this.getServer().getScheduler().scheduleSyncDelayedTask(this, new WorldLoadWorker(this));
//        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new TimeSyncWorker(this), 0, 20);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        if (id == null || id.isEmpty()) {
            id = "offset=0";
        }
        return new uk.co.jacekk.bukkit.skylandsplusminus.generation.ChunkGenerator(id);
    }

}
