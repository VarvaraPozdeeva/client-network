package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class NetworkElement {
    private String id;

    private String name;
    private String type;
    private String vendor;
    @JsonProperty("interfaces")
    private List<Interface> interfaces;

    @Override
    public String toString() {
        return "NetworkElement{" +
                "id='" + id + '\'' +
                ",\nname='" + name + '\'' +
                ",\ntype='" + type + '\'' +
                ",\nvendor='" + vendor + '\'' +
                ",\ninterfaces=" + interfaces +
                '}';
    }
}
