package Interfaz_grafica;

import javax.swing.*;
import java.awt.*;

public class Frame {
    public static void main(String args[]) {
        JFrame frame = new JFrame("Combate pokemon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);

        JButton boton_generar = new JButton("Generar equipo");
        JButton boton_elegir = new JButton("Elegir equipo");
        JButton boton_salir = new JButton("Salir");

        frame.getContentPane().add(boton_generar);
        frame.getContentPane().add(boton_elegir);
        frame.getContentPane().add(boton_salir);

        frame.setVisible(true);
    }
}
