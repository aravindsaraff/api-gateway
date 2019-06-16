package com.asaraff.gateway.config;

import com.asaraff.gateway.web.CustomResponseErrorHandler;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsAsyncClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.*;

@Configuration
@EnableAsync
public class GatewayConfig implements WebMvcConfigurer {

    @Value("${http.async.tomcat.timeout}")
    private Integer asyncDefaultTimeout;

    /* rest template (sync and async) */

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        template.setErrorHandler(new CustomResponseErrorHandler());
        return template;
    }

    /**
     * Todo Migrate to Spring 5.0+ convention (by removing deprecated usages)
     * @param connectTimeout
     * @param reqTimeout
     * @param readTimeout
     * @return
     */
    @Bean
    public AsyncRestTemplate asyncRestTemplate(@Value("${http.async.connect.timeout}") Integer connectTimeout,
                                               @Value("${http.async.connect.requestTimeout}") Integer reqTimeout,
                                               @Value("${http.async.connect.readTimeout}") Integer readTimeout) {

        HttpComponentsAsyncClientHttpRequestFactory httpRequestFactory = new HttpComponentsAsyncClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(connectTimeout);
        httpRequestFactory.setConnectionRequestTimeout(reqTimeout);
        httpRequestFactory.setReadTimeout(readTimeout);

        AsyncRestTemplate template = new AsyncRestTemplate(httpRequestFactory);
        template.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        template.setErrorHandler(new CustomResponseErrorHandler());

        return template;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(asyncDefaultTimeout);
    }

    @Override
    public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters){
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper());
        converters.add(converter);
    }

    @Primary
    @Bean
    public ObjectMapper objectMapper() {

        ObjectMapper mapper = new ObjectMapper();

        // enable Joda integration
        mapper.registerModule(new JodaModule());

        // return dates as human-readable
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // pretty print responses
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // enable guava serialization
        mapper.registerModule(new GuavaModule());

        return mapper;
    }

    @Bean
    public XmlMapper xmlMapper() {

        // create custom introspector that uses JAXB annotations first and falls back on Jackson annotation library
        AnnotationIntrospector introspector = AnnotationIntrospector.pair(
                new JaxbAnnotationIntrospector(),
                new JacksonAnnotationIntrospector());

        XmlMapper mapper = new XmlMapper();
        mapper.setAnnotationIntrospector(introspector);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }


    @Bean
    public ExecutorService executorService(@Value("${executorservice.threads.minSize}") Integer minThreads,
                                           @Value("${executorservice.threads.maxSize}") Integer maxThreads,
                                           @Value("${executorservice.threads.idleTimeSeconds}") Integer threadIdleTimeoutSeconds,
                                           @Value("${executorservice.queue.maxSize}") Integer maxQueueSize) {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(maxQueueSize);
        return new ThreadPoolExecutor(
                minThreads,
                maxThreads,
                threadIdleTimeoutSeconds,
                TimeUnit.SECONDS,
                blockingQueue);
    }

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("x-requested-with", "Origin", "Content-Type", "Accept", "Bearer", "Cache-Control")
                .allowedMethods("POST", "GET", "OPTIONS", "DELETE", "PATCH");
    }

}
