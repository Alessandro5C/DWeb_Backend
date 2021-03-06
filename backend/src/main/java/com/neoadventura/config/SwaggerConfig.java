package com.neoadventura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neoadventura.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, getCustomizedResponseMessages()
                    );
    }

//    private ApiInfo getApiInformation(){
//        return new ApiInfo("Demo REST API",
//                "This is a Demo API created using Spring Boot",
//                "1.0",
//                "API Terms of Service URL",
//                new Contact("Progressive Coder", "www.progressivecoder.com", "coder.progressive@gmail.com"),
//                "API License",
//                "API License URL",
//                Collections.emptyList()
//                );
//    }

    private List<ResponseMessage> getCustomizedResponseMessages(){
        List<ResponseMessage> responseMessages = new ArrayList<>();
        responseMessages.add(new ResponseMessageBuilder().code(500).message("INTERNAL SERVER ERROR, CHECK LOCAL LOG").responseModel(new ModelRef("Error")).build());
        responseMessages.add(new ResponseMessageBuilder().code(403).message("YOU ARE NOT SUPPOSED TO DO THIS WITH YOUR ROL").build());
        responseMessages.add(new ResponseMessageBuilder().code(304).message("NOT SUPPORTED FORMAT, SO, THIS IS NOT GOING TO BE MODIFIED").build());
        return responseMessages;
    }
}

