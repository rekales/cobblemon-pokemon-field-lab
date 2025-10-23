package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class QuestPanelWidget extends SoundlessWidget {

    public static final ResourceLocation BASE_RES = resLoc("textures/gui/field_lab_screen_overlay_base.png");
    public static final ResourceLocation GROUP_BOX_RES = resLoc("textures/gui/field_lab_screen_overlay_group_box.png");
    public static final ResourceLocation GROUP_IND_RES = resLoc("textures/gui/field_lab_screen_overlay_group_ind.png");
    public static final ResourceLocation REROLL_BUTTON_RES = resLoc("textures/gui/field_lab_screen_overlay_reroll_button.png");
    public static final ResourceLocation REROLL_ICON_RES = resLoc("textures/gui/field_lab_screen_overlay_reroll_icon.png");

    public final FieldLabScreen parent;
    public final ClientParty party;
    public int finishedQuests = 4;
    public final List<Quest> questList;

    public QuestPanelWidget(int pX, int pY, FieldLabScreen parent, ClientParty party) {
        super(pX, pY, 208, 189, Component.literal("QuestOverlay"));
        this.parent = parent;
        this.party = party;
        this.questList = parent.getMenu().quests;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();

        GuiUtilsKtExt.blitk(matrices, BASE_RES,
                this.getX()-17, this.getY(),
                this.height, this.width
        );

        int activeGroupBoxes = Math.min(3, finishedQuests/3 + 1);
        for (int i=0 ; i < activeGroupBoxes ; i++) {
            if (finishedQuests/3 > i) {
                GuiUtilsKtExt.blitk(matrices, GROUP_BOX_RES,
                        this.getX() + 7 + 45 * i, this.getY() + 141,
                        25, 37
                );
            }

            int activeGroupIndicators = Math.min(3, (finishedQuests - i * 3));
            for (int j = 0; j < activeGroupIndicators; j++) {
                GuiUtilsKtExt.blitk(matrices, GROUP_IND_RES,
                        this.getX() + 10 + 45 * i + j * 11, this.getY() + 155,
                        9, 9
                );
            }
        }


        for (int i = 0; i < questList.size(); i++) {
            int questOffsetY = 39 * i;
            GuiUtilsKtExt.blitk(matrices, REROLL_BUTTON_RES,
                    this.getX()+5, this.getY()+31+questOffsetY,
                    20, 23
            );

            GuiUtilsKtExt.blitk(matrices, REROLL_ICON_RES,
                    this.getX()+9, this.getY()+34+questOffsetY,
                    14, 12
            );

            // TODO: multiline text rendering
            String str = Component
                    .translatable("cbmnfieldlab.ui.field_lab.quest." + questList.get(i).getType().getKey())
                    .getString();

            String[] parts = str.split("\\n");
            if (parts.length > 1) {
                RenderHelperKtExt.drawScaledText(
                        guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                        strToCompWithModifier(parts[0], questList.get(i).getModifierString()),
                        this.getX()+32, this.getY()+30+questOffsetY,
                        false, true, 1F, 0.9F
                );
                RenderHelperKtExt.drawScaledText(
                        guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                        strToCompWithModifier(parts[1], questList.get(i).getModifierString()),
                        this.getX()+32, this.getY()+42+questOffsetY,
                        false, true, 1F, 0.9F
                );
            } else {
                RenderHelperKtExt.drawScaledText(
                        guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                        strToCompWithModifier(str, questList.get(i).getModifierString()),
                        this.getX()+32, this.getY()+36+questOffsetY,
                        false, true, 1F, 0.9F
                );
            }

            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(Component.literal("5x")),
                    this.getX()+154, this.getY()+48.5+questOffsetY,
                    true, true, 1F, 0.9F
            );
        }

    }

    public static MutableComponent strToCompWithModifier(String str, String modifier) {
        if (str.contains("___")) {
            String[] parts = str.split("___", -1);

            return Component.literal(parts[0])
                    .append(Component.literal(modifier).withStyle(ChatFormatting.BOLD))
                    .append(parts[1]);
        } else {
            return Component.literal(str);
        }
    }
}
