package net.minggao.cms.config;

import net.minggao.core.common.util.DBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author meng gang
 * @date 2012-05-10
 * <p>上传文件的数据库操作</p>
 */
@Component
public class FileDBUtil {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	public  static FileDBUtil fileDBUtil;

	@PostConstruct
	public void init(){
		fileDBUtil=this;
		fileDBUtil.jdbcTemplate = this.jdbcTemplate;
	}

   /**
    * 保存上传文件的信息
    */


	public boolean saveFile(String fileName,String filerealName,Long fileSize,Short encrypt,String filepath){
		 String sql = "insert into TS_ALLATTACH(FILENAME,FILEREALNAME,FILESIZE,ENCRYPT,FIEL_PATH)values(?,?,?,?,?)";
     	int count = fileDBUtil.jdbcTemplate.update(sql, new Object[]{fileName,filerealName,fileSize,encrypt,filepath});
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
	    	int count = fileDBUtil.jdbcTemplate.update(sql, new Object[]{fileName,filerealName,fileSize,encrypt,filepath});
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
		 List list= fileDBUtil.jdbcTemplate.queryForList(sql,new Object[]{fileName});
		 return DBUtils.getInstance().queryForObjArrList(sql,new Object[]{fileName});
	}

	public List getFileList2(String sql){
		 return fileDBUtil.jdbcTemplate.queryForList(sql);
//		 return DBUtils.getInstance().queryForObjArrList(sql,new Object[]{fileName});
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
		     //list = DBUtils.getInstance().queryForObjArrList(sql);
			 list= fileDBUtil.jdbcTemplate.queryForList(sql);
		 }

		return list;
	 }


	/**
	 * 根据文件名称删除文件
	 * @param
	 */

   public boolean deleteFileByName(String fileName){
		  String sql = "delete from  TS_ALLATTACH where  FILENAME = ?";
		  String sql2 = "update T_CMS_ARTICLE set IMAGE=null where IMAGE = ?";
		  fileDBUtil.jdbcTemplate.update(sql2, new Object[]{fileName});
		  int count = fileDBUtil.jdbcTemplate.update(sql, new Object[]{fileName});
		 if(count>0){
				return true;
			}
		  return false;
     }

   /**
	 * 根据文件名称得到文件显示名称
	 * @param
	 */

   public String[] getFileRealByName(String fileName){
		   String[] msg = new String[2];
		   String sql = "select FILEREALNAME,FIEL_PATH from  TS_ALLATTACH  where  FILENAME = ?";
	       List list = fileDBUtil.jdbcTemplate.queryForList(sql,new Object[]{fileName});
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
       List list = fileDBUtil.jdbcTemplate.queryForList(sql,new Object[]{fileName});
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
       List list = fileDBUtil.jdbcTemplate.queryForList(sql,new Object[]{fileName});
       if(list.size()>0){
    	   Map mp =  (Map)list.get(0);
    	   result[0] = mp.get("FIEL_PATH") == null?"":mp.get("FIEL_PATH").toString();
    	   result[1] = mp.get("ENCRYPT") == null?"":mp.get("ENCRYPT").toString();
    	   result[2] = mp.get("FILEREALNAME") == null?"":mp.get("FILEREALNAME").toString();
       }
       return result;
   }

	public static void copyDir(String sourcePath, String newPath) throws IOException {
		//System.out.println("这里是sourcePath"+sourcePath);
   		File file = new File(sourcePath);
		//System.out.println("这里是file"+file);
		String[] filePath = file.list();
		//System.out.println("这里是filePath"+filePath);
		if (!(new File(newPath)).exists()) {
			//System.out.println("这里开始创建文件夹");
			(new File(newPath)).mkdir();
		}
		//System.out.println("这里的文件路径长度"+filePath.length);
		for (int i = 0; i < filePath.length; i++) {
			if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
				copyDir(sourcePath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
			}

			if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
				copyFile(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
			}

		}
	}

	public static void copyFile(String oldPath, String newPath) throws IOException {
		create(oldPath);
		create(newPath);
   		File oldFile = new File(oldPath);
		File file = new File(newPath);
		FileInputStream in = new FileInputStream(oldFile);
		FileOutputStream out = new FileOutputStream(file);;

		byte[] buffer=new byte[2097152];
		int readByte = 0;
		while((readByte = in.read(buffer)) != -1){
			out.write(buffer, 0, readByte);
		}
		in.close();
		out.close();
	}

	public static void create(String pathname){
		//在填写文件路径时，一定要写上具体的文件名称（xx.txt），否则会出现拒绝访问。
		File file = new File(pathname);
		if(!file.exists()){
			//先得到文件的上级目录，并创建上级目录，在创建文件
			file.getParentFile().mkdir();
			try {
				//创建文件
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

    }

}
