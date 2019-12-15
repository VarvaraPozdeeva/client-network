package com.unn.model;

import com.unn.Status;
import com.unn.IObserver;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NetworkModel {
    List<NetworkElement> networkElements;
    List<Link> links;

    List<IObserver> observers = new ArrayList<>();
    Service service = new Service(this);

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

    public void updateFromServer(){
        networkElements =  service.getNetworkElements();
        links = service.getLinks();
        observers.forEach(IObserver::update);
    }

    public void update() {
        service.sendMessage(Status.UPDATE);
        observers.forEach(IObserver::update);
    }

    public void lockElement(String idNe) {
        service.sendMessage(Status.LOCK, idNe);
    }
    public void lockElements(String idNeA, String idNeZ) {
        service.sendMessage(Status.LOCK_FOR_LINK, idNeA, idNeZ);
    }

    public void releaseElement(String idNe) {
        service.sendMessage(Status.UNLOCK, idNe);
    }
    public void releaseElements(String idNeA, String idNeZ) {
        service.sendMessage(Status.UNLOCK_FOR_LINK, idNeA, idNeZ);
    }

    public boolean canShow(String idNe) {
        return !service.getLockingElements().contains(idNe);
    }

    public List<Link> getLinks(String id) {
        return service.getLinks(id);
    }

    public List<Route> getRoutes(String id) {
        return service.getRoutes(id);
    }
}
