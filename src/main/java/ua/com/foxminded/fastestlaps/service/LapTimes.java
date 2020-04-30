package ua.com.foxminded.fastestlaps.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.com.foxminded.fastestlaps.exception.ValidationDataException;

public class LapTimes {
    private static String INDENT = " ";
    private static String DASH = "-";
    private static String LINE_END = "\n";
    private static String UNDERSCORE = "_";
    private static String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static String EXCEPT_DATE_PATTERN = "[A-Z]|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})";
    private static String EXCEPT_ABBREVIATION_LOG_PATTERN = "(\\d{4}-\\d{2}-\\d{2})|(_\\d{2}:\\d{2}:\\d{2}.\\d{3})";
    private static String EXCEPT_ABBREVIATION_ABBREVIATIONS_PATTERN = "\\_.*$";
    private FileReader fileReader = new FileReader();

    public String showReport(String pathToTimeLogStart, String pathToTimeLogEnd, String pathToAbbreviations)
            throws ParseException {

        validateData(pathToTimeLogStart, pathToTimeLogEnd, pathToAbbreviations);

        Map<String, String> timeLogEnd = parseTimeLog(pathToTimeLogEnd);
        Map<String, String> timeLogStart = parseTimeLog(pathToTimeLogStart);
        Map<String, String[]> abbreviations = parseAbbreviations(pathToAbbreviations);
        Map<String, String[]> abbreviationsWithIndents = composeIndentAbbreviations(abbreviations);
        Map<String, String> lapTime = composeLapTime(timeLogStart, timeLogEnd);

        return composeResult(abbreviationsWithIndents, lapTime);
    }

    private Map<String, String> parseTimeLog(String fileName) {

        return fileReader.parseFile(fileName).map(s -> s.replaceAll(DATE_PATTERN, "")).map(s -> s.split(UNDERSCORE, 2))
                .collect(Collectors.toMap(a -> a[0], a -> a[1]));
    }

    private Map<String, String[]> parseAbbreviations(String fileName) {

        return fileReader.parseFile(fileName).map(s -> s.split(UNDERSCORE, 3))
                .collect(Collectors.toMap(a -> a[0], a -> a));
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
        StringBuilder result = new StringBuilder("");
        int i = 1;
        int delimiterLine = 16;
        int indentedNumbers = 10;
        String[] description;

        for (Map.Entry<String, String> entry : lapTime.entrySet()) {
            if (i == delimiterLine) {
                result.append(calculateIndentation(DASH, calculateLengthOfDelimiter(abbreviations))).append(LINE_END);
            }

            if (i < indentedNumbers) {
                result.append(INDENT);
            }

            description = abbreviations.get(entry.getKey());

            result.append(i).append(". ").append(description[1]).append(" | ").append(description[2]).append(" | ")
                    .append(entry.getValue()).append(LINE_END);

            i++;
        }
        return result.toString();
    }

    private int calculateLengthOfDelimiter(Map<String, String[]> abbreviations2) {
        Map.Entry<String, String[]> entry = abbreviations2.entrySet().iterator().next();
        String[] value = entry.getValue();

        return value[1].length() + value[2].length() + 19;
    }

    private void validateData(String pathToTimeLogStart, String pathToTimeLogEnd, String pathToAbbreviations) {
        Supplier<Stream<String>> startLines = () -> fileReader.parseFile(pathToTimeLogStart);
        Supplier<Stream<String>> endLines = () -> fileReader.parseFile(pathToTimeLogEnd);
        Supplier<Stream<String>> abbreviationsLines = () -> fileReader.parseFile(pathToAbbreviations);

        validateFilesLenght(startLines.get(), endLines.get(), abbreviationsLines.get());
        validateDatesInLogFile(startLines.get());
        validateDatesInLogFile(endLines.get());
        validateDatesInLogs(startLines.get(), endLines.get());
        validateTimeLogAbbreviationLength(startLines.get());
        validateTimeLogAbbreviationLength(endLines.get());
        validateTimeLogDates(startLines.get());
        validateTimeLogDates(endLines.get());
        validateAbbreviationInAbbreviations(abbreviationsLines.get());
        validateAbbreviationsInFiles(startLines.get(), endLines.get(), abbreviationsLines.get());
        validateSecondDelimiterInAbbreviations(abbreviationsLines.get());
    }

    private void validateFilesLenght(Stream<String> startLines, Stream<String> endLines,
            Stream<String> abbreviationsLines) {
        int startLogLength = (int) startLines.count();
        int endLogLength = (int) endLines.count();
        int abbreviationsLength = (int) abbreviationsLines.count();

        if (startLogLength != endLogLength || startLogLength != abbreviationsLength) {
            throw new ValidationDataException("The number of lines in all files should be the same");
        }
    }

    private void validateDatesInLogFile(Stream<String> logFileLines) {
        if (!logFileLines.allMatch(line -> line.matches("(.*)" + DATE_PATTERN + "(.*)"))) {
            throw new ValidationDataException("Dates in end.log and start.log files do not match");
        }
    }

    private void validateDatesInLogs(Stream<String> startLines, Stream<String> endLines) {
        List<String> startLog = startLines.map(s -> s.replaceAll(EXCEPT_DATE_PATTERN, "")).collect(Collectors.toList());
        List<String> endLog = endLines.map(s -> s.replaceAll(EXCEPT_DATE_PATTERN, "")).collect(Collectors.toList());
        int i = 0;

        for (String line : startLog) {
            if (line.equals(endLog.get(i))) {
                i++;
            } else {
                throw new ValidationDataException("Dates in end.log and start.log files do not match");
            }
        }

    }

    private void validateAbbreviationsInFiles(Stream<String> startLines, Stream<String> endLines,
            Stream<String> abbreviationsLines) {
        List<String> abbreviationsFromStartLog = startLines
                .map(line -> line.replaceAll(EXCEPT_ABBREVIATION_LOG_PATTERN, "")).collect(Collectors.toList());
        List<String> abbreviationsFromEndLog = endLines
                .map(line -> line.replaceAll(EXCEPT_ABBREVIATION_LOG_PATTERN, "")).collect(Collectors.toList());
        List<String> abbreviationsFromTxt = abbreviationsLines
                .map(line -> line.replaceAll(EXCEPT_ABBREVIATION_ABBREVIATIONS_PATTERN, ""))
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

    private void validateTimeLogAbbreviationLength(Stream<String> logFileLines) {
        List<String> timeLog = logFileLines.map(s -> s.replaceAll(EXCEPT_ABBREVIATION_LOG_PATTERN, ""))
                .collect(Collectors.toList());

        for (String line : timeLog) {
            if (line.length() != 3) {
                throw new ValidationDataException("There is an error in the abbreviation in the log file");
            }
        }
    }

    private void validateTimeLogDates(Stream<String> logFileLines) {
        Map<String, String> timeLog = logFileLines.map(s -> s.replaceAll(DATE_PATTERN, ""))
                .map(s -> s.split(UNDERSCORE, 2)).collect(Collectors.toMap(a -> a[0], a -> a[1]));

        for (Map.Entry<String, String> entry : timeLog.entrySet()) {
            if (!entry.getValue().matches("\\d{2}:\\d{2}:\\d{2}.\\d{3}")) {
                throw new ValidationDataException("There is an error in the date in the log file");
            }
        }
    }
    
    private void validateAbbreviationInAbbreviations(Stream<String> abbreviationsLines) {
        List<String> parseAbbreviations = abbreviationsLines
                .map(line -> line.replaceAll(EXCEPT_ABBREVIATION_ABBREVIATIONS_PATTERN, ""))
                .collect(Collectors.toList());

        for (String line : parseAbbreviations) {
            if (line.length() != 3) {
                throw new ValidationDataException("There is an error in the abbreviation in the abbreviations.txt file");
            }
        }
    }

    private void validateSecondDelimiterInAbbreviations(Stream<String> abbreviationsLines) {
        Map<String, String[]> abbreviations = abbreviationsLines.map(s -> s.split(UNDERSCORE, 3))
                .collect(Collectors.toMap(a -> a[0], a -> a));
        String[] description;

        for (Map.Entry<String, String[]> entry : abbreviations.entrySet()) {
            description = abbreviations.get(entry.getKey());

            if (description.length < 3) {
                throw new ValidationDataException(
                        "Missing second delimiter in the abbreviations.txt file");
            }
        }
    }
}
