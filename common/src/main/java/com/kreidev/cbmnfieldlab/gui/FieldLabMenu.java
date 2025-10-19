package com.kreidev.cbmnfieldlab.gui;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FieldLabMenu extends AbstractContainerMenu {

    private BlockPos blockPos;
    private Level level;
    private ContainerLevelAccess access;

    protected FieldLabMenu(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    public FieldLabMenu(int id, Inventory inventory, FriendlyByteBuf extraData) {
        this(id, inventory, extraData.readBlockPos());
    }

    public FieldLabMenu(int id, Inventory inventory, BlockPos blockPos) {
        super(PokemonFieldLab.FIELD_LAB_MENU.get(), id);
        this.blockPos = blockPos;
        this.level = inventory.player.level();
        this.access = ContainerLevelAccess.create(level, blockPos);
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
