package net.etherbunny.mbb;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class MobBoomBlocker extends JavaPlugin {

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler
      public void onExplosion(EntityExplodeEvent e) {
        if (e.getEntity().getType() != EntityType.PRIMED_TNT) {
          e.blockList().clear();
        }
      }
    }, this);
  }

}
