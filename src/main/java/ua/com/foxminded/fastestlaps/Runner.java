package ua.com.foxminded.fastestlaps;

import java.text.ParseException;

public class Runner {

    public static void main(String[] args) throws ValidationDataException, ParseException {
        String pathToTimeLogStart = "src\\main\\resources\\start.log";
        String pathToTimeLogEnd = "src\\main\\resources\\end.log";
        String pathToAbbreviations = "src\\main\\resources\\abbreviations.txt";
        LapTimes lapTimes = new LapTimes();
        
        System.out.println(lapTimes.showReport(pathToTimeLogStart, pathToTimeLogEnd, pathToAbbreviations));

    }

}
