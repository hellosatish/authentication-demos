package org.sk.security.demo.config.swagger;

import static io.swagger.models.auth.In.HEADER;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
@Configuration
public class SwaggerConfigurations {


	@Bean
	public Docket api() {                
	    return new Docket(SWAGGER_2)
		        .securitySchemes(singletonList(new ApiKey("JWT", AUTHORIZATION, HEADER.name())))
		        .securityContexts(singletonList(
		            SecurityContext.builder()
		                .securityReferences(
		                    singletonList(SecurityReference.builder()
		                        .reference("JWT")
		                        .scopes(new AuthorizationScope[0])
		                        .build()
		                    )
		                )
		                .build())
		        )
		        .select()
		        .apis(RequestHandlerSelectors.basePackage("org.sk.security.demo.web"))
		        .build();
	}
	 
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "Booking API", 
	      "API. Demonstrating RBAC at API level", 
	      "API TOS", 
	      "Terms of service", 
	      new Contact("Satish Sharma", "https://github.com/hellosatish", "myeaddress@abc.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
	
	@Bean
	  UiConfiguration uiConfig() {
	    return UiConfigurationBuilder.builder()
	        .deepLinking(true)
	        .displayOperationId(false)
	        .defaultModelsExpandDepth(1)
	        .defaultModelExpandDepth(1)
	        .defaultModelRendering(ModelRendering.EXAMPLE)
	        .displayRequestDuration(false)
	        .docExpansion(DocExpansion.NONE)
	        .filter(false)
	        .maxDisplayedTags(null)
	        .operationsSorter(OperationsSorter.ALPHA)
	        .showExtensions(false)
	        .tagsSorter(TagsSorter.ALPHA)
	        .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
	        .validatorUrl(null)
	        .build();
	  }
}
