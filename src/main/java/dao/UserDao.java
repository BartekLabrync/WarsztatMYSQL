package dao;

//Na podstawie query przykładowego z coderslaba zrób inne query do operacji updateowania, usuwania
// wyszukiwania userów po id oraz wyszukania wszystkich userów

import entity.User;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            " insert into users(username, email, password) Values (?, ?, ?) ";

    private static final String READ_USER_QUERY =
            "select * from users where id = ?";

    private static final String UPDATE_USER_QUERY =
            "update users set username = ?, email = ?, password = ? where id = ? ";

    private static final String DELETE_USER_QUERY =
            "delete from users where id = ?";

    private static final String FIND_USERS_QUERY =
            "select * from users";

    public User create(User user) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
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

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User read(int userId) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(READ_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("UserName"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(User user) {
        try (Connection connection = DbUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(UPDATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getEmail());
            statement.setString(3, this.hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId){
        try (Connection connection = DbUtil.getConnection()){
            PreparedStatement statement =
                    connection.prepareStatement(DELETE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User[] findAllUsers (){
        try (Connection connection = DbUtil.getConnection()){
            User[] users = new User[0];
            PreparedStatement statement =
                    connection.prepareStatement(FIND_USERS_QUERY, Statement.RETURN_GENERATED_KEYS);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("UserName"));
                user.setEmail(resultSet.getString("Email"));
                user.setPassword(resultSet.getString("Password"));
                users = addToArray(user, users);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1); // Tworzymy kopię tablicy powiększoną o 1.
        tmpUsers[users.length] = u; // Dodajemy obiekt na ostatniej pozycji.
        return tmpUsers; // Zwracamy nową tablicę.
    }

}