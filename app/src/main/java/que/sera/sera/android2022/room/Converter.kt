package que.sera.sera.android2022.room

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

class Converter {
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime) = dateTime.toEpochSecond(ZoneOffset.UTC)

    @TypeConverter
    fun longToLocalDateTime(dateTime: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(dateTime, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}