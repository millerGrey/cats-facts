package grey.example.catsfacts.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import grey.example.catsfacts.model.Fact;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FactDao {

    @Query("SELECT * from factTable ORDER BY id DESC")
    Single<List<Fact>> getFacts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertFact(Fact fact);

    @Delete
    Completable deleteFact(Fact fact);
}
