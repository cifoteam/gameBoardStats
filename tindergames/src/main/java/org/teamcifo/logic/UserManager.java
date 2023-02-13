package org.teamcifo.logic;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.teamcifo.utils.Helpers;

import java.util.HashMap;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserMAnager{

  private Map<String, User> users;
  
  public UserManager() {
    users = new HashMap<>();
  }
  
  public void addUser(User user) {
    users.put(user.getUserId(), user);
  }
  
  public User getUser(String userId) {
    return users.get(userId);
  }
  
  public boolean authenticate(String userId, String password) {
    User user = users.get(userId);
    if (user == null) {
      return false;
    }
    return user.getPassword().equals(password);
  }
  
  public void updateUser(User user) {
    users.put(user.getUserId(), user);
  }
  
  public void deleteUser(String userId) {
    users.remove(userId);
  }
}  

