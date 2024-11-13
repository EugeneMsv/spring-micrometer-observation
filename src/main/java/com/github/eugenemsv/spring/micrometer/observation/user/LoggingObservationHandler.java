package com.github.eugenemsv.spring.micrometer.observation.user;

import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.Observation.Event;
import io.micrometer.observation.ObservationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class LoggingObservationHandler implements ObservationHandler<Context> {

  private static final Logger log = LoggerFactory.getLogger(LoggingObservationHandler.class);

  @Override
  public void onStart(final Context context) {
    String observationName = context.getName();
    if (isUserObservation(observationName)) {
      log.info("Started user '{}' observation ", extractUserOperationName(observationName));
    }
  }

  @Override
  public void onError(final Context context) {
    String observationName = context.getName();
    if (isUserObservation(observationName)) {
      log.error(
          "Error during user '{}' observation",
          extractUserOperationName(observationName),
          context.getError());
    }
  }

  @Override
  public void onEvent(final Event event, final Context context) {
    String observationName = context.getName();
    if (isUserObservation(observationName)) {
      log.info(
          "User '{}' event '{}' recorded ",
          extractUserOperationName(observationName),
          event.getName());
    }
  }

  @Override
  public void onStop(final Context context) {
    String observationName = context.getName();
    if (isUserObservation(observationName)) {
      log.info(
          "User '{}' observation successfully finished", extractUserOperationName(observationName));
    }
  }

  @Override
  public boolean supportsContext(final Context context) {
    return true;
  }

  private String extractUserOperationName(final String observationName) {
    return observationName.split("\\.")[1];
  }

  private boolean isUserObservation(final String observationName) {
    return observationName.startsWith("user.");
  }
}
