package net.minggao.core.framework;

/**
 * 测试apiDoc的功能
 * @api {get} /user/:id Request User information
 * @apiName GetUser
 * @apiGroup User
 *
 * @apiParam {Number} id Users unique ID.
 *
 * @apiSuccess {String} firstname Firstname of the User.
 * @apiSuccess {String} lastname  Lastname of the User.
 *
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "firstname": "John",
 *       "lastname": "Doe"
 *     }
 *
 * @apiError UserNotFound The id of the User was not found.
 *
 * @apiErrorExample Error-Response:
 *     HTTP/1.1 404 Not Found
 *     {
 *       "error": "UserNotFound"
 *     }
 */

/*
 * @api 
 * @apiDefine  Develop Platform application Constant
 * @apiVersion 6.1.0
 */

/*
 * 
 * 本类中存放的是本程序中全局变量
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-3-22 下午2:32:49
 */
public class ApplicationConstant {
	//存放config.xml的本地文件绝对路径
	//public static  String configFilePath = null;
	
	public static final String PAGE_ORDER_FIELDNAME = "PAGE_ORDER_FIELD"; //分页排序字段名参数
	public static final String PAGE_ORDER_BY        = "PAGE_ORDER_BY";    //分页排序方式参数
	
	
	public final static String ADD_SUCCESS       = "新增成功!";
	public final static String ADD_FAIL          = "新增失败!";
	public final static String MODI_SUCCESS      = "修改成功!";
	public final static String MODI_FAIL         = "修改失败!";  
	public final static String DEL_SUCCESS       = "删除成功!";
	public final static String DEL_FAIL          = "删除失败!";
	public final static String DEL_BATCH_SUCCESS = "批量删除成功!";
	public final static String DEL_BATCH_FAIL    = "批量删除失败!";
	public final static String IMP_XLS_SUCESS    = "xls导入成功!";
	public final static String IMP_XLS_FAIL      = "xls导入失败!";
	
	
	public final static String  STRUTS_MVC_SESSIONOUT      = "_minggao_sessiontimeout";
	public final static String  STRUTS_MVC_NORIGHT         = "_minggao_hasnoright_";
	public final static String  STRUTS_MVC_PAGEFORWORD     = "page";
	public final static String  STRUTS_MVC_JSONFORWORD     = "json";
	public final static String  SYS_STRING_TRUE            = "true";
	public final static String  SYS_STRING_FALSE           = "false";
	public final static String  SYS__NORIGHT               = "hasnoright";  //没有权限
	public final static String 	SYS_SESSIONOUT             =  "session-timeout"; //超时
	
	
	public final static String MENU_PRE       = "mg_"; //菜单id的前缀
	public final static int MENUS_DEFAULTCODE = 500000000; //菜单默认的编码
	public final static int MENUS_DEFAULTSTEP = 5000;     //菜单默认的级别增加码
	
	//add 20170425
	public final static String BLCY_EDIT = "blcyedit"; //办理查阅下的保存退出（修改表单数据，与流程没有任何关系 执行的是cmdSaveExit方法）
	
	public final static String DBTYPE_ORACLE       = "oracle";
	public final static String DBTYPE_SQLSERVER    = "sqlserver";
	public final static String DBTYPE_MYSQL        = "mysql";
	public final static String DBTYPE_POSTGRESQL   = "postgresql";
	public final static String DBTYPE_DB2          = "db2";
	
	public final static String ORACLE_ID_SEQ = "hibernate_sequence.nextval";
	//通过GET方式提交的参数中有中文的转换为base64提交，自动处理成汉字的（目前 “测试”字样，不正常）
	public final static String HTTP_PARAM_64_PRE = ":*MGDES*:";
	//通过Get方式提交的参数中有中文的转换为16进制提交，提交后自动处理解密
	public final static String HTTP_PARAM_16_PRE = ":MG16N:";
	
	/**---------session中存放的元素名称---------START-------------------*/
	public final static String SESSION_USERID                 = "uid";
	public final static String SESSION_USERNAME               = "uName";
	public final static String SESSION_USERENGLISHNAME        = "uEngName";
	public final static String SESSION_USERACCOUNT            = "uAccount";
	public final static String SESSION_USERCODE               = "uCode";
	public final static String SESSION_USER_BUSINESSPHONE     = "uBusPhone";
	public final static String SESSION_USER_POSITION          = "uPosition";
	public final static String SESSION_DOMAINID               = "did";
	public final static String SESSION_DUTY_LEVEL             = "dutyLevel";
	public final static String SESSION_ORGID                  = "orgId";
	public final static String SESSION_ORGFULLID              = "orgFullId";
	public final static String SESSION_ORGNAME                = "orgName";
	public final static String SESSION_ORGCODE                = "orgCode";
	public final static String SESSION_ORG_SIMPLENAME         = "orgSimName";
	public final static String SESSION_ORG_ENGLISHNAME        = "orgEngName";
	public final static String SESSION_ORGFULLNAME            = "orgFullName";
	public final static String SESSION_GROUP_ID               = "gid";
	public final static String SESSION_IDCARD                 = "idcard";
	public final static String SESSION_DUTYNAME               = "dutyName";
	public final static String SESSION_SIGN_PHOTO             = "signPhoto";
	public final static String SESSION_FORM_TOKEN             = "_formToken";  //防止重复提交
	
	/**---------session中存放的元素名称---------END-------------------*/
	//允许上传的文件的扩展名串
	public final static String UPLOAD_ALLOW_EXTENSION         = "rar,zip,7z,txt,pdf,xml,dwg,vsd,eml,msg,ceb,tif,tiff,key,iso,ofd,doc,docx,xls,xlsx,xlsm,ppt,pptx,pps,wps,mpp,odt,ods,odp,jpg,jpeg,gif,png,bmp,asf,wmv,wav,swf,flv,mp3,mp4,rm,rmvb,avi";  
	
	public final static long   UPLOAD_MAX_SIZE                = 1024*1024*1024;//最大1G上传
}
