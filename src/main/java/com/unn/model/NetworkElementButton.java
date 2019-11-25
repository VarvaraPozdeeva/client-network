package com.unn.model;

import lombok.Data;

import javax.swing.*;

@Data
public class NetworkElementButton extends JButton {
    //это должен быть класс вершины графа
   private NetworkElement element;

    public NetworkElementButton(NetworkElement element) {
        this.element = element;
        setText(element.getName());
    }

    //логика по нажатию на вершину(более подробная информация о элементе)
}
