package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class DataFile extends YamlConfiguration {

   private Plugin plugin;
   private File configFile;
   private static DataFile config;

   private Plugin main() {
      return Hub.getInstance();
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

   public static DataFile getConfig() {
      if (config == null) {
         config = new DataFile();
      }

      return config;
   }

   public DataFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "data.yml");
      this.saveDefault();
      this.reload();
   }

   public void save() {
      try {
         super.save(this.configFile);
      } catch (Exception var2) {
      }

   }

   public void saveDefault() {
      this.plugin.saveResource("data.yml", false);
   }
}
