package me.aj.uhc.game.event;


import me.aj.gm.chat.Title;
import me.aj.uhc.game.UHCGame;
import org.bukkit.ChatColor;

public class PvPEnabledEvent extends AbstractEvent {

    private Title title = new Title(ChatColor.YELLOW + "" + ChatColor.BOLD + "PVP",
            ChatColor.YELLOW + "" + ChatColor.BOLD + "IS NOW ENABLED");

    public PvPEnabledEvent() {
        super(EventType.PVP_ENABLED, 30, EventType.BORDER_SHRINK, "PvP Enabled");
    }

    @Override
    public void onSecondZero(UHCGame game) {
        //Title Message, etc...
        title.broadcast();
    }
}
