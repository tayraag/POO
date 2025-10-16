
public class PlanDeEstudio
{
   private TipoPlan tipoPlan;
   
   public PlanDeEstudio(TipoPlan tipoPlan){
        this.tipoPlan = tipoPlan;}
    
   public boolean validaPlan(Alumno alumno, Materia materia){return tipoPlan.validaPlan(alumno, materia);}

   public TipoPlan getTipoPlan(){return tipoPlan;}

   public void setTipoPlan(TipoPlan tipoPlan){this.tipoPlan = tipoPlan;}
}