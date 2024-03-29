package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class NetworkElement {
    private String id;
    private String name;
    private String type;
    private String vendor;
    @JsonProperty("interfaces")
    private List<Interface> interfaces;

}
