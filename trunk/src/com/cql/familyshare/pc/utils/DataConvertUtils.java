package com.cql.familyshare.pc.utils;

/**
 * 基本类型转换实用类
 * 
 * @author 34262_000
 * 
 */
public class DataConvertUtils {

	/**
	 * int转byte[]
	 * 
	 * @param i
	 * @return
	 */
	public static byte[] int2bytes(int i) {
		byte[] b = new byte[4];

		b[0] = (byte) (0xff & i);
		b[1] = (byte) ((0xff00 & i) >> 8);
		b[2] = (byte) ((0xff0000 & i) >> 16);
		b[3] = (byte) ((0xff000000 & i) >> 24);
		return b;
	}

	/**
	 * byte[]转int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int bytes2int(byte[] bytes) {
		int num = bytes[0] & 0xFF;
		num |= ((bytes[1] << 8) & 0xFF00);
		num |= ((bytes[2] << 16) & 0xFF0000);
		num |= ((bytes[3] << 24) & 0xFF000000);
		return num;
	}

	/**
	 * byte转long
	 * 
	 * @param b
	 * @return
	 */
	public static long bytes2long(byte[] b) {
		long temp = 0;
		long res = 0;
		for (int i = 0; i < 8; i++) {
			res <<= 8;
			temp = b[i] & 0xff;
			res |= temp;
		}
		return res;
	}

	/**
	 * long转bytes[]
	 * 
	 * @param num
	 * @return
	 */
	public static byte[] long2bytes(long num) {
		byte[] b = new byte[8];
		for (int i = 0; i < 8; i++) {
			b[i] = (byte) (num >>> (56 - (i * 8)));
		}
		return b;
	}
	

}