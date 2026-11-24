package com.github.hwc2243.cms;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CmsApplicationTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void standaloneApplicationRendersPublishedMarkdownPage() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("CMS Example")))
        .andExpect(content().string(containsString("This page is rendered from")))
        .andExpect(content().string(containsString("<strong>Markdown</strong>")));
  }
}
