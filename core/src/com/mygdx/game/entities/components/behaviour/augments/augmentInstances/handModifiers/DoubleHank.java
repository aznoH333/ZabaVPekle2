package com.mygdx.game.entities.components.behaviour.augments.augmentInstances.handModifiers;

import com.mygdx.game.entities.ComponentName;
import com.mygdx.game.entities.components.behaviour.augments.SimpleOnApplyModifierAugmentInstance;
import com.mygdx.game.entities.components.behaviour.gun.BulletOrigin;
import com.mygdx.game.entities.components.behaviour.gun.Gun;
import com.mygdx.game.entities.fields.FieldName;

import java.util.Optional;

public class DoubleHank extends SimpleOnApplyModifierAugmentInstance {
    public DoubleHank() {
        super(
            "another gun",
            (owner) -> {
                Gun gun = (Gun) owner.getComponentByName(ComponentName.GUN);

                Optional<BulletOrigin> firstOrigin = gun.bulletOrigins.stream().filter((it) -> it.aimOffset == 0f).findFirst();
                firstOrigin.ifPresent(bulletOrigin -> bulletOrigin.aimOffset = -0.15f);

                gun.addBulletOrigin(
                    new BulletOrigin(0.15f, true)
                );
            });

        super.augmentMap.put(FieldName.FireRateMultiplier, 0.25f);

    }
}
