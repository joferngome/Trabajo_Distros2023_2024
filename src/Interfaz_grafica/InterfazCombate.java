package Interfaz_grafica;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazCombate extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonAtacar, botonCambiar, botonSalir;
    private JLabel imagen;

    public InterfazCombate() {
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

    /*
    Este esta solo para probar más rápido
    public static void main(String[] args) {
        new InterfazCombate();
    }*/

}

