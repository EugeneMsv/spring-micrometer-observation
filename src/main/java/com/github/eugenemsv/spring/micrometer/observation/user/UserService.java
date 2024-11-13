package com.github.eugenemsv.spring.micrometer.observation.user;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.annotation.Observed;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class UserService {
  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  // Not using a real data storage for demo purposes
  private Map<String, String> userNamesById = new ConcurrentHashMap<>();

  private final UserDeletionObserver deletionObserver;

  private final ObservationRegistry observationRegistry;

  UserService(
      final UserDeletionObserver deletionObserver, final ObservationRegistry observationRegistry) {
    this.deletionObserver = deletionObserver;
    this.observationRegistry = observationRegistry;
  }

  @Observed(name = "user.creation")
  public String addUser(final String userName) {
    log.info("Adding the user '{}'", userName);

    String userId = UUID.randomUUID().toString();
    userNamesById.put(userId, userName);
    return userId;
  }

  public String findUser(final String userId) {
    return Observation.createNotStarted("user.search", observationRegistry)
        .observe(
            () -> {
              log.info("Getting the user '{}'", userId);
              return userNamesById.get(userId);
            });
  }

  public String deleteUser(final String userId) {
    deletionObserver.startObservation(userId);
    try {
      log.info("Deleting the user '{}'", userId);
      String previousValue = userNamesById.remove(userId);
      if (previousValue != null) {
        deletionObserver.recordIntermediateEvent("removed.count");
      }
      deletionObserver.endSuccessfully(previousValue == null ? "not_found" : "removed");
      return previousValue;
    } catch (Throwable e) {
      deletionObserver.endWithFailure(e);
      throw new RuntimeException(e);
    }
  }
}
