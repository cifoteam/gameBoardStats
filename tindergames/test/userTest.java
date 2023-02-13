package org.teamcifo.logic;
package org.teamcifo.domain;

import com.github.javafaker.Faker;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {
  @Test
  public void testGettersAndSetters() {
    // Create an instance of the Faker library to generate fake data
    Faker faker = new Faker();
    User user = new User();
    // Set the user's properties using fake data
    user.setUserId(faker.number().digits(10));
    user.setName(faker.name().firstName());
    user.setLastname(faker.name().lastName());
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    
    // Check that the user's properties have been correctly set
    assertEquals(faker.number().digits(10), user.getUserId());
    assertEquals(faker.name().firstName(), user.getName());
    assertEquals(faker.name().lastName(), user.getLastname());
    assertEquals(faker.internet().emailAddress(), user.getEmail());
    assertEquals(faker.internet().password(), user.getPassword());
  }
}