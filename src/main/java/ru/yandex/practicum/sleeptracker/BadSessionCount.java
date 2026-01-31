package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSessionCount implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String TITLE = "Количество плохих сессий: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        long count = sleepingSessions.stream()
                .filter(session -> session.quality() == SleepQuality.BAD)
                .count();
        return new SleepAnalysisResult(TITLE, count);
    }
}
