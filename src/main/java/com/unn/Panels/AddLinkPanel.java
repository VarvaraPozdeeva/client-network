package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.Interface;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddLinkPanel extends JPanel implements IObserver {

    private NetworkModel model;
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
        String aName="";
        Iterator iter = model.getNetworkElements().iterator();
        List<NetworkElement> elements = new ArrayList<>(); /// список элементов для комбобокса для z эдементов
        List<Interface> zInterfaces = new ArrayList<>(); /// список интерфейсов для комбобокса для z интерфейсов
        List<Interface> aInterfaces = new ArrayList<>(); /// список интерфейсов для комбобокса для ф интерфейсов
        while(iter.hasNext()){
            NetworkElement ne = (NetworkElement) iter.next();
            if(ne.getId().equals(idNe)) {
                aName = ne.getName();
                aInterfaces =ne.getInterfaces();
            }
            else {
                elements.add(ne);
                zInterfaces = ne.getInterfaces();// этого не будт тк, сейчас у нас только два элемента, вообще говоря
                // их много и мы не знаем какой выбирут.
            }
        }
        aElCombo.setText(aName);

        aIntCombo.setText(aInterfaces.get(0).getName());//зget не от 0,
        // соответственно, а у кажого комбоэлемента свой потом по выбранному
        // имени должен искаться ид для вставдения в строчку добавления линки, аналогично для з

        zElCombo.setText(elements.get(0).getName());

        zIntCombo.setText(zInterfaces.get(0).getName());
    }

    private void init() {

        setLayout(new GridLayout(5,2));
        aElement = new JLabel("A-Element");



        aElCombo = new JTextField();
        aElCombo.setColumns(30) ;
        aInterface = new JLabel("A-Interface");
        aIntCombo = new JTextField();
        aIntCombo.setColumns(30);
        zElement = new JLabel("Z-Element");


        zElCombo = new JTextField();
        zElCombo.setColumns(30);
        zInterface = new JLabel("Z-Interface"); // меняются данные в зависимости от выбранного элемента
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

                model.addLink("{ \"a-ne\": \"" + aElCombo.getText()+ "\"," +
                        " \"z-ne\": \"" + zElCombo.getText() + "\"," +
                        "\"a-interface\": \"" + aIntCombo.getText() + "\"," +
                        "\"z-interface\": \"" + zIntCombo.getText() + "\" }");

            }
        });

        add(addButton);
    }

    @Override
    public void update() {

    }
}