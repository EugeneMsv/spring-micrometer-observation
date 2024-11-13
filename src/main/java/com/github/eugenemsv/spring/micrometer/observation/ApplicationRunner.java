package com.github.eugenemsv.spring.micrometer.observation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(ApplicationRunner.class);

  @Override
  public void run(final String... args) {
    log.info("Running the application");
  }
}
