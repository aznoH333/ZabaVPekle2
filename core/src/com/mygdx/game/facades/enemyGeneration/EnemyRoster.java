package com.mygdx.game.facades.enemyGeneration;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.utils.Trait;
import com.mygdx.game.utils.TraitPicker;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class EnemyRoster {
    private final ArrayList<Trait<Entity>> weightedEnemies;


    public EnemyRoster(
            ArrayList<Trait<Entity>> roster
    ) {
        this.weightedEnemies = roster;
    }



    public TraitPicker<Entity> createBudgetedPickerForRoom(float budget) {
        ArrayList<Integer> indexesToExclude = new ArrayList<>();
        ArrayList<Trait<Entity>> roomEnemies = new ArrayList<>();
        for (int i = Math.min(weightedEnemies.size(), NumberUtils.randomInt(1, 3)); i > 0; i--) {
            int pickedIndex;
            do {
                pickedIndex = NumberUtils.randomInt(0, weightedEnemies.size() - 1);
            } while (indexesToExclude.contains(pickedIndex));

            indexesToExclude.add(pickedIndex);
            roomEnemies.add(weightedEnemies.get(pickedIndex));
        }

        // build queue
        return new TraitPicker<>(roomEnemies, budget);

    }
}
