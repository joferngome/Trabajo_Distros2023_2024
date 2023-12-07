package Interfaz_grafica;

import Pokemon.Equipo_Pokemon;
import Pokemon.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;

public class InterfazGraficaServidor extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    //Panel inicial
    private JButton salir, botonCombatir, salirCombate;
    private JButton generarEquipo;
    private JButton elegirEquipo;
    private JPanel panelInicio;
    private Image imagenFondo;
    private JPanel panelImagen;

    //Servidor
    private static String mensaje;
    private Socket s;
    private Equipo_Pokemon eq1;

    //generar equipo
    private JLabel[] imagenes;
    private JPanel panelEquipo;
    private JPanel panelImagenes;


    // combate
    private JPanel panelCombate;
    private JButton botonAtacar, botonCambiar, botonSalir;
    private JLabel imagen;
    private JPanel panelBotonesCombate;
    private JLayeredPane panelImagenesCombate;

    // atacar
    private JPanel panelAtacar;
    //elegir ataque
    private JPanel panelElegirAtaque;
    private JPanel panelBotonesElegirAtaque;
    private Attack ataqueElegido;
    private JButton botonSeleccionarAtaque;

    //elegir pokemon
    private JPanel panelElegir;
    private JPanel panelBotonesElegir;
    private Pokemon pokemonActivo;
    private JButton botonSeleccionar;



    public InterfazGraficaServidor() {
        super("Combate pokemon");
        //s = socket;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        salir = new JButton("Salir");
        generarEquipo = new JButton("Generar equipo aleatorio");
        elegirEquipo = new JButton("Elegir equipo");

        salir.addActionListener(this);
        generarEquipo.addActionListener(this);
        elegirEquipo.addActionListener(this);

        panelInicio = new JPanel();
        panelInicio.add(generarEquipo);
        panelInicio.add(salir);

        add(panelInicio, BorderLayout.SOUTH);

        ImageIcon icono = new ImageIcon(getClass().getResource("portada.png"));
        imagenFondo = icono.getImage();

       panelImagen = new JPanel() {
           private static final long serialVersionUID = 1L;

           @Override
           public void paintComponent(Graphics g) {
               super.paintComponent(g);
               g.drawImage(imagenFondo, 0, 0, null);
           }
       };
        panelImagen.setPreferredSize(new Dimension(500, 300));
        add(panelImagen, BorderLayout.CENTER);


        //Panel equipo aleatorio

        panelEquipo = new JPanel();

        panelEquipo.setLayout(new BorderLayout());

        panelImagenes = new JPanel();
        panelImagenes.setLayout(new GridLayout(1, 6));

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        botonCombatir = new JButton("Combatir");
        salirCombate = new JButton("Salir");

        botonCombatir.addActionListener(this);
        salirCombate.addActionListener(this);

        panelBotones.add(botonCombatir);
        panelBotones.add(salirCombate);

        panelEquipo.add(panelBotones, BorderLayout.SOUTH);


        //Panel combate

        panelCombate = new JPanel();
        panelCombate.setLayout(new BorderLayout());

        panelBotonesCombate = new JPanel();
        panelBotonesCombate.setLayout(new GridLayout(2, 3));

        botonAtacar = new JButton("Atacar");
        botonCambiar = new JButton("Cambiar");
        botonSalir = new JButton("Salir");


        botonAtacar.addActionListener(this);
        botonCambiar.addActionListener(this);
        botonSalir.addActionListener(this);

        panelBotonesCombate.add(botonAtacar);
        panelBotonesCombate.add(botonCambiar);
        panelBotonesCombate.add(botonSalir);

        panelCombate.add(panelBotonesCombate, BorderLayout.SOUTH);



        this.pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir ||e.getSource() == salirCombate ||e.getSource() ==  botonSalir) {

            System.exit(0);

        } else if (e.getSource() == generarEquipo) {

            remove(panelImagen);
            remove(panelInicio);

            add(panelEquipo, BorderLayout.CENTER);
            revalidate();
            repaint();

            imagenes = new JLabel[6];
            eq1 = Equipo_Pokemon.generar_equipo();
            for(int i = 0; i< eq1.getEquipo().size(); i++){

                imagenes[i] = new JLabel();

                ImageIcon icono = new ImageIcon(getClass().getResource("132_front.png"));

                Image imagen = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imagenes[i].setIcon(new ImageIcon(imagen));
                panelImagenes.add(imagenes[i]);
                panelImagenes.add(new Label(eq1.getEquipo().get(i).getName()));
            }

            panelEquipo.add(panelImagenes, BorderLayout.NORTH);

            this.pack();

            revalidate();
            repaint();

        } else if (e.getSource() == botonCombatir || e.getSource() == botonCambiar) {
            //Panel elegir pokemon

            remove(panelEquipo);
            remove(panelCombate);

            panelElegir = new JPanel();
            panelElegir.setLayout(new BorderLayout());

            panelBotonesElegir = new JPanel();

            ArrayList<Pokemon> disponibles = eq1.getEquipoDisponible();

            panelBotonesElegir.setLayout(new GridLayout(disponibles.size(), 0));

            for(Pokemon p : disponibles){
                JButton seleccionarPokemon = new JButton(p.getName());

                seleccionarPokemon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        pokemonActivo  = p;
                    }
                });
                panelBotonesElegir.add(seleccionarPokemon);
            }

            botonSeleccionar = new JButton("Seleccionar");


            botonSeleccionar.addActionListener(this);



            panelElegir.add(panelBotonesElegir,BorderLayout.NORTH );
            panelElegir.add(botonSeleccionar, BorderLayout.SOUTH);
            add(panelElegir);

            this.pack();

            revalidate();
            repaint();


            System.out.println("combatir");

        }else if (e.getSource() == botonSeleccionar) {

            eq1.setPokemonActivo(pokemonActivo);
            System.out.println(eq1.getPokemonActivo().getName());
            remove(panelElegir);

            panelImagenesCombate = new JLayeredPane();
            panelImagenesCombate.setPreferredSize(new Dimension(480, 224));

            //Fondo
            imagen = new JLabel();
            ImageIcon icono = new ImageIcon(getClass().getResource("fondo_combate.png"));
            Image img = icono.getImage().getScaledInstance(240*2, 112*2, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img));
            imagen.setBounds(0, 0, 480, 224);
            panelImagenesCombate.add(imagen, Integer.valueOf(1));

            //Sprite poke propio
            imagen = new JLabel();
            ImageIcon icono1 = new ImageIcon(getClass().getResource("132.png"));
            Image img1 = icono1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img1));
            imagen.setBounds(50, 150, 100, 100);
            panelImagenesCombate.add(imagen, Integer.valueOf(2));

            //Sprite poke rival
            imagen = new JLabel();
            ImageIcon icono2 = new ImageIcon(getClass().getResource("132_front.png")); // Reemplaza esto con la ruta a tu segunda imagen
            Image img2 = icono2.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(img2));
            imagen.setBounds(300, 50, 100, 100);
            panelImagenesCombate.add(imagen, Integer.valueOf(2));

            panelCombate.add(panelImagenesCombate, BorderLayout.NORTH);
            add(panelCombate);

            this.pack();

            revalidate();
            repaint();
        }else if (e.getSource() == botonAtacar) {
            panelCombate.remove(panelBotonesCombate);
            System.out.println("atacar");

            panelAtacar = new JPanel();
            panelAtacar.setLayout(new GridLayout(2, 3));

            panelBotonesElegirAtaque = new JPanel();

            for(Attack a : eq1.getPokemonActivo().getAttacks()){
                JButton seleccionarPokemon = new JButton(a.getName());

                seleccionarPokemon.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ataqueElegido = a;
                    }
                });
                panelBotonesElegirAtaque.add(seleccionarPokemon);
            }
            botonSeleccionarAtaque = new JButton("Seleccionar ataque");

            panelAtacar.add(panelBotonesElegirAtaque, BorderLayout.NORTH);
            panelAtacar.add(botonSeleccionarAtaque, BorderLayout.SOUTH);

            panelCombate.add(panelBotonesElegirAtaque, BorderLayout.SOUTH);

            this.pack();

            revalidate();
            repaint();
        }
    }

    public static void main(String[] args) {
        InterfazGraficaServidor i = new InterfazGraficaServidor();
    }

}
