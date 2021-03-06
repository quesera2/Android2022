package que.sera.sera.android2022.entity.room

import androidx.room.TypeConverter
import java.util.*

internal class Converter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}