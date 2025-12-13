package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.gui.GuiUtilsKt;
import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;

public class SubmitConfirmButton extends Button implements CobblemonRenderable {

    public static final ResourceLocation BUTTON_RES = cobblemonResource("textures/gui/pc/pc_release_button_confirm.png");
    public static final int WIDTH = 30;
    public static final int HEIGHT = 13;

    public final MutableComponent subKey;

    public SubmitConfirmButton(int x, int y, MutableComponent subKey, OnPress onPress) {
        super(x, y, WIDTH, HEIGHT, Component.literal("SubmitConfirm"), onPress, DEFAULT_NARRATION);
        this.subKey = subKey;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        GuiUtilsKt.blitk(
                guiGraphics.pose(), BUTTON_RES, this.getX(), this.getY(),
                this.height, this.width, 0, isHovered() ? this.height : 0,
                this.width, this.height*2, 0, 1, 1, 1, 1f, true,
                1F
        );

        FieldLabScreen.RenderHelperKtExt.drawScaledText(guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                TextKt.bold(subKey),
                this.getX()+(WIDTH / 2), this.getY()+2, true, true
        );
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(CobblemonSounds.GUI_CLICK, 1.0F));
    }
}
