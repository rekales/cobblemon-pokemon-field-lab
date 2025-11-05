package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.cobblemon.mod.common.client.render.RenderHelperKt;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

public class QuestPanelWidget extends SoundlessWidget {

    public static final ResourceLocation BASE_RES = resLoc("textures/gui/%s/base_overlay.png", PokemonFieldLab.FIELD_LAB_NAME);
    public static final ResourceLocation GROUP_BOX_RES = resLoc("textures/gui/%s/group_box.png", PokemonFieldLab.FIELD_LAB_NAME);
    public static final ResourceLocation GROUP_IND_RES = resLoc("textures/gui/%s/group_indicator.png", PokemonFieldLab.FIELD_LAB_NAME);

    public final FieldLabScreen parent;
    public final ClientParty party;
    public PlayerQuestContainer container;

    public QuestSlotWidget qs1;
    public QuestSlotWidget qs2;
    public QuestSlotWidget qs3;

    public QuestPanelWidget(int pX, int pY, FieldLabScreen parent, ClientParty party) {
        super(pX, pY, 208, 189, Component.literal("QuestOverlay"));
        this.parent = parent;
        this.party = party;
        this.updateQuestContainer(parent.getMenu().questContainer);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        if (this.parent.ticksElapsed < 1) return;
        PoseStack matrices = guiGraphics.pose();

        float alpha = Math.min(1F, this.parent.ticksElapsed/(float)FieldLabScreen.TICKS_TO_LOAD);

        GuiUtilsKtExt.blitk(matrices, BASE_RES,
                this.getX()-17, this.getY(),
                this.height, this.width, alpha, 1F
        );

        for (int i=0; i<3; i++) {
            RenderHelperKt.drawScaledText(
                    guiGraphics, null, Component.literal("exp"),
                    this.getX() + 26 + 45*i, this.getY() + 143, 1F, 1F,
                    Integer.MAX_VALUE, 0x003B6F26 + ((int)(255*alpha)<<24),
                    true, false, null, null
            );
        }

        int finishedQuests = container.getFinishedQuests() % 9;
        int activeGroupBoxes = Math.min(3, finishedQuests/3 + 1);
        for (int i=0 ; i < activeGroupBoxes ; i++) {
            if (finishedQuests/3 > i) {
                GuiUtilsKtExt.blitk(matrices, GROUP_BOX_RES,
                        this.getX() + 7 + 45*i, this.getY() + 141,
                        25, 37, alpha, 1F
                );

                RenderHelperKt.drawScaledText(
                        guiGraphics, null, Component.literal("exp"),
                        this.getX() + 26 + 45*i, this.getY() + 143, 1F, 0.9F,
                        Integer.MAX_VALUE, 0x00FFFFFF + ((int)(255*alpha)<<24),
                        true, false, null, null
                );
            }

            int activeGroupIndicators = Math.min(3, (finishedQuests - i * 3));
            for (int j = 0; j < activeGroupIndicators; j++) {
                GuiUtilsKtExt.blitk(matrices, GROUP_IND_RES,
                        this.getX() + 10 + 45 * i + j * 11, this.getY() + 155,
                        9, 9, alpha, 1F
                );
            }
        }

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        int displayedItemIndex = (int)(parent.getGameTime()/40) % container.nextMajorRewards.size();
        ItemStack displayedItem = container.getNextMajorRewards().get(displayedItemIndex);
        guiGraphics.renderItem(displayedItem, this.getX()+147, this.getY()+146);
        guiGraphics.renderItemDecorations(
                Minecraft.getInstance().font, displayedItem,
                this.getX()+147, this.getY()+146
        );
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        this.qs1.render(guiGraphics, mouseX, mouseY, delta);
        this.qs2.render(guiGraphics, mouseX, mouseY, delta);
        this.qs3.render(guiGraphics, mouseX, mouseY, delta);
    }

    // Could've just recreated the whole quest panel object but eh I already made this.
    public void updateQuestContainer(PlayerQuestContainer container) {
        this.container = container;

        if (this.qs1 != null)
            this.removeWidget(this.qs1);
        if (this.qs2 != null)
            this.removeWidget(this.qs2);
        if (this.qs3 != null)
            this.removeWidget(this.qs3);
        this.qs1 = new QuestSlotWidget(this.getX()+4, this.getY()+22, this, this.container.getQuest(0), 0);
        this.qs2 = new QuestSlotWidget(this.getX()+4, this.getY()+61, this, this.container.getQuest(1), 1);
        this.qs3 = new QuestSlotWidget(this.getX()+4, this.getY()+100, this, this.container.getQuest(2), 2);
        this.addWidget(this.qs1);
        this.addWidget(this.qs2);
        this.addWidget(this.qs3);
    }
}
