package ru.korneevdev.note.test_utils

import ru.korneevdev.note.utils.TimeStampManager
import ru.korneevdev.note.entity.NoteTimeStamp

class TestTimeStampManager : TimeStampManager {

    var time = 0L

    override fun getCurrentTimeLong() = time

    override fun getCurrentTimeStamp() = NoteTimeStamp.FirstCreated(time)

}