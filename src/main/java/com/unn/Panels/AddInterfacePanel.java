package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInterfacePanel extends JDialog {
    private NetworkModel model;

    private JLabel nameIntLabel;
    private JLabel ipIntLabel;
    private JLabel macIntLabel;
    private String idNe;

    private JTextField ipAddress;
    private JTextField nameInterface;
    private JTextField macAddress;

    private JButton addButton;


    public AddInterfacePanel(NetworkModel model) {
        this.model = model;
        init();
        setVisible(false);
        setLocationRelativeTo(null);
        setSize(350, 200);
    }

    public void setIdNe(String idNe){
        this.idNe = idNe;
    }

    private void init() {
        JPanel labelPanel = new JPanel();
        JPanel mPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        addButton = new JButton("Add");

        nameIntLabel = new JLabel("Name Interface");
        nameInterface = new JTextField();
        nameInterface.setColumns(30);

        ipIntLabel = new JLabel("IP Address");
        ipAddress = new JTextField();
        ipAddress.setColumns(30);

        macIntLabel = new JLabel("MAC-Address");
        macAddress = new JTextField();
        macAddress.setColumns(30);

        labelPanel.setLayout(new GridLayout(3,2));
        //labelPanel.setPreferredSize(new Dimension(400, 300));
        labelPanel.add(nameIntLabel);
        labelPanel.add(nameInterface);
        labelPanel.add(ipIntLabel);
        labelPanel.add(ipAddress);
        labelPanel.add(macIntLabel);
        labelPanel.add(macAddress);

        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);

        mPanel.setLayout(new BorderLayout(5,10));
        mPanel.add(labelPanel, BorderLayout.CENTER);
        mPanel.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addInterface("{ \"name\": \"" + nameInterface.getText()+ "\"," +
                        " \"ip-address\": \"" + ipAddress.getText() + "\"," +
                        "\"mac-address\": \"" + macAddress.getText() + "\" }", idNe);

            }
        });
        add(mPanel);
    }
}
