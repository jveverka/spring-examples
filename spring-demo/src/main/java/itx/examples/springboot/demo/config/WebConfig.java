package itx.examples.springboot.demo.config;

import itx.examples.springboot.demo.config.converters.DataMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan({ "itx.examples.springboot.demo" })
public class WebConfig implements WebMvcConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        LOG.info("extendMessageConverters: {}", converters.size());
        List<HttpMessageConverter<?>> result = new ArrayList<>();
        DataMessageConverter dataMessageConverter = new DataMessageConverter();
        result.add(dataMessageConverter);
        result.addAll(converters);
        converters.clear();
        converters.addAll(result);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
