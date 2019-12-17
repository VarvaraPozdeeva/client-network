package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements IObserver {
    private InfoPanel infoPanel;
    private JButton edit;
    private JButton addLinkButton;
    private JPanel elements;
    private NetworkModel model;
    private JButton addElement;
    private String neID ="";
    private GridLayout g;

    private AddElement elemPanel;
    private List<RowNe> listNe;

    public MainPanel(NetworkModel model) {
        listNe = new ArrayList<>();
        this.model = model;
        model.addObserver(this);
        init();
    }
    private void init(){
        elements = new JPanel();
        JPanel buttons = new JPanel();
        JPanel mpanel = new JPanel();
        JPanel biPanel = new JPanel();
        JPanel bPanfI = new JPanel();

        addElement = new JButton("Add new element");

        edit = new JButton("edit");
        addLinkButton = new JButton("Add new Link");
        infoPanel = new InfoPanel(model);

        buttons.add(edit);
        buttons.add(addElement);
        buttons.add(addLinkButton);

        g = new GridLayout(model.getNetworkElements().size(), 1);
        elements.setLayout(g);
        Border infBor = BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), neID),
                        BorderFactory.createEmptyBorder(30, 30, 30, 30));
        bPanfI.setBorder(infBor);
        bPanfI.add(infoPanel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(bPanfI);
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(600,400));
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane);

        biPanel.setLayout(new BorderLayout(0,20));
        biPanel.add(buttons, BorderLayout.NORTH);
        biPanel.add(panel, BorderLayout.CENTER);
        mpanel.setLayout(new GridLayout(1,2));
        mpanel.add(biPanel);
        mpanel.add(elements);

        createNElements();
        elemPanel = new AddElement(model);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.canShow(neID)) {
                    model.lockElement(neID);
                    infoPanel.editElement();
                    revalidate();
                }else {
                    JOptionPane.showMessageDialog(null, "ELEMENT IS LOCKED");
                }
            }
        });
       /* save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.releaseElement(neID);
            }
        });*/

        addElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               elemPanel.setVisible(true);
                revalidate();
            }
        });
        List<NetworkElement> networkElements = new ArrayList<>();
        addLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listNe.forEach(el->{
                    if(el.isSelected()){
                        networkElements.add(el.getElement());
                    }
                });
                if(networkElements.size()!=2){
                    networkElements.removeAll(networkElements);
                    JOptionPane.showMessageDialog(null, "NEED SELECTED TWO ELEMENTS");
                }
                else {
                    model.lockElements(networkElements.get(0).getId(), networkElements.get(1).getId());
                    System.out.println("LOCK ELEMENTS");
                    AddLinkPanel linkPanel = new AddLinkPanel(model, networkElements);
                    networkElements.removeAll(networkElements);
                    linkPanel.setVisible(true);
                }
            }
        });

        add(mpanel);
    }

    private void createNElements() {
        g.setRows(model.getNetworkElements().size());
        elements.removeAll();
        listNe.removeAll(listNe);
        model.getNetworkElements().forEach(e->{
            RowNe el = new RowNe(e);
            el.getNameNe().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        if( model.canShow(el.getElement().getId()) ) {
                            neID = el.getElement().getId();
                            infoPanel.setNetworkElement(el.getElement());
                        }else{
                            JOptionPane.showMessageDialog(null, "ELEMENT IS LOCKED");
                        }
                    }
                });
            listNe.add(el);
            elements.add(el);
            elements.revalidate();
        });
    }

    @Override
    public void update() {
        createNElements();
    }
}
