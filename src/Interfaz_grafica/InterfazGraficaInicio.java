package Interfaz_grafica;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterfazGraficaInicio extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JButton salir;
    private JButton generarEquipo;
    private JButton elegirEquipo;
    private JPanel panel;
    private Image imagenFondo;

    public InterfazGraficaInicio() {
        super("Combate pokemon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 440);
        setLocationRelativeTo(null);
        setResizable(false);

        // Agregamos los botones
        salir = new JButton("Salir");
        generarEquipo = new JButton("Generar equipo aleatorio");
        elegirEquipo = new JButton("Elegir equipo");

        // Agregamos los listeners
        salir.addActionListener(this);
        generarEquipo.addActionListener(this);
        elegirEquipo.addActionListener(this);

        // Agregamos los botones al panel
        panel = new JPanel();
        panel.add(generarEquipo);
        panel.add(elegirEquipo);
        panel.add(salir);
        // Agregamos el panel a la ventana
        add(panel, BorderLayout.SOUTH);

        // Cargamos la imagen de fondo
        ImageIcon icono = new ImageIcon(getClass().getResource("./portada.png"));
        imagenFondo = icono.getImage();

        // Agregamos un panel para la imagen de fondo
       JPanel panelImagen = new JPanel() {
           private static final long serialVersionUID = 1L;

           @Override
           public void paintComponent(Graphics g) {
               super.paintComponent(g);
               g.drawImage(imagenFondo, 0, 0, null);
           }
       };
        panelImagen.setPreferredSize(new Dimension(500, 300));
        add(panelImagen, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir) {
            System.exit(0);
        } else if (e.getSource() == generarEquipo) {
            System.out.println("generar equipo");
        } else if (e.getSource() == elegirEquipo) {
            System.out.println("elegir equipo");
        }
    }

    public static void main(String[] args) {
        new InterfazGraficaInicio();
    }
}
