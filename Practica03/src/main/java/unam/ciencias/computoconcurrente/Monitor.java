package unam.ciencias.computoconcurrente;
public class Monitor{
  private int personas;
  private boolean escribiendo;

  public Monitor(){
    personas=0;
    escribiendo= false;

  }
  public synchronized boolean entraEscritor(String escritor){
    if (personas == 0){
      escribiendo = true;
      personas++;
      System.out.println("Entro"+ escritor+", personas: "+personas) ;
      return true;
    }

    else{
      try{
        wait();
      }catch(Exception exc){}
    }
    return false;
  }

  public synchronized int entraLector(String lector){
    if (!escribiendo){
        personas++;
        System.out.println("Entro "+lector+" , personas: " +personas);
    }
    else{
        try{
          wait();
        }catch(Exception exc){}
    }
    return personas;
  }

  public synchronized int saleLector(String lector){
    if (personas>0){
      if(!escribiendo){
        personas--;
        System.out.println("Salio "+lector+ " ,personas: "+personas);
      }
    }else{
      notifyAll();
    }
    return personas;
  }

  public synchronized int saleEscritor(String escritor){
    if(personas > 0){
      personas--;
      escribiendo = false;
      System.out.println("Salio el "+ escritor +", personas: "+ personas);

      notifyAll();
      try{
        wait();
        }catch(Exception exc){}

    }
    return personas;
  }



}
