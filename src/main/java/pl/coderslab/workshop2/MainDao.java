package pl.coderslab.workshop2;

import pl.coderslab.workshop2.entity.User;
import pl.coderslab.workshop2.entity.UserDao;

public class MainDao {
    public static void main(String[] args) {
//        addUser("Zbyszek", "zbynio@onet.pl", "passdaf");
//        showUser(-1);
//        changeUserName(4, "Piotrek");
//        deleteUser(5);
        showAllUsers();
    }

    private static void showAllUsers() {
        UserDao userDao = new UserDao();
        User[] users = userDao.findAll();
        for (User user : users) {
            user.printUser();
        }
    }

    private static void deleteUser(int id) {
        UserDao userDao = new UserDao();
        userDao.delete(id);
    }

    private static void changeUserName(int id, String name) {
        UserDao userDao = new UserDao();
        User user = userDao.read(id);
        if (user == null) {
            System.out.println("Brak użytkownika o id = " + id + " w bazie.");
            return;
        }
        user.setUserName(name);
        userDao.update(user);
    }

    private static void showUser(int i) {
        UserDao userDao = new UserDao();
        User user = userDao.read(i);
        if (user != null) {
            user.printUser();
        } else {
            System.out.println("Nie ma użytkownika o id = " + i);
        }
    }

    private static void addUser(String name, String email, String password) {
        UserDao userDao = new UserDao();
        User user = new User(0, name, email, password);
        user = userDao.create(user);
        user.printUser();
    }
}
