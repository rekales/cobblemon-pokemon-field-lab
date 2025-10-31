package com.kreidev.cbmnfieldlab.data.neoforge;

import com.kreidev.cbmnfieldlab.data.ConditionDataLoader;

@SuppressWarnings("unused")
public class ConditionDataLoaderImpl {
    public static ConditionDataLoader getInstance() {
        return ConditionDataLoader.INSTANCE;
    }
}