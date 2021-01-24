package dev.panda.hub.utilities.time;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang.time.DurationFormatUtils;

public class DurationFormatter {
   
   private static final long HOUR;
   private static final long MINUTE;

   public static String getRemaining(long lng, boolean v1, boolean v2) {
      return v1 && lng < MINUTE ? String.valueOf((new StringBuilder()).append(((DecimalFormat)(v2 ? DateTimeFormats.REMAINING_SECONDS_TRAILING : DateTimeFormats.REMAINING_SECONDS).get()).format((double)lng * 0.001D)).append('s')) : DurationFormatUtils.formatDuration(lng, String.valueOf((new StringBuilder()).append(lng >= HOUR ? "HH:" : "").append("mm:ss")));
   }

   static {
      MINUTE = TimeUnit.MINUTES.toMillis(1L);
      HOUR = TimeUnit.HOURS.toMillis(1L);
   }

   public static String getRemaining(long lng, boolean type) {
      return getRemaining(lng, type, true);
   }
}
