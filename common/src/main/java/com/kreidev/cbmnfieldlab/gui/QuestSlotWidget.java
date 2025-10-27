package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.CobblemonRenderable;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.network.RerollPacket;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;

// NOTE: "Slot"? maybe there's a better name for this.
public class QuestSlotWidget extends SoundlessWidget implements CobblemonRenderable {

    public static final ResourceLocation QUEST_HOVER_OVERLAY_RES =
            resLoc("textures/gui/%s/quest_slot_hover.png", PokemonFieldLab.FIELD_LAB_NAME);
    public static final ResourceLocation QUEST_SELECTED_RES =
            resLoc("textures/gui/%s/quest_slot_active.png", PokemonFieldLab.FIELD_LAB_NAME);

    public final QuestPanelWidget parent;
    public final Quest quest;
    public final RerollButton rerollButton;
    public final int index;

    public QuestSlotWidget(int pX, int pY, QuestPanelWidget parent, Quest quest, int index) {
        super(pX, pY, 166, 38, Component.literal("QuestSlot"));
        this.parent = parent;
        this.quest = quest;
        this.index = index;
        this.rerollButton = new RerollButton(pX+1, pY+9, this, this::onReroll);
        this.addWidget(this.rerollButton);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();

        if (this.parent.parent.previewPokemon != null &&
                this.quest.isEligible(this.parent.parent.previewPokemon)) {
            FieldLabScreen.GuiUtilsKtExt.blitk(
                    matrices, QUEST_SELECTED_RES,
                    this.getX(), this.getY(),
                    38, 166
            );
        }

        String[] parts = Component
                    .translatable("cbmnfieldlab.ui.field_lab.quest." + quest.getType().id())
                    .getString()
                    .split("\\n");

        int startY = this.getY()+16;
        int offsetY = 0;
        if (parts.length == 2) {
            startY = this.getY()+10;
            offsetY = 8;
        } else if (parts.length == 3) {
            startY = this.getY()+7;
            offsetY = 8;
        }

        for (int i=0; i<parts.length; i++) {
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    strToCompWithModifier(parts[i], quest.getModifierString()),
                    this.getX()+28, startY+offsetY*i,
                    false, true, 1F, 0.9F
            );
        }

        RenderHelperKtExt.drawScaledText(
                guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                TextKt.bold(Component.literal(quest.getReward().getCount() + "x")),
                this.getX()+150, this.getY()+27,
                true, true, 1F, 0.9F
        );

        guiGraphics.renderItem(quest.getReward().copyWithCount(1), this.getX()+143, this.getY()+5);
        guiGraphics.renderItemDecorations(
                Minecraft.getInstance().font, quest.getReward().copyWithCount(1),
                this.getX()+143, this.getY()+5
        );

        this.rerollButton.render(guiGraphics, mouseX, mouseY, delta);
    }


    public static MutableComponent strToCompWithModifier(String str, String modifier) {
        if (str.contains("___")) {
            String[] parts = str.split("___", -1);

            return Component.literal(parts[0])
//                    .append(Component.literal(modifier).withStyle(ChatFormatting.BOLD))
                    .append(Component.literal(modifier))
                    .append(parts[1]);
        } else {
            return Component.literal(str);
        }
    }

    public void onReroll(Button button) {
        PokemonFieldLab.LOGGER.warn("{}", (quest.getTimeStamp()+CommonConfig.rerollTimeSeconds*20L) - this.parent.parent.getGameTime());
        if (quest.getTimeStamp()+CommonConfig.rerollTimeSeconds*20L > this.parent.parent.getGameTime()) return;

        // TODO: remove quest and add a loading icon while waiting for a refresh
        NetworkManager.sendToServer(new RerollPacket(this.index));
    }
}
