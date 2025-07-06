package fr.GameOfFamous.hellstyliaAPI.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Player;

public class SendMessage {

    public static void SendTextMessage(Player receiver, Component text) {
        receiver.sendMessage(text);
    }

    public static void SendTextGradientMessage(Player receiver, String color1, String color2, String text) {
        MiniMessage mini = MiniMessage.miniMessage();
        Component gradient = mini.deserialize("<gradient:" + color1 + ":" + color2 + ">" + text + "</gradient>");
        receiver.sendMessage(gradient);
    }

    public static void SendActionBar(Player receiver, NamedTextColor color, String text) {
        receiver.sendActionBar(Component.text(text).color(color));
    }

    public static void SendClickable(Player receiver, String text, String URL, String hover, NamedTextColor color) {
        Component link = Component.text(text)
                .clickEvent(ClickEvent.openUrl(URL))
                .hoverEvent(HoverEvent.showText(Component.text(hover)))
                .color(color);

        receiver.sendMessage(link);
    }

    public static void SendTitle(Player receiver, String title, NamedTextColor colorTitle, TextDecoration decoration,
                                 String subTitle, NamedTextColor colorSubtitle) {

        Title titleObj = Title.title(
                Component.text(title).color(colorTitle).decorate(decoration),
                Component.text(subTitle).color(colorSubtitle)
        );

        receiver.showTitle(titleObj);
    }

}
