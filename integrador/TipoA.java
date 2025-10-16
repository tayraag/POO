import java.util.*;

public class TipoA implements TipoPlan
{
   @Override
   public boolean validaPlan(Alumno alum, Materia mat){
       List<Materia>correlativas = mat.getCorrelativas();
       List<Materia>aprobadasAlumno = alum.getMateriasAprobadas();
       for(Materia mate : correlativas){
           if(!aprobadasAlumno.contains(mate)||!mate.cursadaAprobada()){
               return false;}}
       return true;
    }
}
