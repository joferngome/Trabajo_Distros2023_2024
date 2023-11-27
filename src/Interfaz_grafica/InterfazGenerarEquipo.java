package Interfaz_grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazGenerarEquipo extends JFrame{

    private JPanel panelPrincipal;
    private JButton botonSalir, botonCombatir;
    private JLabel[] imagenes;

    public InterfazGenerarEquipo(JFrame interfazInicio){

        setTitle("Generar equipo aleatorio");
        setSize(800, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelImagenes = new JPanel();
        panelImagenes.setLayout(new GridLayout(1, 6));

        JLabel textoCargando = new JLabel("Cargando...");
        textoCargando.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(textoCargando, BorderLayout.CENTER);

        imagenes = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            imagenes[i] = new JLabel();
            ImageIcon icono = new ImageIcon(getClass().getResource("132_front.png"));
            Image imagen = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagenes[i].setIcon(new ImageIcon(imagen));
            panelImagenes.add(imagenes[i]);
        }

        textoCargando.setText("");

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonSalir = new JButton("Salir");
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interfazInicio.setVisible(true);
                dispose();
            }
        });

        botonCombatir = new JButton("Combatir");
        botonCombatir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InterfazCombate combate = new InterfazCombate();
                setVisible(false);
                System.out.println("combatir");
            }
        });

        panelBotones.add(botonCombatir);
        panelBotones.add(botonSalir);

        panelPrincipal.add(panelImagenes, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        this.pack();
        setVisible(true);
    }
}
