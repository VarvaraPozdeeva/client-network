package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Link {
    private String id;

    private final Interface interfaceA;
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
