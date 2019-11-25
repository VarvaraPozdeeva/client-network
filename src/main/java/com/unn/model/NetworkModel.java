package com.unn.model;

import com.unn.IObserver;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NetworkModel {
    List<NetworkElement> networkElements;
    List<Link> links;

    List<IObserver> observers = new ArrayList<>();
    Service service = new Service();

    public NetworkModel() {
       networkElements =  service.getNenworkElements();
       links = service.getLinks();
    }

    public void addObserver(IObserver obs){
        observers.add(obs);
    }

    public String addNetworkElement(String ne){
        NetworkElement netElem = service.addNetworkElement(ne);
        networkElements.add(netElem);
        update();
        return  ne;
    }

    private void update() {
        observers.forEach(IObserver::update);
    }
}
