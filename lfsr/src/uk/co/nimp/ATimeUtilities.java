package uk.co.nimp;


import java.text.SimpleDateFormat;
import java.util.Date;


public class ATimeUtilities {
    static public String getTimeTagSecond(){
        SimpleDateFormat date=new SimpleDateFormat();
        date.applyPattern("yyyyMMddHHmmss");
        return date.format(new Date());
    }
    static public String getTimeTagMillisecond(){
        SimpleDateFormat date=new SimpleDateFormat();
        date.applyPattern("yyyyMMddHHmmssSSS");
        return date.format(new Date());
    }
    static public String getTimeForUi(){
        SimpleDateFormat date=new SimpleDateFormat();
        date.applyPattern("yyyy-MM-dd - HH:mm:ss");
        return date.format(new Date());
    }
    static public String getUserFriendlyTimeMs(long timeNs) {
        if(timeNs<0)
            throw new RuntimeException("timeNs is negative: "+timeNs);
        final long MILLISEC = 1000000;
        final long SEC = 1000 * MILLISEC;
        final long MINUTE = SEC * 60;
        final long HOUR = MINUTE * 60;
        if (timeNs < MILLISEC) {
            return "0ms";
        }
        StringBuilder sb = new StringBuilder();
        String comma = "";
        long hours = timeNs / HOUR;
        timeNs -= hours * HOUR;
        if (0 != hours) {
            sb.append(hours);
            sb.append(" hours");
            comma = ",";
        }
        long minutes = timeNs / MINUTE;
        timeNs -= minutes * MINUTE;
        if (0 != minutes) {
            sb.append(comma);
            sb.append(minutes);
            sb.append(" minutes");
            comma = ",";
        }
        long sec = timeNs / SEC;
        timeNs -= sec * SEC;
        if (0 != sec) {
            sb.append(comma);
            sb.append(sec);
            sb.append(" seconds");
            comma = ",";
        }
        long ms = timeNs / MILLISEC;
        timeNs -= ms * MILLISEC;
        if (0 != ms) {
            sb.append(comma);
            sb.append(ms);
            sb.append("ms");
            comma = ",";
        }
        return sb.toString();
    }
    static public String getUserFriendlyTimeUs(long timeNs) {
        if(timeNs<0)
            throw new RuntimeException("timeNs is negative: "+timeNs);
        final long USEC = 1000;
        final long MILLISEC = 1000000;
        if (timeNs < USEC) {
            return "0us";
        }
        StringBuilder sb = new StringBuilder();
        String comma = "";
        sb.append(getUserFriendlyTimeMs(timeNs));
        if (sb.length() >0) {
            sb.append(",");
        }
        long us=(timeNs%MILLISEC)/USEC;
        sb.append(us);
        sb.append("us");
        return sb.toString();
    }

}
