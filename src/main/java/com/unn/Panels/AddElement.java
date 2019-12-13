package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddElement extends JDialog implements IObserver {
    private NetworkModel model;
    private JLabel header;
    private JLabel nameNeLable;
    private JLabel typeNeLable;
    private JTextField neField;
    private JTextField typeField;
    private JButton addButton;
    private JPanel addPanel;


    public AddElement(NetworkModel model){
        this.model = model;
        model.addObserver(this);
        init();
        setVisible(false);
        setLocationRelativeTo(null);
        setSize(600, 500);
    }

    public void init(){
        JPanel tlPanel = new JPanel();
        JPanel textFPan = new JPanel();
        JPanel mainPan = new JPanel();
        addPanel = new JPanel();
        addButton = new JButton("Save");
        header = new JLabel("Adding new element");
        nameNeLable = new JLabel("Name element:");
        typeNeLable = new JLabel("Type");
        neField = new JTextField();
        typeField = new JTextField();
        neField.setColumns(30);
        typeField.setColumns(30);


        //neField.setPreferredSize(new Dimension(300, 50));
       // textFPan.add(nameNeLable);
        //textFPan.add(neField);
        //textFPan.add(typeNeLable);
        //textFPan.add(typeField);
        mainPan.setLayout(new BorderLayout());
        tlPanel.setLayout(new GridLayout(2,2));
        tlPanel.setSize(400, 300);
        tlPanel.add(nameNeLable);
        tlPanel.add(neField);
        tlPanel.add(typeNeLable);
        tlPanel.add(typeField);

        mainPan.add(header, BorderLayout.NORTH);
        mainPan.add(tlPanel, BorderLayout.CENTER);
        mainPan.add(addButton, BorderLayout.SOUTH);

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

        //setLayout(new FlowLayout());
        //addPanel.add(nameNeLable);
       // addPanel.add(neField);
       // addPanel.add(addButton);
        add(mainPan);
    }

    @Override
    public void update() {

    }
}
