package com.epam.training.client;

public class VipClient extends Client {
    VipClient(Long clientId, String name) {
        super(clientId, name,null,null,null,null,Sex.Unknown,Ethnicity.Unknown);
    }
}
