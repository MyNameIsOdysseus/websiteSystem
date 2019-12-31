package net.minggao.cms.config;


import net.minggao.core.common.util.Random;
import net.minggao.core.framework.ApplicationConstant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * 处理附件上传，同时处理是否加密和ftp方式的上传等
 * 
 * pluUploader上传组件
 * 参照：http://www.th7.cn/Program/java/201608/941575.shtml
 * http://www.cnblogs.com/2050/p/3913184.html
 * 
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-1-18 下午4:43:53
 */
@WebServlet(name = "PluUploadServlet", urlPatterns = "/pluUploader")
public class PluUploadServlet extends HttpServlet {
	private Log logger = LogFactory.getLog(PluUploadServlet.class);
	private static final long serialVersionUID = 1L;
	String repositoryPath;
	String uploadPath;
	private AbstractUpload aupload = new AbstractUpload();

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			  throws ServletException, IOException {
		this.doPost(request, response);
	}
	/**
	 * 上传处理
	 * @方法名:doPost
	 * @参数:@param request
	 * @参数:@param response
	 * @参数:@throws ServletException
	 * @参数:@throws IOException
	 * @see HttpServlet#doPost(HttpServletRequest, HttpServletResponse)
	 */
	 @SuppressWarnings("unchecked")
	 public void doPost(HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
	  response.setCharacterEncoding("UTF-8");
	  Integer schunk = null;//分割块数
	  Integer schunks = null;//总分割数
	  String name = null;//文件名
	  BufferedOutputStream outputStream=null;
	  String domainId = request.getParameter("domainId") == null?"1":request.getParameter("domainId"); //
	  
	  //附件是否加密
	  boolean isEncrypt = false;
	  FileDBUtil dbUtil = new FileDBUtil();
		 
	  if (ServletFileUpload.isMultipartContent(request)) {
	      try {
	         DiskFileItemFactory factory = new DiskFileItemFactory();
	         String tmpfile = request.getRealPath("upload")+"/tmp";
			 repositoryPath = tmpfile;
			 File tf = new File(tmpfile);
			 if(!tf.exists()) tf.mkdirs();
			 factory.setRepository(tf); //设置临时目录,如果没有设置,就是默认的System.getProperty("user.dir")目录为临时目录
			 factory.setSizeThreshold(1024*8); //8k的缓冲区,文件大于8K则保存到临时目录
				
	         ServletFileUpload upload = new ServletFileUpload(factory);
	         upload.setHeaderEncoding("UTF-8");
	         upload.setSizeMax(1024 * 1024 * 1024);//设置文件最大值
	         List<FileItem> items = upload.parseRequest(request);
	         
	         String realPath = request.getRealPath("upload");
			 String rootPath = request.getContextPath();

			 String folder = request.getParameter("folder") == null?"":request.getParameter("folder").trim();
			 String path= request.getParameter("path") == null?"":request.getParameter("path").trim();
			 
			 if(folder.startsWith(rootPath)){
				 folder = folder.substring(folder.indexOf(rootPath) + rootPath.length(), folder.length());
			 }
			
			String _separator ="/";
                               //folder路径是否带/或\\
			if((folder.startsWith("/") || folder.startsWith(File.separator))){
				_separator = "";
			}
           if(realPath.endsWith("/") || realPath.endsWith(File.separator)){
             _separator = "";
           }

			//创建文件夹
			String realPathFolder = realPath + _separator +  folder+"/"+path;
                               if(!realPathFolder.endsWith("/")){
                                 realPathFolder += "/";
                               }
 
			File file = new File(realPathFolder);  
			if (!file.exists()) { 
	             file.mkdirs();
		    }
			
			logger.info("PluUploadServlet---realPathFolder = "+realPathFolder);
			
	                //生成新文件名
	                String newFileName = null;
	                for (FileItem item : items) {
	                	String extName=""; //扩展名
	                	String fileName=""; //文件名
	                	String preffix_date="";
	                	String pre_folder = "";
	                	String localFile = "";
	                	long size = 0; //附件大小，单位字节
	                	
	                    if (!item.isFormField()) {// 如果是文件类型
	                        name = item.getName();// 获得文件名
	                        size = item.getSize();//获取附件大小
	                        //newFileName = UUID.randomUUID().toString().replace("-","").concat(".").concat(FilenameUtils.getExtension(name));
	                        
	                        if (name == null || "".equals(name.trim())) {
								continue;
							}
							if (name.lastIndexOf(".") >= 0) {
								extName = name.substring(name.lastIndexOf("."));
								if(!aupload.isAllowedUploadFile(extName)){
									continue; //add by liumc上述类型的文件不运行上传
								}
							}
	                        fileName = new Random().getRandom() + extName;
							preffix_date = fileName.substring(0,6);
							//文件夹按月份分类存
							pre_folder = preffix_date + "/";
							file = new File(realPathFolder + pre_folder);
							if (!file.exists()) {
									file.mkdirs();
                            }
							// 要保存的文件路径
							localFile = realPathFolder + pre_folder + fileName;
							this.logger.info("name 111 : "+name);
							//处理显示本地全路径的问题(C:\Users\wangj\Desktop\工作文件夹\office文件\新建文件夹\17-26-17-33-1.jpg)
							if(name.indexOf("\\")>0) name = name.replace('\\', '/');
							if(name.indexOf("/")>0) {
								name = name.substring(name.lastIndexOf("/")+1);
							}
							this.logger.info("name 222 : "+name);
							if(name!=null){
								File saveFile = new File(localFile);
								newFileName = fileName;
								item.write(saveFile);    //需要在这里处理数据库记录保存，另外还要处理是否附件加密的操作
								
								logger.info("localFile="+localFile);
								//附件加密
								if(isEncrypt){
									aupload.encryptFile(localFile, new File(localFile));
								}
								
								logger.info("fileDbutil saveFile start....");
								dbUtil.saveFile(fileName, name, new Long(size),Short.valueOf((isEncrypt?"1":"0")) , folder + ((folder.endsWith("/") || folder.endsWith(File.separator))?"":"/")+path+"/" + pre_folder);
								logger.info("fileDbutil saveFile ended....");
								
							}
	                    } 
	                }
	                String json = "{\"status\":true,\"newName\":\""+newFileName+"\",\"oldName\":\""+name+"\"}";
	                logger.info("json : "+json);
	                response.getWriter().write(json);
	            } catch (FileUploadException e) {
	                e.printStackTrace();
	                response.getWriter().write("{\"status\":false}");
	            } catch (Exception e) {
	                e.printStackTrace();
	                response.getWriter().write("{\"status\":false}");
	            }finally{ 
	                try { 
	                    if(outputStream!=null)
	                        outputStream.close(); 
	                } catch (IOException e) { 
	                    e.printStackTrace(); 
	                } 
	            }  
	        }
	    }
	
	    public void init(ServletConfig config) throws ServletException {
	    	String EXTENSION = ApplicationConstant.UPLOAD_ALLOW_EXTENSION;
	    	logger.info("EXTENSION : "+EXTENSION);
	    	aupload.setExtesion(EXTENSION);
	    }
	    
	    
}