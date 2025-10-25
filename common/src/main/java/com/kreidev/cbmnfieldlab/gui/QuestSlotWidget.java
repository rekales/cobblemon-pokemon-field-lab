package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.summary.widgets.SoundlessWidget;
import com.kreidev.cbmnfieldlab.FieldLabNetworkManager;
import com.kreidev.cbmnfieldlab.quest.Quest;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.RenderHelperKtExt;
import static com.kreidev.cbmnfieldlab.gui.FieldLabScreen.GuiUtilsKtExt;
import static com.kreidev.cbmnfieldlab.PokemonFieldLab.resLoc;

// NOTE: "Slot"? maybe there's a better name for this.
public class QuestSlotWidget extends SoundlessWidget {

    public final QuestPanelWidget parent;
    public final Quest quest;
    public final RerollButton rerollButton;

    public QuestSlotWidget(int pX, int pY, QuestPanelWidget parent, Quest quest) {
        super(pX, pY, 166, 38, Component.literal("QuestSlot"));
        this.parent = parent;
        this.quest = quest;
        this.rerollButton = new RerollButton(pX+1, pY+9, this::onReroll);
        this.addWidget(this.rerollButton);
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
//        guiGraphics.fill(this.getX(), this.getY(), this.width+this.getX(), this.height+this.getY(), 0x30FFFFFF);

        String str = Component
                    .translatable("cbmnfieldlab.ui.field_lab.quest." + quest.getType().getKey())
                    .getString();

        String[] parts = str.split("\\n");

        int startY = this.getY()+14;
        int offsetY = 0;
        if (parts.length == 2) {
            startY = this.getY()+10;
            offsetY = 9;
        } else if (parts.length == 3) {
            startY = this.getY()+6;
            offsetY = 9;
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
                this.getX()+150, this.getY()+26.5,
                true, true, 1F, 0.9F
        );

        guiGraphics.renderItem(quest.getReward(), this.getX()+143, this.getY()+5);
        guiGraphics.renderItemDecorations(
                Minecraft.getInstance().font, quest.getReward(),
                this.getX()+143, this.getY()+5
        );


        this.rerollButton.render(guiGraphics, mouseX, mouseY, delta);
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

    public void onReroll(Button button) {
        RegistryFriendlyByteBuf buf = new RegistryFriendlyByteBuf(new FriendlyByteBuf(Unpooled.buffer()),
                Minecraft.getInstance().level.registryAccess());
        buf.writeInt(this.parent.container.getQuestIndex(quest));
        NetworkManager.sendToServer(FieldLabNetworkManager.REROLL_ID, buf);
    }
}
