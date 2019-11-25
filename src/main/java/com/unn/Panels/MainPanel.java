package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElementButton;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.unn.Blocking.BLOCK;
import static com.unn.Blocking.UNBLOCK;

public class MainPanel extends JPanel implements IObserver {
    JTextArea textArea;
    JButton lock;
    JButton unLock;
    JPanel elements;
    NetworkModel model;

    public MainPanel(NetworkModel model) {
        this.model = model;
        model.addObserver(this);
        init();
    }
    private void init(){
        elements = new JPanel(new FlowLayout());
        JPanel buttons = new JPanel();
        textArea = new JTextArea();
        lock = new JButton("lock");
        lock.setBackground(Color.LIGHT_GRAY);
        unLock = new JButton("unlock");
        unLock.setBackground(Color.RED);

        textArea.setColumns(20);
        textArea.setRows(2);
        buttons.add(textArea);
        buttons.add(lock);
        buttons.add(unLock);

        createNElements();

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getService().sendMessage(BLOCK);
                lock.setBackground(Color.RED);
                unLock.setBackground(Color.LIGHT_GRAY);
            }
        });
        unLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getService().sendMessage(UNBLOCK);
                unLock.setBackground(Color.RED);
                lock.setBackground(Color.LIGHT_GRAY);
            }
        });
        setLayout(new FlowLayout());
        add(buttons);
        add(elements);
    }

    private void createNElements() {

        elements.removeAll();

        model.getNetworkElements().forEach(e->{
            NetworkElementButton el = new NetworkElementButton(e);
            el.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(model.getService().isNotBlocking()){
                        textArea.setText("name - " + el.getElement().getName() +
                                "\ntype - " + el.getElement().getType() );
                    }
                }
            });
            elements.add(el);
        });
    }

    @Override
    public void update() {
        createNElements();
    }
}
