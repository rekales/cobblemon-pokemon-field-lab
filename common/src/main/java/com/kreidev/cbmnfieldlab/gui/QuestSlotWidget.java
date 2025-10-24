package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.kreidev.cbmnfieldlab.quest.Quest;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

// NOTE: "Slot"? maybe there's a better name for this.
public class QuestSlotWidget extends SoundlessWidget {

    public final QuestPanelWidget parent;
    public final Quest quest;
    public final RerollButton rerollButton;

    public QuestSlotWidget(int pX, int pY, QuestPanelWidget parent, Quest quest) {
        super(pX, pY, 166, 38, Component.literal("QuestSlot"));
        this.parent = parent;
        this.quest = quest;
        this.rerollButton = new RerollButton(pX+1, pY+9, button->{});
        this.addWidget(this.rerollButton);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        guiGraphics.fill(this.getX(), this.getY(), this.width+this.getX(), this.height+this.getY(), 0x30FFFFFF);
        this.rerollButton.render(guiGraphics, mouseX, mouseY, delta);
    }
}
