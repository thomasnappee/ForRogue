/*
 * Copyright (C) 2018 tama
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package forrogue.map;

import charva.awt.Component;
import charva.awt.Point;
import charvax.swing.JOptionPane;
import forrogue.Chest;
import forrogue.GameObject;
import forrogue.character.Player;
import forrogue.character.ennemy.Ennemy;
import forrogue.character.friendly.QuestMan;
import forrogue.game.GameConstant;
import forrogue.game.GameEngine;
import forrogue.item.Item;
import forrogue.item.quest.Gem;

import static charvax.swing.JOptionPane.YES_NO_OPTION;
import static charvax.swing.JOptionPane.YES_OPTION;

/**
 *
 * @author tama
 */

public class Map {

    private Object[][] matrix;
    private GameEngine gEngine;

    public Map(GameEngine gEngine){
        this.gEngine = gEngine;
        this.matrix = gEngine.getHub().getMatrix();
    }

    public void movePlayer(Point move) {

        Point coord_player = this.gEngine.getPlayer().getPosition();

        Object target = this.matrix[coord_player.y+move.y][coord_player.x+move.x];

        if(!(target instanceof GameObject)){
            if((char) target == GameConstant.SKIN_VOID) {
                this.matrix[coord_player.y][coord_player.x] = GameConstant.SKIN_VOID;
                this.matrix[coord_player.y + move.y][coord_player.x + move.x] = GameConstant.SKIN_PLAYER;
                this.gEngine.getPlayer().move(move);
            }
        }

        else if(target instanceof Dungeon){
            this.matrix[coord_player.y][coord_player.x] = GameConstant.DUNGEON_PLAYER_POS;
            this.matrix[coord_player.y + move.y][coord_player.x + move.x] = new Dungeon(((Dungeon) target).getDifficulty(), gEngine.getHub(), gEngine.getRandom());
            this.setMatrix(((Dungeon) target).getMatrix());
            this.setPlayerPosition();
            this.gEngine.getGameWindow().getGameView().repaint();
        }

        else if(target instanceof  Hub){
            this.setMatrix(((Hub) target).getMatrix());
            this.setPlayerPosition();
            this.gEngine.getGameWindow().getGameView().repaint();
        }

        else if(target instanceof Ennemy){
            Ennemy ennemy = (Ennemy) target;

            if(ennemy.getSpeed() > gEngine.getPlayer().getSpeed()){
                ennemy.attack(gEngine.getPlayer());
                gEngine.getPlayer().attack(ennemy);
            } else{
                gEngine.getPlayer().attack(ennemy);
                ennemy.attack(gEngine.getPlayer());
            }

            if(ennemy.getHp() <= 0){
                this.matrix[coord_player.y + move.y][coord_player.x + move.x] = GameConstant.SKIN_VOID;
                this.gEngine.getPlayer().addGems(
                        Gem.values()[this.gEngine.getRandomNumber()%Gem.values().length].getColor(),
                        this.gEngine.getRandomNumber()%5+1
                );
            }

            if(gEngine.getPlayer().getHp() <= 0){
                // TODO : Prévoir le cas où le joueur meurt
            }
        }

        else if(target instanceof Chest){
            Player player = gEngine.getPlayer();
            for(Item item : ((Chest) target).getChestContent()){
                player.getInventory().add(item);
            }
            this.matrix[coord_player.y + move.y][coord_player.x + move.x] = GameConstant.SKIN_VOID;
            this.gEngine.sendUpdateInventorySignal();
        }

        else if(target instanceof QuestMan){

            QuestMan questMan = (QuestMan) target;

            Object[] objString = {
                    GameConstant.STRING_QUESTMAN_0,
                    GameConstant.STRING_QUESTMAN_1,
                    GameConstant.STRING_QUESTMAN_2,
                    " ",
                    String.format("Hey ! I need %s %s gem.", questMan.getQuestQuantity(), questMan.getQuestItem()),
                    "Have any ?"
            };

            int resp = JOptionPane.showConfirmDialog(
                    (Component) gEngine.getGameWindow().getCommandPrompt(),
                    objString,
                    "The QuestMan",
                    YES_NO_OPTION);

            if(resp == YES_OPTION){
                objString[4] = "Thanks bro !";
                objString[5] = "Here is your reward";
                if(questMan.checkPlayerGems(this.gEngine.getPlayer().getInventory(), this.gEngine.getPlayer().getGems())){
                    JOptionPane.showMessageDialog(
                            (Component) gEngine.getGameWindow().getCommandPrompt(),
                            objString,
                            "The Quest Man",
                            0
                    );
                    questMan.generateQuest(Math.abs(this.gEngine.getRandom().nextInt()));
                    gEngine.getGameWindow().updateInventory();

                } else{
                    objString[4] = "Haha u suck";
                    objString[5] = "GTFO xD";

                    JOptionPane.showMessageDialog(
                            (Component) gEngine.getGameWindow().getCommandPrompt(),
                            objString,
                            "The Quest Man",
                            0
                    );
                }
            } else{
                objString[4] = "Oh...";
                objString[5] = "Come back when you want !";
                JOptionPane.showMessageDialog(
                        (Component) gEngine.getGameWindow().getCommandPrompt(),
                        objString,
                        "The Quest Man",
                        0
                );
            }
        }
    }

    public Point getPlayerPosition() {
        int x = 0, y = 0;
        for(Object[] line : this.matrix){
            for(Object o : line){
                if(o.getClass() == Player.class){
                    return new Point(x, y);
                } x++;
            } y++; x = 0;
        } return new Point(-1,-1);
    }

    public void setPlayerPosition(){
        int x = 0, y = 0;
        for(Object[] line : this.matrix){
            for(Object o : line){
                if(o != null) { // TODO : Vérifier pourquoi ça fait null
                    if (!(o instanceof GameObject)) {
                        if (((char) o) == GameConstant.DUNGEON_PLAYER_POS) {
                            this.gEngine.getPlayer().setPosition(new Point(x, y));
                            this.matrix[y][x] = this.gEngine.getPlayer();
                        }
                    }
                    x++;
                }
            } y++; x = 0;
        }
    }

    public Object[][] getMatrix(){
        return this.matrix;
    }

    public void setMatrix(Object[][] matrix){
        this.matrix = matrix;
    }



}
