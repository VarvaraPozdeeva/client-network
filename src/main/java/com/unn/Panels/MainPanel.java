package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements IObserver {
    private JTextArea textArea;
    private InfoPanel infoPanel;
    private JButton edit;
    private JButton save;
    private JButton addLinkButton;
    private JPanel elements;
    private NetworkModel model;
    private JButton addElement;
    GridLayout g;

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

        addElement = new JButton("Add new element");

        textArea = new JTextArea();
        edit = new JButton("edit");
        edit.setBackground(Color.LIGHT_GRAY);
        save = new JButton("save");
        save.setBackground(Color.RED);
        addLinkButton = new JButton("Add new Link");
        infoPanel = new InfoPanel(model);

        textArea.setColumns(20);
        textArea.setRows(2);
        textArea.setPreferredSize(new Dimension(300,250));
        edit.setPreferredSize(new Dimension(150, 50));
        save.setPreferredSize(new Dimension(150,50));
        buttons.add(edit);
        buttons.add(save);
        buttons.add(addElement);

        g = new GridLayout(model.getNetworkElements().size(), 1);
        elements.setLayout(g);
        mpanel.setLayout(new BorderLayout(5,5));
        buttons.add(addLinkButton);
        mpanel.setLayout(new BorderLayout());
        mpanel.add(buttons, BorderLayout.SOUTH);
        mpanel.add(infoPanel, BorderLayout.CENTER);
        mpanel.add(elements , BorderLayout.EAST);

        createNElements();
        elemPanel = new AddElement(model);
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.lockElement(textArea.getText());
                infoPanel.editElement();
                revalidate();
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.releaseElement(textArea.getText());
            }
        });

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
                public void actionPerformed(ActionEvent actionEvent) {
                    infoPanel.setNetworkElement(el.getElement());
                    if( model.canShow(el.getElement().getId()) ) {
                        textArea.setText("");
                        textArea.setText(el.getElement().getId());
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
