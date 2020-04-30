package ua.com.foxminded.fastestlaps.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.stream.Stream;

import ua.com.foxminded.fastestlaps.exception.ValidationDataException;

public class FileReader {

    public Stream<String> parseFile(String fileName) {
        String pathToFile;
        
        try {
            pathToFile = getClass().getClassLoader().getResource(fileName).getPath();
        } catch (NullPointerException e) {
            throw new ValidationDataException("No such file " + fileName);
        }
        
        try {
            if (Files.lines(new File(pathToFile).toPath()).filter(""::equals).count() >= 1) {
                throw new ValidationDataException("Blank line in " + fileName);
            } else if (!Files.lines(new File(pathToFile).toPath()).allMatch(line -> line.matches("(.*)_(.*)"))) {
                throw new ValidationDataException("Missing delimiter in " + fileName);
            } else {
                return Files.lines(new File(pathToFile).toPath());
            }
        } catch (NoSuchFileException e) {
            throw new ValidationDataException("No such file " + fileName);
        } catch (IOException e) {
            throw new ValidationDataException("IOException in " + fileName);
        }
    }
}
