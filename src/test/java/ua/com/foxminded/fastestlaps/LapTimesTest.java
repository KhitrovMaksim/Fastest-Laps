package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ExpectedTheSameMessageAboutAmountOfLines_IfEndLogWitoutLine() {
        String errorMessage = "The number of lines in all files should be the same";
        pathToTimeLogEnd = "endWithoutLine.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ExpectedTheSameMessageAboutAmountOfLines_IfAbbreviationsWithoutLine() {
        String errorMessage = "The number of lines in all files should be the same";
        pathToabbreviations = "abbreviationsWithoutLine.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
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

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogStart = "startWrongAbbreviationLength.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutAbbreviation.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWrongAbbreviationLength.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogDeletedTeam() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogEnd = "endWithoutTeam.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogWrongAbbreviationLength() {
        String errorMessage = "There is an error in the abbreviation in the log file";
        pathToTimeLogEnd = "endWrongAbbreviationLength.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogDeletedDate() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogStart = "startWithoutDate.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedFirstDelimiter() {
        String errorMessage = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutFirstDelimiter.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsDeletedSecondDelimiter()
            throws ValidationDataException, ParseException {
        String errorMessage = "Missing second delimiter in the abbreviations.txt file";
        pathToabbreviations = "abbreviationsWithoutSecondDelimiter.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
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

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogDeletedTime() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithoutTime.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInLogsWrongDate() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithWrongDate.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInLogsWrongTime() {
        String errorMessage = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "endWithWrongTime.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInEndLogWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToTimeLogEnd = "endWithWrongAbbreviation.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInStartLogWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToTimeLogStart = "startWithWrongAbbreviation.log";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }

    @Test
    void lapTimes_ShouldReturnTheSameErrorMessage_IfInAbbreviationsWrongAbbreviation() {
        String errorMessage = "There is an error in the abbreviation in files";
        pathToabbreviations = "abbreviationsWithWrongAbbreviation.txt";

        assertThrows(ValidationDataException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        }, errorMessage);
    }
}
