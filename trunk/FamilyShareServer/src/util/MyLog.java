package util;

import android.util.Log;
import android.widget.TextView;

public class MyLog {
	TextView textView_message=null;
	
	public static void logError(String message) {
		Log.e("myerror", "myerror:" + message);
	}

	public static void logDBError(Exception e) {
		Log.e("myerror", "mydberror:" + e.getMessage());
	}
	
	public static void logErrot(Exception e,String tag){
		Log.e(tag+":", tag + e.getMessage());
	}

	public TextView getTextView_message() {
		return textView_message;
	}

	public void setTextView_message(TextView textView_message) {
		this.textView_message = textView_message;
	}
	
	public void showTextViewMessage(String message){
		if(textView_message!=null){
			textView_message.setText(message+"\n\r");
		}
	}
	public void showTextViewError(Exception e){
		if(textView_message!=null){
			textView_message.setText(e.getMessage()+"\n\r");
		}
	}
	
	public void addTextViewMessage(String message){
		if(textView_message!=null){
			textView_message.append(message+"\n\r");
		}
	}
	
	public void addTextViewError(Exception e){
		if(textView_message!=null){
			textView_message.append(e.getMessage()+"\n\r");
		}
	}
	
	public static void showTextViewMessage(String message,TextView textview){
		if(textview!=null){
			textview.setText(message+"\n\r");
		}
	}
	public static void addTextViewMessage(String message,TextView textview){
		if(textview!=null){
			textview.append(message+"\n\r");
		}
	}
}
