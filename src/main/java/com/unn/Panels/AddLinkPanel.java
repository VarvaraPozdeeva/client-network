package com.unn.Panels;

import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddLinkPanel extends JDialog {

    private NetworkModel model;
    private NetworkElement neA;
    private NetworkElement neZ;

    private JLabel aInterface;
    private JLabel zInterface;

    private JComboBox aIntCombo;
    private JComboBox zIntCombo;

    private JButton addButton;

    private JPanel mainPanel;


    public AddLinkPanel(NetworkModel model, List<NetworkElement> networkElements) {
        this.model = model;
        neA = networkElements.get(0);
        neZ = networkElements.get(1);
        init();
        setVisible(false);
        setLocationRelativeTo(null);
        setSize(600, 500);
    }

    private void init() {
        mainPanel = new JPanel();
        JPanel lcPanel = new JPanel();
        JPanel savePanel = new JPanel();
        aInterface = new JLabel("Interface A");
        zInterface = new JLabel("Interface Z");

        List<String> namesA = new ArrayList<>();
        neA.getInterfaces().forEach(inter -> namesA.add(inter.getName()));
        List<String> namesZ = new ArrayList<>();
        neZ.getInterfaces().forEach(inter -> namesZ.add(inter.getName()));

        aIntCombo = new JComboBox(namesA.toArray());
        zIntCombo = new JComboBox(namesZ.toArray());

        addButton = new JButton("Save");

        lcPanel.setLayout(new GridLayout(1,2));
        lcPanel.add(aInterface);
        lcPanel.add(aIntCombo);
        lcPanel.add(zInterface);
        lcPanel.add(zIntCombo);

        savePanel.setLayout(new FlowLayout());
        savePanel.add(addButton);

        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(lcPanel, BorderLayout.CENTER);
        mainPanel.add(savePanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addLink("{ \"a-ne\": \""+ neA.getName() + "\","+
                        "\"z-ne\": \""  + neZ.getName() + "\","+
                        "\"a-interface\": \"" + aIntCombo.getSelectedItem() + "\","+
                        "\"z-interface\": \""+ zIntCombo.getSelectedItem() +"\" }");
            }
        });
        add(mainPanel);
    }
}