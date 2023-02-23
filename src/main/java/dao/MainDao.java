package dao;

import entity.User;

public class MainDao {
    public static void main(String[] args) {

        UserDao userDao = new UserDao();
//        User user = new User();
//        user.setUsername("Alp");
//        user.setEmail("Lopezo@87.pl");
//        user.setPassword("Prty423");
//        userDao.create(user);
//
//        User read = userDao.read(1);
//        System.out.println(read);
//
//        User userToUpdate = userDao.read(1);
//        userToUpdate.setUsername("Mort");
//        userToUpdate.setEmail("Alf@0.pl");
//        userToUpdate.setPassword("zcs43");
//        userDao.update(userToUpdate);

        userDao.deleteUser(2);
//
//        User secondUser = new User();
//        secondUser.setUsername("Paweł");
//        secondUser.setEmail("Pablo@pl");
//        secondUser.setPassword("Paweł90");
//        userDao.create(secondUser);
//        User[] alluser = userDao.findAllUsers();
//        for (User users : alluser){
//            System.out.println(users);
//        }
//
//
//



    }


}