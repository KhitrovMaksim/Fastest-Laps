package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ua.com.foxminded.fastestlaps.exception.ValidationDataException;
import ua.com.foxminded.fastestlaps.service.FileReader;

public class FileReaderTest {
    FileReader fileReader = new FileReader();

    @Test
    void fileReader_ThrowsValidationException_IfStartLogFileIsNotFound() {
        String pathToTimeLogStart = "startNotExist.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfEndLogFileIsNotFound() {
        String pathToTimeLogEnd = "endNotExist.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfAbbreviationFilesIsNotFound() {
        String pathToabbreviations = "abbreviationsNotExist.txt";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInAbbreviationsBlankLine() {
        String pathToabbreviations = "abbreviationsWithBlankLine.txt";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInStartLogBlankLine() {
        String pathToTimeLogStart = "startWithBlankLine.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInEndLogBlankLine() {
        String pathToTimeLogEnd = "endWithBlankLine.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInEndLogDeletedDelimiter() {
        String pathToTimeLogEnd = "endWithoutDelimiter.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogEnd);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInStartLogDeletedDelimiter() {
        String pathToTimeLogStart = "startWithoutDelimiter.log";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToTimeLogStart);
        });
    }

    @Test
    void fileReader_ThrowsValidationException_IfInAbbreviationsDeletedTwoDelimiters() {
        String pathToabbreviations = "abbreviationsWithoutTwoDelimiters.txt";

        assertThrows(ValidationDataException.class, () -> {
            fileReader.parseFile(pathToabbreviations);
        });
    }
}
