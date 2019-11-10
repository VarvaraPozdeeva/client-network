package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Interface {
    private String id;

    private String name;

    @JsonProperty("mac-address")
    private String macAddress;

    @JsonProperty("ip-address")
    private String ipAddress;
}
