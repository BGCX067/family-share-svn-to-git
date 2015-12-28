/**
 * 
 */
package com.cql.familyshare;

/**
 * 数据报类型，用于首部一个字节
 * @author 蔡庆亮
 * 
 */
public enum DatagramType {
	CHECKSUM_START, CHECKSUM_END,
	ALLMATCH,ZEROMATCH,
	UNMATCHDATA_START, UNMATCHDATA_END,
	MATCHINDEXS_START, MATCHINDEXS_END
}
