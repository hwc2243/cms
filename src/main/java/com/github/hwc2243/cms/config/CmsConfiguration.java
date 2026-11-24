package com.github.hwc2243.cms.config;

import com.github.hwc2243.cms.entity.ContentPageEntity;
import com.github.hwc2243.cms.persistence.ContentPagePersistence;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Registers the CMS services, entities, and generated repositories for a host
 * Spring Boot application.
 */
@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = {
    "com.github.hwc2243.cms",
    "com.github.hwc2243.documentlibrary.service"
})
@EntityScan(basePackageClasses = ContentPageEntity.class)
@EnableJpaRepositories(basePackageClasses = ContentPagePersistence.class)
public class CmsConfiguration {
}
