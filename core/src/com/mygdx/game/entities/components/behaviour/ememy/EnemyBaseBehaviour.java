package com.mygdx.game.entities.components.behaviour.ememy;

import com.mygdx.game.Managers;
import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.components.behaviour.ememy.actionAi.EnemyCombatBehaviour;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;
import com.mygdx.game.utils.types.NumberUtils;

import java.util.ArrayList;

public class EnemyBaseBehaviour extends EntityComponent {


    private Entity target = null;
    private Gun gun = null;
    private final ArrayList<EnemyCombatBehaviour> combatBehaviours;
    private final boolean pickBehaviourRandomly;
    private int currentBehaviourIndex = 0;
    private int currentBehaviourTimer = 0;
    private EnemyCombatBehaviour currentBehaviour;

    public EnemyBaseBehaviour(
            ArrayList<EnemyCombatBehaviour> combatBehaviours,
            boolean pickBehaviourRandomly
            ) {
        super.name = ComponentName.ENEMY;
        this.combatBehaviours = combatBehaviours;
        this.pickBehaviourRandomly = pickBehaviourRandomly;

        this.currentBehaviour = combatBehaviours.get(0).copy(); // TODO : this is unfinished and sucks ass, but i lack the motivation to fix it
        this.currentBehaviourTimer = currentBehaviour.getDuration();
    }

    @Override
    public void onUpdate(Entity owner) {
        if (target == null) {
            target = Managers.entityManager.findClosestEntityWithComponent(owner, ComponentName.PLAYER);
        }

        owner.setField(FieldName.Target, target);


        if (target != null) {
            currentBehaviour.act(owner);
        }

    }

    private void pickNextBehaviour() {
        if (pickBehaviourRandomly) {
            currentBehaviourIndex = NumberUtils.randomInt(0, combatBehaviours.size());
        }else {
            currentBehaviourIndex++;
            currentBehaviourIndex %= combatBehaviours.size();
        }

        currentBehaviour = combatBehaviours.get(currentBehaviourIndex);
        currentBehaviourTimer = currentBehaviour.getDuration();
    }

    @Override
    public void onSudoku(Entity owner) {
        Managers.soundManager.playSound("enemy_death", 1f, 0.1f);
        Managers.levelManager.killedEnemy();
    }

    @Override
    public void onTakeDamage(Entity owner, float amount) {
        Managers.soundManager.playSound("enemy_hit", 1f, 0.1f);
    }

    @Override
    public void onComponentAttached(Entity owner) {
        gun = (Gun) owner.getComponentByName(ComponentName.GUN);
        // owner.setField(FieldName.Gun, gun);
    }


    @Override
    public void onFirstAttached(Entity owner) {
        owner.knockBackMultiplier = 8f;
        owner.flipWithMoveDirection = true;
        owner.canBeDamaged = true;
        owner.initializeField(FieldName.Target, null);
    }

    @Override
    public void onCollide(Entity owner, Entity other) {
        if (NumberUtils.distance(owner.x, owner.y, other.x, other.y) < 16f && other.hasComponent(ComponentName.ENEMY)) {
            // bump away from each other
            owner.goInDirection(NumberUtils.directionToward(other.x, other.y, owner.x, owner.y), 0.25f);
        }
    }

    @Override
    public EntityComponent copy() {
        return new EnemyBaseBehaviour(combatBehaviours, pickBehaviourRandomly);
    }
}
