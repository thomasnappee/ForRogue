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
package forrogue.character.enemy;

import charva.awt.Point;
import forrogue.character.Character;

/**
 *
 * @author tama
 */
public abstract class Enemy extends Character {
    
    public void findPath(int[][] matrix, Point monster_pos, Point enemy_pos){

        /** 
         * We fill the matrix with 0 and 1
         * 0 means there is nothing in this area
         * 1 means there is something that's blocking this area (other monster, chest, wall, ...)
         */

        for(int i = 0; i<matrix.length ; i++){
            for(int j = 0; j<matrix[i].length; j++){
                if(i == enemy_pos.x && j == enemy_pos.y) matrix[i][j] = 2;
                else if(matrix[i][j] > 0) matrix[i][j] = 1; 
            }
        }
        
        
    }
    
    private Point pathFinder(Point p){
        //TO IMPLEMENT//
        return p;
    }

    public Point findPath(){
        Point p = new Point(0,0);
        //TO IMPLEMENT//
        return p;
    }
}