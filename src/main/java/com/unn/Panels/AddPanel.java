package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPanel extends JPanel implements IObserver {

    private NetworkModel model;
//    private JLabel nameNeLable;
//    private JTextField neField;
    private JLabel nameIntLabel;
    private JLabel ipIntLabel;
    private JLabel macIntLabel;
    private String idNe;

    private JTextField ipAddress;
    private JTextField nameInterface;
    private JTextField macAddress;
    private JButton addButton;


    public AddPanel(NetworkModel model) {
        this.model = model;
        model.addObserver(this);
        init();
    }

    public void setIdNe(String idNe){
        this.idNe = idNe;
    }

    private void init() {
        nameIntLabel = new JLabel("Name Interface");
        nameInterface = new JTextField();
        nameInterface.setColumns(30);
        ipIntLabel = new JLabel("IP Address");
        ipAddress = new JTextField();
        ipAddress.setColumns(30);
        macIntLabel = new JLabel("MAC-Address");
        macAddress = new JTextField();
        macAddress.setColumns(30);
        addButton = new JButton("Add");
        add(nameIntLabel);
        add(nameInterface);
        add(ipIntLabel);
        add(ipAddress);
        add(macIntLabel);
        add(macAddress);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                if(neField.getText().equals("")){
//                    neField.setText("enter name");
//                }
//                else {
//                    model.addNetworkElement("{ \"name\": \"" + neField.getText()+ "\"}");
//                }
                model.addInterface("{ \"name\": \"" + nameInterface.getText()+ "\"," +
                        " \"ip-address\": \"" + ipAddress.getText() + "\"," +
                        "\"mac-address\": \"" + macAddress.getText() + "\" }", idNe);

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
