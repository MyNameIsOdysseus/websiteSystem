package net.minggao.core.common.util;
import java.util.GregorianCalendar;
import java.util.Calendar;
/**
 *  一个产生随机数的类
 * @author <a href="mailto:254840868@qq.com">edision</a>
 * @version v1.00 2012-7-9
 */
public class Random {
	/**
     * 得到一个随机数
     * @return String 返回一个以当前时间(年月日时分秒毫秒)与一个随机数串接后产生的字符串
     */
    public String getRandom() {
        Calendar calendar = new GregorianCalendar();
        StringBuffer buffer = new StringBuffer();
        buffer.append(calendar.get(Calendar.YEAR));
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int min=calendar.get(Calendar.MINUTE);
                int sec=calendar.get(Calendar.SECOND);
                int millsec=calendar.get(Calendar.MILLISECOND);
        if (month < 10) {
            buffer.append(0);
        }
        buffer.append(month);
        if (date < 10) {
            buffer.append(0);
        }
        buffer.append(date);
                if(hour<10){
                        buffer.append(0);
                }
                buffer.append(hour);
                if(min<10){
                        buffer.append(0);
                }
                buffer.append(min);
                if(sec<10){
                        buffer.append(0);
                }
                buffer.append(sec);
                if(millsec<100){
                        if(millsec<10){
                                buffer.append(0);
                        }
                        buffer.append(0);
                }
                buffer.append(millsec);
        String random = String.valueOf(Math.random());
        random = random.substring(random.length()-8, random.length());
        buffer.append(random);
        return buffer.toString();

    }
    public static void main(String[] args) {
		//System.out.println((int)(java.lang.Math.random()* 1000 ));
		//System.out.println(new Random().getRandom());
	}
    
}
