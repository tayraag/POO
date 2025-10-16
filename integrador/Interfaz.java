import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Interfaz{
    private Facultad facultad; 
    private JTextArea mensajesArea;
    
    public Interfaz(JTextArea mensajesArea){
        this.facultad = new Facultad("UNTDF");} 
    
    public void iniciarInterfaz(){
        JFrame frame = new JFrame("Facultad UNTDF");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,750);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        JLabel title = new JLabel("¡Bienvenido!", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);

        JPanel menuPanel = new JPanel(new FlowLayout());
        JComboBox<String> inscribirCombo = new JComboBox<>(new String[]{"Carrera", "Materia"});
        JComboBox<String> altaCombo = new JComboBox<>(new String[]{"Carrera", "Materia", "Alumno", "Correlativa"});
        menuPanel.add(new JLabel("Inscribir alumno a:"));
        menuPanel.add(inscribirCombo);
        menuPanel.add(new JLabel("Alta de:"));
        menuPanel.add(altaCombo);
        mainPanel.add(menuPanel);
        
        JComboBox<String> tipoPlanCom = new JComboBox<>(new String[]{"A", "B", "C", "D", "E"});
        JComboBox<String> obliOno = new JComboBox<>(new String[]{"Si", "No"});

        JPanel formPanelIN = new JPanel(new CardLayout());
        JPanel formPanelAL = new JPanel(new CardLayout());
        
        JPanel carreraFormIN = crearFormInscripcionCarrera("Carrera");//estos son para la inscripcion
        JPanel materiaFormIN = crearFormInscripcionMateria("Materia");
        
        JPanel carreraFormAL = formAltaCarrera("Carrera",tipoPlanCom);//estos son para la alta
        JPanel materiaFormAL = formAltaMateria("Materia",obliOno);
        JPanel alumnoFormAL = formAltaAlumno("Alumno");
        JPanel correlativaFormAL = formAltaCorrelativa("Correlativa");
        
        formPanelIN.add(new JPanel(), "Opciones");
        formPanelIN.add(carreraFormIN, "Carrera");
        formPanelIN.add(materiaFormIN, "Materia");
        formPanelAL.add(new JPanel(), "Opciones");
        formPanelAL.add(carreraFormAL,"Carrera");
        formPanelAL.add(materiaFormAL,"Materia");
        formPanelAL.add(alumnoFormAL, "Alumno");
        formPanelAL.add(correlativaFormAL, "Correlativa");
        mainPanel.add(formPanelIN);
        mainPanel.add(formPanelAL);
        
        JButton inscribirButton = new JButton("Inscribir");
        JButton altaButton = new JButton("Alta");
        
        JTextArea mensajesArea = new JTextArea(10, 30);
        mensajesArea.setEditable(false);
        mensajesArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JScrollPane scrollPane = new JScrollPane(mensajesArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane, BorderLayout.SOUTH);
        
        inscribirButton.addActionListener(e -> {
            String selectedOption = (String) inscribirCombo.getSelectedItem();
            mensajesArea.append("Realizando inscripción en " + selectedOption + "...\n");
            if(selectedOption.equals("Carrera")){
                String[] datos = obtenerDatosInscripcionCarrera(carreraFormIN);
                Carrera carrera = facultad.getCarreras().stream().filter(c->c.getNombreCarrera().equals(datos[1])).findFirst().orElse(null);
                Alumno alum = facultad.getAlumnos().stream().filter(a->a.getNombreCompleto().equals(datos[0])).findFirst().orElse(null);
                try{facultad.inscribirAlumnoACarrera(alum, carrera);}
                catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
            } else if(selectedOption.equals("Materia")){
                String[] datos = obtenerDatosInscripcionMateria(materiaFormIN);
                Materia mat= facultad.getCarreras().stream().flatMap(c -> c.getMaterias().stream()) .filter(m -> m.getNombre().equals(datos[1])) .findFirst().orElse(null);
                Alumno alum = facultad.getAlumnos().stream().filter(c->c.getNombreCompleto().equals(datos[0])).findFirst().orElse(null); 
                try{facultad.inscribirAlumnoAMateria(alum, mat);}
                catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
            }
        });
    
        altaButton.addActionListener(e -> {
            String selectedOption = (String) altaCombo.getSelectedItem();
            mensajesArea.append("Realizando alta de " + selectedOption + "...\n");
            if(selectedOption.equals("Carrera")){
                String[] datos = obtenerDatosAltaCarrera(carreraFormAL);
                if(datos[0] == null){mensajesArea.append("El nombre de la carrera no puede ser nulo.");return;}
                else if(datos[1] == null){mensajesArea.append("La cantidad de cuatrimestres no puede ser nula.");return;}
                else if(datos[2] == null){mensajesArea.append("La cantidad de optativas minimas no puede ser nula.");return;}
                else if(datos[3] == null){mensajesArea.append("El tipo de plan de estudios no puede ser nulo.");return;}
                try{String plan = (String) tipoPlanCom.getSelectedItem();
                    PlanDeEstudio tipo;
                    switch(plan){
                        case "A": tipo = new PlanDeEstudio(new TipoA());break;
                        case "B": tipo = new PlanDeEstudio(new TipoB());break;
                        case "C": tipo = new PlanDeEstudio(new TipoC());break;
                        case "D": tipo = new PlanDeEstudio(new TipoD());break;
                        case "E": tipo = new PlanDeEstudio(new TipoE());break;
                        default: mensajesArea.append("Tipo desconocido: "+plan+"\n");
                                 throw new IllegalArgumentException("Tipo de plan desconocido: " + plan);}
                    facultad.agregarCarrera(datos[0], Integer.parseInt(datos[1]), Integer.parseInt(datos[2]), tipo);
                    mensajesArea.append("Carrera agregada exitosamente.\n");}
                catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
            }else if(selectedOption.equals("Materia")){ 
                    String[] datos = obtenerDatosAltaMateria(materiaFormAL);
                    Carrera carrera = facultad.getCarreras().stream().filter(c -> c.getNombreCarrera().equals(datos[0])).findFirst().orElse(null); 
                    if (carrera == null) { mensajesArea.append("Error: La carrera '" + datos[0] + "' no existe. Por favor, verifica el nombre e inténtalo nuevamente.\n");return;}
                    else if(datos[1] == null){mensajesArea.append("El nombre de la materia no puede ser nulo.");return;}
                    else if(datos[2] == null){mensajesArea.append("El cuatrimestre no puede ser nulo.");return;}
                    else if(datos[3] == null){mensajesArea.append("La obligatoriedad no puede ser nula.");return;}
                    try{boolean obli = datos[3].equals("Si");
                        facultad.agregarMateria(datos[1], Integer.parseInt(datos[2]), obli, new ArrayList<>(), datos[0]);
                        mensajesArea.append("Materia agregada exitosamente.\n");}
                    catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
                }else if(selectedOption.equals("Alumno")){
                        String[] datos = obtenerDatosAltaAlumno(alumnoFormAL);
                        if(datos[0] == null){mensajesArea.append("El nombre del alumno no puede ser nulo.");return;}
                           else if(datos[1] == null){mensajesArea.append("La carrera no puede ser nula.");return;}
                        try{facultad.agregarAlumno(datos[0], datos[1]);mensajesArea.append("Alumno agregada exitosamente.\n");}
                        catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
                    }else if(selectedOption.equals("Correlativa")){
                        String[] datos = obtenerDatosAltaCorrelativa(correlativaFormAL);
                        Carrera carrera = facultad.getCarreras().stream().filter(c->c.getNombreCarrera().equals(datos[0])).findFirst().orElse(null);
                        Materia mat1 = carrera.getMaterias().stream().filter(c->c.getNombre().equals(datos[1])).findFirst().orElse(null);
                        Materia corre = carrera.getMaterias().stream().filter(c->c.getNombre().equals(datos[2])).findFirst().orElse(null);
                        if(mat1 == null){mensajesArea.append("La materia no puede ser nula.");return;}
                           else if(corre == null){mensajesArea.append("La correlativa no puede ser nula.");return;}
                        try{facultad.agregarCorrelativa(mat1, corre);mensajesArea.append("Correlativa agregada exitosamente.\n");}
                        catch(IllegalArgumentException ex){mensajesArea.append("Error: "+ex.getMessage()+"\n");}
                    }
        });
    
        JPanel botonesPanel = new JPanel(new FlowLayout());
        botonesPanel.add(inscribirButton);
        botonesPanel.add(altaButton);
        mainPanel.add(botonesPanel);
        
        inscribirCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) inscribirCombo.getSelectedItem();
                CardLayout clIN = (CardLayout) formPanelIN.getLayout();
                clIN.show(formPanelIN, selectedOption);
                mensajesArea.append("Formulario de inscripcion para "+selectedOption+" seleccionado.\n");
            }
        });

        altaCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) altaCombo.getSelectedItem();
                CardLayout clAL = (CardLayout) formPanelAL.getLayout();
                clAL.show(formPanelAL, selectedOption);
                mensajesArea.append("Formulario de inscripcion para "+selectedOption+" seleccionado.\n");
            }
        });
        
        JPanel verificarPanel = new JPanel(new FlowLayout());
        JTextField alumTextField = new JTextField(30);
        JButton verificarButton = new JButton("Verifica");
        verificarPanel.add(new JLabel("Alumno:"));
        verificarPanel.add(alumTextField);
        verificarPanel.add(new JLabel("Verifica si el alumno terminó la carrera:"));
        verificarPanel.add(verificarButton);
        mainPanel.add(verificarPanel);
        verificarButton.addActionListener(e ->{
             String nombre = alumTextField.getText().trim();
             Alumno alum = facultad.getAlumnos().stream() .filter(a -> a.getNombreCompleto().equalsIgnoreCase(nombre)) .findFirst() .orElse(null);
             if(alum == null){mensajesArea.append("El alumno no está registrado.\n");} 
             else{mensajesArea.append(facultad.verificarSiAlumnoFinalizoCarrera(alum));}});

        frame.setVisible(true);
        frame.setLocationRelativeTo(null); 
    }

    private static JPanel crearFormInscripcionCarrera(String tipo) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para la inscripción:");
        panel.add(new JLabel("Alumno:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Carrera:"));
        panel.add(new JTextField());
        return panel;
    } 
    
    private static String[] obtenerDatosInscripcionCarrera(JPanel panel){ 
        JTextField alumnoField = (JTextField) panel.getComponent(1);
        JTextField carreraField = (JTextField) panel.getComponent(3); 
        return new String[]{alumnoField.getText(), carreraField.getText()};
    }
    
    private static JPanel crearFormInscripcionMateria(String tipo) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para la inscripción:", JLabel.NORTH_EAST);
        panel.add(new JLabel("Alumno:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Materia:"));
        panel.add(new JTextField());
        return panel;
    }  
    
    private static String[] obtenerDatosInscripcionMateria(JPanel panel){ 
        JTextField alumnoField = (JTextField) panel.getComponent(1);
        JTextField materiaField = (JTextField) panel.getComponent(3); 
        return new String[]{alumnoField.getText(), materiaField.getText()};
    }
    
    private static JPanel formAltaCarrera(String tipo, JComboBox<String> tipoPlanCom) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para el alta de la carrera:", JLabel.NORTH_EAST);
        panel.add(new JLabel("Nombre:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Cantidad de cuatrimestres:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Tipo de Plan:"));
        panel.add(tipoPlanCom);
        panel.add(new JLabel("Cantidad de optativas minimas:"));
        panel.add(new JTextField());
        return panel;
    }  
    
    private static String[] obtenerDatosAltaCarrera(JPanel panel){ 
        JTextField nomField = (JTextField) panel.getComponent(1);
        JTextField cuatField = (JTextField) panel.getComponent(3); 
        JComboBox<String> tipoPlanField = (JComboBox<String>) panel.getComponent(5);
        JTextField optMinField = (JTextField) panel.getComponent(7);
        return new String[]{nomField.getText(), cuatField.getText(), optMinField.getText(), (String)tipoPlanField.getSelectedItem()};
    }
    
    private static JPanel formAltaMateria(String tipo, JComboBox<String> obliOno){
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para el alta de la materia:", JLabel.NORTH_EAST);
        panel.add(new JLabel("Carrera:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Nombre:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Cuatrimestre:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Obligatoria:"));
        panel.add(obliOno);
        return panel;
    }  

     private static String[] obtenerDatosAltaMateria(JPanel panel){ 
        JTextField carrField = (JTextField) panel.getComponent(1);
        JTextField nombreField = (JTextField) panel.getComponent(3);
        JTextField cuatriField = (JTextField) panel.getComponent(5); 
        JComboBox<String> obliField = (JComboBox<String>) panel.getComponent(7);
        return new String[]{carrField.getText(),nombreField.getText(), cuatriField.getText(), (String)obliField.getSelectedItem()};
    }
    
    private static JPanel formAltaAlumno(String tipo) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para el alta del alumno:", JLabel.NORTH_EAST);
        panel.add(new JLabel("Nombre Completo:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Carrera:"));
        panel.add(new JTextField());
        return panel;
    }  
    
    private static String[] obtenerDatosAltaAlumno(JPanel panel){ 
        JTextField nombreField = (JTextField) panel.getComponent(1);
        JTextField carreraField = (JTextField) panel.getComponent(3); 
        return new String[]{nombreField.getText(), carreraField.getText()};
    }
    
    private static JPanel formAltaCorrelativa(String tipo){
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titulo = new JLabel("Ingrese datos para agregar correlativa:", JLabel.NORTH_EAST);
        panel.add(new JLabel("Carrera:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Materia:"));
        panel.add(new JTextField());
        panel.add(new JLabel("Correlativa:"));
        panel.add(new JTextField());
        return panel; 
    }
    
    private static String[] obtenerDatosAltaCorrelativa(JPanel panel){ 
        JTextField carreraField = (JTextField) panel.getComponent(1);
        JTextField materiaField = (JTextField) panel.getComponent(3);
        JTextField correlativaField = (JTextField) panel.getComponent(5); 
        return new String[]{carreraField.getText(), materiaField.getText(), correlativaField.getText()};
    }
    
    public Facultad getFacultad(){return facultad;}
}
