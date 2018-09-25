package com.epam.training.client;

public class BasicClient extends Client{
    BasicClient(Long clientId, String name) {
        super(clientId, name, null, null, null, null, Sex.UNKNOWN, Ethnicity.UNKNOWN);
    }
}
