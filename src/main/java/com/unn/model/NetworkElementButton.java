package com.unn.model;

import lombok.Data;

import javax.swing.*;

@Data
public class NetworkElementButton extends JButton {
   private NetworkElement element;

    public NetworkElementButton(NetworkElement element) {
        this.element = element;
        setText(element.getName());
    }
}
