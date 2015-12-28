package com.familyshare.pre.match;

/**
 * 校验类（包含滚动和md4）
 * @author 34262_000
 *
 */
public class MySum {

	/**
	 * 滚动校验码
	 */
	private long rollCheckSum = 0;
	/**
	 * md4校验码
	 */
	private byte[] md4CheckSum = null;
	/**
	 * Hash索引
	 */
	private int hashIndex = 0;
	/**
	 * 当Hash索引冲突时添加校验类到此数组中
	 */
	private MySum[] mySum = null;
	/**
	 * mySum数组的索引
	 */
	private int mySum_index = 0;
	/**
	 * 用来表示发过来块的索引
	 */
	private int buffer_index = 0;
	/**
	 * 静态变量：用来表示发过来的块数量
	 */
	private static int buffer_num = 0;

	//
	// public MySum() {
	//
	// }

	public MySum(long rollCheckSum, byte[] sum2) {
		this.rollCheckSum = rollCheckSum;
		this.hashIndex = Math.abs(MyHash.BIG_SUM2HASH(rollCheckSum));
		this.md4CheckSum = sum2.clone();
		buffer_index = buffer_num++;
	}

	/**
	 * 当哈希索引冲突时调用，添加到mySum数组
	 * @param mySum
	 */
	public void addNextSum(MySum mySum) {
		int i = 0;
		if (this.mySum == null) {
			this.mySum = new MySum[8000];
		}
		while (this.mySum[i] != null) {
			i++;
		}
		this.mySum[i] = mySum;
		// if (this.mySum == null) {
		// this.mySum = mySum;
		// } else if (this.mySum.mySum == null) {
		// this.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum == null) {
		// this.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum == null) {
		// this.mySum.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum.mySum == null) {
		// this.mySum.mySum.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum.mySum.mySum == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum ==
		// null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum = mySum;
		// } else if (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum = mySum;
		// } else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum ==
		// null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum =
		// mySum;
		// } else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// } else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }else if
		// (this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// == null) {
		// this.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum.mySum
		// = mySum;
		// }
	}

	/**
	 * 获取mySum中下一个校验
	 * @return
	 */
	public MySum getNextSum() {
		if (mySum == null) {
			return null;
		} else {
			return mySum[mySum_index++];
		}
	}

	public long getRollCheckSum() {
		return rollCheckSum;
	}

	public int getHashIndex() {
		return hashIndex;
	}

	// public MySum getMySum() {
	// return mySum;
	// }

	public String getMd4CheckSum() {
		return Md4Checking.toHexString(md4CheckSum);
	}

	/**
	 * 判断校验是否相等
	 * @param mySum
	 * @return
	 */
	public boolean equals(MySum mySum) {
		// TODO Auto-generated method stub
		if (mySum.getRollCheckSum() != rollCheckSum) {
			return false;
		} else if (!mySum.getMd4CheckSum().equals(this.getMd4CheckSum())) {
			return false;
		} else {
			return true;
		}
	}

	public int getBuffer_index() {
		return buffer_index;
	}

	public static void setBuffer_num(int buffer_num) {
		MySum.buffer_num = buffer_num;
	}

}
