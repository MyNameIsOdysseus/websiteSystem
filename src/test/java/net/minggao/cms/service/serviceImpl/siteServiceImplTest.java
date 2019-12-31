package net.minggao.cms.service.serviceImpl;

import net.minggao.cms.dao.SiteMapper;
import net.minggao.cms.model.Site;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class siteServiceImplTest {
    @Resource
    SiteMapper siteMapper;

    @Test
    public void get() {
        Site s = siteMapper.selectByPrimaryKey(1l);
        System.out.println(s);
    }
}