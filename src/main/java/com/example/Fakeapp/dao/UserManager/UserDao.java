package com.example.Fakeapp.dao.UserManager;

import com.example.Fakeapp.dao.bookManager.Book;

import java.util.ArrayList;

public interface UserDao {
//    public boolean oldCreate(String username, String password, String gmail);
    public boolean create(User user);

    public User findByUsername(String username);
    public User findById(Long id);

    
    public boolean exists(String username);
    public boolean existsByUsernameOrGmail(String username, String gmail);
    public boolean delete(String username);
    public boolean edit(String username, User user);

    public ArrayList<User> search(String query);
    public ArrayList<User> getAll();
}
