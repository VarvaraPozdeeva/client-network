package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPanel extends JPanel implements IObserver {

    private NetworkModel model;
    private JLabel nameNeLable;
    private JTextField neField;
    private JButton addButton;

    public AddPanel(NetworkModel model) {
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        nameNeLable = new JLabel("Enter Network Element name");
        neField = new JTextField();
        neField.setColumns(30);
        addButton = new JButton("Add");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(neField.getText().equals("")){
                    neField.setText("enter name");
                }
                else {
                    model.addNetworkElement("{ \"name\": \"" + neField.getText()+ "\"}");
                }
            }
        });

        setLayout(new FlowLayout());
        add(nameNeLable);
        add(neField);
        add(addButton);
    }

    @Override
    public void update() {

    }
}
