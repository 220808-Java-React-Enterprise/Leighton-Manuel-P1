package com.revature.lmp1.daos;

import com.revature.lmp1.models.User;

import java.io.IOException;
import java.util.List;

public class UserDAO implements CrudDAO<User>{
    @Override
    public void save(User obj) throws IOException {

    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
<<<<<<< Updated upstream
=======

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

    public void setActive(String id, boolean status) {
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET is_active = ? WHERE user_id = ?");
            ps.setBoolean(1, status);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }

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
>>>>>>> Stashed changes
}
