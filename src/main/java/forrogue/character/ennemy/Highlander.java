/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forrogue.character.ennemy;

/**
 *
 * @author user
 */
public class Highlander extends Lourds {
    
    public Highlander(){
        this.setHp(12);
        this.setMaxHp(12);
        this.setAttack(4);
        this.setDefense(0);
        this.setName("Highlander");
        this.setGender("Male");
        // Rajouter l'inventaire quand Pierre aura implémenté toute les armes
    }
    
}