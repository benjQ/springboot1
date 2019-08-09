package com.java.springboot.configuration;

import com.java.springboot.interceptor.MethodInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private MethodInterceptor methodInterceptor;
    @Autowired
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

/*
    @Bean//使用@Bean注解将拦截器示例初始化spring容器 拦截器中的注入就能正常使用了
    MethodInterceptor methodInterceptor() {
        return new MethodInterceptor();
        // 这个方法才能在拦截器中自动注入查询数据库的对象
    }
*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*
        "/*" 是拦截所有的文件夹，不包含子文件夹,
        "/**" 是拦截所有的文件夹及里面的子文件夹.
        */
        //registry.addInterceptor(methodInterceptor).addPathPatterns("/**");
        registry.addInterceptor(methodInterceptor).addPathPatterns("/**").excludePathPatterns("/login");



    }
}