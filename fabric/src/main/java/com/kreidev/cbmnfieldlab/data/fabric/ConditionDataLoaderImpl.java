package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.ConditionDataLoader;

@SuppressWarnings("unused")
public class ConditionDataLoaderImpl {
    public static ConditionDataLoader getInstance() {
        return ConditionDataLoaderFabric.INSTANCE;
    }
}