/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programta.Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Nadiya
 */
public class DoubleNumbersKeyListener implements KeyListener {

    public final HashSet<Character> valid_keys = new HashSet<>();
    public final ArrayList<Character> sequence = new ArrayList<>();

    public DoubleNumbersKeyListener() {
        valid_keys.add('.');
        valid_keys.add('0');
        valid_keys.add('1');
        valid_keys.add('2');
        valid_keys.add('3');
        valid_keys.add('4');
        valid_keys.add('5');
        valid_keys.add('6');
        valid_keys.add('7');
        valid_keys.add('8');
        valid_keys.add('9');
        valid_keys.add((char) KeyEvent.VK_BACK_SPACE);
        valid_keys.add((char) KeyEvent.VK_DELETE);
    }

    @Override
    public void keyTyped(KeyEvent event) {
        char c = event.getKeyChar();
            if (!valid_keys.contains(c)) {
                event.consume();
            } else {
                if (c == KeyEvent.VK_DELETE || c == KeyEvent.VK_BACK_SPACE) {
                    if (!sequence.isEmpty()) {
                        char last = sequence.remove(sequence.size() - 1);
                        if (last == '.') {
                            valid_keys.add(last);
                        }
                    }
                } else {
                    sequence.add(c);
                    if (c == '.') {
                        valid_keys.remove(c);
                    }
                }
            }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}