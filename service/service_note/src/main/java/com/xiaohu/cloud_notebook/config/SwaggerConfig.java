package com.xiaohu.cloud_notebook.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

/**
 * @author xiaohu
 * @date 2022/11/13/ 17:46
 * @description
 */
@Configuration
@EnableKnife4j
@EnableSwagger2WebMvc
public class SwaggerConfig {
    @Bean(value = "note apis")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xiaohu.cloud_notebook.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("笔记服务 Apis")
                .description("云笔记服务在线调试文档")
                .termsOfServiceUrl("https://www.baidu.com/")
                .contact(new Contact("xiaohu", "", "linpeibiao2022@163.com"))
                .version("2.0")
                .build();
    }
}
