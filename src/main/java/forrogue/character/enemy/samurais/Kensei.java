/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forrogue.character.enemy.samurais;

import forrogue.character.enemy.samurais.Samurais;

/**
 *
 * @author maurice
 */
public class Kensei extends Samurais {
    
    public Kensei(){
        this.setHp(12);
        this.setMaxHp(12);
        this.setAttack(4);
        this.setDefense(1);
        this.setName("Kensei");
        this.setGender("Male");
        // Rajouter l'inventaire quand Pierre aura implémenté toute les armes
    }
    
}