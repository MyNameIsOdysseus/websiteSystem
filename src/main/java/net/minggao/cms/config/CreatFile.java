package net.minggao.cms.config;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CreatFile {
	
	private static String fileName;
	
	private static void getSite(){
		if (fileName==null) {
//			List<Map> maplist = DBUtils.getInstance().queryForMapList("select SITE_CATALOG from T_CMS_SITE", null, null);
			String sql = "select SITE_CATALOG from T_CMS_SITE";
			List<Map> maplist= FileDBUtil.fileDBUtil.getFileList2(sql);
			if(maplist.size()>0){
				fileName = maplist.get(0).get("SITE_CATALOG").toString()+"/WEB-INF/templates/";
				File file = new File(fileName);
//			File fileParent = file.getParentFile();
				if (!file.exists()) {
					file.mkdirs();
				}
			}else{
				return;
			}

		}
	}
	
	public static String getFileName(){
		if (fileName==null) {
			getSite();
		}
		return fileName;
	}

}
