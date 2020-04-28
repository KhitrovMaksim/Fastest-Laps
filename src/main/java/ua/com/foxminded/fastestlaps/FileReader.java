package ua.com.foxminded.fastestlaps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {
    
    public Stream<String> parseFile(String fileName) throws IOException {
        return Files.lines(Paths.get(fileName));
    }

}
