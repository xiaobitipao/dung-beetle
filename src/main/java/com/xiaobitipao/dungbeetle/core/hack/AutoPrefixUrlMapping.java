package com.xiaobitipao.dungbeetle.core.hack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * <pre>
 * 为 `@RequestMapping` 注解的路由添加前缀(Controller 所在的包名)
 * </pre>
 */
public class AutoPrefixUrlMapping extends RequestMappingHandlerMapping {

    @Value("${dungbeetle.api-package}")
    private String apiPackagePath;

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        // 基类的返回值就是 RequestMapping 信息
        // 接下来对该信息进行修改
        RequestMappingInfo mappingInfo = super.getMappingForMethod(method, handlerType);
        if (mappingInfo != null) {
            String prefix = this.getPrefix(handlerType);
            // 以 prefix 创建新的 RequestMappingInfo 并合并父类返回的 RequestMappingInfo
            return RequestMappingInfo.paths(prefix).build().combine(mappingInfo);
        }
        return mappingInfo;
    }

    private String getPrefix(Class<?> handlerType) {
        String packageName = handlerType.getPackage().getName();
        String dotPath = packageName.replaceAll(this.apiPackagePath, "");
        return dotPath.replace(".", "/");
    }
}
