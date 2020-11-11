package com.hui.apiversion.config;


import com.hui.apiversion.annotion.ApiVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

import java.lang.reflect.Method;

@Configuration
public class ApiVersionConfig extends WebMvcConfigurationSupport {
    @Override
    public RequestMappingHandlerMapping requestMappingHandlerMapping(ContentNegotiationManager contentNegotiationManager, FormattingConversionService conversionService, ResourceUrlProvider resourceUrlProvider) {
        CustomRequestMappingHandlerMapping customRequestMappingHandlerMapping = new CustomRequestMappingHandlerMapping();
        customRequestMappingHandlerMapping.setOrder(1);
        return customRequestMappingHandlerMapping;
    }

    private static class CustomRequestMappingHandlerMapping extends RequestMappingHandlerMapping{
        private RequestCondition<ApiVersionCondition> createCondition(ApiVersion apiVersion){
            return apiVersion == null ? null : new ApiVersionCondition(apiVersion.value());
        }

        @Override
        protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(handlerType, ApiVersion.class);
            return createCondition(apiVersion);
        }

        @Override
        protected RequestCondition<?> getCustomMethodCondition(Method method) {
            ApiVersion apiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
            return createCondition(apiVersion);
        }
    }
}
