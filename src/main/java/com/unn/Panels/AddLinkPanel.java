package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddLinkPanel extends JPanel implements IObserver {

    private NetworkModel model;
    //    private JLabel nameNeLable;
//    private JTextField neField;
    private JLabel aElement;
    private JLabel aInterface;
    private JLabel zElement;
    private JLabel zInterface;
    private String idNe;

    private JTextField aElCombo;
    private JTextField aIntCombo;
    private JTextField zElCombo;
    private JTextField zIntCombo;
    private JButton addButton;


    public AddLinkPanel(NetworkModel model) {
        this.model = model;
        model.addObserver(this);
        init();
    }

    public void setIdNe(String idNe){
        this.idNe = idNe;
    }

    private void init() {
        aElement = new JLabel("A-Element");
        aElCombo = new JTextField();
        aElCombo.setColumns(30);
        aInterface = new JLabel("A-Interface");
        aIntCombo = new JTextField();
        aIntCombo.setColumns(30);
        zElement = new JLabel("Z-Element");
        zElCombo = new JTextField();
        zElCombo.setColumns(30);
        zInterface = new JLabel("Z-Interface");
        zIntCombo = new JTextField();
        zIntCombo.setColumns(30);
        addButton = new JButton("Add");
        add(aElement);
        add(aElCombo);
        add(aInterface);
        add(aIntCombo);
        add(zElement);
        add(zElCombo);
        add(zInterface);
        add(zIntCombo);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(neField.getText().equals("")){
//                    neField.setText("enter name");
//                }
//                else {
//                    model.addNetworkElement("{ \"name\": \"" + neField.getText()+ "\"}");
//                }
                model.addInterface("{ \"name\": \"" + aIntCombo.getText()+ "\"," +
                        " \"ip-address\": \"" + aElCombo.getText() + "\"," +
                        "\"mac-address\": \"" + zElCombo.getText() + "\" }", idNe);

            }
        });

        setLayout(new FlowLayout());
//        add(nameNeLable);
//        add(neField);
        add(addButton);
    }

    @Override
    public void update() {

    }
}