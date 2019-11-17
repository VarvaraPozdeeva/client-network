package com.unn.Panels;

import com.unn.Utils;
import com.unn.model.NetworkElementButton;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.unn.Blocking.BLOCK;
import static com.unn.Blocking.UNBLOCK;

public class MainPanel extends JPanel {
    JTextArea textArea;
    JButton lock;
    JButton unLock;
    JPanel elements;
    Utils utils;
    NetworkModel model;

    public MainPanel(Utils utils, NetworkModel model) {
        this.utils = utils;
        this.model = model;
        utils.createSocket();
        init();
    }
    private void init(){
        elements = new JPanel(new FlowLayout());
        textArea = new JTextArea();
        lock = new JButton("lock");
        lock.setBackground(Color.LIGHT_GRAY);
        unLock = new JButton("unlock");
        unLock.setBackground(Color.RED);

        textArea.setColumns(20);
        textArea.setRows(2);
        elements.add(textArea);
        elements.add(lock);
        elements.add(unLock);

        createNElements();

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.sendMessage(BLOCK);
                lock.setBackground(Color.RED);
                unLock.setBackground(Color.LIGHT_GRAY);
            }
        });
        unLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.sendMessage(UNBLOCK);
                unLock.setBackground(Color.RED);
                lock.setBackground(Color.LIGHT_GRAY);
            }
        });
        setLayout(new FlowLayout());
        add(elements);
    }

    private void createNElements() {

        model.getNetworkElements().forEach(e->{
            NetworkElementButton el = new NetworkElementButton(e);
            el.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(utils.isNotBlocking()){
                        textArea.setText("name - " + el.getElement().getName() +
                                "\ntype - " + el.getElement().getType() );
                    }
                }
            });
            elements.add(el);
        });
    }
}
