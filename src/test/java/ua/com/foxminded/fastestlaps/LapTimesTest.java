package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

public class LapTimesTest {

    LapTimes lapTimes = new LapTimes();
    String pathToTimeLogStart = "src\\test\\resources\\start.log";
    String pathToTimeLogEnd = "src\\test\\resources\\end.log";
    String pathToabbreviations = "src\\test\\resources\\abbreviations.txt";
    
    @Test
    void lapTimes_ThrowsException_IfEndLogWitoutLine() {
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutLine.log";

        assertThrows(NullPointerException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfAbbreviationsWithoutLine() {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutLine.txt";

        assertThrows(NullPointerException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ShouldReturnCertainString_IfInputIsThreeTeam() throws IOException, ParseException, ValidationDataException {

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ThrowsException_IfInStartLogDeletedTeam() throws IOException, ParseException {
        pathToTimeLogStart = "src\\test\\resources\\startWithoutTeam.log";

        assertThrows(NullPointerException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInStartLogDeletedTime() throws IOException, ParseException {
        pathToTimeLogStart = "src\\test\\resources\\startWithoutTime.log";

        assertThrows(ParseException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInStartLogDeletedDate() throws ValidationDataException, ParseException {
        pathToTimeLogStart = "src\\test\\resources\\startWithoutDate.log";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ThrowsException_IfInStartLogDeletedDelimiter() throws IOException, ParseException {
        pathToTimeLogStart = "src\\test\\resources\\startWithoutDelimiter.log";

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInEndLogDeletedTeam() throws IOException, ParseException {
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutTeam.log";

        assertThrows(NullPointerException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInEndLogDeletedTime() throws IOException, ParseException {
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutTime.log";

        assertThrows(ParseException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInEndLogDeletedDate() throws ValidationDataException, ParseException {
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutDate.log";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ThrowsException_IfInEndLogDeletedDelimiter() throws IOException, ParseException {
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutDelimiter.log";

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInaAbbreviationsDeletedTeam() throws ValidationDataException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutTeam.txt";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson |                      | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ThrowsException_IfInaAbbreviationsDeletedDriver() throws ValidationDataException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutDriver.txt";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3.                 | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ThrowsException_IfInaAbbreviationsDeletedAbbreviation() throws IOException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutAbbreviation.txt";

        assertThrows(NullPointerException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }

    @Test
    void lapTimes_ThrowsException_IfInAbbreviationsDeletedDelimiter() throws IOException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutDelimiter.txt";

        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations);
        });
    }
}
