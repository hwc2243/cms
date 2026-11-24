package com.github.hwc2243.cms.service;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

/**
 * Converts authored content into HTML that is safe to render with Thymeleaf
 * using {@code th:utext}.
 */
@Service("contentRenderer")
public class ContentRenderer {

  private final Parser markdownParser;
  private final HtmlRenderer markdownRenderer;
  private final Safelist safelist;

  public ContentRenderer() {
    this.markdownParser = Parser.builder().build();
    this.markdownRenderer = HtmlRenderer.builder()
        .escapeHtml(true)
        .sanitizeUrls(true)
        .build();
    this.safelist = Safelist.relaxed()
        .preserveRelativeLinks(true)
        .addProtocols("a", "href", "http", "https", "mailto")
        .addProtocols("img", "src", "http", "https");
  }

  public String markdown(String source) {
    if (source == null || source.isBlank()) {
      return "";
    }
    Node document = markdownParser.parse(source);
    return sanitize(markdownRenderer.render(document));
  }

  public String richText(String source) {
    return sanitize(source);
  }

  public String sanitize(String html) {
    if (html == null || html.isBlank()) {
      return "";
    }
    return Jsoup.clean(html, "", safelist, new org.jsoup.nodes.Document.OutputSettings()
        .prettyPrint(false));
  }
}
