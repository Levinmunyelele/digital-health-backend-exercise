package org.example.patient.config;

import org.example.patient.security.ApiKeyFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

@Configuration
public class SecurityConfig {

    @Value("${API_KEY:}") 
    private String apiKey;

    @Bean
    public FilterRegistrationBean<ApiKeyFilter> apiKeyFilter() {
        ApiKeyFilter filter = new ApiKeyFilter(apiKey);

        FilterRegistrationBean<ApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/api/*"); 
        return registrationBean;
    }
}
