/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forrogue.character.enemy.vikings;

import forrogue.character.enemy.vikings.Vikings;

/**
 *
 * @author user
 */
public class Berserker extends Vikings {
    
    public Berserker(){
        this.setHp(6);
        this.setMaxHp(6);
        this.setAttack(5);
        this.setDefense(0);
        this.setName("Berseker");
        this.setGender("Male");
        this.setSkin('B'); // Pour mes tests (tama)
        // Rajouter l'inventaire quand Pierre aura implémenté toute les armes
    }
    
    
    
}