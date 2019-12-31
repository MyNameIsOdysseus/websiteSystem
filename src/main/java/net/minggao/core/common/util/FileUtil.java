package net.minggao.core.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * @author <a href="mailto:254840868@qq.com">edision</a>
 * @version v1.00 2012-7-9
 */
public class FileUtil {
	private static final Log logger = (Log) LogFactory.getLog(FileUtil.class);

	private final static int BUFFER = 1024;
	/**
	 * 功 能: 移动文件(只能移动文件) 
	 * 参 数: strSourceFileName:指定的文件全路径名 strDestDir: 移动到指定的文件夹 返回值: 如果成功true;否则false
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean copyTo(String strSourceFileName, String strDestDir){

		File fileSource = new File(strSourceFileName);
		File fileDest = new File(strDestDir);

		// 如果源文件不存或源文件是文件夹
		if (!fileSource.exists() || !fileSource.isFile()) {
			logger.debug("源文件[" + strSourceFileName + "],不存在或是文件夹!");
			return false;
		}

		// 如果目标文件夹不存在
		if (!fileDest.isDirectory() || !fileDest.exists()) {
			if (!fileDest.mkdirs()) {
				logger.debug("目录文件夹不存，在创建目标文件夹时失败!");
				return false;
			}
		}

		try {
			String strAbsFilename = strDestDir + File.separator + fileSource.getName();

			FileInputStream fileInput = new FileInputStream(strSourceFileName);
			FileOutputStream fileOutput = new FileOutputStream(strAbsFilename);

			logger.debug("开始拷贝文件");

			int count = -1;

			long nWriteSize = 0;
			long nFileSize = fileSource.length();

			byte[] data = new byte[BUFFER];

			while (-1 != (count = fileInput.read(data, 0, BUFFER))) {

				fileOutput.write(data, 0, count);

				nWriteSize += count;

				long size = (nWriteSize * 100) / nFileSize;
				long t = nWriteSize;

				String msg = null;

				if (size <= 100 && size >= 0) {
					msg = "\r拷贝文件进度:   " + size + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				} else if (size > 100) {
					msg = "\r拷贝文件进度:   " + 100 + "%   \t" + "\t   已拷贝:   " + t;
					logger.debug(msg);
				}

			}

			fileInput.close();
			fileOutput.close();

			logger.debug("拷贝文件成功!");
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
	/**
	 * 功 能: 删除指定的文件 
	 * 参 数: 指定绝对路径的文件名 strFileName 返回值: 如果删除成功true否则false;
	 * @param strFileName
	 * @return
	 */
	public static boolean delete(String strFileName) {
		File fileDelete = new File(strFileName);

		if (!fileDelete.exists() || !fileDelete.isFile()) {
			logger.debug(strFileName + "不存在!");
			return false;
		}

		return fileDelete.delete();
	}
	/**
	 * 功 能: 移动文件(只能移动文件) 
	 * 参 数: strSourceFileName: 是指定的文件全路径名 strSourceFileName: 移动到指定的文件夹中 返回值: 如果成功true; 否则false
	 * @param strSourceFileName
	 * @param strDestDir
	 * @return
	 */
	public static boolean moveFile(String strSourceFileName, String strDestDir) {
		if (copyTo(strSourceFileName, strDestDir))
			return delete(strSourceFileName);
		else
			return false;
	}
	/**
	 * 功 能: 创建文件夹 参 数: strDir 要创建的文件夹名称 返回值: 如果成功true;否则false
	 * @param strDir
	 * @return
	 */
	public static boolean makeDir(String strDir) {
		File fileNew = new File(strDir);

		if (!fileNew.exists()) {
			return fileNew.mkdirs();
		} else {
			return true;
		}
	}
	/**
	 * 功 能: 删除文件夹 
	 * 参 数: strDir 要删除的文件夹名称 返回值: 如果成功true;否则false
	 * @param strDir
	 * @return
	 */
	public static boolean removeDir(String strDir) {
		File rmDir = new File(strDir);
		if (rmDir.isDirectory() && rmDir.exists()) {
			String[] fileList = rmDir.list();

			for (int i = 0; i < fileList.length; i++) {
				String subFile = strDir + File.separator + fileList[i];
				File tmp = new File(subFile);
				if (tmp.isFile())
					tmp.delete();
				else if (tmp.isDirectory())
					removeDir(subFile);
			}
			rmDir.delete();
		} else {
			return false;
		}
		return true;
	}
	
	/**
	 * 得到文件大小
	 * @param file
	 * @return
	 */
    public static int getFileSize(File file){
    	InputStream input =null;
     	try {
     		if(null!=file&&file.exists()){
     			 input = new FileInputStream(file);
     			 int sileSize =  input.available()/1024;
     			 if(sileSize<1){
     				  sileSize=1;
     			 }
    			 return sileSize;
	     		}
     		    
			} catch (IOException e) {
				e.printStackTrace();
		}finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	return 0;
    }
    /**
     * 文件单位大小
     * @param size
     * @return
     */
    public static String[] getAccessorySize(long accessorySize){
    	  String[] result = new String[2];
    	  String accSizeStr="";
    	  String suffix = "KB";   
    	    if(accessorySize<(1024*1024)){
    	       accSizeStr=String.valueOf(accessorySize/1024.0);
    	       if(accSizeStr.length()>accSizeStr.indexOf(".")+3){
    	       	  accSizeStr=accSizeStr.substring(0,accSizeStr.indexOf(".")+3);
    	    	 }
    	       suffix ="KB";
    	    }else{
    	    	 accSizeStr=String.valueOf(accessorySize/(1024*1024.0));
    	    	 if(accSizeStr.length()>accSizeStr.indexOf(".")+3){
    	       	  accSizeStr=accSizeStr.substring(0,accSizeStr.indexOf(".")+3);
    	    	 }
    	    	 suffix ="MB";
    	    }
    	    result[0] = accSizeStr;
    	    result[1] = suffix;
    	    return result;
    }
    public static void main(String[] args) {
		long size = 203;
		String[] res = FileUtil.getAccessorySize(size);
		
	}
}
