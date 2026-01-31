package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.sleeptracker.Chronotype.GOLUB;

class SleepTrackerAppTest {
    SleepingSessionCounter counter = new SleepingSessionCounter();
    MinimalSession minimalSession = new MinimalSession();
    MaxSession maxSession = new MaxSession();
    AverageDuration averageDuration = new AverageDuration();
    ChronotypeFunction chronotypeFunction = new ChronotypeFunction();
    SleepLessNightSession sleepLessNightSession = new SleepLessNightSession();
    BadSessionCount badSessionCount = new BadSessionCount();

    List<SleepingSession> sessions = List.of(
            new SleepingSession(
                    LocalDateTime.of(2023, 1, 1, 23, 0),
                    LocalDateTime.of(2023, 1, 2, 7, 0),
                    SleepQuality.BAD
            ),
            new SleepingSession(
                    LocalDateTime.of(2023, 1, 2, 22, 30),
                    LocalDateTime.of(2023, 1, 3, 6, 45),
                    SleepQuality.GOOD
            ),
            new SleepingSession(
                    LocalDateTime.of(2023, 1, 3, 23, 15),
                    LocalDateTime.of(2023, 1, 4, 5, 30),
                    SleepQuality.NORMAL
            ),
            new SleepingSession(
                    LocalDateTime.of(2023, 1, 4, 0, 0),
                    LocalDateTime.of(2023, 1, 4, 8, 0),
                    SleepQuality.BAD
            ),
            new SleepingSession(
                    LocalDateTime.of(2023, 1, 5, 21, 45),
                    LocalDateTime.of(2023, 1, 6, 7, 15),
                    SleepQuality.GOOD
            )
    );

    @Test
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> zeroSessions = List.of();

        SleepAnalysisResult result = counter.apply(zeroSessions);
        assertEquals(0, result.result());
    }

    @Test
    void shouldReturnFive() {
        SleepAnalysisResult result = counter.apply(sessions);

        assertEquals(5, result.result());
    }

    @Test
    void shouldReturn375MinimalSession() {
        SleepAnalysisResult result = minimalSession.apply(sessions);
        assertEquals(375L, result.result());

    }

    @Test
    void shouldReturnZeroFromEmptyList() {
        SleepAnalysisResult result = minimalSession.apply(List.of());
        assertEquals(0L, result.result());
    }

    @Test
    void shouldReturn570MaxSession() {
        SleepAnalysisResult result = maxSession.apply(sessions);
        assertEquals(570L, result.result());

    }

    @Test
    void shouldReturnZeroMax() {
        SleepAnalysisResult result = maxSession.apply(List.of());
        assertEquals(0L, result.result());
    }

    @Test
    void shouldReturnAverageSession() {
        SleepAnalysisResult result = averageDuration.apply(sessions);
        assertEquals(480.0, result.result());
    }

    @Test
    void shouldReturnZeroAverage() {
        SleepAnalysisResult result = averageDuration.apply(List.of());
        assertEquals(0.0, result.result());
    }


    @Test
    void returnBadSessionCount() {
        SleepAnalysisResult result = badSessionCount.apply(sessions);
        assertEquals(2L, result.result());
    }

    @Test
    void shouldReturnZeroFromEmptyListBadSession() {
        SleepAnalysisResult result = badSessionCount.apply(List.of());
        assertEquals(0L, result.result());
    }

    @Test
    void shouldReturnGOLUBChronotype() {
        SleepAnalysisResult result = chronotypeFunction.apply(sessions);
        assertEquals(GOLUB, result.result());
    }

    @Test
    void shouldReturnOneSleeplessNight() {
        SleepAnalysisResult result = sleepLessNightSession.apply(sessions);
        assertEquals(1L, result.result());
    }

}