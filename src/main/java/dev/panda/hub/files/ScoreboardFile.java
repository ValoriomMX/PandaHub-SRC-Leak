package dev.panda.hub.files;

import dev.panda.hub.Hub;
import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ScoreboardFile extends YamlConfiguration {

   private File configFile;
   private static ScoreboardFile config;
   private Plugin plugin;

   public void reload() {
      try {
         super.load(this.configFile);
      } catch (Exception var2) {
      }

   }

   public static ScoreboardFile getConfig() {
      if (config == null) {
         config = new ScoreboardFile();
      }

      return config;
   }

   public void save() {
      try {
         super.save(this.configFile);
      } catch (Exception var3) {
      }

   }

   public ScoreboardFile() {
      this.plugin = this.main();
      this.configFile = new File(this.plugin.getDataFolder(), "scoreboard.yml");
      this.saveDefault();
      this.reload();
   }

   private Plugin main() {
      return Hub.getInstance();
   }

   public void saveDefault() {
      this.plugin.saveResource("scoreboard.yml", false);
   }
}
