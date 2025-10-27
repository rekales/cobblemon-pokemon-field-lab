package com.kreidev.cbmnfieldlab.data.neoforge;

import com.kreidev.cbmnfieldlab.data.DataLoader;

@SuppressWarnings("unused")
public class DataLoaderImpl {
    public static DataLoader getInstance() {
        return DataLoader.INSTANCE;
    }
}