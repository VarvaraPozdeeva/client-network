package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElement;
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
    JButton addElement;
    AddInterfacePanel pan;
    AddLinkPanel linkPanel;
    AddElement elemPanel;

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
        addElement = new JButton("Add new element");
        textArea = new JTextArea();
        lock = new JButton("lock");
        lock.setBackground(Color.LIGHT_GRAY);
        unLock = new JButton("unlock");
        unLock.setBackground(Color.RED);
        pan = new AddInterfacePanel(model);




        textArea.setColumns(20);
        textArea.setRows(2);
        textArea.setPreferredSize(new Dimension(300,250));
        lock.setPreferredSize(new Dimension(150, 50));
        unLock.setPreferredSize(new Dimension(150,50));
        buttons.add(lock);
        buttons.add(unLock);
        buttons.add(addInterface);
        buttons.add(addLink);
        buttons.add(addElement);
        mpanel.setLayout(new BorderLayout());
        mpanel.add(buttons, BorderLayout.SOUTH);
        mpanel.add(textArea, BorderLayout.CENTER);

        createNElements();
        linkPanel = new AddLinkPanel(model);
        elemPanel = new AddElement(model);

        lock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getService().sendMessage(BLOCK, textArea.getText());

                lock.setBackground(Color.RED);
                unLock.setBackground(Color.LIGHT_GRAY);
            }
        });
        unLock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.getService().sendMessage(UNBLOCK, textArea.getText());
                unLock.setBackground(Color.RED);
                lock.setBackground(Color.LIGHT_GRAY);
            }
        });
        addInterface.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pan.setIdNe(textArea.getText());
                add(pan);
                revalidate();
            }
        });
        addLink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkPanel.setIdNe(textArea.getText());
                add(linkPanel);
                revalidate();
            }
        });
        addElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                add(elemPanel);
                revalidate();
            }
        });
        add(mpanel);
        add(elements);
    }

    private void createNElements() {

        elements.removeAll();

        model.getNetworkElements().forEach(e->{
//            NetworkElementButton el = new NetworkElementButton(e);
//
//            el.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    if(model.getService().isNotBlocking()){
//                        textArea.setText("");
//                        textArea.setText(el.getElement().getId());
//                    }
//                }
//            });
            RowNe el = new RowNe(e);
            el.getNameNe().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    textArea.setText("");
                    textArea.setText(el.getElement().getId());
                }
            });
            elements.add(el);
            elements.revalidate();
        });
    }

    @Override
    public void update() {
        createNElements();
    }
}
