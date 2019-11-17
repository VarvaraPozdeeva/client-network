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

    @Override
    public String toString() {
        return "Interface{" +
                "id='" + id + '\'' +
                ", \nname='" + name + '\'' +
                ",\n macAddress='" + macAddress + '\'' +
                ", \nipAddress='" + ipAddress + '\'' +
                '}';
    }
}
