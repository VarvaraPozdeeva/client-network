package com.unn.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@Value
@AllArgsConstructor()
public class Link {

    @JsonProperty("id")
    private String id;

//    @JsonProperty("interfaceA")
//    private final Interface interfaceA;
//    @JsonProperty("interfaceZ")
//    private final Interface interfaceZ;
    @JsonProperty("a-ne")
    private String neAName;

    @JsonProperty("z-ne")
    private String neZName;

    @JsonProperty("a-interface")
    private String interAName;

    @JsonProperty("z-interface")
    private String interZName;

}
