package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.Link;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Data
public class InfoPanel extends Panel implements IObserver {
   private NetworkElement networkElement;
   List<Link> links;
   private NetworkModel model;
   private JLabel nameEl;
   private JLabel typeEl;
   private JLabel nameNeEl;
   private JLabel typeNeEl;

   InfoPanel(NetworkModel model) {
      this.model = model;
   }

   private void init() {

   }

   public void setNetworkElement(NetworkElement ne){
      networkElement =  ne;
      links = model.getLinks(ne.getId());
   }

   @Override
   public void update() {

   }
}
