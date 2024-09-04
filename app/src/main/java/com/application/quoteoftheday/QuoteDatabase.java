package com.application.quoteoftheday;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {QuoteEntity.class}, version = 1)
public abstract class QuoteDatabase extends RoomDatabase {
    public abstract QuoteDao quoteDao();
}
