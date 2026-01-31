package ru.yandex.practicum.sleeptracker;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class MinimalSession implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String TITLE = "Минимальная сессия сна в минутах: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        Long minMinutes = sleepingSessions.stream()
                .map(session -> ChronoUnit.MINUTES.between(session.start(), session.end()))
                .min(Long::compareTo)
                .orElse(0L);
        return new SleepAnalysisResult(TITLE, minMinutes);
    }
}
