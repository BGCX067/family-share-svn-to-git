package com.familyshare.pre.match;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

import com.familyshare.pre.utils.DataConvertUtils;

/**
 * 哈希表相关
 * 
 * @author 34262_000
 * 
 */
public class MyHash {
	public static final long TRADITIONAL_TABLESIZE = 1 << 16;

	public static int tablesize = 1 << 16;

	public static Hashtable<Integer, MySum> hashTable = new Hashtable<Integer, MySum>();

	private MySum mySums_table[];

	private int i;

	public MyHash() {
		createSumTable();
	}

	/**
	 * 建立空哈希表
	 * 
	 * @return
	 */
	public MySum[] createSumTable() {
		mySums_table = new MySum[tablesize];
		return mySums_table;
	}

	/**
	 * 按校验码输入流建立哈希表
	 * 
	 * @param in
	 */
	public void buildHashTable(InputStream in) {
		DataInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new DataInputStream(in);
			byte b[] = new byte[8];
			byte b2[] = new byte[16];
			MySum.setBuffer_num(0);
			do {
				b[0] = bufferedInputStream.readByte();
				b[1] = bufferedInputStream.readByte();
				b[2] = bufferedInputStream.readByte();
				b[3] = bufferedInputStream.readByte();
				b[4] = bufferedInputStream.readByte();
				b[5] = bufferedInputStream.readByte();
				b[6] = bufferedInputStream.readByte();
				b[7] = bufferedInputStream.readByte();

				b2[0] = bufferedInputStream.readByte();
				b2[1] = bufferedInputStream.readByte();
				b2[2] = bufferedInputStream.readByte();
				b2[3] = bufferedInputStream.readByte();
				b2[4] = bufferedInputStream.readByte();
				b2[5] = bufferedInputStream.readByte();
				b2[6] = bufferedInputStream.readByte();
				b2[7] = bufferedInputStream.readByte();
				b2[8] = bufferedInputStream.readByte();
				b2[9] = bufferedInputStream.readByte();
				b2[10] = bufferedInputStream.readByte();
				b2[11] = bufferedInputStream.readByte();
				b2[12] = bufferedInputStream.readByte();
				b2[13] = bufferedInputStream.readByte();
				b2[14] = bufferedInputStream.readByte();
				b2[15] = bufferedInputStream.readByte();
				long sum1 = DataConvertUtils.bytes2long(b);
				// System.out.println("sum1:" + sum1);
				// System.out.println("sum2:" + Md4Checking.toHexString(b2));
				MySum mySum = new MySum(sum1, b2);
				addSum(mySum);
			} while (bufferedInputStream.available() != 0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加校验到哈希表
	 * 
	 * @param mySum
	 * @throws CloneNotSupportedException
	 */
	public void addSum(MySum mySum) throws CloneNotSupportedException {
		int index = mySum.getHashIndex();
		if (mySums_table[index] == null) {
			mySums_table[index] = mySum;
		} else {
			mySums_table[index].addNextSum(mySum);
		}
	}

	/**
	 * 检查哈希表中是否存在该校验码
	 * 
	 * @param mySum
	 * @return
	 */
	public boolean checkExists(MySum mySum) {
		MySum m2 = mySums_table[mySum.getHashIndex()];
		MySum m1 = m2;
		while (m2 != null) {
			// if (mySum.getRollCheckSum() != m2.getRollCheckSum()) {
			// m2 = m1.getNextSum();
			// } else if (!mySum.getMd4CheckSum().equals(m2.getMd4CheckSum())) {
			// m2 = m1.getNextSum();
			// } else {
			// return true;
			// }
			if (m2.equals(mySum)) {
				// System.out.println("match" );
				return true;
			} else {
				m2 = m1.getNextSum();
			}
		}
		// System.out.println("notmatch"+ (++i));
		return false;
	}

	/**
	 * Hash函数
	 * 
	 * @param sum
	 * @return
	 */
	public static long SUM2HASH(long sum) {
		return SUM2HASH2((sum) & 0xFFFF, (sum) >> 16);
	}

	/**
	 * 哈希函数
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static long SUM2HASH2(long s1, long s2) {
		return (((s1) + (s2)) & 0xFFFF);
	}

	/**
	 * 哈希函数
	 * 
	 * @param sum
	 * @return
	 */
	public static int BIG_SUM2HASH(long sum) {
		return (int) (Math.abs(sum) % tablesize);
	}

}
