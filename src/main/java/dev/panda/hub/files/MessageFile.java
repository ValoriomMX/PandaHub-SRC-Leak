package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class MessageFile extends YamlConfiguration {

   private static MessageFile config;
   private Plugin plugin;
   private File configFile;

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }

   public void saveDefault() {
      this.plugin.saveResource("messages.yml", false);
   }

   public static MessageFile getConfig() {
      if (config == null) {
         config = new MessageFile();
      }

      return config;
   }

   public void save() {
      try {
         super.save(this.configFile);
      } catch (Exception var2) {
      }

   }

   public MessageFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "messages.yml");
      this.saveDefault();
      this.reload();
   }

   private Plugin main() {
      return Hub.getInstance();
   }
}
