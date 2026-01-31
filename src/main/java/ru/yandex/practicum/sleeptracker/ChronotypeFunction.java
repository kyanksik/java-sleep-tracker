package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.yandex.practicum.sleeptracker.Chronotype.*;

public class ChronotypeFunction implements Function<List<SleepingSession>, SleepAnalysisResult> {

    private static final String TITLE = "Хронотип сна: ";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult(TITLE, GOLUB);
        }

        Map<Chronotype, Long> typeCounts = sessions.stream()
                .filter(this::isNightSession)
                .map(this::classifyNight)
                .collect(Collectors.groupingBy(chronotype -> chronotype, Collectors.counting()));

        Chronotype userType = getChronotype(typeCounts);
        return new SleepAnalysisResult(TITLE, userType);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalTime endTime = session.end().toLocalTime();

        return session.start().toLocalDate().isBefore(session.end().toLocalDate()) ||
                (endTime.isAfter(LocalTime.MIDNIGHT) && !endTime.isAfter(LocalTime.of(6, 0)));
    }

    private Chronotype classifyNight(SleepingSession session) {
        LocalTime sleepTime = session.start().toLocalTime();
        LocalTime wakeTime = session.end().toLocalTime();

        if (sleepTime.isAfter(LocalTime.of(23, 0)) && wakeTime.isAfter(LocalTime.of(9, 0))) {
            return SOVA;
        } else if (sleepTime.isBefore(LocalTime.of(22, 0)) && wakeTime.isBefore(LocalTime.of(7, 0))) {
            return JAVORONOK;
        }
        return GOLUB;
    }

    private Chronotype getChronotype(Map<Chronotype, Long> counts) {
        long SovaCount = counts.getOrDefault(SOVA, 0L);
        long JavoronokCount = counts.getOrDefault(JAVORONOK, 0L);
        long GolubCount = counts.getOrDefault(GOLUB, 0L);

        long maxCount = Math.max(SovaCount, Math.max(JavoronokCount, GolubCount));

        if ((SovaCount == maxCount && JavoronokCount == maxCount) ||
                (SovaCount == maxCount && GolubCount == maxCount) ||
                (JavoronokCount == maxCount && GolubCount == maxCount)) {
            return GOLUB;
        }

        return SovaCount == maxCount ? SOVA :
                JavoronokCount == maxCount ? JAVORONOK :
                        GOLUB;
    }
}