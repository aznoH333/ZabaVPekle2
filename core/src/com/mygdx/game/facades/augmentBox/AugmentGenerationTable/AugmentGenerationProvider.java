package com.mygdx.game.facades.augmentBox.AugmentGenerationTable;

import com.mygdx.game.facades.augmentBox.AugmentGenerationSpecifier;

import java.util.ArrayList;


/**
 * Sets up the augment generation specifiers. Used just to put the table definition outside the facade implementation class.
 */
public interface AugmentGenerationProvider {
    void fillWithAugments(ArrayList<AugmentGenerationSpecifier> target);
}
