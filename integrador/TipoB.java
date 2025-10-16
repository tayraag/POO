import java.util.*;

public class TipoB implements TipoPlan
{
   @Override
   public boolean validaPlan(Alumno alum, Materia mat){
       List<Materia>correlativas = mat.getCorrelativas();
       List<Materia>aprobadasAlumno = alum.getMateriasAprobadas();
       for(Materia mate : correlativas){
            if(!aprobadasAlumno.contains(mate)||!mate.materiaAprobadaTotal()){
                return false;}}
       return true;
    }
}
