package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class QueueFile extends YamlConfiguration {

   private File configFile;
   private static QueueFile config;
   private Plugin plugin;

   public QueueFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "queue.yml");
      this.saveDefault();
      this.reload();
   }

   public static QueueFile getConfig() {
      if (config == null) {
         config = new QueueFile();
      }

      return config;
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

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }

   public void saveDefault() {
      this.plugin.saveResource("queue.yml", false);
   }

   public void saveAll() {
      this.save();
      this.reload();
   }
}
