package net.ultrasist.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase WebMvcConfig.
 *
 * @author garellano
 * @version $Id: $Id
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /** {@inheritDoc} */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/404/**")
            .addResourceLocations("classpath:/public/error/clouds-404/");
    }

    /** {@inheritDoc} */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("Test cors mapping");
        registry
            .addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("*");
    }
}
