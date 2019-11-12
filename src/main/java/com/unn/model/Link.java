package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Link {
    private String id;

    @JsonProperty("interfaceA")
    private final Interface interfaceA;
    @JsonProperty("interfaceZ")
    private final Interface interfaceZ;
    @JsonProperty("a-ne")
    private String neAName;

    @JsonProperty("z-ne")
    private String neZName;

    @JsonProperty("a-interface")
    private String interAName;

    @JsonProperty("z-interface")
    private String interZName;

}
