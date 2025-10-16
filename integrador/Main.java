import java.util.*;
import javax.swing.*;

public class Main{
    private static JTextArea mensajesArea;
    
    public static void main(String[] args){ 
        JTextArea mensajesArea = new JTextArea();
        Interfaz interfaz = new Interfaz(mensajesArea);
        Facultad facultad = interfaz.getFacultad();
        FactoryCreator factory = new FactoryCreator();
        AdministradorInscripcion admin = new AdministradorInscripcion();
        
        facultad.agregarCarrera("Ingenieria Informatica", 6, 2, new PlanDeEstudio(new TipoA())); 
        facultad.agregarCarrera("Licenciatura en Matematica",  6, 3, new PlanDeEstudio(new TipoC()));
        
        facultad.agregarMateria("Matematicas I", 1, true, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Programacion I", 1, true, new ArrayList<>(),"Ingenieria Informatica"); 
        facultad.agregarMateria("Matematicas II", 2, true, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Programacion II", 2, false, new ArrayList<>(),"Ingenieria Informatica"); 
        facultad.agregarMateria("Algoritmos", 3, true, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Estructuras de Datos", 3, true, new ArrayList<>(),"Ingenieria Informatica");
        facultad.agregarMateria("Bases de Datos", 4, false, new ArrayList<>(),"Ingenieria Informatica"); 
        facultad.agregarMateria("Ingenieria de Software", 4, true, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Redes", 5, true, new ArrayList<>(), "Ingenieria Informatica");
        facultad.agregarMateria("Sistemas Operativos", 5, false, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Inteligencia Artificial", 6, true, new ArrayList<>(), "Ingenieria Informatica"); 
        facultad.agregarMateria("Proyecto Final", 6, true, new ArrayList<>(),"Ingenieria Informatica");
        
        facultad.agregarMateria("Algebra I", 1, true, new ArrayList<>(),"Licenciatura en Matematica"); 
        facultad.agregarMateria("Calculo I", 1, true, new ArrayList<>(),"Licenciatura en Matematica"); 
        facultad.agregarMateria("Algebra II", 2, false, new ArrayList<>(), "Licenciatura en Matematica");
        facultad.agregarMateria("Calculo II", 2, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Estadistica I", 3, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Geometria I", 3, false, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Estadistica II", 4, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Geometria II", 4, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Analisis Matematico I", 5, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Teoria de Numeros", 5, false, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Analisis Matematico II", 6, true, new ArrayList<>(), "Licenciatura en Matematica"); 
        facultad.agregarMateria("Trabajo Final", 6, true, new ArrayList<>(), "Licenciatura en Matematica");
        
        facultad.agregarAlumno("Juan Perez", "Licenciatura en Matematica");
        facultad.agregarAlumno("Ana Garcia", "Ingenieria Informatica");
        
        Alumno alum = facultad.getAlumnos().stream().filter(c->c.getNombreCompleto().equals("Ana Garcia")).findFirst().orElse(null);
        Carrera carrera = facultad.getCarreras().stream().filter(c->c.getNombreCarrera().equals("Ingenieria Informatica")).findFirst().orElse(null);
        
        facultad.inscribirAlumnoACarrera(alum, carrera);
        
        for(Materia materia : carrera.getMaterias()){ 
            facultad.inscribirAlumnoAMateria(alum, materia);
            materia.apruebaCursada(true); 
            materia.apruebaFinal(true);
            materia.apruebaPromocion(true);
            alum.aprobarMateria(materia);}
            
        configurarCorrelativasFacultad(facultad);
            
        if(carrera != null){
            boolean finalizo = carrera.finalizoCarrera(alum); 
            if(finalizo){System.out.println("Ana García ha finalizado la carrera de Ingeniería Informática.\n");}
                else{System.out.println("Ana García no ha finalizado la carrera de Ingeniería Informática.\n");}}
        
        interfaz.iniciarInterfaz();
    }
    
    public void agregarMensajeError(String mensaje){ 
        mensajesArea.append(mensaje + "\n");}
      
    private static void configurarCorrelativasFacultad(Facultad facultad) {
        Map<String, List<String>> correlativasIngenieria = Map.of(
            "Matematicas II", List.of("Matematicas I"),
            "Programacion II", List.of("Programacion I"),
            "Algoritmos", List.of("Matematicas II", "Programacion II"),
            "Estructuras de Datos", List.of("Algoritmos"),
            "Bases de Datos", List.of("Estructuras de Datos"),
            "Ingenieria de Software", List.of("Estructuras de Datos"),
            "Redes", List.of("Estructuras de Datos"),
            "Sistemas Operativos", List.of("Estructuras de Datos"),
            "Inteligencia Artificial", List.of("Bases de Datos", "Ingenieria de Software"),
            "Proyecto Final", List.of("Inteligencia Artificial", "Redes"));
        configurarCorrelativas(facultad, "Ingenieria Informatica", correlativasIngenieria);
        Map<String, List<String>> correlativasMatematica = Map.of(
            "Algebra II", List.of("Algebra I"),
            "Calculo II", List.of("Calculo I"),
            "Estadistica I", List.of("Algebra I", "Calculo I"),
            "Geometria I", List.of("Algebra I"),
            "Estadistica II", List.of("Estadistica I"),
            "Geometria II", List.of("Geometria I"),
            "Analisis Matematico I", List.of("Calculo II"),
            "Teoria de Numeros", List.of("Algebra II"),
            "Analisis Matematico II", List.of("Analisis Matematico I"),
            "Trabajo Final", List.of("Analisis Matematico II", "Teoria de Numeros"));
        configurarCorrelativas(facultad, "Licenciatura en Matematica", correlativasMatematica);
    }
    
    private static void configurarCorrelativas(Facultad facultad, String nombreCarrera, Map<String, List<String>> correlativasMap) {
        correlativasMap.forEach((materia, correlativas) -> {
            Materia materiaPrincipal = buscarMateria(facultad, materia, nombreCarrera);
            if (materiaPrincipal != null) {
                correlativas.forEach(correlativa -> {
                    Materia correlativaMateria = buscarMateria(facultad, correlativa, nombreCarrera);
                    if (correlativaMateria != null) {
                        facultad.agregarCorrelativa(materiaPrincipal, correlativaMateria);
                    }
                });
            }
        });}

    private static Materia buscarMateria(Facultad facultad, String nombreMateria, String nombreCarrera) {
        return facultad.getCarreras().stream().filter(c -> c.getNombreCarrera().equals(nombreCarrera)).flatMap(c -> c.getMaterias().stream()).filter(m -> m.getNombre().equals(nombreMateria)).findFirst().orElse(null);}
}
