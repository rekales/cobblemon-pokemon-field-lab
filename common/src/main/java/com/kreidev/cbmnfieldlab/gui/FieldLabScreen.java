package com.kreidev.cbmnfieldlab.gui;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.api.gui.GuiUtilsKt;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.text.TextKt;
import com.cobblemon.mod.common.client.CobblemonClient;
import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.gui.ExitButton;
import com.cobblemon.mod.common.client.gui.TypeIcon;
import com.cobblemon.mod.common.client.gui.pc.PCGUI;
import com.cobblemon.mod.common.client.gui.summary.Summary;
import com.cobblemon.mod.common.client.gui.summary.widgets.ModelWidget;
import com.cobblemon.mod.common.client.gui.summary.widgets.common.NatureInfoUtilsKt;
import com.cobblemon.mod.common.client.render.RenderHelperKt;
import com.cobblemon.mod.common.client.storage.ClientParty;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.status.PersistentStatus;
import com.cobblemon.mod.common.util.LocalizationUtilsKt;
import com.cobblemon.mod.common.util.MiscUtilsKt;
import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.cobblemon.mod.common.util.MiscUtilsKt.cobblemonResource;

public class FieldLabScreen extends AbstractContainerScreen<FieldLabMenu> {

    public static final ResourceLocation BASE_RES = cobblemonResource("textures/gui/pc/pc_base.png");
    public static final ResourceLocation PORTRAIT_BACKGROUND_RES = cobblemonResource("textures/gui/pc/portrait_background.png");
    public static final ResourceLocation INFO_BOX_RES = cobblemonResource("textures/gui/pc/info_box.png");
    public static final ResourceLocation TOP_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_top.png");
    public static final ResourceLocation BOTTOM_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_bottom.png");
    public static final ResourceLocation RIGHT_SPACER_RES = cobblemonResource("textures/gui/pc/pc_spacer_right.png");
    public static final ResourceLocation TYPE_SPACER_RES = cobblemonResource("textures/gui/pc/type_spacer.png");
    public static final ResourceLocation TYPE_SPACER_SINGLE_RES = cobblemonResource("textures/gui/pc/type_spacer_single.png");
    public static final ResourceLocation TYPE_SPACER_DOUBLE_RES = cobblemonResource("textures/gui/pc/type_spacer_double.png");
    public static final int TICKS_TO_LOAD = 10;

    public @Nullable QuestPanelWidget questPanelWidget;
    public @Nullable PartyPanelWidget partyPanelWidget;  // No lateinit for java, rip
    public @Nullable ModelWidget modelWidget = null;
    public @Nullable Pokemon previewPokemon = null;
    public ClientParty party;
    public int ticksElapsed = 0;
    public int selectPointerOffsetY = 0;
    public boolean selectPointerOffsetIncrement = false;

    @SuppressWarnings("unused")  // I wish there's a way to elegantly not need this
    public FieldLabScreen(FieldLabMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, Component.empty());
        this.party = CobblemonClient.INSTANCE.getStorage().getParty();
    }

    @Override
    protected void init() {
        int x = (width - PCGUI.BASE_WIDTH) / 2;
        int y = (height - PCGUI.BASE_HEIGHT) / 2;

        this.addRenderableWidget(new ExitButton(x+320, y+186, button->this.closeNormally()));

        this.partyPanelWidget = new PartyPanelWidget(x+85, y+27, this, party);
        this.addRenderableWidget(this.partyPanelWidget);

        this.questPanelWidget = new QuestPanelWidget(x+85, y+10, this, party);
        this.addRenderableWidget(this.questPanelWidget);

        this.setPreviewPokemon(null);
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float i, int j, int k) {}

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        PoseStack matrices = guiGraphics.pose();
        int x = (width - PCGUI.BASE_WIDTH) / 2;
        int y = (height - PCGUI.BASE_HEIGHT) / 2;

        // Render Portrait Background
        GuiUtilsKt.blitk(matrices, PORTRAIT_BACKGROUND_RES, x+6, y+27, PCGUI.PORTRAIT_SIZE, PCGUI.PORTRAIT_SIZE);

        // Render Model Portrait
        if (modelWidget != null) {
            modelWidget.render(guiGraphics, mouseX, mouseY, delta);
        }

        // Render Base Resource
        GuiUtilsKt.blitk(matrices, BASE_RES, x, y, PCGUI.BASE_HEIGHT, PCGUI.BASE_WIDTH);

        // Render Info Box
        GuiUtilsKt.blitk(matrices, INFO_BOX_RES, x+9, y+128, PCGUI.INFO_BOX_HEIGHT, PCGUI.INFO_BOX_WIDTH);

        // Render Info Labels
        RenderHelperKtExt.drawScaledText(
                guiGraphics, null,
                TextKt.bold(LocalizationUtilsKt.lang("ui.info.nature")),
                x+39, y+129.5, true, false, PCGUI.SCALE
        );

        RenderHelperKtExt.drawScaledText(
                guiGraphics, null,
                TextKt.bold(LocalizationUtilsKt.lang("ui.info.ability")),
                x+39, y+146.5, true, false, PCGUI.SCALE
        );

        RenderHelperKtExt.drawScaledText(
                guiGraphics, null,
                TextKt.bold(LocalizationUtilsKt.lang("ui.moves")),
                x+39, y+163.5, true, false, PCGUI.SCALE
        );

        // Render Pokemon Info
        Pokemon pokemon = previewPokemon;
        if (pokemon != null) {

            // Status
            PersistentStatus status = pokemon.getStatus() != null ? pokemon.getStatus().getStatus() : null;
            if (pokemon.isFainted() || status != null) {
                String statusName = pokemon.isFainted() ? "fnt" : status.getShowdownName();
                GuiUtilsKt.blitk(
                        matrices,
                        cobblemonResource(String.format("textures/gui/battle/battle_status_%s.png", statusName)),
                        x+34, y+1, 7, 39, 35, 0, 74
                );

                GuiUtilsKt.blitk(
                        matrices,
                        cobblemonResource("textures/gui/summary/status_trim.png"),
                        x+34, y+2, 6, 3
                );

                RenderHelperKt.drawScaledText(
                        guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                        TextKt.bold(LocalizationUtilsKt.lang(String.format("ui.status.%s", statusName))),
                        x+39, y, 1F, 1F,
                        Integer.MAX_VALUE, 0x00FFFFFF + (255<<24),
                        false, false, null, null
                );
            }

            // Level
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(LocalizationUtilsKt.lang("ui.lv")),
                    x+6, y+1.5, true
            );

            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(TextKt.text(pokemon.getLevel())),
                    x+19, y+1.5, true
            );

            // Poké Ball
            ResourceLocation ballResource = cobblemonResource(String.format("textures/item/poke_balls/%s.png", pokemon.getCaughtBall().getName().getPath()));
            GuiUtilsKtExt.blitk(
                    matrices, ballResource, (x+3.5)/PCGUI.SCALE, (y+12)/PCGUI.SCALE,
                    16, 16, PCGUI.SCALE
            );

            RenderHelperKtExt.drawScaledText(
                    guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                    TextKt.bold(pokemon.getDisplayName(false)),
                    x+12, y+11.5, true
            );

            if (pokemon.getGender() != Gender.GENDERLESS) {
                boolean isMale = pokemon.getGender() == Gender.MALE;
                MutableComponent textSymbol = TextKt.bold(TextKt.text(isMale ? "♂" : "♀" ));
                RenderHelperKt.drawScaledText(
                        guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), textSymbol,
                        x+69, y+11.5, 1F, 1F,
                        Integer.MAX_VALUE, isMale ? 0x32CBFF : 0xFC5454,
                        false, true, null, null
                );
            }

            // Held Item
            ItemStack heldItem = pokemon.heldItemNoCopy$common();
            int itemX = x + 3;
            int itemY = y + 98;
            if (!heldItem.isEmpty()) {
                guiGraphics.renderItem(heldItem, itemX, itemY);
                guiGraphics.renderItemDecorations(Minecraft.getInstance().font, heldItem, itemX, itemY);
            }

            RenderHelperKtExt.drawScaledText(
                    guiGraphics, null,
                    LocalizationUtilsKt.lang("held_item"),
                    x+27, y+108.5, false, false, PCGUI.SCALE
            );

            // Shine Icon
            if (pokemon.getShiny()) {
                GuiUtilsKtExt.blitk(
                        matrices, Summary.Companion.getIconShinyResource(),
                        (x+62.5)/PCGUI.SCALE, (y+28.5)/PCGUI.SCALE,
                        16, 16, PCGUI.SCALE
                );
            }

            GuiUtilsKtExt.blitk(
                    matrices, pokemon.getSecondaryType() != null ? TYPE_SPACER_DOUBLE_RES : TYPE_SPACER_SINGLE_RES,
                    (x+7)/PCGUI.SCALE, (y+118.5)/PCGUI.SCALE,
                    PCGUI.TYPE_SPACER_HEIGHT, PCGUI.TYPE_SPACER_WIDTH, PCGUI.SCALE
            );

            new TypeIcon(
                    x+39, y+117,
                    pokemon.getPrimaryType(), pokemon.getSecondaryType(),
                    true, true, 10F, 5F, 1F
            ).render(guiGraphics);

            // Nature
            MutableComponent natureText = NatureInfoUtilsKt.reformatNatureTextIfMinted(pokemon);
            RenderHelperKt.drawScaledText(
                    guiGraphics, null, natureText,
                    x+39, y+137, PCGUI.SCALE, 1F,
                    Integer.MAX_VALUE, 0xFFFFFFFF,
                    true, true, mouseX, mouseY
            );

            // Ability
            RenderHelperKtExt.drawScaledText(
                    guiGraphics, null,
                    MiscUtilsKt.asTranslated(pokemon.getAbility().getDisplayName()),
                    x+39, y+154, true, true, PCGUI.SCALE
            );

            // Moves
            List<Move> moves = pokemon.getMoveSet().getMoves();
            for (int i = 0; i < moves.size(); i++) {
                RenderHelperKtExt.drawScaledText(
                        guiGraphics, null,
                        moves.get(i).getDisplayName(),
                        x+39, y+170.5 + (7*i), true, true, PCGUI.SCALE
                );
            }

        } else {
            GuiUtilsKtExt.blitk(
                    matrices, TYPE_SPACER_RES, (x+7)/PCGUI.SCALE, (y+118.5)/PCGUI.SCALE,
                    PCGUI.TYPE_SPACER_HEIGHT, PCGUI.TYPE_SPACER_WIDTH, PCGUI.SCALE
            );
        }

        // Title
        RenderHelperKtExt.drawScaledText(
                guiGraphics, CobblemonResources.INSTANCE.getDEFAULT_LARGE(),
                TextKt.bold(Component.translatable("cbmnfieldlab.ui.field_lab.title")),
                x+172, y+15, true, false
        );

        GuiUtilsKtExt.blitk(
                matrices, TOP_SPACER_RES, (x+86.5)/PCGUI.SCALE, (y+13)/PCGUI.SCALE,
                PCGUI.PC_SPACER_HEIGHT, PCGUI.PC_SPACER_WIDTH, PCGUI.SCALE
        );

        GuiUtilsKtExt.blitk(
                matrices, BOTTOM_SPACER_RES, (x+86.5)/PCGUI.SCALE, (y+189)/PCGUI.SCALE,
                PCGUI.PC_SPACER_HEIGHT, PCGUI.PC_SPACER_WIDTH, PCGUI.SCALE
        );

        GuiUtilsKtExt.blitk(
                matrices, RIGHT_SPACER_RES, (x+275.5)/PCGUI.SCALE, (y+184)/PCGUI.SCALE,
                24, 64, PCGUI.SCALE
        );

        super.render(guiGraphics, mouseX, mouseY, delta);

        // Item tooltips
        if (pokemon != null && !pokemon.heldItemNoCopy$common().isEmpty()) {
            int itemX = x + 3;
            int itemY = y + 98;
            if ((mouseX >= itemX && mouseX <= itemX + 16) && (mouseY >= itemY && mouseY <= itemY + 16)) {
                guiGraphics.renderTooltip(
                        Minecraft.getInstance().font,
                        pokemon.getHeldItem$common(),
                        mouseX,
                        mouseY
                );
            }
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int i, int j) {}

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        this.renderBg(guiGraphics, f, i, j);
    }

    @Override
    protected void containerTick() {
        ticksElapsed++;

        // Calculate select pointer offset
        int delayFactor = 3;
        if (ticksElapsed % (2 * delayFactor) == 0) selectPointerOffsetIncrement = !selectPointerOffsetIncrement;
        if (ticksElapsed % delayFactor == 0) selectPointerOffsetY += selectPointerOffsetIncrement ? 1 : -1;
    }

    public void setPreviewPokemon(@Nullable Pokemon pokemon) {
        if (pokemon != null) {
            previewPokemon = pokemon;

            int x = (width - PCGUI.BASE_WIDTH) / 2;
            int y = (height - PCGUI.BASE_HEIGHT) / 2;
            modelWidget = new ModelWidget(
                    x+6,
                    y+27,
                    PCGUI.PORTRAIT_SIZE,
                    PCGUI.PORTRAIT_SIZE,
                    pokemon.asRenderablePokemon(),
                    2F,
                    325F,
                    -10.0,
                    false,
                    false
            );
        } else {
            previewPokemon = null;
            modelWidget = null;
        }
    }

    public void playSound(SoundEvent soundEvent) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(soundEvent, 1.0F));
    }

    public void closeNormally() {
        playSound(CobblemonSounds.PC_OFF);
        Minecraft.getInstance().setScreen(null);
    }

    @Override
    public void onClose() {
        playSound(CobblemonSounds.PC_OFF);
        super.onClose();
    }

    public long getGameTime() {
        if (this.minecraft == null || this.minecraft.level == null) return -1;
        return this.minecraft.level.getGameTime();
    }

    public void updateQuests(PlayerQuestContainer container) {
        this.getMenu().updateQuestContainer(container);
        if (questPanelWidget != null) {
            this.questPanelWidget.updateQuestContainer(container);
        }
        this.setPreviewPokemon(null);
    }

    @SuppressWarnings("SameParameterValue")
    static class GuiUtilsKtExt {
        static void blitk(PoseStack matrixStack, ResourceLocation texture,
                          Number x, Number y, Number height, Number width, float alpha, float scale) {
            GuiUtilsKt.blitk(
                    matrixStack, texture, x, y,
                    height, width, 0, 0,
                    width, height, 0, 1, 1, 1, alpha, true,
                    scale
            );
        }

        static void blitk(PoseStack matrixStack, ResourceLocation texture,
                          Number x, Number y, Number height, Number width, float scale) {
            GuiUtilsKtExt.blitk(
                    matrixStack, texture, x, y,
                    height, width, 1F, scale
            );
        }

        static void blitk(PoseStack matrixStack, ResourceLocation texture,
                          Number x, Number y, Number height, Number width) {
            GuiUtilsKtExt.blitk(
                    matrixStack, texture, x, y,
                    height, width, 1F
            );
        }
    }

    @SuppressWarnings("SameParameterValue")
    static class RenderHelperKtExt {
        static void drawScaledText(GuiGraphics context, ResourceLocation font,
                                   MutableComponent text, Number x, Number y, boolean shadow) {
            RenderHelperKtExt.drawScaledText(context, font, text, x, y, false, shadow);
        }

        static void drawScaledText(GuiGraphics context, ResourceLocation font,
                                   MutableComponent text, Number x, Number y, boolean centered, boolean shadow) {
            RenderHelperKtExt.drawScaledText(context, font, text, x, y, centered, shadow, 1F);
        }

        static void drawScaledText(GuiGraphics context, ResourceLocation font,
                                   MutableComponent text, Number x, Number y, boolean centered, boolean shadow, float scale) {
            RenderHelperKtExt.drawScaledText(context, font, text, x, y, centered, shadow, scale, 1F);
        }

        static void drawScaledText(GuiGraphics context, ResourceLocation font,
                                   MutableComponent text, Number x, Number y,
                                   boolean centered, boolean shadow, float scale, float opacity) {
            RenderHelperKt.drawScaledText(
                    context, font, text,
                    x, y, scale, opacity,
                    Integer.MAX_VALUE, 0x00FFFFFF + ((int) (opacity * 255) << 24),
                    centered, shadow, null, null
            );
        }
    }
}
