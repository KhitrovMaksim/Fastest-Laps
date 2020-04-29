package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

public class FileReaderTest {
    FileReader fileReader = new FileReader();
    
    @Test
    void fileReader_ThrowsValidationException_IfStartLogFileIsNotFound() {
        String pathToTimeLogStart = "start.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        });
    }
    
    @Test
    void fileReader_ThrowsValidationException_IfEndLogFileIsNotFound() {
        String pathToTimeLogEnd = "end.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        });
    }
    
    @Test
    void fileReader_ThrowsValidationException_IfAbbreviationFilesIsNotFound() {
        String pathToabbreviations = "abbreviations.txt";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        });
    }
    
    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInAbbreviationsBlankLine() throws ValidationDataException, ParseException {
        String pathToabbreviations = "src\\test\\resources\\abbreviationsWithBlankLine.txt";
        String message = "Blank line in " + pathToabbreviations;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        }, message);
    }
    
    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInStartLogBlankLine() throws ValidationDataException, ParseException {
        String pathToTimeLogStart = "src\\test\\resources\\startWithBlankLine.log";
        String message = "Blank line in " + pathToTimeLogStart;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        }, message);
    }
    
    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInEndLogBlankLine() throws ValidationDataException, ParseException {
        String pathToTimeLogEnd = "src\\test\\resources\\endWithBlankLine.log";
        String message = "Blank line in " + pathToTimeLogEnd;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        }, message);
    }

    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInEndLogDeletedDelimiter() throws IOException, ParseException {
        String pathToTimeLogEnd = "src\\test\\resources\\endWithoutDelimiter.log";
        String message = "Missing delimiter in " + pathToTimeLogEnd;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        }, message);
    }

    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInStartLogDeletedDelimiter() throws IOException, ParseException {
        String pathToTimeLogStart = "src\\test\\resources\\startWithoutDelimiter.log";
        String message = "Missing delimiter in " + pathToTimeLogStart;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        }, message);
    }
    
    @Test
    void fileReader_ThrowsValidationExceptionWithMessage_IfInAbbreviationsDeletedTwoDelimiters() throws IOException, ParseException {
        String pathToabbreviations = "src\\test\\resources\\abbreviationsWithoutTwoDelimiters.txt";
        String message = "Missing delimiter in " + pathToabbreviations;
        
        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        }, message);
    }
}
