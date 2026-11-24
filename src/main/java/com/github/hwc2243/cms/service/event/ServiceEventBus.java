package com.github.hwc2243.cms.service.event;

public interface ServiceEventBus {
  void publish(ServiceEvent event);
}
