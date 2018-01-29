package com.sdxd.api.doc;

import com.fasterxml.classmate.TypeResolver;
import com.sdxd.common.web.doc.RestExpandedParameterBuilder;
import com.sdxd.common.web.doc.RestFieldProvider;
import com.sdxd.common.web.doc.RestOperationParameterReader;
import com.sdxd.common.web.doc.RestParameterTypeReader;
import com.sdxd.common.web.rest.ContextParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

import javax.ws.rs.BeanParam;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.AuthorizationScopeBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.property.field.FieldProvider;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ExpandedParameterBuilderPlugin;
import springfox.documentation.spi.service.OperationBuilderPlugin;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * *****************************************************************************
 * <p>
 * 功能名           ：com.sdxd.common.web.doc
 * 系统名           ：
 * <p>
 * *****************************************************************************
 * Modification History
 * <p>
 * Date        Name                    Reason for Change
 * ----------  ----------------------  -----------------------------------------
 * 16/11/1     melvin                 Created
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public ExpandedParameterBuilderPlugin expandedParameterBuilderPlugin() {
        return new RestExpandedParameterBuilder();
    }

    @Bean
    public ParameterBuilderPlugin parameterTypeReader() {
        return new RestParameterTypeReader();
    }

    @Bean
    @Autowired
    public FieldProvider fieldProvider(TypeResolver typeResolver) {
        return new RestFieldProvider(typeResolver);
    }

    @Bean
    @Autowired
    public OperationBuilderPlugin operationParameterReader(ModelAttributeParameterExpander expander) {
        RestOperationParameterReader reader = new RestOperationParameterReader(expander);
        reader.setShouldExpandAnnotationTypes(BeanParam.class);
        return reader;
    }

    @Bean
    public Docket adminRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                groupName("admin-api").
                ignoredParameterTypes(ContextParam.class).
                securitySchemes(newArrayList(new BasicAuth("test"))).
                securityContexts(securityContexts()).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.sdxd.api.app.admin")).
                paths(PathSelectors.any()).
                build().
                pathMapping("/").directModelSubstitute(Date.class, long.class);
    }

    @Bean
    public Docket customerRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                groupName("customer-api").
                ignoredParameterTypes(ContextParam.class).
                securitySchemes(newArrayList(new BasicAuth("test"))).
                securityContexts(securityContexts()).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.sdxd.api.app.customer")).
                paths(PathSelectors.any()).
                build().
                pathMapping("/").directModelSubstitute(Date.class, long.class);
    }

    @Bean
    public Docket officialRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                groupName("official-api").
                ignoredParameterTypes(ContextParam.class).
                securitySchemes(newArrayList(new BasicAuth("test"))).
                securityContexts(securityContexts()).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.sdxd.api.app.official")).
                paths(PathSelectors.any()).
                build().
                pathMapping("/").directModelSubstitute(Date.class, long.class);
    }

    @Bean
    public Docket utilitiesRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).
                groupName("utilities-api").
                ignoredParameterTypes(ContextParam.class).
                securitySchemes(newArrayList(new BasicAuth("test"))).
                securityContexts(securityContexts()).
                apiInfo(apiInfo()).
                select().
                apis(RequestHandlerSelectors.basePackage("com.sdxd.api.app.utilities")).
                paths(PathSelectors.any()).
                build().
                pathMapping("/").directModelSubstitute(Date.class, long.class);
    }

    private List<SecurityContext> securityContexts() {
        AuthorizationScope[] authScopes = new AuthorizationScope[]{
                new AuthorizationScopeBuilder().scope("read").description("read access").build()
        };
        SecurityReference securityReference =
                SecurityReference.builder().reference("test").scopes(authScopes).build();
        return newArrayList(
                SecurityContext.builder().
                        forPaths(PathSelectors.ant("/pvt/**")).
                        securityReferences(newArrayList(securityReference)).build());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SDXD APP REST API")
                .description("Descriptions.")
                .termsOfServiceUrl("http://api.sdxd.com")
                .contact(new Contact("melvin", "", "zhangmin@shengdaxd.com"))
                .version("1.0")
                .build();
    }
}
