package util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class DialogUtils {
	static boolean flag;

	public static boolean showMessageDialog(String message, Activity activity) {
		flag = false;
		new AlertDialog.Builder(activity).setTitle("Confirm")
				.setMessage(message)
				.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						flag = true;
						dialog.dismiss();
					}
				}).setNegativeButton("Cancel", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						flag = false;
						dialog.dismiss();
					}
				}).show();
		return flag;
	}

	public static void showSuccessOrFailure(boolean flag, Activity activity) {
		new AlertDialog.Builder(activity).setTitle("��ʾ")
				.setMessage((flag ? "Success!" : "Fail!"))
				.setPositiveButton("Ok", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();
	}


}
