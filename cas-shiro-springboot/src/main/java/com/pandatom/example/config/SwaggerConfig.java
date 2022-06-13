package com.pandatom.example.config;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年03月25日 11:07 AM
 */

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Swagger2的接口配置
 *
 * @author hcly
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                //.apis(RequestHandlerSelectors.basePackage("com.hcly.project.tool.swagger"))
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
//        //添加header参数
//        ParameterBuilder ticketPar = new ParameterBuilder();
//        List<Parameter> pars = new ArrayList<>();
//        ticketPar.name("token").description("user token")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build(); //header中的ticket参数非必填，传空也可以
//        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                // 是否启用Swagger
//                .enable(enabled)
//                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
//                .apiInfo(apiInfo())
//                // 设置哪些接口暴露给Swagger展示
//                .select()
//                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                // 扫描指定包中的swagger注解
//                //.apis(RequestHandlerSelectors.basePackage("com.hcly.project.tool.swagger"))
//                // 扫描所有 .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(pars);
    }

    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("标题：管理系统_接口文档")
                // 描述
                .description("描述：用于与安卓端信息交互,具体包括XXX,XXX模块...")
                // 作者信息
                .contact("panadatom")
                // 版本
                .version("版本号:1.0.0" )
                .build();
    }

//    @Bean
//    public LinkDiscoverers discoverers() {
//        List<LinkDiscoverer> plugins = new ArrayList<>();
//        plugins.add(new CollectionJsonLinkDiscoverer());
//        return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
//    }
}