package net.minggao.core.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

/**
 * 记录附件大小处理类
 * 
 */
public class UploadFile {

    private String separator = System.getProperty("file.separator");

    public static final int SALT = 1;

    private long docSize;

    public UploadFile() {
        this.docSize = 0;
    }

/**
    public boolean setFileSize(String fileName, int fileSize) {
        return setFileSize(fileName, fileSize, null, null, false);
    }
*/
    
    /**
     * 存储附件大小到数据库附件表
     * 
     * @param fileName
     * @param fileSize
     * @param path
     * @param request
     * @param encrypt
     * @return
     */
    public boolean setFileSize(String fileName, int fileSize, String path,
            HttpServletRequest request, boolean encrypt,String fileRealName) {
        boolean res = false;

        boolean isPicOrMedia = isPicOrMedia(fileName);
        String ymFolder = "";
        if (fileName.length() > 20) {
            ymFolder = fileName.substring(0, 6);
        }
        try {
        	String existSQL = "select count(1) from TS_ALLATTACH where filename = :filename";
        	long existCount = DBUtils.getInstance().queryForLong(existSQL, new String[]{"filename"}, new Object[]{fileName});
        	String sql = "";
        	String[] paramsNamed = null;
        	Object[] paramsValue = null;
        	if(existCount<1){//不存在
        		sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'0',:fileRealName,:filePath)";
	            if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'1',:fileRealName,:filePath)";
	            }
	            
	            paramsNamed = new String[]{"filename","filesize","fileRealName","filePath"};
	            paramsValue = new Object[]{fileName,fileSize,fileRealName,"upload/"+path+"/"+ymFolder};
        	}else{//存在
        		sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='0' where filename=:filename";
        		if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='1' where filename=:filename";
	            }
        		paramsNamed = new String[]{"filesize","fileRealName","filePath","filename"};
	            paramsValue = new Object[]{fileSize,fileRealName,"upload/"+path+"/"+ymFolder,fileName};
        	}
            int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
            if(cnt>0) res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        if (isPicOrMedia)
            return res;

        // encrypt file...
        if (encrypt) {
            String uploadFolder = request.getRealPath(separator + "upload"+ separator + path + separator);
            String preffix_date = fileName.substring(0, 6);
            String filePath = uploadFolder + separator + preffix_date+ separator + fileName;
            File oldFile = new File(filePath);
            if (oldFile.exists()) {
                encryptFile(filePath, oldFile);
            } else {
                filePath = uploadFolder + separator + fileName;
                oldFile = new File(filePath);
                if (oldFile.exists()) {
                    encryptFile(filePath, oldFile);
                }
            }
        }
        
        return res;
    }

    public boolean setFileSize(String fileName, int fileSize, String path, String absolutePath, boolean encrypt,String fileRealName) {
        boolean res = false;
        boolean isPicOrMedia = isPicOrMedia(fileName);
        String ymFolder = "";
        if (fileName.length() > 20) {
            ymFolder = fileName.substring(0, 6);
        }
        try {
        	String existSQL = "select count(1) from TS_ALLATTACH where filename = :filename";
        	long existCount = DBUtils.getInstance().executeSQL(existSQL, new String[]{"filename"}, new Object[]{fileName});
        	String sql = "";
        	String[] paramsNamed = null;
        	Object[] paramsValue = null;
        	if(existCount<1){//不存在
	        	sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'0',:fileRealName,:filePath)";
	            if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'1',:fileRealName,:filePath)";
	            }
	            
	             paramsNamed = new String[]{"filename","filesize","fileRealName","filePath"};
	             paramsValue = new Object[]{fileName,fileSize,fileRealName,"upload/"+path+"/"+ymFolder};
        	}else{//存在
        		sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='0' where filename=:filename";
        		if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='1' where filename=:filename";
	            }
        		paramsNamed = new String[]{"filesize","fileRealName","filePath","filename"};
	            paramsValue = new Object[]{fileSize,fileRealName,"upload/"+path+"/"+ymFolder,fileName};
        	}
            int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
            if(cnt>0) res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        if (isPicOrMedia)
            return res;

        // encrypt file...
        if (encrypt) {
            String uploadFolder = absolutePath + separator + "upload" + separator + path;
            String preffix_date = fileName.substring(0, 6);
            String filePath = uploadFolder + separator + preffix_date
                    + separator + fileName;
            File oldFile = new File(filePath);
            if (oldFile.exists()) {
                encryptFile(filePath, oldFile);
            } else {
                filePath = uploadFolder + separator + fileName;
                oldFile = new File(filePath);
                if (oldFile.exists()) {
                    encryptFile(filePath, oldFile);
                }
            }
        }
        
        return res;
    }
    
    /**
     * 同上个方法，对于已经加密过的附件不再进行加密处理
     * 
     * @param fileName
     * @param fileSize
     * @param path
     * @param request
     * @param encrypt
     * @return
     */
    public boolean setFileSizeNew(String fileName, int fileSize, String path,
            HttpServletRequest request, boolean encrypt,String fileRealName) {
        boolean res = false;
        boolean isPicOrMedia = isPicOrMedia(fileName);
        
        String ymFolder = "";
        if (fileName.length() > 20) {
            ymFolder = fileName.substring(0, 6);
        }
        
        try {
        	String existSQL = "select count(1) from TS_ALLATTACH where filename = :filename";
        	long existCount = DBUtils.getInstance().executeSQL(existSQL, new String[]{"filename"}, new Object[]{fileName});
        	String sql = "";
        	String[] paramsNamed = null;
        	Object[] paramsValue = null;
        	if(existCount<1){//不存在
	        	sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'0',:fileRealName,:filePath)";
	            if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'1',:fileRealName,:filePath)";
	            }
	            
	            paramsNamed = new String[]{"filename","filesize","fileRealName","filePath"};
	            paramsValue = new Object[]{fileName,fileSize,fileRealName,"upload/"+path+"/"+ymFolder};
        	}else{//存在
        		sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='0' where filename=:filename";
        		if (isPicOrMedia == false && encrypt) {// encrypt...
	                sql = "update TS_ALLATTACH set filesize=:filesize,filerealname=:fileRealName,fiel_path=:filePath,encrypt='1' where filename=:filename";
	            }
        		paramsNamed = new String[]{"filesize","fileRealName","filePath","filename"};
	            paramsValue = new Object[]{fileSize,fileRealName,"upload/"+path+"/"+ymFolder,fileName};
        	}
            int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
            if(cnt>0) res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            
        }

        if (isPicOrMedia)
            return res;

        return res;
    }

    private void encryptFile(String fileName, File oldFile) {
        try {
            File newFile = new File(fileName + "_encrypt");
            InputStream is = new FileInputStream(oldFile);
            OutputStream os = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024 * 4];// 4 kb
            int n = 0;
            while (-1 != (n = is.read(buffer))) {
                for (int i0 = 0; i0 < n; i0++) {
                    buffer[i0] = (byte) (buffer[i0] - SALT);
                }
                os.write(buffer, 0, n);
            }
            os.flush();
            os.close();
            is.close();
            oldFile.delete();
            newFile.renameTo(new File(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptFile(String fileName, OutputStream os, boolean encrypt) {
        boolean isPicOrMedia = isPicOrMedia(fileName);
        try {
            InputStream is = new FileInputStream(fileName);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024 * 4];
            int n = 0;
            if (isPicOrMedia == false && encrypt) {// 解密
                while (-1 != (n = bis.read(buffer))) {
                    for (int i0 = 0; i0 < n; i0++) {
                        buffer[i0] = (byte) (buffer[i0] + SALT);
                    }
                    os.write(buffer, 0, n);
                }
            } else {
                while (-1 != (n = bis.read(buffer))) {
                    os.write(buffer, 0, n);
                }
            }
            os.flush();
            os.close();
            is.close();
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    public String getFileEncrypt(String fileName) {
        String encrypt = "";
        String sql = "select encrypt from TS_ALLATTACH where filename = :filename";
        String[] paramsNamed = {"filename"};
        Object[] paramsValue = {fileName};
        
        encrypt = DBUtils.getInstance().queryForString(sql, paramsNamed, paramsValue);
        return encrypt;
    }

    /**
     * 取得年月份文件夹
     * 
     * @param fileName
     *            String
     * @return String
     */
    public String getSubFolder(String fileName) {
        String sub_folder = "";
        if (fileName.length() > 20) {
        	sub_folder = fileName.substring(0, 6);
        }
        return sub_folder;
    }

    /**
     * 
     * 获取文件的大小 
     * @param fileName
     * @return      
     * @author        liumc
     * @Date          2016-7-26 上午10:40:54
     */
    public String getFileSize(String fileName) {
        String fileSize = "OK";
        long accessorySize = 0;
        String sql = "select filesize from ts_allattach where filename= :filename";
        String[] paramsNamed = {"filename"};
        Object[] paramsValue = {fileName};
        
        accessorySize = DBUtils.getInstance().queryForLong(sql, paramsNamed, paramsValue);

        this.docSize = accessorySize;

        if (accessorySize < (1024 * 1024)) {
            fileSize = String.valueOf(accessorySize / 1024.0);
            if (fileSize.length() > fileSize.indexOf(".") + 3) {
                fileSize = fileSize.substring(0, fileSize.indexOf(".") + 3);
            }
            fileSize += "K";
        } else {
            fileSize = String.valueOf(accessorySize / (1024 * 1024.0));
            if (fileSize.length() > fileSize.indexOf(".") + 3) {
                fileSize = fileSize.substring(0, fileSize.indexOf(".") + 3);
            }
            fileSize += "M";
        }

        return fileSize;
    }

    public long getFileLongSize() {
        return this.docSize;
    }

    public boolean setFileSizeArray(String fileName, String fileSize,String fileRealName) {
        return setFileSizeArray(fileName, fileSize, null, null, false,fileRealName);
    }

    public boolean setFileSizeArray(String fileName, String fileSize,
            String path, HttpServletRequest request, boolean encrypt,String fileRealName) {
        boolean res = false;
        try {
            if (fileName.indexOf("|") >= 0) {
                String[] fileNameArr = fileName.split("\\|");
                String[] fileSizeArr = fileSize.split("\\|");
                String[] fileRealNameArr = fileRealName.split("\\|");
                for (int i = 0; i < fileNameArr.length; i++) {
                    if (fileNameArr[i] != null && !"".equals(fileNameArr[i])) {
                        boolean isPicOrMedia = isPicOrMedia(fileNameArr[i]);
                        
//                        String ymFolder = "";
//                        if(fileNameArr[i].length()>20) ymFolder = fileNameArr[i].substring(0,6);
                        
                        String sql = "insert into TS_ALLATTACH(filename,filesize,encrypt) values(:filename,:filesize,'0')";
                        if (isPicOrMedia == false && encrypt) {// encrypt...
                            sql = "insert into TS_ALLATTACH(filename,filesize,encrypt) values(:filename,:filesize,'1')";
                        }
                        
                        String[] paramsNamed = {"filename","filesize"};
                        Object[] paramsValue = {fileName,fileSize};
                        int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
                        
                    }
                }
            } else {
                boolean isPicOrMedia = isPicOrMedia(fileName);
                
                String sql = "insert into TS_ALLATTACH(filename,filesize,encrypt) values(:filename,:filesize,'0')";
                if (isPicOrMedia == false && encrypt) {// encrypt...
                    sql = "insert into TS_ALLATTACH(filename,filesize,encrypt) values(:filename,:filesize,'1')";
                }
                
                String[] paramsNamed = {"filename","filesize"};
                Object[] paramsValue = {fileName,fileSize};
                int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
            }
            res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

        return res;
    }

    /**
     * 记录文件大小并增加年月字段值
     * 
     * @param fileName
     *            String
     * @param fileSize
     *            String
     * @param subFolder
     *            String
     * @return boolean
     *//*
    public boolean setFileSizeArrayWithSubFolder(String fileName,
            String fileSize, String subFolder,String fileRealName) {
        boolean res = false;
        try {
            if (fileName.indexOf("|") >= 0) {
                String[] fileNameArr = fileName.split("\\|");
                String[] fileSizeArr = fileSize.split("\\|");
                String[] fileRealNameArr = fileRealName.split("\\|");
                for (int i = 0; i < fileNameArr.length; i++) {
                    if (fileNameArr[i] != null && !"".equals(fileNameArr[i])) {
                    	
                    	String sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'0',:fileRealName,:filePath)";
                        if (isPicOrMedia == false && encrypt) {// encrypt...
                            sql = "insert into TS_ALLATTACH(filename,filesize,encrypt,filerealname,fiel_path) values(:filename,:filesize,'1',:fileRealName,:filePath)";
                        }
                        
                        String[] paramsNamed = {"filename","filesize","fileRealName","filePath"};
                        Object[] paramsValue = {fileName,fileSize,fileRealName,"upload/"+path+"/"+ymFolder};
                        int cnt = DBUtils.getInstance().executeSQL(sql, paramsNamed, paramsValue);
                        
                        String sql = "insert into oa_allattach(filename,filesize,sub_folder) values('"
                                + fileNameArr[i]
                                + "',"
                                + fileSizeArr[i]
                                + ",'"
                                + subFolder + "')";
                        stmt.executeUpdate(sql);
                    }
                }
            } else {
                // boolean isPicOrMedia = isPicOrMedia(fileName);
                String sql = "insert into oa_allattach(filename,filesize,sub_folder) values('"
                        + fileName + "'," + fileSize + ",'" + subFolder + "')";
                stmt.executeUpdate(sql);
            }
            stmt.close();
            res = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        }

        return res;
    }*/

    private boolean isPicOrMedia(String filename) {
        String ret = filename.toLowerCase();
        return ret.endsWith(".jpg") || ret.endsWith(".jpeg")
                || ret.endsWith(".gif") || ret.endsWith(".bmp")
                || ret.endsWith(".png") || ret.endsWith(".avi")
                || ret.endsWith(".mpeg") || ret.endsWith(".mpg")
                || ret.endsWith(".mp3") || ret.endsWith(".wmv")
                || ret.endsWith(".mov") || ret.endsWith(".asf")
                || ret.endsWith(".rm") || ret.endsWith(".rmvb")
                || ret.endsWith(".mp3") || ret.endsWith(".swf")
                || ret.endsWith(".ipx") || ret.endsWith(".dwf")
                || ret.endsWith(".wav");
    }

    /**
     * 判断文件在DB中是否存在
     * 
     * @param fileName
     *            String
     * @return boolean true-存在 false-不存在
     */
    public boolean isExistFileFromDB(String fileName) {
        boolean ret = false;
        String sql = "select count(1) from TS_ALLATTACH where filename = :filename";
        String[] paramsNamed = {"filename"};
        Object[] paramsValue = {fileName};
        long cnt = DBUtils.getInstance().queryForLong(sql, paramsNamed, paramsValue);
        if(cnt>0) ret = true;
        return ret;
    }

    /**
     * 生成附件下载链接地址
     * 
     * @param fileSaveName
     *            String 文件保存名 eg：2012022017300100018852988.txt
     * @param fileRealName
     *            String 文件实际显示名 eg：测试.txt
     * @param path
     *            String 文件目录
     * @param fileServer
     *            String 文件服务
     * @return String
     */
    public String getDownloadFileLink(String fileSaveName, String fileRealName,
            String path, String fileServer) {
        String result = "";
        String encryptParam = "";
        String encrypt = getFileEncrypt(fileSaveName);
        if ("1".equals(encrypt)) {
            encryptParam = "&encrypt=1";
        }

        try {
            result = fileServer + "/DownloadServlet?FileName=" + fileSaveName
                    + "&name="
                    + java.net.URLEncoder.encode(fileRealName, "UTF-8")
                    + "&path=" + path + "&cd=inline";
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return (result + encryptParam);
    }
}
