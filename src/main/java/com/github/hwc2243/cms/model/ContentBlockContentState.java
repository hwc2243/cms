package com.github.hwc2243.cms.model;

import java.util.stream.Stream;

public enum ContentBlockContentState {
  DRAFT("DRAFT"),
  PUBLISHED("PUBLISHED");

  private final String name;

  private ContentBlockContentState(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  // A static lookup method to find the enum by its name
  public static ContentBlockContentState fromValue(String name) {
    return Stream.of(ContentBlockContentState.values())
        .filter(type -> type.getName().equals(name))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Unknown enum value: " + name));
  }
}
