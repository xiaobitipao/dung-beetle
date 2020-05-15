package com.xiaobitipao.dungbeetle.core.configuration;

import com.xiaobitipao.dungbeetle.core.hack.AutoPrefixUrlMapping;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * <pre>
 * 注册 AutoPrefixUrlMapping 到 IOC 容器
 * </pre>
 */
@Configuration
public class AutoPrefixConfiguration implements WebMvcRegistrations {
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new AutoPrefixUrlMapping();
    }
}
