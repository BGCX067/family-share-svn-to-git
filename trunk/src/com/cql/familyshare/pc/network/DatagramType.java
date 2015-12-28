/**
 * 
 */
package com.cql.familyshare.pc.network;


/**
 * This class is used for : List all the type of datagram 
 * 
 * @author cql
 *
 * date:2013年8月22日
 */
public interface DatagramType {
	byte ALLMATCH = 0;
	byte CHECKSUM_END = 1;
	byte CHECKSUM_START = 2;
	byte MATCHINDEXS_END = 3;
	byte MATCHINDEXS_START = 4;
	byte UNMATCHDATA_END = 5;
	byte UNMATCHDATA_INDEX_INVALID = 6;
	byte UNMATCHDATA_INDEX_VALID = 7;
	byte UNMATCHDATA_START = 8;
	byte ZEROMATCH = 9;
}
