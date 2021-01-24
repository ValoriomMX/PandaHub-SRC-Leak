package dev.panda.hub.utilities.time;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang.time.FastDateFormat;

public class DateTimeFormats {
   
   public static ThreadLocal<DecimalFormat> REMAINING_SECONDS;
   public static FastDateFormat HR_MIN;
   public static FastDateFormat HR_MIN_AMPM_TIMEZONE;
   public static FastDateFormat DAY_MTH_YR_HR_MIN_AMPM;
   public static ZoneId SERVER_ZONE_ID;
   public static FastDateFormat DAY_MTH_HR_MIN_SECS;
   public static TimeZone SERVER_TIME_ZONE = TimeZone.getTimeZone("CET");
   public static FastDateFormat DAY_MTH_HR_MIN_AMPM;
   public static ThreadLocal<DecimalFormat> REMAINING_SECONDS_TRAILING;
   public static FastDateFormat MIN_SECS;
   public static FastDateFormat HR_MIN_AMPM;
   public static FastDateFormat KOTH_FORMAT;

   static {
      SERVER_ZONE_ID = SERVER_TIME_ZONE.toZoneId();
      DAY_MTH_HR_MIN_SECS = FastDateFormat.getInstance("dd/MM HH:mm:ss", SERVER_TIME_ZONE, Locale.ENGLISH);
      DAY_MTH_YR_HR_MIN_AMPM = FastDateFormat.getInstance("dd/MM/yy hh:mma", SERVER_TIME_ZONE, Locale.ENGLISH);
      DAY_MTH_HR_MIN_AMPM = FastDateFormat.getInstance("dd/MM hh:mma", SERVER_TIME_ZONE, Locale.ENGLISH);
      HR_MIN_AMPM = FastDateFormat.getInstance("hh:mma", SERVER_TIME_ZONE, Locale.ENGLISH);
      HR_MIN_AMPM_TIMEZONE = FastDateFormat.getInstance("hh:mma z", SERVER_TIME_ZONE, Locale.ENGLISH);
      HR_MIN = FastDateFormat.getInstance("hh:mm", SERVER_TIME_ZONE, Locale.ENGLISH);
      MIN_SECS = FastDateFormat.getInstance("mm:ss", SERVER_TIME_ZONE, Locale.ENGLISH);
      KOTH_FORMAT = FastDateFormat.getInstance("m:ss", SERVER_TIME_ZONE, Locale.ENGLISH);
      REMAINING_SECONDS = new ThreadLocal<DecimalFormat>() {
         protected DecimalFormat initialValue() {
            return new DecimalFormat("0.#");
         }
      };
      REMAINING_SECONDS_TRAILING = new ThreadLocal<DecimalFormat>() {
         protected DecimalFormat initialValue() {
            return new DecimalFormat("0.0");
         }
      };
   }
}
