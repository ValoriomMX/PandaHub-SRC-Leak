package dev.panda.hub.commands.toggle;

import com.google.common.collect.Sets;
import dev.panda.hub.Hub;
import dev.panda.hub.files.MessageFile;
import dev.panda.hub.utilities.CC;
import dev.panda.hub.utilities.scoreboard.Board;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScoreboardCommand implements CommandExecutor {

   private static ScoreboardCommand instance;
   private final Set<UUID> scoreboard = Sets.newHashSet();

   public Set<UUID> getScoreboard() {
      return this.scoreboard;
   }

   public ScoreboardCommand() {
      instance = this;
      Hub.getInstance().getCommand("togglescoreboard").setExecutor(this);
   }

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      if (!(sender instanceof Player)) {
         CC.getNoConsole(sender);
         return true;
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("pandahub.togglescoreboard")) {
            CC.getNoPermission(player);
            return true;
         } else {
            if (this.scoreboard.contains(player.getUniqueId())) {
               Hub.getInstance().getBoardManager().getBoardMap().put(player.getUniqueId(), new Board(player, Hub.getInstance().getBoardManager()));
               this.scoreboard.remove(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("SCOREBOARD.ENABLE")));
            } else {
               Hub.getInstance().getBoardManager().getBoardMap().remove(player.getUniqueId());
               player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
               this.scoreboard.add(player.getUniqueId());
               player.sendMessage(CC.set(MessageFile.getConfig().getString("SCOREBOARD.DISABLE")));
            }

            return true;
         }
      }
   }

   public static ScoreboardCommand getInstance() {
      return instance;
   }
}
