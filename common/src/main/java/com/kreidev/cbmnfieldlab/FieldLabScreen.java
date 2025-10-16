package com.kreidev.cbmnfieldlab;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.FIELD_LAB_NAME;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class FieldLabScreen extends AbstractContainerScreen<FieldLabMenu> {

    public static final ResourceLocation TEXTURE = resLoc("textures/gui/%s.png", FIELD_LAB_NAME);
    public static final int TEXTURE_WIDTH = 349;
    public static final int TEXTURE_HEIGHT = 205;

    private final ResourceLocation texture;

    public FieldLabScreen(FieldLabMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = TEXTURE_WIDTH;
        this.imageHeight = TEXTURE_HEIGHT;
        this.texture = TEXTURE;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int i, int j) {
        guiGraphics.blit(this.texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
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
