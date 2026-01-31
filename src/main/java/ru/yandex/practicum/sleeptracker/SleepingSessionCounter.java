package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class SleepingSessionCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final static String TITLE = "Количество сессий сна: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        int result = sleepingSessions.size();

        return new SleepAnalysisResult(TITLE, result);
    }
}
