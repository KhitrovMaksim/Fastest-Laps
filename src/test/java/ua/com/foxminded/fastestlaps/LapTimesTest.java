package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.fastestlaps.exception.ValidationDataException;
import ua.com.foxminded.fastestlaps.service.LapTimes;

public class LapTimesTest {

    LapTimes lapTimes = new LapTimes();
    String pathToTimeLogStart = "start.log";
    String pathToTimeLogEnd = "end.log";
    String pathToabbreviations = "abbreviations.txt";

    @Test
    void lapTimes_ExpectedTheSameMessageAboutAmountOfLines_IfStartLogWitoutLine() {
        String errorMessage = "The number of lines in all files should be the same";
        pathToTimeLogStart = "startWithoutLine.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ExpectedTheSameMessageAboutAmountOfLines_IfEndLogWitoutLine() {
        String errorMessage = "The number of lines in all files should be the same";
        pathToTimeLogEnd = "endWithoutLine.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ExpectedTheSameMessageAboutAmountOfLines_IfAbbreviationsWithoutLine() {
        String errorMessage = "The number of lines in all files should be the same";
        pathToabbreviations = "abbreviationsWithoutLine.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnCertainString_IfInputIsThreeTeam() throws ParseException {

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogDeletedTeam() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogStart = "startWithoutTeam.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogStart = "startWrongAbbreviationLength.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutAbbreviation.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWrongAbbreviationLength.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogDeletedTeam() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogEnd = "endWithoutTeam.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogEnd = "endWrongAbbreviationLength.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogDeletedDate() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogStart = "startWithoutDate.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedFirstDelimiter() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutFirstDelimiter.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedSecondDelimiter()
            throws ValidationDataException, ParseException {
        String errorMessage = "Missing second delimiter in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutSecondDelimiter.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnCertainString_IfInaAbbreviationsriver() throws ValidationDataException, ParseException {
        pathToabbreviations = "abbreviationsWithoutDriver.txt";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3.                 | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogDeletedTime() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogStart = "startWithoutTime.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogDeletedTime() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithoutTime.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInLogsWrongDate() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithWrongDate.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInLogsWrongTime() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithWrongTime.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToTimeLogEnd = "endWithWrongAbbreviation.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToTimeLogStart = "startWithWrongAbbreviation.log";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToabbreviations = "abbreviationsWithWrongAbbreviation.txt";

        Exception exception = assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });

        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
