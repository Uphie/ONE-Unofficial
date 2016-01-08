package studio.uphie.one.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class TimeUtil {

    /**
     * 日期格式
     */
    private static SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 时间格式，注：
     * h am/pm 中的小时数（1-12） Number 12
     * H 一天中的小时数（0-23） Number 0
     * k 一天中的小时数（1-24） Number 24
     * K am/pm 中的小时数（0-11） Number 0
     */
    private static SimpleDateFormat format_time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取当前日期，eg: 2015-09-06
     *
     * @return
     */
    public static String getDate() {
        return format_date.format(new Date());
    }

    /**
     * 获取指定时间的日期，eg: 2015-09-06
     *
     * @param time 2015-08-26 17:16:12 格式
     * @return
     */
    public static String getDate(String time) {
        try {
            Date date = format_time.parse(time);
            return format_date.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取英文格式的日期，eg: Sep. 9,2015
     *
     * @param date
     * @return
     */
    public static String getEngDate(String date) {
        try {
            Date d = format_date.parse(date);
            StringBuilder builder = new StringBuilder();
            switch (d.getMonth()) {
                case 0:
                    builder.append("January");
                    break;
                case 1:
                    builder.append("February");
                    break;
                case 2:
                    builder.append("March");
                    break;
                case 3:
                    builder.append("April");
                    break;
                case 4:
                    builder.append("May");
                    break;
                case 5:
                    builder.append("June");
                    break;
                case 6:
                    builder.append("July");
                    break;
                case 7:
                    builder.append("Aguest");
                    break;
                case 8:
                    builder.append("September");
                    break;
                case 9:
                    builder.append("October");
                    break;
                case 10:
                    builder.append("November");
                    break;
                case 11:
                    builder.append("December");
                    break;
            }
            builder.append(" " + new SimpleDateFormat("dd,yyyy").format(d));
            return builder.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 将指定日期进行偏移，获取偏移后的日期，eg:2015-09-06
     *
     * @param date   要偏移的日期，需是"2015-09-06"格式
     * @param offset 偏移的值，可正可负
     * @return
     */
    public static String getPreviousDate(String date, int offset) {
        try {
            Date d = format_date.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.roll(Calendar.DAY_OF_MONTH, offset);
            return format_date.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得日期中的日
     *
     * @param date
     * @return
     */
    public static String getDay(String date) {
        try {
            Date d = format_date.parse(date);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(d);
            int day=calendar.get(Calendar.DAY_OF_MONTH);

            return day<10?"0"+day:""+day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 月份和年
     *
     * @param date
     * @return
     */
    public static String getMonthAndYear(String date) {
        try {
            Date d = format_date.parse(date);
            StringBuilder builder = new StringBuilder();
            switch (d.getMonth()) {
                case 0:
                    builder.append("Jan,");
                    break;
                case 1:
                    builder.append("Feb,");
                    break;
                case 2:
                    builder.append("Mar,");
                    break;
                case 3:
                    builder.append("Apr,");
                    break;
                case 4:
                    builder.append("May,");
                    break;
                case 5:
                    builder.append("Jun,");
                    break;
                case 6:
                    builder.append("Jul,");
                    break;
                case 7:
                    builder.append("Agg,");
                    break;
                case 8:
                    builder.append("Sep,");
                    break;
                case 9:
                    builder.append("Oct,");
                    break;
                case 10:
                    builder.append("Nov,");
                    break;
                case 11:
                    builder.append("Dec,");
                    break;
            }
            builder.append(d.getYear()+1900);
            return builder.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
