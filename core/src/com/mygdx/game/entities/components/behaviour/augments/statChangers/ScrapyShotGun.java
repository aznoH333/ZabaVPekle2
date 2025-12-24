package com.mygdx.game.entities.components.behaviour.augments.statChangers;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

public class ScrapyShotGun extends EntityComponent {
    public ScrapyShotGun() {
        super.effectDescription = "scrapy shot gun";
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(FieldName.FireRateMultiplier, 0.75f);
        owner.addNumericStat(FieldName.FireRate, 30f);
        owner.addNumericStat(FieldName.DamageMultiplier, -0.25f);
        owner.addNumericStat(FieldName.Damage, -0.25f);
        owner.addNumericStat(FieldName.ProjectilesPerShot, 3f);
        owner.addNumericStat(FieldName.ProjectileLifeTime, -60f);
        owner.addNumericStat(FieldName.ProjectileSpread, 0.24f);
        owner.addNumericStat(FieldName.ProjectileSpreadMultiplier, 0.05f);
    }

    @Override
    public EntityComponent copy() {
        return new ScrapyShotGun();
    }

}
