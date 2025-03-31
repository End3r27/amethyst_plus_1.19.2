package end3r.amethystplus.client;

import com.mojang.blaze3d.systems.RenderSystem;
import end3r.amethystplus.item.EffectEnergizedStaff;
import end3r.amethystplus.item.EffectEnergizedStaffMK2;
import end3r.amethystplus.item.EffectEnergizedStaffMK3;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class RenderEnergyBar {
    private static final Identifier ENERGY_BAR_TEXTURE = new Identifier("amethystplus", "textures/gui/energy_bar.png");

    public static void renderEnergyBar(MatrixStack matrices, float partialTicks, int screenWidth, int screenHeight, ItemStack heldItem) {
        // Check if the held item is an instance of any of the staff classes
        if (!(heldItem.getItem() instanceof EffectEnergizedStaff
                || heldItem.getItem() instanceof EffectEnergizedStaffMK2
                || heldItem.getItem() instanceof EffectEnergizedStaffMK3)) {
            return; // Ensure the item is a valid staff
        }

        // Cast to the appropriate class to access energy functions
        float percentage;
        if (heldItem.getItem() instanceof EffectEnergizedStaff staff) {
            percentage = staff.getEnergyPercentage(heldItem);
        } else if (heldItem.getItem() instanceof EffectEnergizedStaffMK2 staffMK2) {
            percentage = staffMK2.getEnergyPercentage(heldItem);
        } else if (heldItem.getItem() instanceof EffectEnergizedStaffMK3 staffMK3) {
            percentage = staffMK3.getEnergyPercentage(heldItem);
        } else {
            return; // If for some reason the conditional checks fail (which shouldn't happen)
        }

        // Energy bar size and position
        int barWidth = 100; // Total width of the bar
        int barHeight = 10; // Total height of the bar
        int filledWidth = MathHelper.ceil(barWidth * percentage); // Calculate the width of the filled section

        // Bar positioning (bottom-left corner of the screen)
        int x = 10; // Padding from the left edge
        int y = screenHeight - barHeight - 10; // Padding from the bottom edge (10px spacing)

        // Bind texture and draw the bar
        RenderSystem.setShaderTexture(0, ENERGY_BAR_TEXTURE);

        // Draw the empty bar (background)
        DrawableHelper.drawTexture(matrices, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight * 2);

        // Draw the filled bar (foreground)
        DrawableHelper.drawTexture(matrices, x, y, 0, barHeight, filledWidth, barHeight, barWidth, barHeight * 2);
    }
}