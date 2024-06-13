package ru.korneevdev.entity.test_utils

import ru.korneevdev.entity.utils.TimeStampManager

class TestTimeStampManager : TimeStampManager {

    var time = 0L

    override fun getCurrentTimeLong() = time

    override fun getCurrentTimeStamp() = ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(time)

}