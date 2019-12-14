package com.unn.Panels;
import com.unn.IObserver;
import com.unn.model.Interface;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;
import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class RowNe extends JPanel implements IObserver {
    private NetworkElement element;
    private NetworkModel model;
    private JButton nameNe;
    private JLabel typeNe;
    private JCheckBox selNe;

    public RowNe(NetworkElement element) {
        this.element = element;
        nameNe = new JButton();
        nameNe.setText(element.getName());
        nameNe.setBorderPainted(false);
        nameNe.setContentAreaFilled(false);
        nameNe.setFocusPainted(false);
        typeNe = new JLabel();
        typeNe.setText(element.getType());
        selNe = new JCheckBox();
        add(nameNe);
        add(typeNe);
        add(selNe);
    }

    public Boolean isSelected(){
        return selNe.isSelected();
    }

    @Override
    public void update() {

    }
}
