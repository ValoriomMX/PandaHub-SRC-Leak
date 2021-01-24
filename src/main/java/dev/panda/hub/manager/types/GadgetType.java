package dev.panda.hub.manager.types;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.files.ItemFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.item.ItemCreator;
import java.beans.ConstructorProperties;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum GadgetType {
   // $FF: synthetic field
   BOW_TELEPORT("Bow Teleport", ItemFile.getConfig().getString("GADGET.BOW-TELEPORT.NAME"), Material.BOW, 0),
   SNOWMAN("Snowman", ItemFile.getConfig().getString("GADGET.SNOWMAN.NAME"), Material.SNOW_BALL, 0);
   private int data;
   private Material material;
   private String name;

   private String displayName;

   @ConstructorProperties({"name", "displayName", "material", "data"})
   private GadgetType(String name, String displayName, Material material, int data) {
      this.name = name;
      this.displayName = displayName;
      this.material = material;
      this.data = data;
   }

   public int getData() {
      return this.data;
   }

   public ItemStack getItem(Player player) {
      PlayerData data = new PlayerData(player.getUniqueId());
      ItemCreator creator = new ItemCreator(this.getMaterial());
      creator.setDurability(this.getData());
      creator.setName(this.getDisplayName());
      creator.setLore(Arrays.asList("", "&aClick to equip this gadget."));
      if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.cosmetics.gadget.").append(this.getName().toLowerCase().replace(" ", "_"))))) {
         creator.setLore(Arrays.asList("", "&cYou don't have permission to this gadget.", String.valueOf((new StringBuilder()).append("&cPurchase at ").append(ConfigFile.getConfig().getString("STORE")))));
      }

      if (this.getName().equals(data.getGadget())) {
         creator.setLore(Arrays.asList("", "&aEquipped."));
      }

      return creator.get();
   }

   public Material getMaterial() {
      return this.material;
   }

   public String getDisplayName() {
      return CC.set(this.displayName);
   }

   public String getName() {
      return this.name;
   }
}
