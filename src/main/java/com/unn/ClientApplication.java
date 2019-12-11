package com.unn;

import com.unn.Panels.AddPanel;
import com.unn.Panels.MainPanel;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;

public class ClientApplication extends JFrame {

    NetworkModel networkModel;

    public ClientApplication(){
        super("Network");

        networkModel = new NetworkModel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
        pack();
        setVisible(true);
    }

    private void initComponent() {
        Font font = new Font("Verdana", Font.PLAIN, 10);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());


        JPanel main = new MainPanel(networkModel);
        //JPanel add = new AddPanel(networkModel);
        tabbedPane.addTab("Main", main);
        //tabbedPane.addTab("Add", add);

        content.add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(content);
    }


    public static void main(String[] args) {
        System.out.println();
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                new ClientApplication();
            }
        });
    }
}
