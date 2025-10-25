package com.kreidev.cbmnfieldlab.gui;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.quest.PlayerQuestContainer;
import com.kreidev.cbmnfieldlab.quest.Quest;
import com.kreidev.cbmnfieldlab.quest.QuestManager;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtException;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

public class FieldLabMenu extends AbstractContainerMenu {

    public PlayerQuestContainer questContainer;
    private final ContainerLevelAccess access;

    public FieldLabMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        super(PokemonFieldLab.FIELD_LAB_MENU.get(), id);

        BlockPos blockPos = extraData.readBlockPos();

        CompoundTag tag = extraData.readNbt();
        if (tag == null) throw new NbtException("NBT null for some reason");

//        PlayerQuestContainer questContainer = PlayerQuestContainer.load(tag);

        this.access = ContainerLevelAccess.create(inventory.player.level(), blockPos);
        this.questContainer = PlayerQuestContainer.CODEC.codec().parse(NbtOps.INSTANCE, tag.get("quest_container"))
                .resultOrPartial(error->LOGGER.error("Quest data was not loaded \n"+error))
                .orElse(null);
        if (this.questContainer == null) throw new NbtException("");

        LOGGER.info(this.questContainer.getQuest(0)+"");
        LOGGER.info(this.questContainer.getQuest(1)+"");
        LOGGER.info(this.questContainer.getQuest(2)+"");
        LOGGER.info(this.questContainer.getFinishedQuests()+"");
    }

    public FieldLabMenu(int id, Inventory inventory, BlockPos blockPos, ServerPlayer player) {
        super(PokemonFieldLab.FIELD_LAB_MENU.get(), id);
        this.access = ContainerLevelAccess.create(inventory.player.level(), blockPos);
        this.questContainer = QuestManager.getQuestContainer(player);
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
