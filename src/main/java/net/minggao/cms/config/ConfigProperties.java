package net.minggao.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author robin
 * @Description: TODO
 * @Param:
 * @Return:
 * @Create: 2018/6/4 22:18
 */

@Primary
@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
public class ConfigProperties {
    @Value("${ESServices}")
    public String ESServices;

    public String getESServices() {
        return ESServices;
    }

    public void setESServices(String ESServices) {
        this.ESServices = ESServices;
    }
}
