package net.minggao.core.component.pluUploader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AbstractUpload {
	private Log logger = LogFactory.getLog(AbstractUpload.class);

	public static final int SALT = 9;
	protected static  String EXTENSION = ""; //系统支持上传的文件的类型
	protected static Map EXTENSION_MAP = null;//系统支持上传的文件的类型
	
	/**
     * 
     * 是否为系统允许上传的文件
     * @param filenameExt  文件的扩展名
     * @return      
     * @author        liumc
     * @Date          2017-3-16 下午4:36:31
     * 
     * @return  true允许上传，false不允许上传
     */
	protected boolean isAllowedUploadFile(String filenameExt){
    	boolean rslt = false;
    	if(null == EXTENSION_MAP){
    		EXTENSION_MAP = new HashMap();
	    	String[] arr = EXTENSION.split(",");
	    	for(String str:arr){
	    		EXTENSION_MAP.put(str, str);
	    	}
    	}
    	logger.info("filenameExt 11111 : "+filenameExt);
    	filenameExt = filenameExt.replaceAll("\\.", "");
    	if((null == EXTENSION_MAP.get(filenameExt.toLowerCase())) && (null == EXTENSION_MAP.get(filenameExt.toUpperCase())) ){
    		;
    	}else{
    		rslt = true;
    	}
    	logger.info("EXTENSION_MAP : "+EXTENSION_MAP);
    	logger.info("filenameExt : "+filenameExt);
    	logger.info("rslt : "+rslt);
    	return rslt;
    }
	protected void setExtesion(String extension) {
		EXTENSION = extension;
	}
    
    
  //附件加密
	protected void encryptFile(String fileName, File oldFile){
        try {
        	if(isPicOrMedia(fileName)) return; //图片不加密
            File newFile = new File(fileName + "_encrypt");
            InputStream is = new FileInputStream(oldFile);
            OutputStream os = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024 * 4];//4 kb
            int n = 0;
            while ( -1 != (n = is.read(buffer))) {
                for (int i0 = 0; i0 < n; i0++) {
                    buffer[i0] = (byte)(buffer[i0] - SALT);
                }
                os.write(buffer, 0, n);
            }
            os.close();
            is.close();
            oldFile.delete();
            newFile.renameTo(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	//附件解密
	protected void decryptFile(String fileName, OutputStream os, boolean encrypt) {

        try {
            InputStream is = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            while ( -1 != (n = bis.read(buffer))) {
                if(encrypt){
                    for (int i0 = 0; i0 < n; i0++) {
                        buffer[i0] = (byte)(buffer[i0] + SALT);
                    }
                }
                os.write(buffer, 0, n);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 是否是图片或媒体文件
     * @param filename
     * @return
     */
	protected boolean isPicOrMedia(String filename) {
        return filename.toLowerCase().endsWith(".jpg")
                || filename.toLowerCase().endsWith(".jpeg") ||
                filename.toLowerCase().endsWith(".gif")
                || filename.toLowerCase().endsWith(".bmp") ||
                filename.toLowerCase().endsWith(".png")
                || filename.toLowerCase().endsWith(".avi") ||
                filename.toLowerCase().endsWith(".mpeg")
                || filename.toLowerCase().endsWith(".mpg") ||
                filename.toLowerCase().endsWith(".mp3")
                || filename.toLowerCase().endsWith(".wmv") ||
                filename.toLowerCase().endsWith(".mov")
                || filename.toLowerCase().endsWith(".asf") ||
                filename.toLowerCase().endsWith(".rm")
                || filename.toLowerCase().endsWith(".rmvb") ||
                filename.toLowerCase().endsWith(".mp3") ||
                filename.toLowerCase().endsWith(".swf")
                || filename.toLowerCase().endsWith(".ipx") ||
                filename.toLowerCase().endsWith(".dwf") || filename.toLowerCase().endsWith(".wav");
    }
}
