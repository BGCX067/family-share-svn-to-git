package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

	public static int getCurrentDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * ��ȡ��ǰ������Сʱ������
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = format.format(new Date());
		return nowTime;
		// return calendar.get;
	}

	/**
	 * ��ȡ��ǰ�����ڣ�������
	 * 
	 * @return
	 */
	public static String getDate_Day() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = format.format(new Date());
		return nowTime;
		// return calendar.get;
	}

	/**
	 * ��ȡ���ʱ���id
	 * 
	 * @return
	 */
	public static String getDate_MillionSeconds() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String nowTime = format.format(new Date());
		return nowTime;
		// return calendar.get;
	}

	public static String formateDayById(int dayid) {
		String Day = "";
		switch (dayid) {
		case 1:
			Day = "Monday";
			break;
		case 2:
			Day = "TuesDay";
			break;
		case 3:
			Day = "WednesDay";
			break;
		case 4:
			Day = "Thirsday";
			break;
		case 5:
			Day = "Friday";
			break;
		case 6:
			Day = "Saturday";
			break;
		case 0:
			Day = "Sunday";
			break;
		}
		return Day;
	}

}
