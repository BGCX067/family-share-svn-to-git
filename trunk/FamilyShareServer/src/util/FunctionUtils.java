/**
 * 
 */
package util;

/**
 * @author 34262_000 ����10:13:04
 */
public class FunctionUtils {
	TryCatchMethod tryCathMethod;

	/**
	 * ִ�к���ȷ���Ƿ�ɹ�
	 * 
	 * @param tryCathMethod
	 * @return
	 */
	public static boolean doTryCatchMethod(TryCatchMethod tryCatchMethod) {
		boolean flag = false;
		try {
			tryCatchMethod.doMethod();
			flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			MyLog.logDBError(e);
			flag = false;
		} finally {
			tryCatchMethod.dofinnalMethod();
		}
		return flag;
	}
}
