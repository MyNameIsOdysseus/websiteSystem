package net.minggao.cms.service;

import net.minggao.cms.model.Allattach;
import net.minggao.cms.model.Site;

public interface siteService {
    Site getSiteMessageSingle();
    int setSiteMessage(Site site);
    Allattach checkppt(String fileid);
}
