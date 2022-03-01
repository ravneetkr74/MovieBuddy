package com.lambton.moviebuddy.ui.DaoInterface;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lambton.moviebuddy.ui.Model.Tickets;
import com.lambton.moviebuddy.ui.Model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert
    void insert(User user);

    @Update
    void update(User repos);
}
