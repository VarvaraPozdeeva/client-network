package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.Link;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;
import com.unn.model.Route;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class InfoPanel extends Panel implements IObserver {
   private NetworkElement networkElement;
   List<Link> links;
   private NetworkModel model;
   private JLabel labelInterface;
   private JLabel labelLink;
   private JButton addInterface;
   private JButton deleteElement;
   private JButton saveElement;
   private AddInterfacePanel pan;


   InfoPanel(NetworkModel model) {
      addInterface = new JButton("Add Interface");
      deleteElement = new JButton("Delete Element");
      saveElement = new JButton("Save");


      this.model = model;
      model.addObserver(this);
      setLayout(new GridLayout(10,1));
      pan = new AddInterfacePanel(model);

      addInterface.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            pan.setIdNe(networkElement.getId());
            //add(pan);
            pan.setVisible(true);
            revalidate();
         }
      });

      saveElement.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            saveElement();
         }
      });

      deleteElement.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent actionEvent) {
            model.deleteNetworkElement(networkElement.getId());
            removeAll();
            revalidate();
         }
      });
   }

   public void setNetworkElement(NetworkElement ne){
      removeAll();
      revalidate();
      networkElement = ne;
      Map<String, List<Route>> routes = new HashMap<>();
      links = model.getLinks(ne.getId());

      JLabel interfaces = new JLabel("INTERFACES");
      add(interfaces);
      networkElement.getInterfaces().forEach(inter->{

         routes.put(inter.getName(), model.getRoutes(inter.getId()));
         JLabel name = new JLabel(inter.getName());
         JLabel ip = new JLabel(inter.getIpAddress());
         JLabel mac = new JLabel(inter.getMacAddress());
         JPanel interfacePanel = new JPanel();
         interfacePanel.add(name);
         interfacePanel.add(ip);
         interfacePanel.add(mac);
         add(interfacePanel);
      });

      /*JLabel linksLabel = new JLabel("LINKS");
      add(linksLabel);
      links.forEach(link->{

         JLabel elementA = new JLabel(link.getNeAName()+" - ");
         JLabel elementZ = new JLabel(link.getNeZName()+" - ");
         JLabel l = new JLabel("-- >");
         JLabel interfaceA = new JLabel(link.getInterAName());
         JLabel interfaceZ = new JLabel(link.getInterZName());
         JPanel linkPanel = new JPanel();
         linkPanel.add(elementA);
         linkPanel.add(interfaceA);
         linkPanel.add(l);
         linkPanel.add(elementZ);
         linkPanel.add(interfaceZ);
         add(linkPanel);
      });*/
/*
      routes.forEach((key, route)->{
         JLabel interfaceA = new JLabel(key);
         JPanel rP = new JPanel(new GridLayout(route.size()+1, 1 ));
         rP.add(interfaceA);
         route.forEach(r->{
            JLabel elementZ = new JLabel(r.getNe()+" - ");
            JLabel interfaceZ = new JLabel(r.getInter());
            JLabel hops = new JLabel( "hops = " + r.getHops());
            JPanel linkPanel = new JPanel();
            linkPanel.add(elementZ);
            linkPanel.add(interfaceZ);
            linkPanel.add(hops);
            rP.add(linkPanel);

         });

         add(rP) ;
      });*/

      revalidate();
      repaint();
   }

   public void editElement(){
      JPanel buttonPanel = new JPanel();
      buttonPanel.setLayout(new FlowLayout());
      buttonPanel.add(addInterface);
      buttonPanel.add(deleteElement);
      buttonPanel.add(saveElement);
      add(buttonPanel);
      repaint();
   }

   public void saveElement() {
      model.releaseElement(networkElement.getId());
      remove(addInterface);
      remove(deleteElement);
      remove(saveElement);
      repaint();
   }

   @Override
   public void update() {
      model.getNetworkElements().forEach(element->{
         if (networkElement.getId().equals(element.getId())){
            setNetworkElement(element);
         }
      });
      repaint();
   }
}
