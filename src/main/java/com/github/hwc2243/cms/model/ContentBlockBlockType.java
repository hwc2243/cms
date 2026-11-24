package com.github.hwc2243.cms.model;

import java.util.stream.Stream;

public enum ContentBlockBlockType {
  MARKDOWN("MARKDOWN"),
  RICH_TEXT("RICH_TEXT"),
  HERO("HERO"),
  IMAGE_BANNER("IMAGE_BANNER"),
  DOCUMENT_LIST("DOCUMENT_LIST"),
  CALLOUT("CALLOUT");

  private final String name;

  private ContentBlockBlockType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  // A static lookup method to find the enum by its name
  public static ContentBlockBlockType fromValue(String name) {
    return Stream.of(ContentBlockBlockType.values())
        .filter(type -> type.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown enum value: " + name));
  }
}
