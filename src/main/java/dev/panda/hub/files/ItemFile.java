package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ItemFile extends YamlConfiguration {

   private File configFile;
   private static ItemFile config;
   private Plugin plugin;

   public ItemFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "items.yml");
      this.saveDefault();
      this.reload();
   }

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
      this.plugin.saveResource("items.yml", false);
   }

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }

   public void saveAll() {
      this.save();
      this.reload();
   }

   public static ItemFile getConfig() {
      if (config == null) {
         config = new ItemFile();
      }

      return config;
   }
}
