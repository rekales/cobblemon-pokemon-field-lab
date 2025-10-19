package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.storage.party.PartyPosition;
import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.LocalizationUtilsKt;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;

public class PartyPanelWidget extends SoundlessWidget {

    public static final ResourceLocation PARTY_PANEL_RES = cobblemonResource("textures/gui/pc/party_panel.png");

    public final List<PartyPanelSlot> partySlots = new ArrayList<>();
    public final FieldLabScreen parent;
    public final ClientParty party;
    public final SubmitButton submitButton;
    public final SubmitConfirmButton submitYesButton;
    public final SubmitConfirmButton submitNoButton;
    public boolean displayConfirmSubmit = false;

    public PartyPanelWidget(int pX, int pY, FieldLabScreen parent, ClientParty party) {
        super(pX, pY, 263, 155, Component.literal("PartyPanelOverlay"));
        this.parent = parent;
        this.party = party;
        setupPartySlot();
        this.submitButton = new SubmitButton(
                this.getX()+194, this.getY()+124,
                button -> PokemonFieldLab.LOGGER.info("clicks")
        );
        this.submitYesButton = new SubmitConfirmButton(
                this.getX()+190, this.getY()+131,
                LocalizationUtilsKt.lang("ui.generic.yes"), this::onSubmit
        );
        this.submitNoButton = new SubmitConfirmButton(
                this.getX()+226, this.getY()+131,
                LocalizationUtilsKt.lang("ui.generic.no"), this::onSubmit
        );
    }

    private void setupPartySlot() {
        for (int partyIndex = 0; partyIndex < 6; partyIndex++) {
            int partyX = this.getX() + 193;
            int partyY = this.getY() + 8;

            if (partyIndex > 0) {
                boolean isEven = partyIndex % 2 == 0;
                int offsetIndex = (partyIndex - (isEven ? 0 : 1)) / 2;
                int offsetX = isEven ? 0 : 31;
                int offsetY = isEven ? 0 : 8;

                partyX += offsetX;
                partyY += 31*offsetIndex + offsetY;
            }

            // NOTE: either override the storage widget or reimplement PartyStorageSlot, did the latter
            PartyPanelSlot slot = new PartyPanelSlot(
                    partyX, partyY, this, party,
                    new PartyPosition(partyIndex), this::onSlotClicked
            );
            this.addWidget(slot);
            this.partySlots.add(slot);
        }
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();

        GuiUtilsKtExt.blitk(
                matrices, PARTY_PANEL_RES,
                this.getX()+182, this.getY()-19,
                PCGUI.RIGHT_PANEL_HEIGHT, PCGUI.RIGHT_PANEL_WIDTH);

        RenderHelperKtExt.drawScaledText(
                guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                TextKt.bold(LocalizationUtilsKt.lang("ui.party")),
                this.getX()+213, this.getY()-15.5, true, true
        );

        for (PartyPanelSlot slot : this.partySlots) {
            slot.render(guiGraphics, mouseX, mouseY, delta);
        }

        if (displayConfirmSubmit) {
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(Component.translatable("cbmnfieldlab.ui.field_lab.submit")),
                    this.getX()+223, this.getY()+119,
                    true, false
            );

            this.submitYesButton.render(guiGraphics, mouseX, mouseY, delta);
            this.submitNoButton.render(guiGraphics, mouseX, mouseY, delta);
        } else {
            this.submitButton.render(guiGraphics, mouseX, mouseY, delta);
        }
    }


    public void onSlotClicked(Button button) {
        if (!(button instanceof PartyPanelSlot slot)) return;

        Pokemon pokemon = slot.getPokemon();

        // Unselect if clicked again
        if (pokemon == parent.previewPokemon) {
            pokemon = null;
        }

        parent.setPreviewPokemon(pokemon);
    }

    public void onSubmit(Button button) {
        // TODO: figure out why this is not working
        PokemonFieldLab.LOGGER.info("clicked");
        this.displayConfirmSubmit = !this.displayConfirmSubmit;
    }
}