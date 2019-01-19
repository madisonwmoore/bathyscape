package com.madison.Bathyscape.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Award {
private	boolean islocked=true;
private	SubAbyss game;
private	int awardnumber;
private	TextureRegion awardframe;
private SpriteDrawable drawable;
public Award(SubAbyss game, TextureRegion awardframe, int awardnumber) {
	this.game=game;
	islocked=game.keeper.isAwardLocked(awardnumber);
	this.awardnumber=awardnumber;
	this.awardframe=awardframe;
	drawable=new SpriteDrawable(new Sprite(awardframe));
}
public boolean isLocked() {
	return islocked;
}

public void setLocked(boolean locked) {
	islocked=locked;
	game.keeper.setAwardLocked(awardnumber, locked);
}
public TextureRegion getTexture() {
	
			return awardframe;
}
public SpriteDrawable getDrawable() {
	
	return drawable;
}
}
