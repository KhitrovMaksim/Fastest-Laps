package ua.com.foxminded.fastestlaps;

import java.io.IOException;
import java.text.ParseException;

public class Runner {

    public static void main(String[] args) throws ValidationDataException, ParseException, IOException {

        
        String pathToTimeLogStart = "start.log";
        String pathToTimeLogEnd = "end.log";
        String pathToAbbreviations = "abbreviations.txt";
        LapTimes lapTimes = new LapTimes();

        System.out.println(lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToAbbreviations));

    }

}
