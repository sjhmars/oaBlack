package com.kunkun.oaBlack.common.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootConfiguration
@EnableOpenApi
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //处理用api注解的controller
                .paths(PathSelectors.any()) //处理所有路径
                .build();
    }


    //自定义接口文档展示信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("mortal给困困的借口文档")
                .description("office_oa接口文档")
                .contact(new Contact("mortal","https://jjf539r8.xiaomy.net","139852422@qq.com"))
                .version("1.0")
                .build();
    }
}
