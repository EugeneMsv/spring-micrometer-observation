package com.github.eugenemsv.spring.micrometer.observation.user;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import java.util.Optional;

public class UserObservationContext extends Observation.Context {

  public UserObservationContext userId(final String userId) {
    addHighCardinalityKeyValue(KeyValue.of("userId", userId));
    return this;
  }

  public String userId() {
    return Optional.ofNullable(getHighCardinalityKeyValue("userId"))
        .map(KeyValue::getValue)
        .orElse("unknown");
  }
}
