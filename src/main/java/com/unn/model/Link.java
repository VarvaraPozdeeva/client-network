package com.unn.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Link {

    @JsonProperty("id")
    private String id;

//    @JsonProperty("interfaceA")
    private  Interface interfaceA;
//    @JsonProperty("interfaceZ")
    private  Interface interfaceZ;
    @JsonProperty("a-ne")
    private String neAName;

    @JsonProperty("z-ne")
    private String neZName;

    @JsonProperty("a-interface")
    private String interAName;

    @JsonProperty("z-interface")
    private String interZName;

   /*
   "{ \"a-ne\": \" "+ + "\","+
            "\"z-ne\": \""  + + "\","+
            "\"a-interface\": \"" + + "\","+
            "\"z-interface\": \" "+ +"\" }"
   */
}
