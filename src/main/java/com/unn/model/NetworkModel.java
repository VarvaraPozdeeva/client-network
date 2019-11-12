package com.unn.model;

import lombok.Data;

import java.util.List;

@Data
public class NetworkModel {
    List<NetworkElement> networkElements;
    List<Interface> interfaces;
    List<Link> links;
}
