package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends Panel implements IObserver {
   private NetworkElement neEl;
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

   @Override
   public void update() {

   }
}
