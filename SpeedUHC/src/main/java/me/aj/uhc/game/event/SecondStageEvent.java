package me.aj.uhc.game.event;


import me.aj.uhc.game.UHCGame;

public class SecondStageEvent extends AbstractEvent {

    public SecondStageEvent() {
        super(EventType.SECOND_STAGE, 195, EventType.NETHER_CLOSE, "Second Stage");
    }

    @Override
    public void onSecondZero(UHCGame game) {}
}
