package grey.example.catsfacts.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }

    @TypeConverter
    public static Date timestampToDate(Long timestamp) {
        return new Date(timestamp);
    }

}
