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
    private  JButton addLinkButton;
    private JButton saveLinkButton;
    private JPanel elements;
    private NetworkModel model;
    private JButton addInterface;
    private JButton addElement;
    private JButton deleteElement;
    private AddInterfacePanel pan;
    private AddLinkPanel linkPanel;
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
        addInterface = new JButton("Add Interface");
        addElement = new JButton("Add new element");
        deleteElement = new JButton("Delete element");
        textArea = new JTextArea();
        edit = new JButton("edit");
        edit.setBackground(Color.LIGHT_GRAY);
        save = new JButton("save");
        save.setBackground(Color.RED);
        addLinkButton = new JButton("Add new Link");
        saveLinkButton = new JButton("save Link");
        pan = new AddInterfacePanel(model);
        infoPanel = new InfoPanel(model);

        textArea.setColumns(20);
        textArea.setRows(2);
        textArea.setPreferredSize(new Dimension(300,250));
        edit.setPreferredSize(new Dimension(150, 50));
        save.setPreferredSize(new Dimension(150,50));
        buttons.add(edit);
        buttons.add(save);
        buttons.add(addInterface);
        buttons.add(addElement);

        elements.setLayout(new GridLayout(8, 1));
        mpanel.setLayout(new BorderLayout(5,5));
        buttons.add(addLinkButton);
        buttons.add(saveLinkButton);
        buttons.add(deleteElement);
        mpanel.setLayout(new BorderLayout());
        mpanel.add(buttons, BorderLayout.SOUTH);
        mpanel.add(infoPanel);
        mpanel.add(textArea, BorderLayout.CENTER);
        mpanel.add(elements , BorderLayout.EAST);

        createNElements();
        linkPanel = new AddLinkPanel(model);
        elemPanel = new AddElement(model);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.lockElement(textArea.getText());
                edit.setBackground(Color.RED);
                save.setBackground(Color.LIGHT_GRAY);
            }
        });
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.releaseElement(textArea.getText());
                save.setBackground(Color.RED);
                edit.setBackground(Color.LIGHT_GRAY);
            }
        });
        addInterface.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                pan.setIdNe(textArea.getText());
                add(pan);
                revalidate();
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
                    System.out.println("NEED SELECTED TWO ELEMENTS");
                }
                else {
                    model.lockElements(networkElements.get(0).getId(), networkElements.get(1).getId());
                    System.out.println("LOCK ELEMENTS");
                }
            }
        });
        saveLinkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.releaseElements(networkElements.get(0).getId(), networkElements.get(1).getId());
                networkElements.removeAll(networkElements);
                System.out.println("UNLOCK ELEMENTS");
            }
        });
        deleteElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.deleteNetworkElement(textArea.getText());
            }
        });
        add(mpanel);
    }

    private void createNElements() {
        elements.removeAll();
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
