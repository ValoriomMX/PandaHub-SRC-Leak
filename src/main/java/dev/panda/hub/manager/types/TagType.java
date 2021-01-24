package dev.panda.hub.manager.types;

import dev.panda.hub.files.ConfigFile;
import dev.panda.hub.manager.data.PlayerData;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.item.ItemCreator;
import java.beans.ConstructorProperties;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public enum TagType {
   
   SNOWMAN("Snowman", "&f☃"),
   HEART("Heart", "&c❤"),
   FLOWER("Flower", "&d✿"),
   FROZEN("Frozen", "&b❆"),
   TOXIC("Toxic", "&2☣"),
   STAR("Star", "&6✪"),
   MUSIC("Music", "&c♪&e♫&a♬");
   private String name;
   private String tag;

   @ConstructorProperties({"name", "tag"})
   private TagType(String name, String tag) {
      this.name = name;
      this.tag = tag;
   }

   public String getName() {
      return this.name;
   }

   public ItemStack getItem(Player player) {
      PlayerData date = new PlayerData(player.getUniqueId());
      ItemCreator creator = new ItemCreator(Material.NAME_TAG);
      creator.setName(String.valueOf((new StringBuilder()).append("&c").append(this.getName()).append(" Tag")));
      creator.setLore(Arrays.asList(String.valueOf((new StringBuilder()).append("&7Displayed as: ").append(this.getTag())), "", "&aClick to equip this tag."));
      if (!player.hasPermission(String.valueOf((new StringBuilder()).append("pandahub.settings.tag.").append(this.getName().toLowerCase())))) {
         creator.setLore(Arrays.asList("", "&cYou don't have permission to this tag.", String.valueOf((new StringBuilder()).append("&cPurchase at ").append(ConfigFile.getConfig().getString("STORE")))));
      }

      if (this.getName().equals(date.getTag())) {
         creator.setLore(Arrays.asList("", "&aEquipped."));
      }

      return creator.get();
   }

   public String getTag() {
      return CC.set(this.tag);
   }
}
