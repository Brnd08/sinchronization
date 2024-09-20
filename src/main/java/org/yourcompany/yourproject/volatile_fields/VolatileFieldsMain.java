package org.yourcompany.yourproject.volatile_fields;

import static java.lang.System.out;

class VolatileFieldsMain{
    public static void main(String[] args) throws InterruptedException {
        // Creamos una instancia de nuestro Thread modificado
        KillableThread t = new KillableThread();

        /* 
         * Obtenemos el tiempo inicial, solo para fines ilustrativos, e iniciamos 
         * la ejecución del thread en segundo plano.
         */
        System.out.printf("*Grace period started. 'keepRunning' is %b ----%n", t.keepRunning);
        var startTime = System.nanoTime();
        t.start();

        
        // Detenemos el hilo principal, para dejar ejecutar el thread en segundo plano durante unos instantes
        Thread.sleep(2000);

        /*
         * Cambiamos el valor de la bandera, el Thread deberia detenerse al momento.
         * Guardamos el tiempo en que se cambia la bandera para fines ilustrativos.
         */
        t.keepRunning = false;
        var flagChangeTime = System.nanoTime();
        System.out.printf("*Grace period ended. 'keepRunning' set to %b ----%n", t.keepRunning);

        /*
         * Bloqueamos la ejecución del hilo principal hasta que el hilo en segundo plano termine de correr
         * o hasta un maximo de 15 segundos.
         */
        out.println("*If thread never dies, it will automatically die after 20s.");
        t.join(20_000);

        // Calculamos tiempo de ejecución, solo para fines ilustrativos.
        var stopTime = System.nanoTime();
        var nanosecondsInASecond =  Math.pow(10, 9);
        out.printf("*Thread terminated. Ran for %f seconds.%n", (stopTime-startTime)/nanosecondsInASecond);
        out.printf("*Stop flag was sent at second %f.%n", (flagChangeTime-startTime)/nanosecondsInASecond);

    }
}