/**
 * 
 */
package util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @author 34262_000 ����9:47:23
 */
public class WindowUtils {

	public static int getWidth(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels; // ��Ļ��ȣ����أ�

	}

	public static int getHeight(Activity activity) {
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels; // ��Ļ��ȣ����أ�
	}
}
