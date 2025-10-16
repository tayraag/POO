
import java.util.*;
import javax.swing.*;

public class AdministradorInscripcion {

    private List<Alumno> alumnos;

    public AdministradorInscripcion() {
        this.alumnos = new ArrayList<>();
    }

    public void inscribirAlumnoCarrera(Alumno alum, Carrera carr) {
        try {
            if (alum == null) {
                System.out.println("El alumno no puede ser nulo.");
            } else if (carr == null) {
                System.out.println("La carrera no puede ser nula.");
            }
            alum.setCarrera(carr);
            if (!alumnos.contains(alum)) {
                alumnos.add(alum);
            }
            System.out.println("El alumno " + alum.getNombreCompleto() + " se inscribio a " + carr.getNombreCarrera());
        } catch (IllegalArgumentException e) {
            System.err.println("Error en la inscripción a la carrera: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado al inscribir al alumno en la carrera: " + e.getMessage());
        }
    }

    public boolean inscribirAlumnoMateria(Alumno alum, Materia mat) {
        try {
            if (alum == null) {
                System.out.println("El alumno no puede ser nulo.");
                return false;
            } else if (mat == null) {
                System.out.println("La materia no puede ser nula.");
                return false;
            }
            Carrera carrAlum = alum.getCarrera();
            if (carrAlum == null) {
                System.out.println("El alumno no esta inscripto a ninguna carrera.");
                return false;
            }
            if (!carrAlum.getMateriasObligatorias().contains(mat) && !carrAlum.getMateriasOptativas().contains(mat)) { //verifica si la materia a la que se quiere inscribir es parte de la carrera si no lo esta lanza excepcion
                System.out.println("La materia a la que se quiere inscribir no pertenece a la carrera.");
                return false;
            }
            PlanDeEstudio plan = carrAlum.getPLanDeEstudio();
            //ahora se verifica si cumple con el plan y luego se lo inscribe o no a la materia
            if (plan.validaPlan(alum, mat)) {
                if (!alum.getMateriaCursadas().contains(mat)) {
                    alum.getMateriaCursadas().add(mat);
                    System.out.println("El alumno " + alum.getNombreCompleto() + " se ha inscrito en la materia " + mat.getNombre());
                    return true;
                } else {
                    System.out.println("El alumno " + alum.getNombreCompleto() + " ya está inscrito en la materia " + mat.getNombre());
                    return false;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error en la inscripción a la materia: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Se produjo un error inesperado al inscribir al alumno en la materia: " + e.getMessage());
        }
        return false;
    }
}
