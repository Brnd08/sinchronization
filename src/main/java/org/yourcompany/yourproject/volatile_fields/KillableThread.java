package org.yourcompany.yourproject.volatile_fields;

public class KillableThread extends Thread{
    // Attributo que indica si este thread debe seguir corriendo.
    boolean keepRunning = true;

    public KillableThread(){
        // Creamos este thread como daemon para que no evite que el programa termine.
        this.setDaemon(true);
    }

    @Override
    public void run() {
        // Mantenemos el hilo vivo hasta que la bandera indique lo contrario
        while (keepRunning) {
        }
    }
}