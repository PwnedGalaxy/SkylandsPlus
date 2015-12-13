package uk.co.jacekk.bukkit.skylandsplusminus.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.skylandsplusminus.SkylandsPlusMinus;
import uk.co.jacekk.bukkit.skylandsplusminus.generation.ChunkGenerator;

public class PhysicsListener extends BaseListener<SkylandsPlusMinus> {
    
	public PhysicsListener(SkylandsPlusMinus plugin){
		super(plugin);
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPhysics(BlockPhysicsEvent event){
		Material changed = event.getChangedType();
		
		if (changed == Material.SAND || changed == Material.GRAVEL){
			if (event.getBlock().getWorld().getGenerator() instanceof ChunkGenerator){
				event.setCancelled(true);
			}
		}
	}
    
    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void preventFallDamage(EntityDamageEvent event) {
        if (this.plugin.fallingEntities.contains(event.getEntity()) && event.getCause() == DamageCause.FALL) {
            this.plugin.fallingEntities.remove(event.getEntity());
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void preventSkylandsSleep(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        
        if (event.getClickedBlock().getType() != Material.BED_BLOCK && event.getClickedBlock().getType() != Material.BED)
            return;
        
        Location loc = event.getPlayer().getLocation();
        if (loc.getWorld().getEnvironment() != World.Environment.NORMAL || (
                !(loc.getWorld().getGenerator() instanceof uk.co.jacekk.bukkit.skylandsplusminus.generation.ChunkGenerator)
                && !loc.getWorld().getName().endsWith("_skylands")
                )) {
            return;
        }
        
        event.getClickedBlock().getLocation().getWorld().createExplosion(event.getClickedBlock().getLocation(), 5f, true);
        event.setCancelled(true);
    }
}
