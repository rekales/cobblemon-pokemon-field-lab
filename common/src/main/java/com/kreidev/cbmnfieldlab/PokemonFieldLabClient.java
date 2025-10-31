package com.kreidev.cbmnfieldlab;

import com.kreidev.cbmnfieldlab.network.FieldLabNetworkManagerClient;

public class PokemonFieldLabClient {
    public static void initClient() {
        // NOTE: invoked by NeoForge common entrypoint.
        FieldLabNetworkManagerClient.registerPacketsClient();
    }
}
