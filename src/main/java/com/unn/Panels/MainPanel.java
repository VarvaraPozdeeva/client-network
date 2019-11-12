package com.unn.Panels;

import com.unn.Utils;
import sample.ClientMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {
    JTextArea textArea;
    JButton lock;
    JButton unLock;
    JPanel elements;
    Utils utils;

    public MainPanel(Utils utils) {
        this.utils = utils;
        utils.createSocket();
        init();
    }
    private void init(){
        elements = new JPanel(new FlowLayout());
        textArea = new JTextArea();
        lock = new JButton("lock");
        unLock = new JButton("unlock");

        elements.add(textArea);
        elements.add(lock);
        elements.add(unLock);

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.getSession().send("/app/hello", new ClientMessage("111", 1));
            }
        });
    }
}
