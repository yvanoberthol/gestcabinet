package com.yvanscoop.gestcabinet.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public final static Date gererDate(Date jour){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setLenient(false);
        Calendar cal = Calendar.getInstance();
        cal.setTime(jour);
        cal.add(Calendar.DATE,1);
        return cal.getTime();
    }
}
