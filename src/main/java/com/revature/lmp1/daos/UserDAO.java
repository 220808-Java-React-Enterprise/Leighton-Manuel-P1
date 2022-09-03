package com.revature.lmp1.daos;

import com.revature.lmp1.models.User;
import com.revature.lmp1.utils.custom_exceptions.InvalidSQLException;
import com.revature.lmp1.utils.database.ConnectionFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Types.NULL;

public class UserDAO implements CrudDAO<User>{
    @Override
    public void save(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1,obj.getId());
            ps.setString(2,obj.getUsername());
            ps.setString(3,obj.getEmail());
            ps.setString(4,obj.getPassword());
            ps.setString(5,obj.getGivenName());
            ps.setString(6,obj.getSurname());
            ps.setNull(7, NULL);
            ps.setNull(8, NULL);
            ps.executeUpdate();
            System.out.println("Got to the save part");
        } catch (SQLException e) {
            throw new InvalidSQLException("Error. Could not save user to database");
        }
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(String id) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE * FROM users WHERE user_id = ?");
            ps.setString(1,id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
    }

    @Override
    public User getById(String id) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        rs.getString("role_id")
                );
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }


    public User getByUsernameAndPassword(String username, String password) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        rs.getString("role_id")
                );
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }

    public void setActive(String id, boolean status, String role) {
        try(Connection con = ConnectionFactory.getInstance().getConnection( )) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET is_active = ?,role_id = ? WHERE user_id = ?");
            ps.setBoolean(1, status);
            ps.setString(2, role);
            ps.setString(3, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }

    }

    public String getRoleId(String role){
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM user_roles WHERE role = ?");

            ps.setString(1,role);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("role_id");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }


    public void resetPassword(String id, String password) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ? WHERE user_id = ?");
            ps.setString(1, password);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }

    }

    public String getUsername(String username) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT username FROM users WHERE username = ?");

            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("username");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }

    public String getEmail(String email) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (email) FROM users WHERE email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("username");
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }



}
