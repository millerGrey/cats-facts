package grey.example.catsfacts.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import grey.example.catsfacts.model.Fact;

@Database(entities = Fact.class, version = 1)
public abstract class FactDatabase extends RoomDatabase {
    public abstract FactDao factDao();
}

