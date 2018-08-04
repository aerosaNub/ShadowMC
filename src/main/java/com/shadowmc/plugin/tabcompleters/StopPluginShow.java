package com.shadowmc.plugin.tabcompleters;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.shadowmc.plugin.Main;
import org.bukkit.entity.Player;

public class StopPluginShow {

    public static void stopShowPlugins() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(Main.getPlugin(), new PacketType[] { PacketType.Play.Client.TAB_COMPLETE}) {
            public void onPacketReceiving(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
                    Player player = event.getPlayer();
                    PacketContainer container = event.getPacket();
                    String tab = container.getStrings().read(0);
                    if (tab.toLowerCase().contains("/bukkit:ver") || tab.toLowerCase().contains("/about") || tab.toLowerCase().contains("/bukkit:about") || tab.toLowerCase().contains("/ver")) {
                        event.setCancelled(true);
                    }
                }
            }
        });
    }
}
