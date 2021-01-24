package dev.panda.hub.utilities;

import dev.panda.hub.Hub;

public class Description {
   public static String getVersion() {
      return Hub.getInstance().getDescription().getVersion();
   }

   public static String getAuthor() {
      return Hub.getInstance().getDescription().getAuthors().toString().replace("[", "").replace("]", "");
   }

   public static String getName() {
      return Hub.getInstance().getDescription().getName();
   }
}
