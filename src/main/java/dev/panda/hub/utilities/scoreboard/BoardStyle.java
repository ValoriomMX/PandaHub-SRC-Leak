package dev.panda.hub.utilities.scoreboard;

public enum BoardStyle {

   DEFAULT(false, 0);
   private boolean descending;
   private int start;

   BoardStyle(boolean type, int i) {
      this.descending = type;
      this.start = i;
   }

   public boolean isDescending() {
      return this.descending;
   }

   public int getStart() {
      return this.start;
   }
}
