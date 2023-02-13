package dao;

//Na podstawie query przykładowego z coderslaba zrób inne query do operacji updateowania, usuwania
// wyszukiwania userów po id oraz wyszukania wszystkich userów

import entity.User;
import pl.coderslab.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            " insert into users(username, email, password) Values (? ? ?) ";

    private static final String READ_USER_QUERY =
            "select * from users where id = ?";

    private static final String UPDATE_USER_QUERY =
            "update users set username = ?, email = ?, password = ? where id = ? ";

    private static final String DELETE_USER_QUERY =
            "delete * from users where id = ?";

    private static final String FIND_USERS_QUERY =
            "select * from users";

    public User create(User user) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }




//
//    ResultSet rs = preStmt.getGeneratedKeys();
//    if (rs.next()) {
//        long id = rs.getLong(1);
//        System.out.println("Inserted ID: " + id);
//    }



}
