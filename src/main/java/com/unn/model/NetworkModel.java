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
       networkElements =  service.getNetworkElements();
       links = service.getLinks();
    }

    public void addObserver(IObserver obs){
        observers.add(obs);
    }

    public NetworkElement addNetworkElement(String ne){
        NetworkElement netElem = service.addNetworkElement(ne);
        networkElements.add(netElem);
        update();
        return  netElem;
    }

    public NetworkElement addNetworkElementWithInterface(String ne, String inter){
        NetworkElement netElem = service.addNetworkElement(ne);
        Interface i = service.addInterface(inter, netElem.getId());
        netElem.setInterfaces(new ArrayList<Interface>());
        netElem.getInterfaces().add(i);
        networkElements.add(netElem);
        update();
        return  netElem;
    }

    public String addInterface(String inter, String idNe) {
        Interface i = service.addInterface(inter, idNe);
        networkElements.forEach(ne->{
            if (ne.getId().equals(idNe)) {
                ne.addInterface(i);
            }
        });
        update();
        return inter;
    }

    public String addLink(String link) {
        Link l = service.addLink(link);
        links.add(l);
        update();
        return link;
    }

    private void update() {
        observers.forEach(IObserver::update);
    }
}
