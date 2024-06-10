package ru.korneevdev.note.mock

import ru.korneevdev.note.TimeStampManager
import ru.korneevdev.note.entity.NoteTimeStamp

class TestTimeStampManager : TimeStampManager {

    var time = 0L

    override fun getCurrentTimeLong() = time

    override fun getCurrentTimeStamp() = NoteTimeStamp.FirstCreated(time)
}