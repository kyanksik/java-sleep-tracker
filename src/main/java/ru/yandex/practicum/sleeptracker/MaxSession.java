package ru.yandex.practicum.sleeptracker;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;


public class MaxSession implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String TITLE = "Максимальная сессия сна в минутах: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        Long maxMinutes = sleepingSessions.stream()
                .map(session -> ChronoUnit.MINUTES.between(session.start(), session.end()))
                .max(Long::compareTo)
                .orElse(0L);

        return new SleepAnalysisResult(TITLE, maxMinutes);
    }
}
