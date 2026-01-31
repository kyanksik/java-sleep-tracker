package ru.yandex.practicum.sleeptracker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SleepTrackerApp {

    public static final String SEPARATOR = ";";
    private static final String SESSIONS_FILE_NAME = "src/main/resources/sleep_log.txt";
    private static final DateTimeFormatter LOG_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    private final List<Function<List<SleepingSession>, SleepAnalysisResult>> ANALYTIC_FUNCTIONS = List.of(
            new SleepingSessionCounter(), new MinimalSession(), new MaxSession(), new AverageDuration(),
            new BadSessionCount(), new SleepLessNightSession(), new ChronotypeFunction());

    public static void main(String[] args) {

        SleepTrackerApp app = new SleepTrackerApp();
        try {
            List<SleepingSession> sessions = app.readFile(app.getFile());
            List<SleepAnalysisResult> results = app.analyzeSessions(sessions);
            results.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла");

        }
    }

    private File getFile() throws FileNotFoundException {
        Path filepath = Paths.get(SleepTrackerApp.SESSIONS_FILE_NAME);
        File file = filepath.toFile();
        if (!file.exists()) {
            throw new FileNotFoundException("Не существует такого файла");
        }
        return file;
    }


    private List<SleepAnalysisResult> analyzeSessions(List<SleepingSession> sessions) {
        return ANALYTIC_FUNCTIONS.stream()
                .map(function -> function.apply(sessions))
                .toList();
    }


    private List<SleepingSession> readFile(File file) {
        List<SleepingSession> sessions = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            sessions = bufferedReader.lines()
                    .map(this::parseLine)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
            if (sessions.isEmpty()) {
                System.out.println("Файл пустой" + file.getName());
            }

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла " + e.getMessage());
        }

        return sessions;
    }

    private Optional<SleepingSession> parseLine(String line) {
        try {
            String[] parts = line.split(SEPARATOR);
            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), LOG_TIME_FORMATTER);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), LOG_TIME_FORMATTER);
            SleepQuality sleepQuality = SleepQuality.valueOf(parts[2].trim().toUpperCase());

            if (start.isAfter(end)) {
                return Optional.empty();
            }

            return Optional.of(new SleepingSession(start, end, sleepQuality));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }


}









