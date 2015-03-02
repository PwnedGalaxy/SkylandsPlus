package uk.co.jacekk.bukkit.skylandsplus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Entity;

import org.bukkit.generator.ChunkGenerator;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.skylandsplus.listeners.MobSpawnListener;
import uk.co.jacekk.bukkit.skylandsplus.listeners.PhysicsListener;

public class SkylandsPlus extends BasePlugin {
    private PlayerMovementWorker worker;
    public List<Entity> fallingEntities = new ArrayList<Entity>();
	
	public void onEnable(){
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		if (this.config.getBoolean(Config.PREVENT_SAND_FALLING)){
			this.pluginManager.registerEvents(new PhysicsListener(this), this);
		}
		
		if (this.config.getBoolean(Config.RESTRICT_MOB_SPAWNING)){
			this.pluginManager.registerEvents(new MobSpawnListener(this), this);
		}
                
            this.worker = new PlayerMovementWorker(this);
            this.getServer().getScheduler().scheduleSyncRepeatingTask(this, this.worker, 0, 1);
            this.getServer().getScheduler().scheduleSyncDelayedTask(this, new WorldLoadWorker(this));

	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id){
		if(id == null || id.isEmpty()){
			id = "offset=0";
		}
		return new uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator(id);
	}
	
}
