package com.unn.Panels;

import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddElement extends JDialog {
    private NetworkModel model;

    private JLabel header;
    private JLabel nameNeLable;
    private JLabel typeNeLable;
    private JLabel nameIntLabel;
    private JLabel ipIntLabel;
    private JLabel macIntLabel;

    private JTextField neField;
    private JTextField typeField;
    private JTextField ipAddress;
    private JTextField nameInterface;
    private JTextField macAddress;

    private JButton addButton;


    public AddElement(NetworkModel model){
        this.model = model;
        init();
        setVisible(false);
        setLocationRelativeTo(null);
        setSize(600, 500);
    }

    public void init(){
        JPanel tlPanel = new JPanel();
        JPanel mainPan = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel namePanel = new JPanel();

        addButton = new JButton("Save");

        header = new JLabel("Adding new element");
        nameNeLable = new JLabel("Name element:");
        typeNeLable = new JLabel("Type");
        nameIntLabel = new JLabel("Name Interface");
        ipIntLabel = new JLabel("IP Address");
        macIntLabel = new JLabel("MAC-Address");

        neField = new JTextField();
        typeField = new JTextField();
        nameInterface = new JTextField();
        ipAddress = new JTextField();
        macAddress = new JTextField();

        macAddress.setColumns(30);
        macAddress.setPreferredSize(new Dimension(30, 5));
        nameInterface.setColumns(30);
        ipAddress.setColumns(30);
        neField.setColumns(30);
        typeField.setColumns(30);
        addButton.setMaximumSize(new Dimension(50, 25));

        tlPanel.setLayout(new GridLayout(5,2));
        tlPanel.setPreferredSize(new Dimension(400, 300));
        tlPanel.add(nameNeLable);
        tlPanel.add(neField);
        tlPanel.add(typeNeLable);
        tlPanel.add(typeField);
        tlPanel.add(nameIntLabel);
        tlPanel.add(nameInterface);
        tlPanel.add(ipIntLabel);
        tlPanel.add(ipAddress);
        tlPanel.add(macIntLabel);
        tlPanel.add(macAddress);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        namePanel.setLayout(new FlowLayout());
        namePanel.add(header);
        mainPan.setLayout(new BorderLayout(0,20));
        mainPan.add(namePanel, BorderLayout.NORTH);
        mainPan.add(tlPanel, BorderLayout.CENTER);
        mainPan.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(neField.getText().equals("")){
                    neField.setText("enter name");
               }
                else {
                    NetworkElement ne = model.addNetworkElementWithInterface("{ \"name\": \"" + neField.getText()+ "\", "+
                " \"type\": \"" + typeField.getText() + "\"}", "{ \"name\": \"" + nameInterface.getText()+ "\"," +
                            " \"ip-address\": \"" + ipAddress.getText() + "\"," +
                            "\"mac-address\": \"" + macAddress.getText() + "\" }" );
                    dispose();
                    neField.setText("");
                    typeField.setText("");
                    nameInterface.setText("");
                    ipAddress.setText("");
                    macAddress.setText("");
                }
            }
        });

        add(mainPan);
    }
}
