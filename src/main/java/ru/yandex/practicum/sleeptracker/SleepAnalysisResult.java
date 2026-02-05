package ru.yandex.practicum.sleeptracker;


public record SleepAnalysisResult(String functionTitle, Object result) {


    @Override
    public String toString() {
        return functionTitle + " " + result;
    }
}
