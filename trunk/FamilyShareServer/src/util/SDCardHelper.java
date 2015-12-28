/**
 * 
 */
package util;

import java.io.File;
import java.io.IOException;

import android.os.Environment;

/**
 * @author 34262_000
 * 
 */
public class SDCardHelper {
	private String saveHome = Environment.getExternalStorageDirectory()
			.getName();

	/**
	 * 判断sd卡是否可用
	 * 
	 * @return
	 */
	public boolean checkSDAccess() {
		boolean flag = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			flag = true;
		}
		return flag;
	}
	
	public File createFileRelatively(String relativePath,String name) throws IOException{
		File path=new File(saveHome+"/"+relativePath);
		File file=new File(saveHome+"/"+relativePath+"/"+name);
		if(!path.exists()){
			path.mkdirs();
		}
		if(!file.exists()){
				file.createNewFile();
		}
		return file;
	}


	public String getSaveHome() {
		return saveHome;
	}

	public void setSaveHome(String saveHome) {
		this.saveHome = saveHome;
	}
	
	public File getFile(String relativePath,String name){
		File file=new File(saveHome+"/"+relativePath+"/"+name);
		if(!file.exists()){
				return null;
		}
		return file;
	}
}
