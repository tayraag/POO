import java.util.*;
import javax.swing.*;

public class Facultad{

    private String nombreFacultad;
    private List<Carrera> carreras;
    private List<Alumno> alumnos;
    private List<Materia> materias;
    private AdministradorInscripcion inscribe;
    private FactoryCreator factory;
    
    public Facultad(String nombreFacultad){
        this.nombreFacultad = nombreFacultad;
        this.carreras = new ArrayList<>();
        this.alumnos = new ArrayList<>();
        this.materias = new ArrayList<>();
        this.inscribe = new AdministradorInscripcion();
        this.factory = new FactoryCreator();
    }

    public String getNombreFacultad(){
        return nombreFacultad;}

    public void agregarCarrera(String nombre, int cantCuatrimestres, int cantOptativasMinimas, PlanDeEstudio plan){
        Carrera carrera = factory.creaCarrera(nombre, cantCuatrimestres, cantOptativasMinimas, plan);
        if(!carreras.contains(carrera)){
            carreras.add(carrera);
            System.out.println("Carrera agregada: " + carrera.getNombreCarrera());}
        else{System.out.println("La carrera ya está registrada en la facultad.");}
    }
    
    public void agregarMateria(String nombre, int cuatrimestre, boolean obligatoria, List<Materia> correlativa, String carr){
        Carrera carrera = getCarreras().stream().filter(c->c.getNombreCarrera().equals(carr)).findFirst().orElse(null);
        Materia materia = factory.creaMateria(nombre, cuatrimestre, obligatoria, correlativa, carrera);
        carrera.agregaMateria(materia);
        if(!materias.contains(materia)){
            System.out.println("Materia agregada: " + materia.getNombre());}
        else{System.out.println("La materia ya está registrada en la facultad.");}
    }

    public void agregarAlumno(String nom, String carr){
        Carrera carrera = getCarreras().stream().filter(c->c.getNombreCarrera().equals(carr)).findFirst().orElse(null);
        Alumno alumno = factory.creaAlumno(nom, carrera);
        if(!alumnos.contains(alumno)){
            alumnos.add(alumno);
            System.out.println("Alumno agregado: " + alumno.getNombreCompleto());}
        else{System.out.println("El alumno ya está registrado en la facultad.");}
    }
    
    public void agregarCorrelativa(Materia mat1, Materia corre){
        factory.creaCorrelativa(mat1, corre);
    }

    public void inscribirAlumnoACarrera(Alumno alumno, Carrera carrera){
        inscribe.inscribirAlumnoCarrera(alumno, carrera);}

    public boolean inscribirAlumnoAMateria(Alumno alumno, Materia materia){
        return inscribe.inscribirAlumnoMateria(alumno, materia);}

    public String verificarSiAlumnoFinalizoCarrera(Alumno alumno){
        if(alumno.finalizoCarrera()){return "El alumno " + alumno.getNombreCompleto() + " ha finalizado su carrera.";} 
            else{return "El alumno " + alumno.getNombreCompleto() + " no ha finalizado la carrera.";}
    }

    public List<Carrera>getCarreras(){return carreras;}

    public List<Alumno>getAlumnos(){return alumnos;}

    @Override
    public String toString(){
        return "Facultad{"+"nombreFacultad='"+nombreFacultad+'\''+", carreras="+carreras+", alumnos="+alumnos+'}';
    }
}
