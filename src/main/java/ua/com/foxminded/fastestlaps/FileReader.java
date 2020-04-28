package ua.com.foxminded.fastestlaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
    
    public Stream<String> parseFile(String fileName) throws ValidationDataException {
        try {
            return Files.lines(Paths.get(fileName));
        } catch (NoSuchFileException e) {
            throw new ValidationDataException("No such file " + fileName);
        } catch (IOException e) {
            throw new ValidationDataException("IOException in " + fileName);
        }
    }

}
