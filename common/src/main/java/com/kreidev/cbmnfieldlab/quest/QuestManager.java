package com.kreidev.cbmnfieldlab.quest;

import com.kreidev.cbmnfieldlab.PokemonFieldLab;
import com.kreidev.cbmnfieldlab.network.RefreshScreenPacket;
import com.kreidev.cbmnfieldlab.network.RerollPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static com.kreidev.cbmnfieldlab.PokemonFieldLab.LOGGER;

public class QuestManager extends SavedData {

    public static final Codec<Map<UUID, PlayerQuestContainer>> CODEC = Codec.unboundedMap(UUIDUtil.CODEC, PlayerQuestContainer.CODEC.codec());
//
//    public static Codec<QuestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.unboundedMap(UUIDUtil.CODEC, PlayerQuestContainer.CODEC.codec())
//                    .fieldOf("player_quests")
//                    .forGetter(QuestManager::getPlayerQuests)
//    ).apply(instance, data -> {
//        QuestManager questData = new QuestManager();
//        questData.playerQuests.putAll(data);
//        return questData;
//    }));

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

//        ListTag list = tag.getList("PlayerCobblemonQuests", Tag.TAG_COMPOUND);
//        for(int i = 0; i < list.size(); i++) {
//            CompoundTag t = list.getCompound(i);
//            UUID id = t.getUUID("UUID");
//            PlayerQuestContainer container = PlayerQuestContainer.load(t.getCompound("PlayerQuestContainer"));
//            playerQuests.put(id, container);
//        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider provider) {
        DataResult<Tag> result = CODEC.encodeStart(NbtOps.INSTANCE, this.getPlayerQuests());
        result.resultOrPartial(error->LOGGER.error("Quest data was not saved \n"+error))
                .ifPresent(nbt -> tag.put("PlayerCobblemonQuests", nbt));

//        ListTag list = new ListTag();
//        for (Map.Entry<UUID, PlayerQuestContainer> entry : playerQuests.entrySet()) {
//            CompoundTag t = new CompoundTag();
//            t.putUUID("UUID", entry.getKey());
//            t.put("PlayerQuestContainer", PlayerQuestContainer.save(new CompoundTag(), provider, entry.getValue()));
//            list.add(t);
//        }
//        tag.put("PlayerCobblemonQuests", list);

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

        // TODO: validate
        PokemonFieldLab.LOGGER.info("reroll");
        NetworkManager.sendToPlayer(player, new RefreshScreenPacket(true));

        return container.replaceQuest(index, Quest.getRandomQuest((ServerLevel) player.level()));
    }

    public static boolean hasQuest(ServerPlayer player, Quest quest) {
        PlayerQuestContainer container = getQuestContainer(player);
        return container.hasQuest(quest);
    }
}
