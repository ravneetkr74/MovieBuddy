package com.lambton.moviebuddy.ui;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.lambton.moviebuddy.ui.DaoInterface.TicketsDao;
import com.lambton.moviebuddy.ui.DaoInterface.UserDao;
import com.lambton.moviebuddy.ui.Model.Tickets;
import com.lambton.moviebuddy.ui.Model.User;

@Database(entities = {Tickets.class, User.class},version = 2)
public abstract class DaoHelper extends RoomDatabase {
    public abstract TicketsDao getTicketInterface();
    public abstract UserDao getUserInterface();

    private static DaoHelper db;
    public static DaoHelper getInstance(Context context) {
        if (null == db) {
            db = buildDatabaseInstance(context);
        }
        return db;
    }
    private static DaoHelper buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                DaoHelper.class,
                "TicketsDB")
                .allowMainThreadQueries().build();
    }

}
