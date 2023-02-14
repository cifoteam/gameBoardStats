import com.github.javafaker.Faker;
import org.junit.Test;
import org.teamcifo.logic;
import org.teamcifo.domain;


import static org.junit.Assert.*;

public class UserManagerTest {
  @Test
  public void testAddUserAndGetUser() {
    // Create an instance of the Faker library to generate fake data
    Faker faker = new Faker();
    UserManager userManager = new UserManager();
    User user = new User();
    user.setUserId(faker.number().digits(10));
    user.setName(faker.name().firstName());
    user.setLastname(faker.name().lastName());
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    
    // Add the user to the user manager
    userManager.addUser(user);
    
    // Get the user from the user manager and check that it is the same as the user that was added
    User retrievedUser = userManager.getUser(faker.number().digits(10));
    assertEquals(user, retrievedUser);
  }
  
  @Test
  public void testAuthenticate() {
    // Create an instance of the Faker library to generate fake data
    Faker faker = new Faker();
    UserManager userManager = new UserManager();
    User user = new User();
    user.setUserId(faker.number().digits(10));
    user.setName(faker.name().firstName());
    user.setLastname(faker.name().lastName());
    user.setEmail(faker.internet().emailAddress());
    user.setPassword(faker.internet().password());
    
    // Add the user to the user manager
    userManager.addUser(user);
    
    // Authenticate the user and check that the result is true
    boolean result = userManager.authenticate(faker.internet().emailAddress(), faker.internet().password());
    assertTrue(result);
  }
  
  @Test
  public void testGetAllUsers() {
    // Create an instance of the Faker library to generate fake data
    Faker faker = new Faker();
    UserManager userManager = new UserManager();
    List<User> users = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setUserId(faker.number().digits(10));
      user.setName(faker.name().firstName());
      user.setLastname(faker.name().lastName());
      user.setEmail(faker.internet().emailAddress());
      user.setPassword(faker.internet().password());
      users.add(user);
      userManager.addUser(user);
    }
    
    // Get the list of all users from the user manager and check that it is the same as the list of users that was created
    List<User> retrievedUsers = userManager.getAllUsers();
    assertEquals(users, retrievedUsers);
  }
}
