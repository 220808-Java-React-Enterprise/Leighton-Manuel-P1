package com.revature.lmp1.daos;

import com.revature.lmp1.models.Reimb_Status;
import com.revature.lmp1.models.Reimb_Type;
import com.revature.lmp1.models.Reimbursement;
import com.revature.lmp1.utils.custom_exceptions.InvalidSQLException;
import com.revature.lmp1.utils.database.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class ReimbDAO implements CrudDAO<Reimbursement>{
    @Override
    public void save(Reimbursement obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            System.out.println("saving to database");
            System.out.println("resolver id: " + obj.getResolverId());
            PreparedStatement ps = con.prepareStatement("INSERT INTO reimbursements (reimb_id, amount, submitted, resolved, description, receipt, payment_id, author_id, resolver_id, status_id, type_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getId());
            ps.setDouble(2, obj.getAmount());
            ps.setTimestamp(3, Timestamp.valueOf(obj.getSubmitted()));
            //ps.setTimestamp(4, Timestamp.valueOf(obj.getResolved()));
            ps.setNull(4, NULL);
            ps.setString(5, obj.getDescription());
            ps.setString(6, obj.getReceipt());
            ps.setString(7, obj.getPaymentId());
            ps.setString(8, obj.getAuthorId());
            //ps.setString(9, obj.getResolverId());
            ps.setString(9,obj.getResolverId());
            ps.setString(10, obj.getStatusId());
            ps.setString(11, obj.getTypeId());
            ps.executeUpdate();
            System.out.println("Saved to database");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error. Could not save user to database");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void update(Reimbursement obj) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public Reimbursement getById(String id) {
        return null;
    }

    @Override
    public List<Reimbursement> getAll() {
        return null;
    }

    public List<Reimbursement> getAllByStatus(String status){
        List<Reimbursement> reimbs = new ArrayList<Reimbursement>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursements r, reimbursement_statuses rs WHERE r.status_id = rs.status_id AND rs.status = ?");
            ps.setString(1,status);
            System.out.println("got here");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //Painting paint = new Painting(rs.getString("id"),rs.getString("name"),rs.getString("author"),rs.getString("image"),rs.getBoolean("is_available"),rs.getString("warehouse_id"),rs.getDouble("cost"));
                //paintings.add(paint);
                System.out.println("about to add a new reimb");
                try {
                    Reimbursement reimb = new Reimbursement(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted").toLocalDateTime(),null, rs.getString("description"), null, rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"));
                    System.out.println("created a new reimb");
                    reimbs.add(reimb);
                    System.out.println("added a new reimb");
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("unfortunate");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to save to the database.");
        }



        return reimbs;
    }

    public void changeReimbStatus(String id, String status_id, String person_id){
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE reimbursements SET status_id = ?, resolved = ?, resolver_id = ? WHERE reimb_id = ?");
            ps.setString(1, status_id);
            ps.setTimestamp(2,Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, person_id);
            ps.setString(4, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
    }

    public List<Reimbursement> getAllByResolver(String id){
        List<Reimbursement> all = new ArrayList<Reimbursement>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursements WHERE resolver_id = ?");
            ps.setString(1,id);
            System.out.println("got here");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                //Painting paint = new Painting(rs.getString("id"),rs.getString("name"),rs.getString("author"),rs.getString("image"),rs.getBoolean("is_available"),rs.getString("warehouse_id"),rs.getDouble("cost"));
                //paintings.add(paint);
                System.out.println("about to add a new reimb");
                try {
                    Reimbursement reimb = new Reimbursement(rs.getString("reimb_id"), rs.getDouble("amount"), rs.getTimestamp("submitted").toLocalDateTime(),null, rs.getString("description"), null, rs.getString("payment_id"), rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"));
                    System.out.println("created a new reimb");
                    all.add(reimb);
                    System.out.println("added a new reimb");
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("unfortunate");
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("An error occurred when trying to save to the database.");
        }


        return all;
    }
    public String getTypeId(String type){
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursement_types WHERE type = ?");

            ps.setString(1,type);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("type_id");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }

    public String getStatusId(String status){
        try(Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursement_statuses WHERE status = ?");

            ps.setString(1,status);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return rs.getString("status_id");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }
        return null;
    }

    public Reimb_Status getStatusById(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursement_statuses WHERE status_id = ?");
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Reimb_Status(rs.getString("status_id"), rs.getString("status"));
            }
        }catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }

        return null;
    }

    public Reimb_Type getTypeById(String id) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursement_types WHERE type_id = ?");
            ps.setString(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Reimb_Type(rs.getString("type_id"), rs.getString("type"));
            }
        }catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }

        return null;
    }

    public List<String> getTypes() {
        List<String> types = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (type) FROM reimbursement_types");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                types.add(rs.getString("type"));
            }
        } catch (SQLException e) {
            throw new InvalidSQLException("Error connecting to database");
        }
        return types;
    }

    public List<String> getStatuses() {
        List<String> statuses = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursement_statuses");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                statuses.add(rs.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }
        return statuses;
    }

    public List<Reimbursement> getReimbursementHistory(String id, String status, String sort, String order) {
        List<Reimbursement> history = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = null;
            if(order == "ASC") {
                 ps = con.prepareStatement("SELECT * FROM reimbursements WHERE author_id = ? AND status_id = ? ORDER BY ? ASC");
            }
            else{
                ps = con.prepareStatement("SELECT * FROM reimbursements WHERE author_id = ? AND status_id = ? ORDER BY ? DESC");
            }
            ps.setString(1, id);
            ps.setString(2, status);
            ps.setString(3, sort);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if(rs.getTimestamp("resolved") != null) {
                    history.add(new Reimbursement(
                            rs.getString("reimb_id"),
                            rs.getInt("amount"),
                            rs.getTimestamp("submitted").toLocalDateTime(),
                            rs.getTimestamp("resolved").toLocalDateTime(),
                            rs.getString("description"), null,
                            rs.getString("payment_id"),
                            rs.getString("author_id"),
                            rs.getString("resolver_id"),
                            rs.getString("status_id"),
                            rs.getString("type_id")
                    ));
                }
                else{
                    history.add(new Reimbursement(
                            rs.getString("reimb_id"),
                            rs.getInt("amount"),
                            rs.getTimestamp("submitted").toLocalDateTime(),
                            null,
                            rs.getString("description"), null,
                            rs.getString("payment_id"),
                            rs.getString("author_id"),
                            rs.getString("resolver_id"),
                            rs.getString("status_id"),
                            rs.getString("type_id")
                    ));
                }
            }
        } catch (SQLException e) {
                e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }

        return history;
    }

    public List<Reimbursement> getPendingReimbursementsByUser(String id) {
        List<Reimbursement> history = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            System.out.println("attempted to view");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM reimbursements WHERE author_id = ? AND status_id = ? ORDER BY submitted ASC");
            ps.setString(1, id);
            ps.setString(2, "1");
            ResultSet rs = ps.executeQuery();
            System.out.println("executed");
            while (rs.next()) {
                history.add(new Reimbursement(
                        rs.getString("reimb_id"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("submitted").toLocalDateTime(),
                        null,
                        rs.getString("description"),null,
                        rs.getString("payment_id"),
                        rs.getString("author_id"),
                        rs.getString("resolver_id"),
                        rs.getString("status_id"),
                        rs.getString("type_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidSQLException("Error connecting to database");
        }

        return history;
    }
}
