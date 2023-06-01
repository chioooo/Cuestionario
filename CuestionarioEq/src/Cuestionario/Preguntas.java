
package Cuestionario;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Preguntas extends JFrame{
        private JPanel contentPane;
    private JLabel lblPregunta;
    private JLabel lblImagen;
    private JButton btnSiguiente;
    private JRadioButton[] opciones;
    private ButtonGroup grupoOpciones;
    private int indicePreguntaActual = 0;
    private int respuestasCorrectas = 0;
    private int respuestasIncorrectas = 0;
    private ImageIcon image;
    private Icon icon;

    private String[] preguntas = {
            "1. ¿Qué componente contenedor es más adecuado para organizar los componentes en una cuadrícula?",
            "2. ¿Qué componente contenedor se utiliza para organizar los componentes en varias pestañas?",
            "3. ¿Qué método se utiliza para inicializar un Applet en Java?",
            "4. ¿Cuál es el método que se utiliza para pintar un Applet en la pantalla?",
            "5. ¿Qué evento se dispara cuando el usuario presiona una tecla mientras el applet tiene el foco?",
            "6. Es una ventana secundaria que se utiliza para mostrar información adicional.",
            "7. JDialog son ventanas modales, lo que significa que el usuario no puede interactuar con la ventana principal hasta que se cierre el diálogo.",
            "8. ¿AWT es una clase más moderna que Java Swing?",
            "9. ¿La generación de métodos consta de auditores y fuentes?",
            "10. ¿En la generación de métodos solo debe haber una fuente?"
    };

    private String[][] opcionesText = {
            {"a) JPanel", "b) JFrame", "c) JTable", "d) JScrollPane"},
            {"a) JPanel", "b) JTabbedPane", "c) JScrollPane", "d) JList"},
            {"a) start()", "b) paint()", "c) init()", "d) stop()"},
            {"a) init()", "b) start()", "c) paint()", "d) stop()"},
            {"a) keyPressed()", "b) keyReleased()", "c) keyTyped()", "d) mouseClicked()"},
            {"a) JFrame", "b) JInternalFrame", "c) JDialog", "d) JApplet"},
            {"a) Verdadero", "b) Falso", "", ""},
            {"a) Verdadero", "b) Falso", "", ""},
            {"a) Verdadero", "b) Falso", "", ""},
            {"a) Verdadero", "b) Falso", "", ""}
    };
    
    private String []imagenes = {"src/imagenes/pregunta1.png","src/imagenes/2.png","src/imagenes/3.png",
        "src/imagenes/4.png","src/imagenes/5.jpg","src/imagenes/6.JPG","src/imagenes/7.png",
        "src/imagenes/8.jpg","src/imagenes/9.png","src/imagenes/10.png"
    
    };

    private char[] respuestas = {'a', 'b', 'c', 'c', 'a', 'c', 'a', 'b', 'a', 'b'};


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Preguntas frame = new Preguntas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Preguntas() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 500);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        lblPregunta = new JLabel();
        lblPregunta.setHorizontalAlignment(SwingConstants.LEFT);
        lblPregunta.setBounds(40, 40, 800, 50);
        contentPane.add(lblPregunta);
        
        lblImagen=new JLabel();
        lblImagen.setBounds(600, 113, 250, 200);
        contentPane.add(lblImagen);

        opciones = new JRadioButton[4];
        grupoOpciones = new ButtonGroup();

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new JRadioButton();
            opciones[i].setFont(new Font("Tahoma", Font.PLAIN, 12));
            opciones[i].setBounds(180, 100 + 30 * i, 250, 30);
            grupoOpciones.add(opciones[i]);
            contentPane.add(opciones[i]);
        }

        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnSiguiente.setBounds(190, 260, 100, 30);
        contentPane.add(btnSiguiente);
        btnSiguiente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verificarRespuesta();

                if (!respuestaCorrecta) {
                    // Respuesta incorrecta, muestra un mensaje de aviso
                    JOptionPane.showMessageDialog(contentPane, "Respuesta incorrecta.", "Aviso",
                            JOptionPane.WARNING_MESSAGE);
                }

                // Avanza a la siguiente pregunta
                indicePreguntaActual++;
                if (indicePreguntaActual < preguntas.length) {
                    mostrarPregunta();
                } else {
                    // Fin del cuestionario
                    contentPane.removeAll();
                    lblPregunta = new JLabel("Fin del cuestionario");
                    lblPregunta.setHorizontalAlignment(SwingConstants.CENTER);
                    lblPregunta.setFont(new Font("Tahoma", Font.BOLD, 16));
                    lblPregunta.setBounds(40, 40, 400, 30);
                    contentPane.add(lblPregunta);

                    JLabel lblRespuestas = new JLabel("Respuestas correctas: " + respuestasCorrectas + "  Respuestas incorrectas: " + respuestasIncorrectas);
                    lblRespuestas.setHorizontalAlignment(SwingConstants.CENTER);
                    lblRespuestas.setFont(new Font("Tahoma", Font.BOLD, 14));
                    lblRespuestas.setBounds(40, 80, 400, 30);
                    contentPane.add(lblRespuestas);

                    // Crear un conjunto de datos para la gráfica
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    dataset.setValue(respuestasCorrectas, "Respuestas Correctas", String.valueOf(respuestasCorrectas*10)+"%");
                    dataset.setValue(respuestasIncorrectas, "Respuestas Incorrectas", String.valueOf(respuestasIncorrectas*10)+"%");

                    // Crear el gráfico de barras
                    JFreeChart chart = ChartFactory.createBarChart(
                            "Resultados del Cuestionario",  // Título del gráfico
                            "Respuestas",                   // Etiqueta del eje X
                            "Cantidad",                     // Etiqueta del eje Y
                            dataset,                        // Conjunto de datos
                            PlotOrientation.VERTICAL,       // Orientación del gráfico
                            true,                           // Incluir leyenda
                            true,                           // Incluir tooltips
                            false                           // Incluir URLs
                    );

                    // Crear el panel de la gráfica y agregarlo al contenido del JFrame
                    ChartPanel chartPanel = new ChartPanel(chart);
                    chartPanel.setBounds(40, 160, 400, 250);
                    contentPane.add(chartPanel);

                    JButton btnSalir = new JButton("Salir");
                    btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
                    btnSalir.setBounds(190, 430, 100, 30);
                    contentPane.add(btnSalir);
                    btnSalir.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            System.exit(0); // Cierra la aplicación
                        }
                    });

                    contentPane.revalidate(); // Vuelve a dibujar la ventana
                    contentPane.repaint();
                }
            }
        });

        mostrarPregunta();
    }
    
    private void setImage(JLabel l, String root){
        this.image=new ImageIcon(root);
        this.icon=new ImageIcon(this.image.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
        l.setIcon(icon);
        this.repaint();
    }

    private void mostrarPregunta() {
        lblPregunta.setText(preguntas[indicePreguntaActual]);
        
        setImage(lblImagen,imagenes[indicePreguntaActual]);
        grupoOpciones.clearSelection();

        for (int i = 0; i < opcionesText[indicePreguntaActual].length; i++) {
            opciones[i].setText(opcionesText[indicePreguntaActual][i]);
        }
    }

    private boolean respuestaCorrecta;

private void verificarRespuesta() {
    respuestaCorrecta = false;
    for (int i = 0; i < opciones.length; i++) {
        if (opciones[i].isSelected()) {
            char opcionSeleccionada = (char) ('a' + i);
            if (opcionSeleccionada == respuestas[indicePreguntaActual]) {
                // La respuesta es correcta
                respuestaCorrecta = true;
                respuestasCorrectas++;
            } else {
                respuestasIncorrectas++;
            }
            break;
        }
    }
}
}
