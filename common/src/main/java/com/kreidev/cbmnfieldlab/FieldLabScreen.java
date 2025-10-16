package com.kreidev.cbmnfieldlab;

import com.cobblemon.mod.common.api.gui.GuiUtilsKt;
import com.cobblemon.mod.common.client.gui.ExitButton;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.FIELD_LAB_NAME;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class FieldLabScreen extends AbstractContainerScreen<FieldLabMenu> {

    public static final ResourceLocation TEXTURE = resLoc("textures/gui/%s.png", FIELD_LAB_NAME);
    public static final int TEXTURE_WIDTH = 349;
    public static final int TEXTURE_HEIGHT = 205;

    private static final ResourceLocation BASE_RES = cobblemonResource("textures/gui/pc/pc_base.png");
    private static final ResourceLocation PORTRAIT_BACKGROUND_RES = cobblemonResource("textures/gui/pc/portrait_background.png");
    private static final ResourceLocation TOP_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_top.png");
    private static final ResourceLocation BOTTOM_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_bottom.png");
    private static final ResourceLocation RIGHT_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_right.png");
    private static final ResourceLocation TYPE_SPACER_RES = cobblemonResource("textures/gui/pc/type_spacer.png");


    public FieldLabScreen(FieldLabMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        int x = (width - PCGUI.BASE_WIDTH) / 2;
        int y = (height - PCGUI.BASE_HEIGHT) / 2;

        this.addRenderableWidget(new ExitButton(x+320, y+186, conf->{}));

        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        PoseStack matrices = guiGraphics.pose();
        int x = (width - PCGUI.BASE_WIDTH) / 2;
        int y = (height - PCGUI.BASE_HEIGHT) / 2;

        GuiUtilsKt.blitk(matrices, PORTRAIT_BACKGROUND_RES, x+6, y+27, PCGUI.PORTRAIT_SIZE, PCGUI.PORTRAIT_SIZE);
        GuiUtilsKt.blitk(matrices, BASE_RES, x, y, PCGUI.BASE_HEIGHT, PCGUI.BASE_WIDTH);


        // TODO: render only if no pokemon
        GuiUtilsKt.blitk(
                matrices, TYPE_SPACER_RES, (x+7)/PCGUI.SCALE, (y+118.5)/PCGUI.SCALE,
                PCGUI.TYPE_SPACER_HEIGHT, PCGUI.TYPE_SPACER_WIDTH, 0, 0,
                PCGUI.TYPE_SPACER_WIDTH, PCGUI.TYPE_SPACER_HEIGHT, 0, 1, 1, 1, 1f, true,
                PCGUI.SCALE
        );

        GuiUtilsKt.blitk(
                matrices, TOP_SPACER_RES, (x+86.5)/PCGUI.SCALE, (y+13)/PCGUI.SCALE,
                PCGUI.PC_SPACER_HEIGHT, PCGUI.PC_SPACER_WIDTH, 0, 0,
                PCGUI.PC_SPACER_WIDTH, PCGUI.PC_SPACER_HEIGHT, 0, 1, 1, 1, 1f, true,
                PCGUI.SCALE
        );  // Because I need to set scale but no easy overload for that.

        GuiUtilsKt.blitk(
                matrices, BOTTOM_SPACER_RES, (x+86.5)/PCGUI.SCALE, (y+189)/PCGUI.SCALE,
                PCGUI.PC_SPACER_HEIGHT, PCGUI.PC_SPACER_WIDTH, 0, 0,
                PCGUI.PC_SPACER_WIDTH, PCGUI.PC_SPACER_HEIGHT, 0, 1, 1, 1, 1f, true,
                PCGUI.SCALE
        );

        GuiUtilsKt.blitk(
                matrices, RIGHT_SPACER_RES, (x+275.5)/PCGUI.SCALE, (y+189)/PCGUI.SCALE,
                24, 64, 0, 0,
                64, 24, 0, 1, 1, 1, 1f, true,
                PCGUI.SCALE
        );

    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBg(guiGraphics, f, i, j);
    }
}
