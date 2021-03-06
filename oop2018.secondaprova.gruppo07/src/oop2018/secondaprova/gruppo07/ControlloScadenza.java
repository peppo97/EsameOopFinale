
package oop2018.secondaprova.gruppo07;

import java.awt.EventQueue;
import java.time.LocalDateTime;
import javax.swing.*;

/**
 * La classe è un thread che si occupa di controllare se i promemoria sono
 * scaduti ad intervalli presettati.
 *
 * @author gruppo07
 */
public class ControlloScadenza extends Thread {

    private ElencoPromemoria elenco;
    private final int secondi;
    private final JFrame frame;

    /**
     * Crea un thread ControllaScadenza
     * 
     * @param elenco Elenco su cui effettuare il controllo
     * @param secondi Intervallo di tempo tra due controlli consecutivi
     * @param frame Frame rispetto al quale far comparire il messaggio di
     * notifica
     */
    public ControlloScadenza(ElencoPromemoria elenco, int secondi, JFrame frame) {
        this.elenco = elenco;
        this.secondi = secondi;
        this.frame = frame;
    }

   
    /**
     * metodo per modificare l'elenco di promemoria su cui opera il thread.
     * Il metodo non è thread safe sull'elenco.
     * @param elenco nuovo elenco da settare
     */
    public void setElenco(ElencoPromemoria elenco) {
        this.elenco = elenco;
    }

    /**
     * Il thread esegue una sleep di un tempo pari a quello passatto come
     * parametro al costruttore in secondi<br>
     * Controlla se sono scaduti promemoria ed eventualemente li elimina facendo
     * comparire una finestra informativa
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(secondi * 1000);
            } catch (InterruptedException ex) {
            }
            Promemoria p = elenco.ricercaPromemoria(LocalDateTime.now());
            if (p != null) {
                EventQueue.invokeLater(() -> JOptionPane.showMessageDialog(frame, p.toString() + "\nPromemoria cancellato", "Scaduto", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/icone/timeIcon.png"))));
                try {
                    elenco.rimuoviPromemoria(p);
                } catch (PromemoriaNonEsistenteException ex) {
                }
            }
        }
    }
}
