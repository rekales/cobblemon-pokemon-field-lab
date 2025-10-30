package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class RerollButton extends Button implements CobblemonRenderable {

    public static final ResourceLocation REROLL_BUTTON_RES =
            resLoc("textures/gui/%s/reroll_button.png", PokemonFieldLab.FIELD_LAB_NAME);
    public static final ResourceLocation REROLL_ICON_RES =
            resLoc("textures/gui/%s/reroll_icon.png", PokemonFieldLab.FIELD_LAB_NAME);
    public static final ResourceLocation REROLL_BUTTON_HOVER_RES =
            resLoc("textures/gui/%s/reroll_button_hover.png", PokemonFieldLab.FIELD_LAB_NAME);
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

        float alpha = Math.min(1F, this.parent.parent.parent.ticksElapsed/(float)FieldLabScreen.TICKS_TO_LOAD);

        GuiUtilsKtExt.blitk(matrices, REROLL_BUTTON_RES,
                this.getX(), this.getY(),
                20, 23, alpha, 1F
        );


        float progress = Math.min(1, (this.parent.parent.parent.getGameTime()-this.parent.quest.getTimeStamp())
                / (CommonConfig.rerollTimeSeconds*20F));
        int cropWidth = (int) (12*progress);

        if (progress < 1) {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, 0.7F*alpha);
        } else {
            guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        }
        guiGraphics.blit(REROLL_ICON_RES,
                this.getX()+4, this.getY()+3,
                0, 0,
                cropWidth, 14,
                12, 14
        );
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);

        if (this.isHovered()) {
            FieldLabScreen.GuiUtilsKtExt.blitk(
                    matrices, REROLL_BUTTON_HOVER_RES,
                    this.getX(), this.getY(),
                    20, 23, alpha, 1F
            );
        }
    }

    @Override
    public void playDownSound(SoundManager soundManager) {}
}
