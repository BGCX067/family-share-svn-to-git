package com.familyshare.pre.match;

/**
 * 滚动校验相关
 * 
 * @author 34262_000
 * 
 */
public class SumChecking {

	private static int block_size = 1024;

	private static char CHAR_OFFSET = 0;

	/**
	 * 非递推滚动校验 a simple 32 bit checksum that can be upadted from either end
	 * (inspired by Mark Adler's Adler-32 checksum)
	 * 
	 * @param bs
	 * @param len
	 * @return
	 */
	public static long checkSum_Adler32(byte[] buf, int len) {
		int i;
		long s1, s2;
		// for (i = 0; i < len ; i++) {
		// if (buf[i] < 0) {
		// buf[i] = (byte) -buf[i];
		// }
		// }
		s1 = s2 = 0;
		for (i = 0; i < (len - 4); i += 4) {
			s2 += 4 * (s1 + buf[i]) + 3 * buf[i + 1] + 2 * buf[i + 2]
					+ buf[i + 3];
			s1 += (buf[i + 0] + buf[i + 1] + buf[i + 2] + buf[i + 3]);
		}
		for (; i < len; i++) {
			s1 += (buf[i]);
			s2 += s1;
		}
		return (s1 & 0xffff) + ((s2 & 0xffff) << 16);
	}

	/**
	 * 递推滚动校验 adler32_checksum(X0, ..., Xn), X0, Xn+1 ----> adler32_checksum(X1,
	 * ..., Xn+1) where csum is adler32_checksum(X0, ..., Xn), c1 is X0, c2 is
	 * Xn+1
	 */
	public static long adler32_rolling_checksum(long csum, int len, byte c1,
			byte c2) {
		// if (c1 < 0) {
		// c1 = (byte) -c1;
		// }
		// if (c2 < 0) {
		// c2 = (byte) -c2;
		// }
		long s1, s2;
		s1 = (csum & 0xffff);
		s2 = (csum >> 16);
		s1 -= (c1 - c2);
		s2 -= (len * c1 - s1);
		return (s1 & 0xffff) + ((s2 & 0xffff) << 16);
	}

	/**
	 * 测试用
	 * 
	 * @param argc
	 */
	public static void main(String argc[]) {
		SumChecking sumChecking = new SumChecking();
		String s1 = "sdfsdSDFSDFSDF";
		String s2 = "dfsdSDFSDFSDFG";
		byte a1[] = { -100, -102 };
		byte a2[] = { -102, -103 };
		int length = a1.length;
		long e1 = SumChecking.checkSum_Adler32(a1, length);
		long e2 = SumChecking.checkSum_Adler32(a2, length);
		long e3 = SumChecking.adler32_rolling_checksum(e1, length, (byte) -100,
				(byte) -103);
		// long e1 = SumChecking.checkSum_Adler32(a1, a1.length);
		System.out.println("e1：" + e1);
		System.out.println("e2：" + e2);
		System.out.println("e3：" + e3);
	}

}
