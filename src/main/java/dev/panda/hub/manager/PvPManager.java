package dev.panda.hub.manager;

import com.google.common.collect.Sets;
import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.listeners.cosmetic.OutfitListener;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.manager.menu.gadget.buttons.GadgetButton;
import dev.panda.hub.utilities.CC;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PvPManager {
   
   private Set<UUID> pvpMode = Sets.newHashSet();

   public void leavePvPMode(Player player) {
      this.pvpMode.remove(player.getUniqueId());
      PlayerData data = new PlayerData(player.getUniqueId());
      player.setHealth(20.0D);
      player.getInventory().setArmorContents((ItemStack[])null);
      player.getInventory().clear();
      player.teleport(player.getWorld().getSpawnLocation());
      player.removePotionEffect(PotionEffectType.SPEED);
      player.setWalkSpeed((float)ConfigFile.getConfig().getDouble("JOIN-SPEED.SPEED"));
      JoinItemManager.setJoinItems(player);
      if (data.getGadget() != null) {
         GadgetButton.checkGadget(player, data);
      }

      player.updateInventory();
      player.sendMessage(CC.set("&cYour leave to PvP Mode!"));
   }

   private void equipArmorContents(Player player) {
      player.getInventory().setArmorContents(new ItemStack[]{new ItemStack(Material.DIAMOND_BOOTS, 1), new ItemStack(Material.DIAMOND_LEGGINGS, 1), new ItemStack(Material.DIAMOND_CHESTPLATE, 1), new ItemStack(Material.DIAMOND_HELMET, 1)});
   }

   public void enterPvPMode(Player player) {
      if (OutfitListener.getInstance().getRainbow().contains(player.getUniqueId())) {
         OutfitListener.getInstance().getRainbow().remove(player.getUniqueId());
      }

      this.pvpMode.add(player.getUniqueId());
      player.setWalkSpeed(0.2F);
      player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
      player.getInventory().setArmorContents((ItemStack[])null);
      player.getInventory().clear();
      this.equipArmorContents(player);
      this.equipContents(player);
      player.updateInventory();
      player.sendMessage(CC.set("&aYour enter to PvP Mode!"));
   }

   public boolean hasPvPMode(Player player) {
      return this.pvpMode.contains(player.getUniqueId());
   }

   public Set<UUID> getPvpMode() {
      return this.pvpMode;
   }

   private void equipContents(Player player) {
      player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.DIAMOND_SWORD)});

      for(int i = 0; i < 7; ++i) {
         player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.POTION, 1, (short)16421)});
      }

      player.getInventory().addItem(new ItemStack[]{JoinItemManager.getLeavePvPMode()});
   }
}
