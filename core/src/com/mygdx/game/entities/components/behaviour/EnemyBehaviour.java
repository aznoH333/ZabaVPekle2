package com.mygdx.game.entities.components.behaviour;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.NumberUtils;

public class EnemyBehaviour extends EntityComponent {


    private Entity target = null;
    private Gun gun = null;

    public EnemyBehaviour() {
        super.name = ComponentName.ENEMY;
    }

    @Override
    public void onUpdate(Entity owner) {
        if (target == null) {
            target = Managers.entityManager.findClosestEntityWithComponent(owner, ComponentName.PLAYER);
        }

        if (target != null) {
            float direction = NumberUtils.directionToward(owner.x, owner.y, target.x, target.y);


            if (gun != null) {
                gun.direction = direction;
                gun.shoot(owner);
            }

            owner.goInDirection(direction, 1f);
        }
    }

    @Override
    public void onSudoku(Entity owner) {
        Managers.soundManager.playSound("enemy_death", 1f, 0.1f);
        Managers.worldManager.killedEnemy();
    }

    @Override
    public void onTakeDamage(Entity owner, float amount) {
        Managers.soundManager.playSound("enemy_hit", 1f, 0.1f);
    }

    @Override
    public void onComponentAttached(Entity owner) {
        gun = (Gun) owner.getComponentByName(ComponentName.GUN);
    }


    @Override
    public void onFirstAttached(Entity owner) {
        owner.knockBackMultiplier = 8f;
        owner.flipWithMoveDirection = true;
        owner.canBeDamaged = true;
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (NumberUtils.pythagoras(owner.x, owner.y, other.x, other.y) < 16f && other.hasComponent(ComponentName.ENEMY)) {
            // bump away from each other
            owner.goInDirection(NumberUtils.directionToward(other.x, other.y, owner.x, owner.y), 0.25f);
        }
    }

    @Override
    public EntityComponent copy() {
        return new EnemyBehaviour();
    }
}
