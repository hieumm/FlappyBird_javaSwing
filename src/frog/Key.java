/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Key implements KeyListener{
    //nhận sự kiện ấn vào nút Up thì cóc sẽ nhảy
    private boolean press = false;
    private boolean release = true;

    public boolean isPress() {
        return press;
    }

    public void setPress(boolean press) {
        this.press = press;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {//khi ấn gọi sự kiện keyPress
        //thả ra thì là keyRealse
        if(e.getKeyCode() == KeyEvent.VK_UP){//chỉ nhận sự kiện phím Up
            if(release){
                press = true;
                release = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        release = true;
    }
    
}
