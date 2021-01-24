package dev.panda.hub.utilities.scoreboard;

import dev.panda.hub.utilities.CC;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Board {
   
   private UUID id;
   private List<String> strings = new ArrayList();
   private Objective objective;
   private List<BoardEntry> entries = new ArrayList();
   private BoardManager board;
   private Scoreboard scoreboard;
   
   public List<BoardEntry> getEntries() {
      return this.entries;
   }

   private static List<ChatColor> colors() {
      List<ChatColor> list = new ArrayList();
      Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).forEach((s) -> {
         list.add(s);
      });
      return list;
   }

   BoardEntry getEntryAtPosition(int v1) {
      return v1 >= this.entries.size() ? null : (BoardEntry)this.entries.get(v1);
   }

   private static String getRandomColor() {
      Random r = new Random();
      return ((ChatColor)colors().get(r.nextInt(colors().size() - 1))).toString();
   }

   public void setUp(Player player) {
      if (player.getScoreboard() != Bukkit.getScoreboardManager().getMainScoreboard()) {
         this.scoreboard = player.getScoreboard();
      } else {
         this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
      }

      this.objective = this.scoreboard.registerNewObjective("Default", "dummy");
      this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      if (this.board.getAdapter().getTitle(player) == null) {
         this.objective.setDisplayName(CC.set("&bDefault Tittle"));
      } else {
         this.objective.setDisplayName(this.board.getAdapter().getTitle(player));
      }

      player.setScoreboard(this.scoreboard);
   }

   public Objective getObjective() {
      return this.objective;
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public Board(Player player, BoardManager manager) {
      this.id = player.getUniqueId();
      this.board = manager;
      this.setUp(player);
   }

   public BoardManager getBoard() {
      return this.board;
   }

   String getUniqueString() {
      String s1;
      for(s1 = getRandomColor(); this.strings.contains(s1); s1 = String.valueOf((new StringBuilder()).append(s1).append(getRandomColor()))) {
      }

      if (s1.length() > 16) {
         return this.getUniqueString();
      } else {
         this.strings.add(s1);
         return s1;
      }
   }

   public List<String> getStrings() {
      return this.strings;
   }

   public UUID getId() {
      return this.id;
   }
}
