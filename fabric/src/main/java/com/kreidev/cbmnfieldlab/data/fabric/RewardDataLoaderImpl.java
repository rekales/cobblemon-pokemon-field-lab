package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.RewardDataLoader;

@SuppressWarnings("unused")
public class RewardDataLoaderImpl {
    public static RewardDataLoader getInstance() {
        return RewardDataLoaderFabric.INSTANCE;
    }
}