package com.hui.apiversion.config;


import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.lang.reflect.Method;

@Configuration
public class ApiVersionConfig extends WebMvcConfigurationSupport {
    /**
     * 重写请求过处理的方法
     * 在使用@RequestMapping注解时，SpringMvc通过RequestMappingHandlerMapping类的Bean解析、注册、缓存映射关系，并提供匹配执行链的功能
     * @param contentNegotiationManager
     * @param conversionService
     * @param resourceUrlProvider
     * @return
     */
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        CustomRequestMappingHandlerMapping customRequestMappingHandlerMapping = new CustomRequestMappingHandlerMapping();
        customRequestMappingHandlerMapping.setOrder(1);
        return customRequestMappingHandlerMapping;
    }

    /**
     * 自定义匹配的处理器
     */
    private static class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping{

        /**
         * 匹配有@ApiVersion注解的类
         * @param handlerType
         * @return
         */
        @Override
        protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            return createCondition(apiVersion);
        }

        /**
         * 匹配有@ApiVersion注解的方法
         * @param method
         * @return
         */
        @Override
        protected RequestCondition<?> getCustomMethodCondition(Method method) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            return createCondition(apiVersion);
        }

        /**
         * 根据版本号创建匹配条件
         * @param apiVersion
         * @return
         */
        private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion){
            return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
        }
    }

    /**
     * 当设置WebMvcConfigurationSupport配置时，WebMvcAutoConfiguration则会失效，导致静态资源无法方法(No mapping for get),
     * 所以将静态资源添加进MVC容器中
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**")
                    .addResourceLocations(
                            "classpath:/META-INF/resources/",
                            "classpath:/resources/",
                            "classpath:/static/",
                            "classpath:/public/"
                    );
        }
    }
}
