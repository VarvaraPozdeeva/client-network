package com.unn.Panels;

import com.unn.IObserver;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
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
        JPanel biPanel = new JPanel();
        JPanel bPanfI = new JPanel();

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
        //save.setPreferredSize(new Dimension(150,50));

        buttons.add(edit);
        //buttons.add(save);
        buttons.add(addElement);
        buttons.add(addLinkButton);

        g = new GridLayout(model.getNetworkElements().size(), 1);
        elements.setLayout(g);
        Border infBor = BorderFactory.createCompoundBorder(new LineBorder(Color.PINK, 4, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bPanfI.setBorder(infBor);
        bPanfI.add(infoPanel);
        biPanel.setLayout(new BorderLayout(0,20));
        biPanel.add(buttons, BorderLayout.NORTH);
        biPanel.add(bPanfI, BorderLayout.CENTER);
        mpanel.setLayout(new GridLayout(1,2));
        mpanel.add(biPanel);
        mpanel.add(elements);


        createNElements();
        elemPanel = new AddElement(model);

        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.lockElement(textArea.getText());
                infoPanel.editElement();
                revalidate();
                //edit.setBackground(Color.RED);
                //save.setBackground(Color.LIGHT_GRAY);
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
