package net.donut.stafftracker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.ArrayList;
import java.util.List;

public class StaffTracker {
    private static final String[] STAFF_ROLES = {
        "owner", "dev", "developer", "admin", "administrator",
        "mod", "moderator", "staff", "manager", "head-admin"
    };

    public static List<String> getOnlineStaff() {
        List<String> foundStaff = new ArrayList<>();
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.getNetworkHandler() != null) {
            for (PlayerListEntry entry : client.getNetworkHandler().getPlayerList()) {
                String name = entry.getProfile().getName();
                String displayName = entry.getDisplayName() != null ? entry.getDisplayName().getString() : name;

                for (String role : STAFF_ROLES) {
                    if (displayName.toLowerCase().contains(role)) {
                        foundStaff.add(displayName);
                        break;
                    }
                }
            }
        }
        return foundStaff;
    }
}
