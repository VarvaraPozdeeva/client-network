package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddElement extends JPanel implements IObserver {
    private NetworkModel model;
    private JLabel nameNeLable;
    private JTextField neField;
    private JButton addButton;


    public AddElement(NetworkModel model){
        this.model = model;
        model.addObserver(this);
        init();
    }

    public void init(){
        addButton = new JButton("Save");
        nameNeLable = new JLabel("Name element:");
        neField = new JTextField();
        neField.setColumns(30);

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
