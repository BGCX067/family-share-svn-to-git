package com.cql.familyshare.pc.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * 文件重组接口
 * @author 蔡庆亮
 *
 */
public interface ClientFileRebuider {

	public static final String FILEREBUILD = "./filerebuild.txt"; //$NON-NLS-1$

	/**
	 * 算法描述： read a flagment of fileback get index,if index =0, write data to
	 * fileback, else read a block of filehalf and write to fileback repeat
	 * index times, read a flagment of fileback... when either hand come to
	 * end,stop repeat and write remained data to fileback
	 * 
	 * 算法描述2：loop until read to the end of fileback ,do : .read a block_fileback
	 * .get index .if curentindex<index .get blockindex from filematchindexs by
	 * curentindex .else get by index .seek to blickindex*Block point in
	 * filehalf,read a block_filehalf .write block_filehalf to filerebuild,write
	 * data from block_fileback to filerebuild. .do end write remained data to
	 * filerebuild
	 * 
	 * @param filehalf
	 * @param fileback
	 * @param filerebuid
	 * @throws IOException
	 */
	public abstract void rebuidFile(String filehalf, String fileback,
			String filerebuid) throws IOException;

	/**
	 * 算法描述： read a flagment of fileback get index,if index =0, write data to
	 * fileback, else read a block of filehalf and write to fileback repeat
	 * index times, read a flagment of fileback... when either hand come to
	 * end,stop repeat and write remained data to fileback
	 * 
	 * 算法描述2：loop until read to the end of fileback ,do : .read a block_fileback
	 * .get index .if curentindex<index .get blockindex from filematchindexs by
	 * curentindex .else get by index .seek to blickindex*Block point in
	 * filehalf,read a block_filehalf .write block_filehalf to filerebuild,write
	 * data from block_fileback to filerebuild. .do end write remained data to
	 * filerebuild
	 * @param filehalf
	 * @param inFileBack
	 * @param outFileRebuid
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public abstract void rebuidFile(String filehalf, InputStream inFileBack,
			OutputStream outFileRebuid) throws FileNotFoundException,
			IOException;

}