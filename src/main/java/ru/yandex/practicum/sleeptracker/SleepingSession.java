package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;

public record SleepingSession(
        LocalDateTime start ,
        LocalDateTime end,
        SleepQuality quality
){


    @Override
    public LocalDateTime end() {
        return end;
    }

    @Override
    public LocalDateTime start() {
        return start;
    }

    @Override
    public SleepQuality quality() {
        return quality;
    }
}
