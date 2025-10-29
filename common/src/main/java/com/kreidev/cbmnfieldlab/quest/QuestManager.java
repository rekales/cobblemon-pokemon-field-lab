package com.kreidev.cbmnfieldlab.quest;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.util.PlayerExtensionsKt;
import com.kreidev.cbmnfieldlab.CommonConfig;
import com.kreidev.cbmnfieldlab.network.RefreshScreenPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

// TODO: maybe let's separate the SavedData subclass to another class instead of using the QuestManager?
@SuppressWarnings("UnusedReturnValue")
public class QuestManager extends SavedData {

    public static final Codec<Map<UUID, PlayerQuestContainer>> CODEC =
            Codec.unboundedMap(UUIDUtil.CODEC, PlayerQuestContainer.CODEC.codec());

    public static QuestManager INSTANCE;

    private final Map<UUID, PlayerQuestContainer> playerQuests;

    public QuestManager() {
        super();
        this.playerQuests = new HashMap<>();
    }

    public QuestManager(CompoundTag tag, HolderLookup.Provider provider) {

        if (tag.contains("PlayerCobblemonQuests")) {
            DataResult<Map<UUID, PlayerQuestContainer>> result = CODEC.parse(NbtOps.INSTANCE, tag.get("PlayerCobblemonQuests"));
            this.playerQuests = result.resultOrPartial(error->LOGGER.error("Quest data was not loaded \n"+error))
                    .orElse(new HashMap<>());
        } else {
            this.playerQuests = new HashMap<>();
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        // TODO: double check, not saving/loading properly
        DataResult<Tag> result = CODEC.encodeStart(NbtOps.INSTANCE, this.getPlayerQuests());
        result.resultOrPartial(error->LOGGER.error("Quest data was not saved \n"+error))
                .ifPresent(nbt -> tag.put("PlayerCobblemonQuests", nbt));
        return tag;
    }

    public Map<UUID, PlayerQuestContainer> getPlayerQuests() {
        return playerQuests;
    }

    public static @NotNull PlayerQuestContainer getQuestContainer(ServerPlayer player) {
        PlayerQuestContainer container = INSTANCE.getPlayerQuests().get(player.getUUID());
        if (container == null) {
            container = new PlayerQuestContainer((ServerLevel) player.level());
            INSTANCE.getPlayerQuests().put(player.getUUID(), container);
        }

        return container;
    }

    public static boolean submitQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);

        // TODO: validate

        container.incrementFinishedQuests();

        // TODO: do checks for major reward

        return container.replaceQuest(quest, Quest.getRandomQuest((ServerLevel) player.level()));
    }

    public static boolean rerollQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);
        int index = container.getQuestIndex(quest);
        if (index == -1) return false;
        return rerollQuest(player, index);
    }

    public static boolean rerollQuest(ServerPlayer player, int index) {
        PlayerQuestContainer container = getQuestContainer(player);

        Quest quest = container.getQuest(index);
        if (quest == null) return false;
        if (quest.getTimeStamp()+CommonConfig.rerollTimeSeconds > player.level().getGameTime()) return false;

        boolean success = container.replaceQuest(index, Quest.getRandomQuest((ServerLevel) player.level()));
        NetworkManager.sendToPlayer(player, new RefreshScreenPacket(container));
        return success;
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);
        return container.hasQuest(quest);
    }

    public static boolean submitPokemon(@NotNull ServerPlayer player, @NotNull Pokemon pokemon) {
        PlayerQuestContainer container = getQuestContainer(player);

        List<ItemStack> stacks = new ArrayList<>();
        int completedQuests = 0;
        List<Quest> quests = container.getQuests();
        for (int i=0; i<quests.size(); i++) {
            if (quests.get(i).isEligible(pokemon)) {
                completedQuests++;
                container.incrementFinishedQuests();
                stacks.add(quests.get(i).getReward());
                container.replaceQuest(i, Quest.getRandomQuest((ServerLevel)player.level()));
            }
        }

//        if ((container.getFinishedQuests()%9)+completedQuests >= 9) {  // TODO: Major reward check
//
//        }

//        if ((container.getFinishedQuests()%3)+completedQuests >= 3) {  // TODO: Minor reward check
//
//        }

        boolean submitted = false;
        if (completedQuests > 0) {
            PlayerExtensionsKt.party(player).remove(pokemon);
            for (ItemStack stack : stacks) {
                player.getInventory().placeItemBackInInventory(stack);
            }
            submitted = true;
        }
        NetworkManager.sendToPlayer(player, new RefreshScreenPacket(container));
        return submitted;
    }
}
