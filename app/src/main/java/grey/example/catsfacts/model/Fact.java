package grey.example.catsfacts.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import grey.example.catsfacts.database.DateConverter;

@Entity(tableName = "factTable")
public class Fact {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long mId;
    @ColumnInfo(name = "text")
    String mText = "";
    @ColumnInfo(name = "path")
    String mImagePath = "";
    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    Date mDate;

    public Fact(String text, String imagePath) {
        mText = text;
        mImagePath = imagePath;
        mDate = new Date();
    }

    public long getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public Date getDate() {
        return mDate;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setText(String text) {
        mText = text;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
