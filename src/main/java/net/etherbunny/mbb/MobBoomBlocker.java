package net.etherbunny.mbb;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("unused")
public final class MobBoomBlocker extends JavaPlugin implements Listener {
  private static Color randomColor() {
    var r = ThreadLocalRandom.current();
    int choices = 0b0001_0010_0100_0110_1001_1000;
    var x = r.nextInt(0x3ff);
    var y = x>>>8;
    var z = x&0xff;
    // Set this channel to 0xff
    var c1 = (choices >>> (y*4)) & 0x3;
    // Set this channel to 0x00-0xff
    var c2 = (choices >>> (y*4+2)) & 0x3;
    return Color.fromRGB((0xff<<(c1*8)) | (z<<(c2*8)));
  }

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  // Shamelessly stolen from
  // https://github.com/MC-Machinations/VanillaTweaks/blob/master/src/main/java/me/machinemaker/vanillatweaks/confetticreepers/ConfettiCreepers.java
  @EventHandler(ignoreCancelled = true)
  public void onExplosionPrimed(ExplosionPrimeEvent e) {
    var entity = e.getEntity();
    if (entity.getType() == EntityType.CREEPER) {
      if (ThreadLocalRandom.current().nextInt(4) == 0) {
        // Shamelessly stolen from
        // https://github.com/MC-Machinations/VanillaTweaks/blob/master/src/main/java/me/machinemaker/vanillatweaks/confetticreepers/ConfettiCreepers.java
        var confetti = FireworkEffect.builder()
          .flicker(false)
          .trail(false)
          .with(FireworkEffect.Type.BURST)
          .withColor(
            randomColor(),
            randomColor(),
            randomColor(),
            randomColor(),
            randomColor(),
            randomColor(),
            randomColor()
          ).build();

        entity.getWorld().spawn(entity.getLocation(), Firework.class, firework -> {
          var fireworkMeta = firework.getFireworkMeta();
          fireworkMeta.setPower(0);
          fireworkMeta.addEffect(confetti);
          firework.setFireworkMeta(fireworkMeta);
          firework.detonate();
        });
      }
    }
  }

  @EventHandler(ignoreCancelled = true)
  public void onEntityChangeBlock(EntityChangeBlockEvent e) {
    if(e.getEntityType() == EntityType.WITHER) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  public void onExplosion(EntityExplodeEvent e) {
    if (e.getEntity().getType() != EntityType.PRIMED_TNT) {
      e.blockList().clear();
    }
  }
}
