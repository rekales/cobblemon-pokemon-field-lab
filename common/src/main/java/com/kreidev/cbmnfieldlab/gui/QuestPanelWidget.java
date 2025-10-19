package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

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
    public final List<Quest> questList = new ArrayList<>(3);

    public QuestPanelWidget(int pX, int pY, FieldLabScreen parent, ClientParty party) {
        super(pX, pY, 208, 189, Component.literal("QuestOverlay"));
        this.parent = parent;
        this.party = party;
        questList.add(new Quest());
        questList.add(new Quest());
        questList.add(new Quest());
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
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    Component.literal("test test test test test test"),
                    this.getX()+32, this.getY()+36+questOffsetY, false, true
            );

            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(Component.literal("5x")),
                    this.getX()+154, this.getY()+48.5+questOffsetY, true, true
            );
        }

    }

    // NOTE: Temporary quest class
    public static class Quest {
        public Quest() {

        }
    }
}
