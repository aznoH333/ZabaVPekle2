package com.mygdx.game.entities.components.behaviour.augments.statChangers;

import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.EntityComponent;
import com.mygdx.game.entities.fields.FieldName;

public class ScrapyMachineGun extends EntityComponent {

    public ScrapyMachineGun() {
        super.effectDescription = "scrapy submachine gun";
    }

    @Override
    public void onFirstAttached(Entity owner) {
        owner.addNumericStat(FieldName.FireRateMultiplier, -0.1f);
        owner.addNumericStat(FieldName.FireRate, -2f);
        owner.addNumericStat(FieldName.DamageMultiplier, -0.25f);
        owner.addNumericStat(FieldName.ProjectileSpread, 0.24f);
        owner.addNumericStat(FieldName.ProjectileSpreadMultiplier, 0.05f);
    }

    @Override
    public EntityComponent copy() {
        return new ScrapyMachineGun();
    }
}
