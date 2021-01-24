package dev.panda.hub.utilities.scoreboard;

import dev.panda.hub.Hub;
import dev.panda.hub.utilities.CC;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class BoardManager {
   
   private BoardAdapter adapter;
   private Map<UUID, Board> boardMap;

   public BoardManager(BoardAdapter adapter, int v1) {
      this.adapter = adapter;
      this.boardMap = new HashMap();
      Bukkit.getPluginManager().registerEvents(new BoardListener(this), Hub.getInstance());
      Bukkit.getScheduler().runTaskTimer(Hub.getInstance(), this::sendScoreboard, 0L, (long)v1);
   }

   public BoardAdapter getAdapter() {
      return this.adapter;
   }

   public Map<UUID, Board> getBoardMap() {
      return this.boardMap;
   }

   public void sendScoreboard() {
      Iterator iterator = Bukkit.getServer().getOnlinePlayers().iterator();

      while(true) {
         Player player;
         Board board;
         do {
            if (!iterator.hasNext()) {
               return;
            }

            player = (Player)iterator.next();
            board = (Board)this.boardMap.get(player.getUniqueId());
         } while(board == null);

         Scoreboard scoreboard = board.getScoreboard();
         Objective objective = board.getObjective();
         String title = CC.set(this.adapter.getTitle(player));
         if (!objective.getDisplayName().equals(title)) {
            objective.setDisplayName(title);
         }

         List<String> lines = this.adapter.getLines(player);
         if (lines != null && !lines.isEmpty()) {
            if (!this.adapter.getBoardStyle(player).isDescending()) {
               Collections.reverse(lines);
            }

            int i;
            if (board.getEntries().size() > lines.size()) {
               for(i = lines.size(); i < board.getEntries().size(); ++i) {
                  BoardEntry entry = board.getEntryAtPosition(i);
                  if (entry != null) {
                     entry.quit();
                  }
               }
            }

            i = this.adapter.getBoardStyle(player).getStart();

            for(int t = 0; t < lines.size(); ++t) {
               BoardEntry entry2 = board.getEntryAtPosition(t);
               String sel = CC.set((String)lines.get(t));
               if (entry2 == null) {
                  entry2 = new BoardEntry(board, sel);
               }

               entry2.setText(sel);
               entry2.setUp();
               entry2.send(this.adapter.getBoardStyle(player).isDescending() ? i-- : i++);
            }
         } else {
            board.getEntries().forEach(BoardEntry::quit);
            board.getEntries().clear();
         }

         if (player.getScoreboard() != scoreboard) {
            player.setScoreboard(scoreboard);
         }
      }
   }
}
