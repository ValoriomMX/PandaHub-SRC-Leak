package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TablistFile extends YamlConfiguration {
   
   private File configFile;
   private Plugin plugin;
   private static TablistFile config;

   public void save() {
      try {
         super.save(this.configFile);
      } catch (Exception var2) {
      }

   }

   public TablistFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "tablist.yml");
      this.saveDefault();
      this.reload();
   }

   public static TablistFile getConfig() {
      if (config == null) {
         config = new TablistFile();
      }

      return config;
   }

   public void saveDefault() {
      this.plugin.saveResource("tablist.yml", false);
   }

   private Plugin main() {
      return Hub.getInstance();
   }

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }
}
