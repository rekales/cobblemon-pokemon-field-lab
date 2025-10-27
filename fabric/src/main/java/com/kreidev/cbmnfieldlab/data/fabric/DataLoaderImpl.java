package com.kreidev.cbmnfieldlab.data.fabric;

import com.kreidev.cbmnfieldlab.data.DataLoader;

@SuppressWarnings("unused")
public class DataLoaderImpl {
    public static DataLoader getInstance() {
        return DataLoaderFabric.INSTANCE;
    }
}