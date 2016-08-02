package me.aj.uhc.game.event;

import com.google.common.collect.Maps;
import me.aj.uhc.game.UHCGame;

import java.util.HashMap;

public abstract class AbstractEvent {

    private static HashMap<EventType, AbstractEvent> registeredEvents = Maps.newHashMap();

    public static AbstractEvent getEvent(EventType eventType){
        return registeredEvents.get(eventType);
    }

    private final EventType eventType;
    private final int countdown;
    private final EventType nextEvent;
    private final String display;

    private int seconds;

    public AbstractEvent(EventType eventType, int countdown, EventType nextEvent, String display){
        this.eventType = eventType;
        this.countdown = countdown;
        this.seconds = countdown;
        this.nextEvent = nextEvent;
        this.display = display;
        registeredEvents.put(eventType, this);
    }

    public EventType getNextEvent() {
        return nextEvent;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getDisplay() {
        return display;
    }

    public int getCountdown() {
        return countdown;
    }

    public int getRemainingSeconds() {
        return seconds;
    }

    public int downSecond() {
        return seconds--;
    }

    public abstract void onSecondZero(UHCGame game);
}
