import java.util.*;

public class TipoE implements TipoPlan
{
   @Override
   public boolean validaPlan(Alumno alum, Materia mat){
       List<Materia>correlativas = mat.getCorrelativas();
       List<Materia>aprobadasAlumno = alum.getMateriasAprobadas();
       for(Materia mate : correlativas){
            if(!aprobadasAlumno.contains(mate)||!mate.materiaAprobadaTotal()){
                return false;}}
       Carrera carrAlum = alum.getCarrera();
       int cuatriActual = mat.getCuatrimestre();
       List<Materia>materiasDeLaCarrera = new ArrayList<>();
       materiasDeLaCarrera.addAll(carrAlum.getMateriasObligatorias()); 
       materiasDeLaCarrera.addAll(carrAlum.getMateriasOptativas());
       for(Materia materia : materiasDeLaCarrera){
           int cuatriMateria = materia.getCuatrimestre();
           if(cuatriMateria >= cuatriActual - 3 && cuatriMateria < cuatriActual){
               if(!aprobadasAlumno.contains(materia)||!materia.materiaAprobadaTotal()){
                return false;}}}
       return true;
    }
}