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
    JButton addLink;
    JButton addInterface;
    AddPanel pan;
    AddLinkPanel linkPanel;

    public MainPanel(NetworkModel model) {
        this.model = model;
        model.addObserver(this);
        init();
    }
    private void init(){
        elements = new JPanel();
        JPanel buttons = new JPanel();
        JPanel mpanel = new JPanel();
        addInterface = new JButton("Add Interface");
        addLink = new JButton("Add Link");
        textArea = new JTextArea();
        lock = new JButton("lock");
        lock.setBackground(Color.LIGHT_GRAY);
        unLock = new JButton("unlock");
        unLock.setBackground(Color.RED);
        pan = new AddPanel(model);

        textArea.setColumns(20);
        textArea.setRows(2);
        textArea.setPreferredSize(new Dimension(300,250));
        lock.setPreferredSize(new Dimension(150, 50));
        unLock.setPreferredSize(new Dimension(150,50));
        buttons.add(lock);
        buttons.add(unLock);
        buttons.add(addInterface);
        buttons.add(addLink);
        mpanel.setLayout(new BorderLayout());
        mpanel.add(buttons, BorderLayout.SOUTH);
        mpanel.add(textArea, BorderLayout.CENTER);

        createNElements();
        linkPanel = new AddLinkPanel(model);

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
        addInterface.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pan.setIdNe(textArea.getText());
                add(pan);

                //pan.setVisible(true);
            }
        });
        addLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkPanel.setIdNe(textArea.getText());
                add(linkPanel);
            }
        });
        //setLayout(new FlowLayout());
        add(mpanel);
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
                        textArea.setText("");
                        textArea.setText(el.getElement().getId());
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
