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

    public String addNetworkElement(String ne){
        NetworkElement netElem = service.addNetworkElement(ne);
        networkElements.add(netElem);
        update();
        return  ne;
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

    public String deleteNetworkElement(String idNe){
        NetworkElement ne = service.deleteNetworkElement(idNe);
        networkElements.remove(ne);
        links = service.getLinks();
        update();
        return idNe;
    }

    public String deleteLink(String idLink){
        Link l = service.deleteLink(idLink);
        links.remove(l);
        update();
        return idLink;
    }

    private void update() {
        observers.forEach(IObserver::update);
    }
}
