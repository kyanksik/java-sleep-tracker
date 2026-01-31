package ru.yandex.practicum.sleeptracker;


import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SleepLessNightSession implements Function<List<SleepingSession>, SleepAnalysisResult> {


    private static final String TITLE = "Количество бессонных ночей:";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(TITLE, 0L);
        }


        LocalDateTime firstStart = sessions.stream()
                .map(SleepingSession::start)
                .min(Comparator.naturalOrder()).orElseThrow();
        LocalDateTime lastEnd = sessions.stream()
                .map(SleepingSession::end)
                .max(Comparator.naturalOrder()).orElseThrow();


        LocalDate startDate = firstStart.toLocalDate();
        if (firstStart.toLocalTime().isAfter(LocalTime.NOON)) {
            startDate = startDate.plusDays(1);
        } else {
            startDate = startDate.minusDays(1);
        }
        LocalDate sDate = startDate;
        LocalDate endDate = lastEnd.toLocalDate();


        long totalNights = ChronoUnit.DAYS.between(startDate, endDate);
        if (totalNights <= 0) return new SleepAnalysisResult(TITLE, 0L);


        Set<LocalDate> nightsWithSleep = sessions.stream()
                .map(this::getNightsCoveredBySession)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());


        long sleptNightsInPeriod = nightsWithSleep.stream()
                .filter(date -> !date.isBefore(sDate) && date.isBefore(endDate))
                .count();

        long sleeplessNights = totalNights - sleptNightsInPeriod;

        return new SleepAnalysisResult(TITLE, Math.max(0, sleeplessNights));
    }

    private Set<LocalDate> getNightsCoveredBySession(SleepingSession session) {
        LocalDateTime start = session.start();
        LocalDateTime end = session.end();

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        Set<LocalDate> covered = new HashSet<>();


        if (start.isBefore(startDate.atTime(6, 0))) {
            covered.add(startDate);
        }


        if (!startDate.equals(endDate) && end.isAfter(endDate.atStartOfDay())) {
            covered.add(endDate);
        }

        return covered;
    }
}

