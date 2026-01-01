package com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationTable;

import com.mygdx.game.entities.facades.AugmentBox.AugmentGenerationSpecifier;

import java.util.ArrayList;


/**
 * Sets up the augment generation specifiers. Used just to put the table definition outside the facade implementation class.
 */
public interface AugmentGenerationProvider {
    void fillWithAugments(ArrayList<AugmentGenerationSpecifier> target);
}
