package dev.panda.hub.utilities.scoreboard;

import java.util.List;
import org.bukkit.entity.Player;

public interface BoardAdapter {
   BoardStyle getBoardStyle(Player var1);

   String getTitle(Player var1);

   List<String> getLines(Player var1);
}
