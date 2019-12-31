package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.config.CreatFile;
import net.minggao.cms.config.FileOperation;
import net.minggao.cms.config.ReflectUtil;
import net.minggao.cms.config.SimpleUtil;
import net.minggao.cms.dao.ChanMapper;
import net.minggao.cms.dao.TemplateMapper;
import net.minggao.cms.model.Template;
import net.minggao.cms.service.TemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

import static net.minggao.cms.config.SimpleUtil.getPageSize;

@Service("TemplateService")
public class TemplateServiceImpl implements TemplateService {
	
	public static int maxnum;
	/*
	 * @Value("#{siteIdBean.siteId}") private String siteId;
	 */
	private String template_path = CreatFile.getFileName();
	
	@Resource
	private TemplateMapper templateMapper;

	@Resource
	ChanMapper chanMapper;
	
	@Override
    public List getPageList(int currentpage, int pagesize,Template template) {
        List list = templateMapper.getTemplateList(template);
        maxnum=list.size();
        return getPageSize(currentpage,pagesize,list);
    }

	@Override
	public int getMaxNum() {
		// TODO Auto-generated method stub
		return maxnum;
	}

	@Override
	public String getStop(Long updateid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStart(Long updateid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String del(Long updateid) {
		Template template = templateMapper.selectByPrimaryKey(updateid);
		List list = chanMapper.selectBytemp(updateid);
		List list1 = chanMapper.selectBytemp2(updateid);
		if(list.size()>0 || list1.size()>0){
			return "删除失败，该模板已被栏目使用";
		}
		FileOperation.deleteFile(template_path+template.getTemplateCode()+".ftl");
		int x= templateMapper.deleteByPrimaryKey(updateid);
        if(x>0){
            return "删除成功";
        }else{
            return "删除失败";
        }
	}

	@Override
	public int saveTemplate(Template template) {
		if(templateMapper.selectByname(template.getTemplateName())!=null && !templateMapper.selectByname(template.getTemplateName()).getTemplateId().equals(template.getTemplateId())){
			return 3;
		}
		if(templateMapper.selectByPrimaryKey(template.getTemplateId())!=null){
			templateMapper.updateByPrimaryKeySelective(template);
			try {
				FileOperation.deleteFile(template_path+template.getTemplateCode()+".ftl");
				FileOperation.creatTxtFile(template.getTemplateCode());
				FileOperation.writeTxtFile(template.getTemplateContent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return 1;
        }
        template.setTemplateId(SimpleUtil.getuuid());
		templateMapper.insert(template);
		try {
			FileOperation.creatTxtFile(template.getTemplateCode());
			FileOperation.writeTxtFile(template.getTemplateContent());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
    public Template lookMessage(Long updateid) {
		Template shan= templateMapper.selectByPrimaryKey(updateid);
		shan=(Template) ReflectUtil.reflect(shan);
        return shan;
    }
	
	@Override
	public String ajaxRepeat(String templateCode) {
		return templateMapper.ajaxRepeat(templateCode);
	}

	@Override
	public List getList() {
		return templateMapper.selectAll();
	}
}
