package net.minggao.core.common.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import net.minggao.core.framework.ApplicationHelper;
/**
 * 
 * @author meng gang
 * @date 2012-05-10
 * <p>上传文件的数据库操作</p>
 */
public class FileDBUtil {
	private JdbcTemplate  jdbcDAO = (JdbcTemplate)ApplicationHelper.getBean("JdbcTemplate");;
   /**
    * 保存上传文件的信息
    */

	public boolean saveFile(String fileName,String filerealName,Long fileSize,Short encrypt,String filepath){
		 String sql = "insert into TS_ALLATTACH(FILENAME,FILEREALNAME,FILESIZE,ENCRYPT,FIEL_PATH)values(?,?,?,?,?)";
     	int count = jdbcDAO.update(sql, new Object[]{fileName,filerealName,fileSize,encrypt,filepath});
	    if(count>0){
	    	return true;
	    }
     return false;
	}
	/**
	 * 保存文件时检查是否重复保存
	 * @param fileName
	 * @param filerealName
	 * @param fileSize
	 * @param encrypt
	 * @param filepath
	 * @return
	 */
	public boolean saveOrUpdateFile(String fileName,String filerealName,Long fileSize,Short encrypt,String filepath){
		 String sql = "insert into TS_ALLATTACH(FILENAME,FILEREALNAME,FILESIZE,ENCRYPT,FIEL_PATH)values(?,?,?,?,?)";
	    if(this.getFileList(fileName).size() == 0){
	    	int count = jdbcDAO.update(sql, new Object[]{fileName,filerealName,fileSize,encrypt,filepath});
		    if(count>0){
		    	return true;
		    }
	    }
    	
    return false;
	}
	
	/**
	 *  
	 * @return
	 */
	
	public List getFileList(String fileName){
		 String sql = "select FILENAME,FILEREALNAME,FILESIZE,ENCRYPT,FIEL_PATH  from TS_ALLATTACH  where FILENAME = ?";		    
		 return DBUtils.getInstance().queryForObjArrList(sql,new Object[]{fileName});
	}
	
	
	
	
	/**
	 * 
	 * @param fileNames 
	 * @return
	 */
	 public List getFileList(String[] fileNames){
		 List list = new ArrayList();
		 String whereSQL = "";
		 if(null !=fileNames && fileNames.length>0){
			 for(int k=0;k<fileNames.length;k++){
				 whereSQL += " FILENAME='" + fileNames[k] + "'";
				 if(k !=fileNames.length-1){
					 whereSQL += " or ";
				 }
			 }
		 }
		 
		 if(!"".equals(whereSQL)){			 
			 String sql = "select FILENAME,FILEREALNAME,FILESIZE,ENCRYPT,FIEL_PATH  from TS_ALLATTACH where" + whereSQL;
		     list = DBUtils.getInstance().queryForObjArrList(sql);
		 }
		
		return list;
	 }
	
	
	/**
	 * 根据文件名称删除文件
	 * @param name
	 */

   public boolean deleteFileByName(String fileName){
		  String sql = "delete from  TS_ALLATTACH where  FILENAME = ?";
		  int count = jdbcDAO.update(sql, new Object[]{fileName});
		 if(count>0){
				return true;
			}
		  return false;
     }

   /**
	 * 根据文件名称得到文件显示名称
	 * @param name
	 */

   public String[] getFileRealByName(String fileName){
		   String[] msg = new String[2];
		   String sql = "select FILEREALNAME,FIEL_PATH from  TS_ALLATTACH  where  FILENAME = ?";
	       List list = jdbcDAO.queryForList(sql,new Object[]{fileName});
	       if(null!=list&&!list.isEmpty()){
	    	   Map mp =  (Map)list.get(0);
	    	   msg[0]=(String)mp.get("FILEREALNAME");
	    	   msg[1]=(String)mp.get("FIEL_PATH");
	       }
		  return msg;	      
    }
   /**
    * 文件是否加密
    * @param fileName
    * @return
    */
   public boolean getFileEncrypt(String fileName) {
	   boolean result = false;
	   String sql = "select ENCRYPT from  TS_ALLATTACH  where  FILENAME = ?";
       List list = jdbcDAO.queryForList(sql,new Object[]{fileName});
       if(list.size()>0){
    	   Map mp =  (Map)list.get(0);
    	   if(null !=mp.get("ENCRYPT") && "1".equals(mp.get("ENCRYPT").toString())){
    		   result = true;
    	   }
       }
       return true;
   }
   
   /**
    * 获取文件路径
    * @param fileName
    * @return
    */
   public String[] getFileInfo(String fileName) {
	   String[] result = new String[3];
	   String sql = "select FIEL_PATH,ENCRYPT,FILEREALNAME from  TS_ALLATTACH  where  FILENAME = ?";
       List list = jdbcDAO.queryForList(sql,new Object[]{fileName});
       if(list.size()>0){
    	   Map mp =  (Map)list.get(0);
    	   result[0] = mp.get("FIEL_PATH") == null?"":mp.get("FIEL_PATH").toString();
    	   result[1] = mp.get("ENCRYPT") == null?"":mp.get("ENCRYPT").toString();
    	   result[2] = mp.get("FILEREALNAME") == null?"":mp.get("FILEREALNAME").toString();
       }
       return result;
   }
}
