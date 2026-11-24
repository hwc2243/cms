package com.github.hwc2243.cms.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ContentRendererTest {

  private final ContentRenderer renderer = new ContentRenderer();

  @Test
  void rendersMarkdownAsSafeHtml() {
    String html = renderer.markdown("# Title\n\nThis is **important**.");

    assertTrue(html.contains("<h1>Title</h1>"));
    assertTrue(html.contains("<strong>important</strong>"));
  }

  @Test
  void stripsUnsafeMarkdownHtmlAndEventHandlers() {
    String html = renderer.markdown("<script>alert('x')</script>\n\n[link](javascript:alert(1))");

    assertFalse(html.contains("<script"));
    assertFalse(html.contains("onclick="));
    assertFalse(html.contains("javascript:"));
  }

  @Test
  void sanitizesRichTextHtml() {
    String html = renderer.richText("<p>Hello</p><img src=\"/image.png\" onerror=\"bad()\"><iframe src=\"evil\"></iframe>");

    assertTrue(html.contains("<p>Hello</p>"));
    assertTrue(html.contains("<img"));
    assertFalse(html.contains("onerror"));
    assertFalse(html.contains("iframe"));
  }
}
