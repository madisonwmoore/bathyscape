package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.utils.TimeUtils;

public class TrapManager {
long lastAnchor=0;
Level1 game;
public TrapManager(Level1 game) {
	
	  this.game=game;
	
}
public boolean Mine() {
return false;
}

public boolean Anchor() {
	if((TimeUtils.millis()>(lastAnchor+10000)||Gdx.input.isKeyPressed(Keys.A))&&(game.sub.getDepth()<170)){
		lastAnchor=TimeUtils.millis();
			return true;
	}
	else{ return false;
	}
	
}

}
