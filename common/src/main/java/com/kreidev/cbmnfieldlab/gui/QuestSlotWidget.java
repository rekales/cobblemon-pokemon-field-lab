package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.kreidev.cbmnfieldlab.network.RerollPacket;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;

// NOTE: "Slot"? maybe there's a better name for this.
public class QuestSlotWidget extends SoundlessWidget {

    // TODO: config
    public static final int REROLL_COOLDOWN = 6 * 20;

    public final QuestPanelWidget parent;
    public final Quest quest;
    public final RerollButton rerollButton;

    public QuestSlotWidget(int pX, int pY, QuestPanelWidget parent, Quest quest) {
        super(pX, pY, 166, 38, Component.literal("QuestSlot"));
        this.parent = parent;
        this.quest = quest;
        this.rerollButton = new RerollButton(pX+1, pY+9, this, this::onReroll);
        this.addWidget(this.rerollButton);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();

//        guiGraphics.fill(this.getX(), this.getY(), this.width+this.getX(), this.height+this.getY(), 0x30FFFFFF);

        String str = Component
                    .translatable("cbmnfieldlab.ui.field_lab.quest." + quest.getType().id())
                    .getString();

        String[] parts = str.split("\\n");

        int startY = this.getY()+16;
        int offsetY = 0;
        if (parts.length == 2) {
            startY = this.getY()+12;
            offsetY = 8;
        } else if (parts.length == 3) {
            startY = this.getY()+8;
            offsetY = 8;
        }

//        matrices.pushPose();
//        matrices.translate(50, 50, 0);   // move origin to (50, 50)
//        matrices.scale(0.7f, 0.7f, 1.0f);
//
//        String text = "Hello, world!";
//        guiGraphics.drawString(this.parent.parent.getFont(), text, 10, 10, 0xFFFFFF, false);
//
//        matrices.popPose();


        for (int i=0; i<parts.length; i++) {
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, null,
                    strToCompWithModifier(parts[i], quest.getModifierString()),
                    this.getX()+28, startY+offsetY*i,
                    false, true, 0.7F, 0.9F
            );
        }

        RenderHelperKtExt.drawScaledText(
                guiGraphics, null,
                Component.literal(quest.getReward().getCount() + "x"),
                this.getX()+151, this.getY()+28.5,
                true, true, 0.65F, 0.9F
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
        if (quest.getTimeStamp()+REROLL_COOLDOWN > this.parent.parent.getGameTime()) return;

        // TODO: remove quest and add a loading icon while waiting for a refresh
        NetworkManager.sendToServer(new RerollPacket(this.parent.container.getQuestIndex(quest)));
    }
}
