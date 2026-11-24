package com.github.hwc2243.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.github.hwc2243.cms.config.CmsConfiguration;
import com.github.hwc2243.documentlibrary.config.DocumentLibraryConfiguration;

/**
 * Standalone launcher for exercising the CMS module.
 *
 * <p>Run locally with {@code mvn spring-boot:run}; the bundled configuration
 * uses an embedded H2 database and the in-process Document Library services.
 */
@SpringBootApplication
@Import({CmsConfiguration.class, DocumentLibraryConfiguration.class})
public class CmsApplication {

  public static void main(String[] args) {
    SpringApplication.run(CmsApplication.class, args);
  }
}
