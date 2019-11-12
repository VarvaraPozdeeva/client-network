package com.unn;

import com.unn.Panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class ClientApplication extends JFrame {

    Utils utils = new Utils();

    public ClientApplication(){
        super("Network");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponent();
        setPreferredSize(new Dimension(400, 500));
        pack();
        setVisible(true);
    }

    private void initComponent() {
        Font font = new Font("Verdana", Font.PLAIN, 10);
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(font);
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());

        JPanel main = new MainPanel(utils);

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
