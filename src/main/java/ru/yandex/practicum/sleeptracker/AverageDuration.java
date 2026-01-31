package ru.yandex.practicum.sleeptracker;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class AverageDuration implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private static final String TITLE = "Средняя длительность сессии сна в минутах: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessionList) {
        double result = sleepingSessionList.stream()
                .mapToLong(session -> ChronoUnit.MINUTES.between(session.start(), session.end()))
                .average().orElse(0);
        return new SleepAnalysisResult(TITLE, result);
    }
}
