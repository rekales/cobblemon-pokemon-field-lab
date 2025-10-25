package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class RerollButton extends Button implements CobblemonRenderable {

    public static final ResourceLocation REROLL_BUTTON_RES = resLoc("textures/gui/field_lab_screen_overlay_reroll_button.png");
    public static final ResourceLocation REROLL_ICON_RES = resLoc("textures/gui/field_lab_screen_overlay_reroll_icon.png");
    public static final int WIDTH = 23;
    public static final int HEIGHT = 20;
    public final QuestSlotWidget parent;

    protected RerollButton(int x, int y, QuestSlotWidget parent, OnPress onPress) {
        super(x, y, WIDTH-2, HEIGHT, Component.literal("Reroll"), onPress, DEFAULT_NARRATION);
        this.parent = parent;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();

        GuiUtilsKtExt.blitk(matrices, REROLL_BUTTON_RES,
                this.getX(), this.getY(),
                20, 23
        );

        float progress = Math.min(1, (parent.parent.parent.getGameTime()-parent.quest.getTimeStamp())/(float) QuestSlotWidget.REROLL_COOLDOWN);
        int cropWidth = (int) (12*progress);

        if (progress < 1) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.7F);
        }

        guiGraphics.blit(REROLL_ICON_RES,
                this.getX()+4, this.getY()+3,
                0, 0,
                cropWidth, 14,
                12, 14
        );

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1F);

//        GuiUtilsKtExt.blitk(matrices, REROLL_ICON_RES,
//                this.getX()+4, this.getY()+3,
//                14, 12
//        );
    }

    @Override
    public void playDownSound(SoundManager soundManager) {}
}
