package dev.panda.hub.utilities.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class BoardEntry {
   
   private Board board;
   private String text;
   private Team team;
   private String string;

   public void setUp() {
      Scoreboard scoreboard = this.board.getScoreboard();
      if (scoreboard != null) {
         String s = this.string;
         if (s.length() > 16) {
            s = s.substring(0, 16);
         }

         Team team;
         if (scoreboard.getTeam(s) != null) {
            team = scoreboard.getTeam(s);
         } else {
            team = scoreboard.registerNewTeam(s);
         }

         if (!team.getEntries().contains(this.string)) {
            team.addEntry(this.string);
         }

         if (!this.board.getEntries().contains(this)) {
            this.board.getEntries().add(this);
         }

         this.team = team;
      }

   }

   public void setBoard(Board board) {
      this.board = board;
   }

   public void quit() {
      this.board.getStrings().remove(this.string);
      this.board.getScoreboard().resetScores(this.string);
   }

   public String getString() {
      return this.string;
   }

   public void setTeam(Team team) {
      this.team = team;
   }

   public void send(int i) {
      if (this.text.length() > 16) {
         String s1 = this.text.substring(0, 16);
         String s2;
         if (s1.charAt(15) == 167) {
            s1 = s1.substring(0, 15);
            s2 = this.text.substring(15);
         } else if (s1.charAt(14) == 167) {
            s1 = s1.substring(0, 14);
            s2 = this.text.substring(14);
         } else if (ChatColor.getLastColors(s1).equalsIgnoreCase(ChatColor.getLastColors(this.string))) {
            s2 = this.text.substring(16);
         } else {
            s2 = String.valueOf((new StringBuilder()).append(ChatColor.getLastColors(s1)).append(this.text.substring(16)));
         }

         if (s2.length() > 16) {
            s2 = s2.substring(0, 16);
         }

         this.team.setPrefix(s1);
         this.team.setSuffix(s2);
      } else {
         this.team.setPrefix(this.text);
         this.team.setSuffix("");
      }

      Score score = this.board.getObjective().getScore(this.string);
      score.setScore(i);
   }

   public void setString(String input) {
      this.string = input;
   }

   public Board getBoard() {
      return this.board;
   }

   public Team getTeam() {
      return this.team;
   }

   public void setText(String input) {
      this.text = input;
   }

   public BoardEntry(Board board, String input) {
      this.board = board;
      this.text = input;
      this.string = board.getUniqueString();
      this.setUp();
   }

   public String getText() {
      return this.text;
   }
}
