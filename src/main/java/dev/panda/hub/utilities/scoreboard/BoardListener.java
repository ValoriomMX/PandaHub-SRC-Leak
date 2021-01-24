package dev.panda.hub.utilities.scoreboard;

import java.beans.ConstructorProperties;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BoardListener implements Listener {
   
   private BoardManager board;

   @ConstructorProperties({"board"})
   public BoardListener(BoardManager board) {
      this.board = board;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      this.board.getBoardMap().put(event.getPlayer().getUniqueId(), new Board(event.getPlayer(), this.board));
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      this.board.getBoardMap().remove(event.getPlayer().getUniqueId());
   }
}
