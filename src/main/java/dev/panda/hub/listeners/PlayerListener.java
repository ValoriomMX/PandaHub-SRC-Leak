package dev.panda.hub.listeners;

import dev.panda.hub.Hub;
import dev.panda.hub.commands.essentials.BuildCommand;
import dev.panda.hub.commands.essentials.FlyCommand;
import dev.panda.hub.commands.essentials.MessageCommand;
import dev.panda.hub.commands.toggle.ScoreboardCommand;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.listeners.cosmetic.OutfitListener;
import dev.panda.hub.manager.JoinItemManager;
import dev.panda.hub.manager.RainbowManager;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.menu.gadget.buttons.GadgetButton;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.Description;
import dev.panda.hub.utilities.Utilities;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerListener implements Listener {

   private Hub plugin = Hub.getInstance();

   @EventHandler
   private void onCreatureSpawn(CreatureSpawnEvent event) {
      if (ConfigFile.getConfig().getBoolean("DISABLE-MOBS-SPAWN")) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   private void onDamage(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (this.plugin.getPvpManager().hasPvPMode(player) && event.getCause().equals(DamageCause.FALL)) {
            event.setCancelled(false);
            return;
         }

         if (ConfigFile.getConfig().getBoolean("DISABLE-PVP")) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler
   private void onDeath(PlayerDeathEvent event) {
      final Player player = event.getEntity();
      if (this.plugin.getPvpManager().hasPvPMode(player)) {
         event.setDeathMessage((String)null);
         event.getDrops().clear();
         (new BukkitRunnable() {
            public void run() {
               plugin.getPvpManager().leavePvPMode(player);
            }
         }).runTaskLaterAsynchronously(Hub.getInstance(), 1L);
      }

   }

   @EventHandler
   private void onAttack(EntityDamageByEntityEvent event) {
      if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
         Player player = (Player)event.getEntity();
         Player damager = (Player)event.getDamager();
         if (this.plugin.getPvpManager().hasPvPMode(player) && this.plugin.getPvpManager().hasPvPMode(damager)) {
            event.setCancelled(false);
            return;
         }

         if (ConfigFile.getConfig().getBoolean("DISABLE-PVP")) {
            event.setCancelled(true);
            if (ConfigFile.getConfig().getBoolean("ENABLE-PVP-MESSAGE")) {
               damager.sendMessage(CC.set(MessageFile.getConfig().getString("PVP")));
            }
         }
      }

   }

   @EventHandler
   private void onPlayerMove(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      if (ConfigFile.getConfig().getBoolean("JUMP-PAD.ENABLE")) {
         Material material = Material.valueOf(ConfigFile.getConfig().getString("JUMP-PAD.BLOCK"));
         if (player.getLocation().getBlock().getType().equals(material)) {
            player.playSound(player.getLocation(), Sound.valueOf(ConfigFile.getConfig().getString("JUMP-PAD.SOUND")), 1.0F, 1.0F);
            player.setVelocity(player.getLocation().getDirection().multiply(ConfigFile.getConfig().getDouble("JUMP-PAD.VELOCITY")).setY(0.5D));
         }
      }

      if (player.getGameMode() != GameMode.CREATIVE && !FlyCommand.fly.contains(player) && player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR && !player.isFlying()) {
         player.setAllowFlight(true);
      }

   }

   @EventHandler
   private void onQuit(PlayerQuitEvent event) {
      event.setQuitMessage((String)null);
         Player player = event.getPlayer();
      if (OutfitListener.getInstance().getRainbow().contains(player.getUniqueId())) {
         player.getInventory().setArmorContents((ItemStack[])null);
      }

      if (this.plugin.getPvpManager().hasPvPMode(player)) {
         this.plugin.getPvpManager().getPvpMode().remove(player.getUniqueId());
         player.getInventory().setArmorContents((ItemStack[])null);
      }

      player.setWalkSpeed(0.2F);
      player.getInventory().clear();
      ScoreboardCommand.getInstance().getScoreboard().remove(player.getUniqueId());
      MessageCommand.getInstance().lastMessage.remove(MessageCommand.getInstance().lastMessage.get(player));
   }

   @EventHandler
   private void onBreak(BlockBreakEvent event) {
      Player player = event.getPlayer();
      if (BuildCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
         event.setCancelled(false);
      } else {
         if (ConfigFile.getConfig().getBoolean("DISABLE-BLOCK-BREAK")) {
            event.setCancelled(true);
            if (ConfigFile.getConfig().getBoolean("ENABLE-BLOCK-BREAK-MESSAGE")) {
               player.sendMessage(CC.set(MessageFile.getConfig().getString("BLOCK-BREAK")));
            }
         }

      }
   }

   public PlayerListener() {
      Bukkit.getServer().getPluginManager().registerEvents(this, Hub.getInstance());
   }

   @EventHandler
   private void onWeatherChange(WeatherChangeEvent event) {
      if (event.toWeatherState()) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   private void onPlace(BlockPlaceEvent event) {
      Player player = event.getPlayer();
      if (BuildCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
         event.setCancelled(false);
      } else {
         if (ConfigFile.getConfig().getBoolean("DISABLE-BLOCK-PLACE")) {
            event.setCancelled(true);
            if (ConfigFile.getConfig().getBoolean("ENABLE-BLOCK-PLACE-MESSAGE")) {
               player.sendMessage(CC.set(MessageFile.getConfig().getString("BLOCK-PLACE")));
            }
         }

      }
   }

   @EventHandler
   private void onVoidTeleport(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player && event.getCause().equals(DamageCause.VOID)) {
         Player player = (Player)event.getEntity();
         event.setCancelled(true);
         player.teleport(player.getWorld().getSpawnLocation());
      }

   }

   @EventHandler
   private void onDrop(PlayerDropItemEvent event) {
      Player player = event.getPlayer();
      if (BuildCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
         event.setCancelled(false);
      } else {
         if (ConfigFile.getConfig().getBoolean("DISABLE-DROP-ITEM")) {
            event.setCancelled(true);
            if (ConfigFile.getConfig().getBoolean("ENABLE-DROP-ITEM-MESSAGE")) {
               player.sendMessage(CC.set(MessageFile.getConfig().getString("DROP-ITEM")));
            }
         }

      }
   }

   @EventHandler
   private void onJoin(PlayerJoinEvent event) {
      event.setJoinMessage((String)null);
      final Player player = event.getPlayer();
      player.setHealth(20.0D);
      player.setFoodLevel(20);
      player.setGameMode(GameMode.ADVENTURE);
      player.teleport(player.getWorld().getSpawnLocation());
      if (OutfitListener.getInstance().getRainbow().contains(player.getUniqueId())) {
         RainbowManager manager = new RainbowManager(OutfitListener.getInstance().getRainbow(), player, 0, 0, 255, 0, 0, 0, 0, 0, 0);
         manager.runTaskTimerAsynchronously(Hub.getInstance(), 0L, 1L);
      }

      if (ConfigFile.getConfig().getBoolean("DISABLE-PLAYER-SEE-IN-HUB")) {
         Bukkit.getServer().getOnlinePlayers().forEach((p) -> {
            if (!p.hasPermission("pandahub.see")) {
               player.hidePlayer(p);
            }

         });
         Bukkit.getServer().getOnlinePlayers().forEach((p) -> {
            if (!player.hasPermission("pandahub.see")) {
               p.hidePlayer(player);
            }

         });
      }

      if (ConfigFile.getConfig().getBoolean("JOIN-SOUND.ENABLE")) {
         player.playSound(player.getLocation(), Sound.valueOf(ConfigFile.getConfig().getString("JOIN-SOUND.SOUND").toUpperCase()), 1.0F, 1.0F);
      }

      if (ConfigFile.getConfig().getBoolean("JOIN-SPEED.ENABLE")) {
         player.setWalkSpeed((float)ConfigFile.getConfig().getDouble("JOIN-SPEED.SPEED"));
      } else {
         player.setWalkSpeed(0.2F);
      }

      if (ConfigFile.getConfig().getBoolean("JOIN-MESSAGE.ENABLE")) {
         if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            ConfigFile.getConfig().getStringList("JOIN-MESSAGE.MESSAGE").forEach((s) -> {
               String s1 = Utilities.getJoinMessage(s, player);
               player.sendMessage(CC.set(PlaceholderAPI.setPlaceholders(player, s1)));
            });
         } else {
            ConfigFile.getConfig().getStringList("JOIN-MESSAGE.MESSAGE").forEach((s) -> {
               String s1 = Utilities.getJoinMessage(s, player);
               player.sendMessage(CC.set(s1));
            });
         }
      }

      final PlayerData data = new PlayerData(player.getUniqueId());
      if (data.getGadget() != null) {
         (new BukkitRunnable() {
            public void run() {
               GadgetButton.checkGadget(player, data);
            }
         }).runTaskLaterAsynchronously(Hub.getInstance(), 1L);
      }

      if (player.getName().equals("Risas") || player.getName().equals("TulioTriste")) {
         player.sendMessage(CC.set("&7&m----------------------------------"));
         player.sendMessage(CC.set("&6This server actually using PandaHub"));
         player.sendMessage("");
         player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&eAuthors&7: &f").append(Description.getAuthor()))));
         player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&eVersion&7: &f").append(Description.getVersion()))));
         player.sendMessage(CC.set(String.valueOf((new StringBuilder()).append("&eProject Name&7: &f").append(Description.getName()))));
         player.sendMessage(CC.set("&7&m----------------------------------"));
      }

   }

   @EventHandler
   private void onClickInventory(InventoryClickEvent event) {
      Player player = (Player)event.getWhoClicked();
      if (this.plugin.getPvpManager().hasPvPMode(player)) {
         ItemStack stack = event.getCurrentItem();
         if (stack != null && stack.isSimilar(JoinItemManager.getLeavePvPMode())) {
            event.setCancelled(true);
            player.updateInventory();
         } else {
            event.setCancelled(false);
         }
      } else if (BuildCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
         event.setCancelled(false);
      } else {
         event.setCancelled(true);
         player.updateInventory();
      }
   }

   @EventHandler
   private void onBlockCommands(PlayerCommandPreprocessEvent event) {
      ConfigFile.getConfig().getStringList("BLOCK-COMMAND.COMMANDS").forEach((s) -> {
         if (event.getMessage().equals(s)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(CC.set(ConfigFile.getConfig().getString("BLOCK-COMMAND.MESSAGE")));
         }

      });
   }

   @EventHandler
   private void onDoubleJump(PlayerToggleFlightEvent event) {
      Player player = event.getPlayer();
      if (this.plugin.getPvpManager().hasPvPMode(player)) {
         player.setFlying(false);
         player.setAllowFlight(false);
      } else {
         if (ConfigFile.getConfig().getBoolean("DOUBLE-JUMP.ENABLE")) {
            if (player.getGameMode().equals(GameMode.CREATIVE) || FlyCommand.fly.contains(player)) {
               return;
            }

            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(ConfigFile.getConfig().getDouble("DOUBLE-JUMP.VELOCITY")).setY(1));
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.valueOf(ConfigFile.getConfig().getString("DOUBLE-JUMP.EFFECT").toUpperCase()), 26, 0, 0.2F, 0.5F, 0.2F, 0.2F, 12, 387);
            player.playSound(player.getLocation(), Sound.valueOf(ConfigFile.getConfig().getString("DOUBLE-JUMP.SOUND").toUpperCase()), 1.0F, 1.0F);
         }

      }
   }

   @EventHandler
   private void onItemPickup(PlayerPickupItemEvent event) {
      Player player = event.getPlayer();
      if (BuildCommand.getInstance().getBuildMode().contains(player.getUniqueId())) {
         event.setCancelled(false);
      } else {
         if (ConfigFile.getConfig().getBoolean("DISABLE-PICKUP-ITEM")) {
            event.setCancelled(true);
            if (ConfigFile.getConfig().getBoolean("ENABLE-PICKUP-ITEM-MESSAGE")) {
               player.sendMessage(CC.set(MessageFile.getConfig().getString("PICKUP-ITEM")));
            }
         }

      }
   }

   @EventHandler
   private void onFood(FoodLevelChangeEvent event) {
      event.setCancelled(true);
   }
}
