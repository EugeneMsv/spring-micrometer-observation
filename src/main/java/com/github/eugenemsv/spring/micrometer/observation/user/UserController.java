package com.github.eugenemsv.spring.micrometer.observation.user;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {

  private final UserService userService;

  UserController(final UserService userService) {
    this.userService = userService;
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public String addUser(@RequestBody UserCreation userCreation) {
    return userService.addUser(userCreation.name());
  }

  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String findUser(@PathVariable("id") String userId) {
    return userService.findUser(userId);
  }

  @DeleteMapping(path = "/{id}")
  public String deleteUser(@PathVariable("id") String userId) {
    return userService.deleteUser(userId);
  }
}
