package ua.com.foxminded.fastestlaps;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
    
}
