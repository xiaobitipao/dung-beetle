package com.xiaobitipao.dungbeetle.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 异常代码配置类
 *
 * 用类的思想去看待一个配置文件。将异常代码的配置文件和类对应起来，当在其他的类中读取配置文件时，就相当于读取一个类的属性。
 *
 * PS: 如果某一个业务的配置项比较多，最好是将其提取成一个单独的配置文件
 *
 * 1. @PropertySource: 通过 @PropertySource 注解将 properties 配置文件和当前类进行关联(前提是当前类必须在 IOC 容器内，所以需要 @Component 注解)
 * 2. @ConfigurationProperties: 通过 @ConfigurationProperties 注解将 prefix 以外的内容和当前类的属性进行匹配，这里是 codes 和 codes 进行匹配
 * </pre>
 */
@PropertySource(value = "classpath:config/exception-code.properties")
@ConfigurationProperties(prefix = "dungbeetle")
@Component
public class ExceptionCodeConfiguration {

    private Map<Integer, String> codes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return codes;
    }

    public void setCodes(Map<Integer, String> codes) {
        this.codes = codes;
    }

    public String getMessage(int code) {
        String message = codes.get(code);
        return message;
    }
}
