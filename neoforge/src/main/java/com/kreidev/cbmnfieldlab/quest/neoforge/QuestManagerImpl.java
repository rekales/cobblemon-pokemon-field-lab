package com.kreidev.cbmnfieldlab.quest.neoforge;

import com.kreidev.cbmnfieldlab.quest.QuestData;

public class QuestManagerImpl {

    public static QuestData getInstance() {
        return SavedQuestData.INSTANCE;
    }
}