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
}
