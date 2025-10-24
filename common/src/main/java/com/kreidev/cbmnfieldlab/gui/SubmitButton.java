package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.gui.GuiUtilsKt;
import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;

public class SubmitButton extends Button implements CobblemonRenderable {

    public static final ResourceLocation BUTTON_RES = cobblemonResource("textures/gui/pc/pc_release_button.png");
    public static final int WIDTH = 58;
    public static final int HEIGHT = 16;

    public SubmitButton(int x, int y, OnPress onPress) {
        super(x, y, WIDTH, HEIGHT, Component.literal("Submit"), onPress, DEFAULT_NARRATION);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        GuiUtilsKt.blitk(
                guiGraphics.pose(), BUTTON_RES, this.getX(), this.getY(),
                this.height, this.width, 0, isHovered() ? this.height : 0,
                this.width, this.height*2, 0, 1, 1, 1, 1f, true,
                1F
        );

        RenderHelperKtExt.drawScaledText(guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                TextKt.bold(Component.translatable("cbmnfieldlab.ui.field_lab.submit")),
                this.getX()+(WIDTH / 2), this.getY()+3.5, true, true
        );
    }

    @Override
    public void playDownSound(SoundManager soundManager) {}
}