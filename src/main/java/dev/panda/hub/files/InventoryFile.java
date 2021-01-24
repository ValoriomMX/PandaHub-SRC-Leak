package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class InventoryFile extends YamlConfiguration {

   private static InventoryFile config;
   private File configFile;
   private Plugin plugin;

   private Plugin main() {
      return Hub.getInstance();
   }

   public void save() {
      try {
         super.save(this.configFile);
      } catch (Exception var2) {
      }

   }

   public void saveDefault() {
      this.plugin.saveResource("inventory.yml", false);
   }

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }

   public static InventoryFile getConfig() {
      if (config == null) {
         config = new InventoryFile();
      }

      return config;
   }

   public InventoryFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "inventory.yml");
      this.saveDefault();
      this.reload();
   }
}
