package com.hui.apiversion.config;

import lombok.Data;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    /**
     * 匹配url路径中的版本，如/v[1-9]/
     */
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile("v(\\d+)");

    // api的版本
    private int apiVersion;

    /**
     * @param apiVersion
     */
    public ApiVersionCondition(int apiVersion){
        this.apiVersion = apiVersion;
    }

    /**
     * 将不同的条件进行合并
     * @param apiVersionCondition
     * @return
     */
    @Override
    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        // 以最后定义的版本为准，即最后版本的定义的方法覆盖上以前版本的方法
        return new ApiVersionCondition(apiVersionCondition.getApiVersion());
    }

    /**
     * 根据请求uri匹配版本信息
     * @param httpServletRequest
     * @return
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        System.out.println("httpServletRequest = " + httpServletRequest);
        Matcher matcher = VERSION_PREFIX_PATTERN.matcher(httpServletRequest.getRequestURI());
        if (matcher.find()) {
            int version = Integer.parseInt(matcher.group(1));
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    /**
     * 比较版本，优先匹配最新的版本号
     * @param apiVersionCondition
     * @param httpServletRequest
     * @return
     */
    @Override
    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest httpServletRequest) {
        return apiVersionCondition.getApiVersion() - this.apiVersion;
    }
}
