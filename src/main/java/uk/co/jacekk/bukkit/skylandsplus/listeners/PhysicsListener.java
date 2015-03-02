package uk.co.jacekk.bukkit.skylandsplus.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import uk.co.jacekk.bukkit.baseplugin.event.BaseListener;
import uk.co.jacekk.bukkit.skylandsplus.SkylandsPlus;
import uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator;

public class PhysicsListener extends BaseListener<SkylandsPlus> {
    
	public PhysicsListener(SkylandsPlus plugin){
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
}
