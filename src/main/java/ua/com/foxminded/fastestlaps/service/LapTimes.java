package ua.com.foxminded.fastestlaps.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ua.com.foxminded.fastestlaps.exception.ValidationDataException;

public class LapTimes {
    private static String INDENT = " ";
    private static String DASH = "-";
    private static String LINE_END = "\n";
    private FileReader fileReader = new FileReader();

    public String showReport(String pathToTimeLogStart, String pathToTimeLogEnd, String pathToAbbreviations)
            throws ParseException, IOException {

        validateFilesLenght(pathToTimeLogStart, pathToTimeLogEnd, pathToAbbreviations);
        validateDates(pathToTimeLogStart, pathToTimeLogEnd);
        validateAbbreviationsInFiles(pathToTimeLogStart, pathToTimeLogEnd, pathToAbbreviations);

        Map<String, String> timeLogEnd = parseTimeLog(pathToTimeLogEnd);

        validateTimeLog(timeLogEnd);

        Map<String, String> timeLogStart = parseTimeLog(pathToTimeLogStart);

        validateTimeLog(timeLogStart);

        Map<String, String[]> abbreviations = parseAbbreviations(pathToAbbreviations);

        validateAbbreviationInAbbreviations(abbreviations);
        validateSecondDelimiterInAbbreviations(abbreviations);

        Map<String, String[]> abbreviationsWithIndents = composeIndentAbbreviations(abbreviations);
        Map<String, String> lapTime = composeLapTime(timeLogStart, timeLogEnd);

        return composeResult(abbreviationsWithIndents, lapTime);
    }

    private void validateFilesLenght(String pathToTimeLogStart, String pathToTimeLogEnd, String pathToAbbreviations) {
        int startLogLength = (int) fileReader.parseFile(pathToTimeLogStart).count();
        int endLogLength = (int) fileReader.parseFile(pathToTimeLogEnd).count();
        int abbreviationsLength = (int) fileReader.parseFile(pathToAbbreviations).count();

        if (startLogLength != endLogLength && startLogLength != abbreviationsLength) {
            throw new ValidationDataException("The number of lines in the files should be the same");
        }
    }

    private Map<String, String> parseTimeLog(String fileName) {

        return fileReader.parseFile(fileName).map(s -> s.replaceAll("\\d{4}-\\d{2}-\\d{2}", ""))
                .map(s -> s.split("_", 2)).collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    private Map<String, String[]> parseAbbreviations(String fileName) throws ValidationDataException, IOException {

        return fileReader.parseFile(fileName).map(s -> s.split("_", 3)).collect(Collectors.toMap(a -> a[0], a -> a));
    }

    private Map<String, String[]> composeIndentAbbreviations(Map<String, String[]> abbreviations) {
        int lengthOfDriverName = 0;
        int lengthOfTeamName = 0;
        String[] description;

        for (Map.Entry<String, String[]> entry : abbreviations.entrySet()) {
            description = abbreviations.get(entry.getKey());

            if (lengthOfDriverName < description[1].length()) {
                lengthOfDriverName = description[1].length();
            }

            if (lengthOfTeamName < description[2].length()) {
                lengthOfTeamName = description[2].length();
            }

        }

        for (Map.Entry<String, String[]> entry : abbreviations.entrySet()) {
            description = abbreviations.get(entry.getKey());

            if (description[1].length() < lengthOfDriverName) {
                description[1] = description[1]
                        + calculateIndentation(INDENT, lengthOfDriverName - description[1].length());
            }

            if (description[2].length() < lengthOfTeamName) {
                description[2] = description[2]
                        + calculateIndentation(INDENT, lengthOfTeamName - description[2].length());
            }

            entry.setValue(description);
        }

        return abbreviations;
    }

    private String calculateIndentation(String indent, int requiredLength) {
        StringBuilder indentation = new StringBuilder("");
        for (int i = 0; i < requiredLength; i++) {
            indentation.append(indent);
        }

        return indentation.toString();
    }

    private Map<String, String> composeLapTime(Map<String, String> startLog, Map<String, String> endLog)
            throws ParseException {
        Map<String, String> lapTime = new HashMap<>();

        for (Map.Entry<String, String> entry : startLog.entrySet()) {
            lapTime.put(entry.getKey(), calculateLapTime(entry.getValue(), endLog.get(entry.getKey())));
        }

        return lapTime.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors
                .toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    private String calculateLapTime(String startTime, String endTime) throws ParseException {
        SimpleDateFormat timeFormatInput = new SimpleDateFormat("HH:mm:ss.SSS");
        SimpleDateFormat timeFormatOutput = new SimpleDateFormat("mm:ss.SSS");
        Date startTimeFomated = timeFormatInput.parse(startTime);
        Date endTimeFomated = timeFormatInput.parse(endTime);
        Date lapTime = new Date(endTimeFomated.getTime() - startTimeFomated.getTime());

        return timeFormatOutput.format(lapTime);
    }

    private String composeResult(Map<String, String[]> abbreviations, Map<String, String> lapTime) {
        String result = "";
        int i = 1;
        int delimiterLine = 16;
        int indentedNumbers = 10;
        String[] description;

        for (Map.Entry<String, String> entry : lapTime.entrySet()) {
            if (i == delimiterLine) {
                result += calculateIndentation(DASH, calculateLengthOfDelimiter(abbreviations)) + LINE_END;
            }

            if (i < indentedNumbers) {
                result += " ";
            }

            description = abbreviations.get(entry.getKey());

            result += i + ". " + description[1] + " | " + description[2] + " | " + entry.getValue() + LINE_END;

            i++;
        }
        return result;
    }

    private int calculateLengthOfDelimiter(Map<String, String[]> abbreviations2) {
        Map.Entry<String, String[]> entry = abbreviations2.entrySet().iterator().next();
        String[] value = entry.getValue();

        return value[1].length() + value[2].length() + 19;
    }

    private void validateTimeLog(Map<String, String> timeLog) {
        for (Map.Entry<String, String> entry : timeLog.entrySet()) {
            if (entry.getKey().length() != 3) {
                throw new ValidationDataException("There is an error in the abbreviation in the end.log file");
            } else if (!entry.getValue().matches("\\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
                throw new ValidationDataException("There is an error in the abbreviation in the end.log file");
            }
        }
    }

    private void validateAbbreviationInAbbreviations(Map<String, String[]> abbreviations) {
        for (Map.Entry<String, String[]> entry : abbreviations.entrySet()) {
            if (entry.getKey().length() != 3) {
                throw new ValidationDataException(
                        "There is an error in the abbreviation in the abbreviations.txt file");

            }
        }

    }

    private void validateSecondDelimiterInAbbreviations(Map<String, String[]> abbreviations) {
        String[] description;

        for (Map.Entry<String, String[]> entry : abbreviations.entrySet()) {
            description = abbreviations.get(entry.getKey());

            if (description.length < 3) {
                throw new ValidationDataException(
                        "There is an error in the abbreviation in the abbreviations.txt file");
            }

        }
    }

    private void validateDates(String pathToTimeLogStart, String pathToTimeLogEnd) {
        List<String> startLog;
        List<String> endLog;

        if (!fileReader.parseFile(pathToTimeLogStart).allMatch(line -> line.matches("(.*)\\d{4}-\\d{2}-\\d{2}(.*)"))) {
            throw new ValidationDataException("Dates in end.log and start.log files do not match");
        } else if (!fileReader.parseFile(pathToTimeLogEnd)
                .allMatch(line -> line.matches("(.*)\\d{4}-\\d{2}-\\d{2}(.*)"))) {
            throw new ValidationDataException("Dates in end.log and start.log files do not match");
        } else {
            startLog = fileReader.parseFile(pathToTimeLogStart)
                    .map(s -> s.replaceAll("[A-Z]|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})", "")).collect(Collectors.toList());
            endLog = fileReader.parseFile(pathToTimeLogEnd)
                    .map(s -> s.replaceAll("[A-Z]|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})", "")).collect(Collectors.toList());
            int i = 0;
            for (String line : startLog) {
                if (line.equals(endLog.get(i))) {
                    i++;
                } else {
                    throw new ValidationDataException("Dates in end.log and start.log files do not match");
                }
            }
        }
    }

    private void validateAbbreviationsInFiles(String pathToTimeLogStart, String pathToTimeLogEnd,
            String pathToAbbreviations) {
        List<String> abbreviationsFromStartLog;
        List<String> abbreviationsFromEndLog;
        List<String> abbreviationsFromTxt;

        abbreviationsFromStartLog = fileReader.parseFile(pathToTimeLogStart)
                .map(line -> line.replaceAll("(\\d{4}-\\d{2}-\\d{2})|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})", ""))
                .collect(Collectors.toList());
        abbreviationsFromEndLog = fileReader.parseFile(pathToTimeLogEnd)
                .map(line -> line.replaceAll("(\\d{4}-\\d{2}-\\d{2})|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})", ""))
                .collect(Collectors.toList());
        abbreviationsFromTxt = fileReader.parseFile(pathToAbbreviations).map(line -> line.replaceAll("\\_.*$", ""))
                .collect(Collectors.toList());
        Collections.sort(abbreviationsFromStartLog);
        Collections.sort(abbreviationsFromEndLog);
        Collections.sort(abbreviationsFromTxt);

        int i = 0;
        for (String line : abbreviationsFromTxt) {
            if ((line.equals(abbreviationsFromStartLog.get(i))) && (line.equals(abbreviationsFromEndLog.get(i)))) {
                i++;
            } else {
                throw new ValidationDataException("There is an error in the abbreviation in files");
            }
        }
    }
}
