package com.github.hwc2243.cms.model;

import java.util.stream.Stream;

public enum ContentPageStatus {
  DRAFT("DRAFT"),
  PUBLISHED("PUBLISHED"),
  ARCHIVED("ARCHIVED");

  private final String name;

  private ContentPageStatus(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  // A static lookup method to find the enum by its name
  public static ContentPageStatus fromValue(String name) {
    return Stream.of(ContentPageStatus.values())
        .filter(type -> type.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown enum value: " + name));
  }
}
