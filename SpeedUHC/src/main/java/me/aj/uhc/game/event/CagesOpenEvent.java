package me.aj.uhc.game.event;


import me.aj.uhc.game.UHCGame;

public class CagesOpenEvent extends AbstractEvent {

    public CagesOpenEvent() {
        super(EventType.CAGES_OPEN, 10, EventType.PVP_ENABLED, "Cages Open");
    }

    @Override
    public void onSecondZero(UHCGame game) {}
}
