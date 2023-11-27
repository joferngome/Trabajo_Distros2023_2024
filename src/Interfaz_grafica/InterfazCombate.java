package Interfaz_grafica;
import Pokemon.Equipo_Pokemon;
import Pokemon.Pokemon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class InterfazCombate extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonAtacar, botonCambiar, botonSalir;
    private JLabel imagen;

    private Socket s;
    private Equipo_Pokemon equipo;

    public InterfazCombate(Equipo_Pokemon equipo, Socket s) {
        this.s = s;
        this.equipo = equipo;
        setTitle("Nueva Interfaz Gráfica");

        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JLayeredPane panelImagenes = new JLayeredPane();
        panelImagenes.setPreferredSize(new Dimension(480, 224));


        //Fondo
        imagen = new JLabel();
        ImageIcon icono = new ImageIcon(getClass().getResource("fondo_combate.png"));
        Image img = icono.getImage().getScaledInstance(240*2, 112*2, Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(img));
        imagen.setBounds(0, 0, 480, 224);
        panelImagenes.add(imagen, Integer.valueOf(1));

        //Sprite poke propio
        imagen = new JLabel();
        ImageIcon icono1 = new ImageIcon(getClass().getResource("132.png"));
        Image img1 = icono1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(img1));
        imagen.setBounds(50, 150, 100, 100);
        panelImagenes.add(imagen, Integer.valueOf(2));

        //Sprite poke rival
        imagen = new JLabel();
        ImageIcon icono2 = new ImageIcon(getClass().getResource("132_front.png")); // Reemplaza esto con la ruta a tu segunda imagen
        Image img2 = icono2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imagen.setIcon(new ImageIcon(img2));
        imagen.setBounds(300, 50, 100, 100);
        panelImagenes.add(imagen, Integer.valueOf(2));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 3));


        botonAtacar = new JButton("Atacar");
        botonAtacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("atacar");



            }
        });
        Ejecutar_turno("atacar", 1);


        botonCambiar = new JButton("Cambiar");
        botonCambiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cambiar");
            }
        });

        botonSalir = new JButton("Salir");
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panelBotones.add(botonAtacar);
        panelBotones.add(botonCambiar);
        panelBotones.add(botonSalir);

        panelPrincipal.add(panelImagenes, BorderLayout.NORTH);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        add(panelPrincipal);

        this.pack();
        setVisible(true);
    }

    //Accion es una string que dice que se va a ejecutar (cambiar/atacar)
    //eleccion es el indice de lo que se ha elegido (0-3 para atacar, 0-5 para cambiar)
    private void Ejecutar_turno(String accion, int eleccion){
        try(ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream())) {

            Equipo_Pokemon equipo_rival;

            //Mandar acción y eleccion
            oos.writeObject(accion);
            oos.writeObject(eleccion);

            //Coge la acción y eleccion del otro
            String accion_rival = (String) ois.readObject();
            int eleccion_rival = (int) ois.readObject();

            if(accion.equals("cambiar")){
                equipo.setPokemonActivo(equipo.getEquipo().get(eleccion));
                //Manda el equipo al rival
                oos.writeObject(equipo);
            }
            else if(accion.equals("atacar")){
                if(accion_rival.equals("cambiar")){
                    //Lee equipo rival
                    equipo_rival = (Equipo_Pokemon) ois.readObject();
                    if(equipo.getPokemonActivo().getSpeed() > equipo_rival.getPokemonActivo().getSpeed()){
                        //Hago mi ataque porque soy más rápido
                        int dano_hecho = equipo.getPokemonActivo().atacar(equipo_rival.getPokemonActivo(), equipo.getPokemonActivo().getAttacks().get(eleccion));
                        if(!equipo_rival.getPokemonActivo().isAlive()){
                            //se ha muerto el rival
                        }else{
                            //ataca el rival
                            int dano_recibido = equipo_rival.getPokemonActivo().atacar(equipo.getPokemonActivo(), equipo_rival.getPokemonActivo().getAttacks().get(eleccion_rival));

                            equipo.getPokemonActivo().setHealth(equipo.getPokemonActivo().getHealth() - dano_recibido);

                            if(!equipo.getPokemonActivo().isAlive()){

                            }
                        }
                    }
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
    Este esta solo para probar más rápido
    public static void main(String[] args) {
        new InterfazCombate();
    }*/

}

