package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.junit.jupiter.api.Test;

public class LapTimesTest {

    LapTimes lapTimes = new LapTimes();
    String pathToTimeLogStart = "src\\test\\resources\\start.log";
    String pathToTimeLogEnd = "src\\test\\resources\\end.log";
    String pathToabbreviations = "src\\test\\resources\\abbreviations.txt";
    
    @Test
    void lapTimes_ExpectedMessageAboutLength_IfStartLogWitoutLine() throws ValidationDataException, ParseException {
        String expected = "The number of lines in the files should be the same";
        pathToTimeLogEnd = "src\\test\\resources\\startWithoutLine.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ExpectedMessageAboutLength_IfEndLogWitoutLine() throws ValidationDataException, ParseException {
        String expected = "The number of lines in the files should be the same";
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutLine.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ExpectedMessageAboutLength_IfAbbreviationsWithoutLine() throws ValidationDataException, ParseException {
        String expected = "The number of lines in the files should be the same";
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutLine.txt";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnCertainString_IfInputIsThreeTeam() throws ParseException, ValidationDataException {

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3. Marcus Ericsson | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInStartLogDeletedTeam() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the start.log file";
        pathToTimeLogStart = "src\\test\\resources\\startWithoutTeam.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInStartLogWrongAbbreviationLength() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the start.log file";
        pathToTimeLogStart = "src\\test\\resources\\startWrongAbbreviationLength.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInAbbreviationsDeletedAbbreviation() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutAbbreviation.txt";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInAbbreviationsWrongAbbreviationLength() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the abbreviations.txt file";
        pathToabbreviations = "src\\test\\resources\\abbreviationsWrongAbbreviationLength.txt";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInEndLogDeletedTeam() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the end.log file";
        pathToTimeLogEnd= "src\\test\\resources\\endWithoutTeam.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInEndLogWrongAbbreviationLength() throws ValidationDataException, ParseException {
        String expected = "There is an error in the abbreviation in the end.log file";
        pathToTimeLogEnd = "src\\test\\resources\\endWrongAbbreviationLength.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInStartLogDeletedDate() throws ValidationDataException, ParseException {
        String expected = "Dates in end.log and start.log files do not match";
        pathToTimeLogStart = "src\\test\\resources\\startWithoutDate.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInAbbreviationsDeletedFirstDelimiter() throws ValidationDataException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutFirstDelimiter.txt";
        String expected = "There is an error in the abbreviation in the abbreviations.txt file";
        
        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInAbbreviationsDeletedSecondDelimiter() throws ValidationDataException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutSecondDelimiter.txt";
        String expected = "There is an error in the abbreviation in the abbreviations.txt file";
        
        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }

    @Test
    void lapTimes_ShouldReturnCertainString_IfInaAbbreviationsriver() throws ValidationDataException, ParseException {
        pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutDriver.txt";

        String expected = " 1. Sergio Perez    | FORCE INDIA MERCEDES | 01:12.848\n"
                + " 2. Romain Grosjean | HAAS FERRARI         | 01:12.930\n"
                + " 3.                 | SAUBER FERRARI       | 01:13.265\n";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInStartLogDeletedTime() throws ValidationDataException, ParseException {
        String expected = "Dates in end.log and start.log files do not match";
        pathToTimeLogStart = "src\\test\\resources\\startWithoutTime.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInEndLogDeletedTime() throws ValidationDataException, ParseException {
        String expected = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "src\\test\\resources\\endWithoutTime.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInLogsWrongDate() throws ValidationDataException, ParseException {
        String expected = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "src\\test\\resources\\endWithWrongDate.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
    
    @Test
    void lapTimes_ShouldReturnErrorMessage_IfInLogsWrongTime() throws ValidationDataException, ParseException {
        String expected = "Dates in end.log and start.log files do not match";
        pathToTimeLogEnd = "src\\test\\resources\\endWithWrongTime.log";

        assertEquals(expected, lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToabbreviations));
    }
}
