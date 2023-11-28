package Interfaz_grafica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import Tipos.*;
import Pokemon.*;

public class InterfazGenerarEquipo extends JFrame{

    private JPanel panelPrincipal;
    private JButton botonSalir, botonCombatir;
    private JLabel[] imagenes;

    private Socket s;

    private Equipo_Pokemon equipo;

    public InterfazGenerarEquipo(JFrame interfazInicio, Socket socket){
        this.s = socket;
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

        //Generar equipo

        Random r = new Random();
        ArrayList<Pokemon> equipArray = new ArrayList<>();
        List<Integer> baneados = Arrays.asList(new Integer[]{10,11,13,14,129,132,201,202,235,265,266,268,292,360, 664,665,771,789,790,824,825});
        List<Integer> num_usados = new ArrayList<>();

        // Numeros de pokemon prohibidos: 10,11,13,14,129,132,201,202,235,265,266,268,292,360,664,665,771,789,790,824,825

        imagenes = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            int numpoke = r.nextInt(1017);
            while (num_usados.contains(numpoke) && baneados.contains(numpoke)) {
                numpoke = r.nextInt(1017);
            }
            System.out.println(numpoke);
            Pokemon pok = Pokemon.generar_pokemon("https://pokeapi.co/api/v2/pokemon/" + numpoke);
            equipArray.add(pok);
            num_usados.add(numpoke);

            imagenes[i] = new JLabel();
            ImageIcon icono = new ImageIcon(getClass().getResource("132_front.png"));
            Image imagen = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagenes[i].setIcon(new ImageIcon(imagen));
            panelImagenes.add(imagenes[i]);
            panelImagenes.add(new Label(pok.getName()));
        }
        equipo = new Equipo_Pokemon(equipArray);

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
                InterfazCombate combate = new InterfazCombate(equipo, s);
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
