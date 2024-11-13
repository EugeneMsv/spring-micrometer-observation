package com.github.eugenemsv.spring.micrometer.observation.user;

import io.micrometer.common.KeyValue;
import io.micrometer.observation.Observation;
import io.micrometer.observation.Observation.Event;
import io.micrometer.observation.Observation.Scope;
import io.micrometer.observation.ObservationRegistry;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class UserDeletionObserver {

  private final ObservationRegistry registry;

  UserDeletionObserver(final ObservationRegistry registry) {
    this.registry = registry;
  }

  public void startObservation(final String userId) {
    UserObservationContext context = new UserObservationContext().userId(userId);
    Observation.createNotStarted("user.deletion", () -> context, registry)
        .contextualName("Observing user deletion call")
        .start()
        .openScope();
  }

  public void recordIntermediateEvent(final String eventName) {
    currentObservation().ifPresent(observation -> observation.event(Event.of(eventName)));
  }

  public void endSuccessfully(final String status) {
    currentScope()
        .map(
            scope -> {
              scope.close();
              return scope.getCurrentObservation();
            })
        .ifPresent(
            observation ->
                observation.lowCardinalityKeyValue(KeyValue.of("status", status)).stop());
  }

  public void endWithFailure(final Throwable throwable) {
    currentScope()
        .map(
            scope -> {
              scope.close();
              return scope.getCurrentObservation();
            })
        .ifPresent(
            observation ->
                observation
                    .lowCardinalityKeyValue(KeyValue.of("cause", throwable.getMessage()))
                    .error(throwable));
  }

  private Optional<Scope> currentScope() {
    return Optional.ofNullable(registry.getCurrentObservationScope());
  }

  private Optional<Observation> currentObservation() {
    return currentScope().map(Scope::getCurrentObservation);
  }
}
