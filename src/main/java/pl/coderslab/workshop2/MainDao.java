package pl.coderslab.workshop2;

import pl.coderslab.workshop2.entity.User;
import pl.coderslab.workshop2.entity.UserDao;

public class MainDao {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        User user = new User();
        user.setUserName("Piotr");
        user.setEmail("peter@yahoo.com");
        user.setPassword("password");
        user = userDao.create(user);
        System.out.println(user.getId());
    }
}
