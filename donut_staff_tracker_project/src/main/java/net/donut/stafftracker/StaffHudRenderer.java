package net.donut.stafftracker;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.List;

public class StaffHudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (!ModConfig.staffListEnabled || client.player == null) {
            return;
        }

        List<String> staffList = StaffTracker.getOnlineStaff();

        int x = ModConfig.hudX;
        int y = ModConfig.hudY;
        int width = 140;
        int lineH = 12;
        int height = 22 + (Math.max(1, staffList.size()) * lineH);

        // Sfondo nero trasparente in stile Meteor Client
        drawContext.fill(x, y, x + width, y + height, 0x90000000);
        drawContext.fill(x, y, x + width, y + 2, 0xFF00AAFF); // Linea superiore azzurra

        // Titolo Box
        drawContext.drawText(client.textRenderer, "Staff / Dev Online", x + 8, y + 6, 0xFFFFFF, true);

        // Elementi della lista
        if (staffList.isEmpty()) {
            drawContext.drawText(client.textRenderer, "Nessuno online", x + 8, y + 22, 0xAAAAAA, false);
        } else {
            int currentY = y + 22;
            for (String name : staffList) {
                drawContext.drawText(client.textRenderer, "• " + name, x + 8, currentY, 0x55FF55, false);
                currentY += lineH;
            }
        }
    }
}
