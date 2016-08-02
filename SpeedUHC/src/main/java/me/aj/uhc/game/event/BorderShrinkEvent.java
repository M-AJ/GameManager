package me.aj.uhc.game.event;


import me.aj.gm.chat.Title;
import me.aj.uhc.SpeedUHC;
import me.aj.uhc.game.UHCGame;
import org.bukkit.ChatColor;

public class BorderShrinkEvent extends AbstractEvent {

    private Title title = new Title(ChatColor.YELLOW + "" + ChatColor.BOLD + "World Border",
            ChatColor.YELLOW + "" + ChatColor.BOLD + "IS NOW SHRINKING!");

    public BorderShrinkEvent() {
        super(EventType.BORDER_SHRINK, 20, EventType.SECOND_STAGE, "Border Shrink");
    }

    @Override
    public void onSecondZero(UHCGame game) {
        //Start Shrinking Border
        game.getBorderManager().runTaskTimer(SpeedUHC.get(), 0, 20L);
        //Title Message
        title.broadcast();
    }
}
