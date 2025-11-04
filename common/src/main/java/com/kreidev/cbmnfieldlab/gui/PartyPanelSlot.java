package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.storage.party.PartyPosition;
import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import com.cobblemon.mod.common.client.gui.PokemonGuiUtilsKt;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.pc.StorageSlot;
import com.cobblemon.mod.common.client.render.RenderHelperKt;
import com.cobblemon.mod.common.client.render.models.blockbench.FloatingState;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.cobblemon.mod.common.entity.PoseType;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.LocalizationUtilsKt;
import com.cobblemon.mod.common.util.math.QuaternionUtilsKt;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;

public class PartyPanelSlot extends Button implements CobblemonRenderable {

    public static final ResourceLocation GENDER_ICON_MALE = cobblemonResource("textures/gui/pc/gender_icon_male.png");
    public static final ResourceLocation GENDER_ICON_FEMALE = cobblemonResource("textures/gui/pc/gender_icon_female.png");
    public static final ResourceLocation SELECT_POINTER_RES = cobblemonResource("textures/gui/pc/pc_pointer.png");
    public static final ResourceLocation SLOT_HOVER_OVERLAY_RES =
            resLoc("textures/gui/%s/party_panel_slot_hover.png", PokemonFieldLab.FIELD_LAB_NAME);

    public final PartyPanelWidget parent;
    public final ClientParty party;
    public final PartyPosition position;
    public final FloatingState state = new FloatingState();

    protected PartyPanelSlot(int x, int y, PartyPanelWidget parent, ClientParty party, PartyPosition position, OnPress onPress) {
        super(x, y, StorageSlot.SIZE, StorageSlot.SIZE, Component.literal("PartyPanelSlot"), onPress, DEFAULT_NARRATION);
        this.parent = parent;
        this.party = party;
        this.position = position;
    }

    public @Nullable Pokemon getPokemon() {
        return party.get(position);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        // no need to copy shouldRender as pokemons aren't "grabbed"
        this.renderSlot(guiGraphics, this.getX(), this.getY(), delta);
    }

    public void renderSlot(GuiGraphics guiGraphics, int posX, int posY, float partialTicks) {
        Pokemon pokemon = this.getPokemon();
        if (pokemon == null) return;
        PoseStack matrices = guiGraphics.pose();
        guiGraphics.enableScissor(
                posX - 2,
                posY + 2,
                posX + StorageSlot.SIZE + 4,
                posY + StorageSlot.SIZE + 4
        );

        // Render Pokémon
        matrices.pushPose();
        matrices.translate(posX + (StorageSlot.SIZE / 2.0), posY + 1.0, 0.0);
        matrices.scale(2.5F, 2.5F, 1F);

        PokemonGuiUtilsKt.drawProfilePokemon(
                pokemon.asRenderablePokemon(),
                matrices,
                QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), new Vector3f(13F, 35F, 0F)),
                PoseType.PROFILE,
                state,
                partialTicks,
                4.5F,
                true, true,
                1F, 1F, 1F, 1F, 0F, 0F
        );
        matrices.popPose();

        guiGraphics.disableScissor();

        // Ensure elements are not hidden behind Pokémon render
        matrices.pushPose();
        matrices.translate(0.0, 0.0, 100.0);
        // Level
        RenderHelperKtExt.drawScaledText(
                guiGraphics, null,
                LocalizationUtilsKt.lang("ui.lv.number", pokemon.getLevel()),
                posX+1, posY+1, false, true, PCGUI.SCALE
        );

        if (pokemon.getGender() != Gender.GENDERLESS) {
            GuiUtilsKtExt.blitk(
                    matrices, pokemon.getGender() == Gender.MALE ? GENDER_ICON_MALE : GENDER_ICON_FEMALE,
                    (posX+21)/PCGUI.SCALE, (posY+1)/PCGUI.SCALE,
                    8, 6, PCGUI.SCALE
            );
        }

        // Held Item
        ItemStack heldItem = pokemon.getHeldItem$common();
        if (!heldItem.isEmpty()) {
            RenderHelperKt.renderScaledGuiItemIcon(
                    heldItem, posX+16, posY+16,
                    0.5F, 100.0F, matrices
            );
        }
        matrices.popPose();

        // Ensure overlay elements are on top
        matrices.pushPose();
        matrices.translate(0.0, 0.0, 500.0);

        if (isHoveredOrFocused()) {
            // Arrow pointer
            GuiUtilsKtExt.blitk(
                    matrices, SELECT_POINTER_RES,
                    (posX+10)/PCGUI.SCALE, (posY-3)/PCGUI.SCALE - parent.parent.selectPointerOffsetY,
                    8, 11, PCGUI.SCALE
            );
        }

        if (this.isHovered()) {
            GuiUtilsKtExt.blitk(
                    matrices, SLOT_HOVER_OVERLAY_RES,
                    posX, posY,
                    25, 25
            );
        }
        matrices.popPose();
    }

    @Override
    public boolean isHoveredOrFocused() {
        return getPokemon() == parent.parent.previewPokemon;
    }
}