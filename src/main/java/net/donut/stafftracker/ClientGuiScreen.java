package net.donut.stafftracker;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClientGuiScreen extends Screen {

    private boolean dragging = false;
    private int dragOffsetX, dragOffsetY;

    public ClientGuiScreen() {
        super(Text.of("Donut Staff Tracker Menu"));
    }

    @Override.
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Disegna Finestra del Menu principale (Meteor style)
        int menuX = 50;
        int menuY = 50;
        int menuW = 220;
        int menuH = 100;

        // Bordo e Sfondo del menu
        context.fill(menuX, menuY, menuX + menuW, menuY + menuH, 0xDD111111);
        context.fill(menuX + 2, menuY + 2, menuX + menuW - 2, menuY + 22, 0xFF222222);
        context.drawText(this.textRenderer, "Donut Staff Tracker - Menu", menuX + 10, menuY + 7, 0xFFFFFF, true);

        // Pulsante / Spunta [ ] Staff List
        int checkX = menuX + 15;
        int checkY = menuY + 40;
        context.fill(checkX, checkY, checkX + 15, checkY + 15, ModConfig.staffListEnabled ? 0xFF00AA00 : 0xFF555555);
        context.drawText(this.textRenderer, "Staff List " + (ModConfig.staffListEnabled ? "(ATTIVO)" : "(DISATTIVO)"), checkX + 25, checkY + 3, 0xFFFFFF, true);

        // Istruzione per spostare l'HUD
        context.drawText(this.textRenderer, "Premi Tasto DX sulla HUD per spostarla", menuX + 10, menuY + 75, 0xAAAAAA, false);

        // Se l'HUD è attiva, mostra un contorno di trascinamento
        if (ModConfig.staffListEnabled) {
            int hudWidth = 140;
            int hudHeight = 50;
            context.fill(ModConfig.hudX, ModConfig.hudY, ModConfig.hudX + hudWidth, ModConfig.hudY + hudHeight, 0x6600FF00);
            context.drawText(this.textRenderer, "[ Tasto DX per trascinare ]", ModConfig.hudX + 5, ModConfig.hudY + 20, 0xFFFFFF, true);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int menuX = 50;
        int menuY = 50;
        int checkX = menuX + 15;
        int checkY = menuY + 40;

        // Click Sinistro (0): Attiva/Disattiva la spunta Staff List
        if (button == 0 && mouseX >= checkX && mouseX <= checkX + 150 && mouseY >= checkY && mouseY <= checkY + 20) {
            ModConfig.staffListEnabled = !ModConfig.staffListEnabled;
            return true;
        }

        // Click Destro (1): Inizia il trascinamento dell'HUD
        if (button == 1 && ModConfig.staffListEnabled) {
            int hudWidth = 140;
            int hudHeight = 50;
            if (mouseX >= ModConfig.hudX && mouseX <= ModConfig.hudX + hudWidth &&
                mouseY >= ModConfig.hudY && mouseY <= ModConfig.hudY + hudHeight) {
                dragging = true;
                dragOffsetX = (int) mouseX - ModConfig.hudX;
                dragOffsetY = (int) mouseY - ModConfig.hudY;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 1) {
            dragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) {
            ModConfig.hudX = (int) mouseX - dragOffsetX;
            ModConfig.hudY = (int) mouseY - dragOffsetY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
