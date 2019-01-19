package com.madison.Bathyscape.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class Bubble {
	Body particle;
	Batch batch;
	 boolean kill;
	//private Array<Body> bodies = new Array<Body>();
	World world;
//	PolygonShape particlebox;
//	private boolean die;
	boolean shoulddie=false;
	Sprite sprite;
	boolean reversed;
	float depth;
	Vector2 directionalvector;
	float speed;
	SubAbyss game;
public Bubble(Sprite sprite,final SubAbyss game, float lifetime,final World world, Batch batch, float posx, float posy,float speed, boolean reversed){
	this.sprite=sprite;
	this.world=world;
	this.reversed=reversed;
	this.batch=batch;
	this.speed=speed;
	this.game=game;
	sprite.setOriginCenter();
	
	
   Timer.schedule(new Timer.Task() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		shoulddie=true;
		dispose();
		
		}
		@Override
		public void cancel() {
			// TODO Auto-generated method stub
		}
		}, lifetime);
 
   sprite.setPosition(posx+MathUtils.random(0.5f,0.9f)*SubAbyss.BOX_WORLD_TO-sprite.getWidth()/2, posy*SubAbyss.WORLD_TO_BOX*SubAbyss.BOX_WORLD_TO-sprite.getHeight()/2);
  if(reversed) {
	  directionalvector=new Vector2(MathUtils.random(0.1f,0.5f),MathUtils.random(0.5f,-1f));
  }else {
   directionalvector=new Vector2(MathUtils.random(-0.1f,-0.5f),MathUtils.random(0.5f,-1f));}
}
public boolean getShouldDie(){
	return this.shoulddie;
}
public void dispose(){

	game.level.bubbles.removeValue(this, false);
}
public void draw(){
	sprite.translate(directionalvector.x*MathUtils.random(0.75f*speed,1.2f*speed), directionalvector.y*MathUtils.random(1f,1.2f));
	
	sprite.draw(batch);
	if(depth<=0) {
		
	}
	else {
		//particle.setGravityScale(0f);
	}
//	depth=(particle.getPosition().y*-2)+150;
}


}
