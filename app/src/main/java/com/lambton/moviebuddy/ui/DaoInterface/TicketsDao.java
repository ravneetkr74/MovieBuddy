package com.lambton.moviebuddy.ui.DaoInterface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lambton.moviebuddy.ui.Model.Tickets;

import java.util.List;

@Dao
public interface TicketsDao {
    @Query("SELECT * FROM Tickets")
    List<Tickets> getAll();

    @Insert
    void insert(Tickets tickets);

    @Update
    void update(Tickets repos);

    @Delete
    void delete(Tickets tickets);


    @Delete
    void delete(Tickets... tickets);
}
