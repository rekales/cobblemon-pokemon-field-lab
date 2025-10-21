package com.kreidev.cbmnfieldlab.gui;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FieldLabMenu extends AbstractContainerMenu {

    public final List<Quest> quests;
    private final ContainerLevelAccess access;

    public FieldLabMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        super(PokemonFieldLab.FIELD_LAB_MENU.get(), id);

        BlockPos blockPos = extraData.readBlockPos();

        List<Quest> quests = new ArrayList<>(3);
        CompoundTag tag = extraData.readNbt();
        if (tag != null) {
            PokemonFieldLab.LOGGER.info(tag.getAsString());
            Quest quest = Quest.load(tag);
            if (quest == null) throw new IllegalArgumentException("Invalid packet data");
            quests.add(quest);
        }
        tag = extraData.readNbt();
        if (tag != null) {
            PokemonFieldLab.LOGGER.info(tag.getAsString());
            Quest quest = Quest.load(tag);
            if (quest == null) throw new IllegalArgumentException("Invalid packet data");
            quests.add(quest);
        }
        tag = extraData.readNbt();
        if (tag != null) {
            PokemonFieldLab.LOGGER.info(tag.getAsString());
            Quest quest = Quest.load(tag);
            if (quest == null) throw new IllegalArgumentException("Invalid packet data");
            quests.add(quest);
        }

        this.access = ContainerLevelAccess.create(inventory.player.level(), blockPos);
        this.quests =  quests;
    }

    public FieldLabMenu(int id, Inventory inventory, BlockPos blockPos, ServerPlayer player) {
        super(PokemonFieldLab.FIELD_LAB_MENU.get(), id);
        this.access = ContainerLevelAccess.create(inventory.player.level(), blockPos);
        this.quests = QuestManager.getQuests(player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int i) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, PokemonFieldLab.FIELD_LAB_BLOCK.get());
    }
}
