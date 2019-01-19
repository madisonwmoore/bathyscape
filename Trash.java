package com.madison.Bathyscape.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Trash extends Fish {
	Body trashbody;
	Sprite trashsprite;
	World world;
	Batch batch;
	Sprite bubblesprite;
	SubAbyss game;
	boolean killable=false;
	boolean caught;
	int hitpoints=10;
public Trash(SubAbyss game, float x, float y) {
	this.game=game;
	world=game.level.world;
	batch=game.level.batch;
	bubblesprite=new Sprite(game.assman.manager.get("data/bubble.png", Texture.class));
	bubblesprite.setColor(Color.BROWN);
	trashsprite=new Sprite(game.assman.manager.get("data/drum.png",Texture.class));
	
	BodyDef subbd = new BodyDef();
	subbd.type = BodyType.DynamicBody;
	  subbd.position.set(x,y);
    PolygonShape trashbox = new PolygonShape();
  
    trashbox.setAsBox((trashsprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(trashsprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
    FixtureDef subfd = new FixtureDef();
    subfd.shape = trashbox;
	subfd.friction=3f;
	subfd.density=1.6f;
	subfd.restitution = 0f;

	this.trashbody =world.createBody(subbd);

	trashbody.createFixture(subfd);
	trashbody.setUserData(trashsprite);
	trashbox.dispose();
//	trashbody.setFixedRotation(true);
	trashbody.setGravityScale(0);
	trashbody.setLinearVelocity(-1f, trashbody.getLinearVelocity().y);
	trashbody.setAngularVelocity(-0.5f);
	trashbody.setAngularDamping(0.25f);
}
public void draw() {
	if(!game.level.paused) {
	 bubblesprite.setScale(-MathUtils.random(0.2f,1f));
	 Level1.bubbles.add(new Bubble(new Sprite(bubblesprite),game, 1f,world,batch,((trashbody.getPosition().x*SubAbyss.BOX_WORLD_TO)),((trashbody.getPosition().y*SubAbyss.BOX_WORLD_TO)),5f, false));
	//trashbody.setLinearVelocity(-1f, trashbody.getLinearVelocity().y);
	}
	trashsprite.setPosition(trashbody.getPosition().x*SubAbyss.BOX_WORLD_TO-trashsprite.getWidth()/2f,trashbody.getPosition().y*SubAbyss.BOX_WORLD_TO-trashsprite.getHeight()/2f);
	   trashsprite.setRotation(trashbody.getAngle()*MathUtils.radiansToDegrees);	
	  trashsprite.draw(batch);	  
	  
		if(caught){
			if(game.level.sub.getArm().getType().equals("Chopstick Arm")) {
			trashbody.setTransform(game.level.sub.getArm2Body().getPosition().x+1.1f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+1.1f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
			}
			else {
			trashbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
			}
			trashsprite.setColor(Color.RED);
			trashbody.setUserData(trashsprite);
			
		}
	  
		   if(depth<=0) {
			   trashbody.setGravityScale(10f); 
			
		   }
		   else {
			   trashbody.setGravityScale(0f); 
			   }

		 depth=(trashbody.getPosition().y*-2)+150;       
	  
	  
}
public String getFishType(){
	return "trash drum";
}
public boolean getKill() {
	return killable;
}
public Body getBody() {
	return trashbody;
}
public void setCaught(boolean caught) {
	this.caught=caught;
}
public void Kill(){
	killable=true;

	
}
public boolean getCaught() {
	return caught;
}
public void hit(int hit) {
	Timer.schedule(new Task(){
        @Override
        public void run() {
           trashsprite.setColor(Color.RED);
    
            Timer.schedule(new Task(){
                @Override
                public void run() {
                   trashsprite.setColor(Color.WHITE);
        
                }
            }
            , .2f        //    (delay)
            , .4f,2    //    (seconds)
        );
        }
    }
    , 0f        //    (delay)
    , .4f,2    //    (seconds)
);
	
	hitpoints=hitpoints-hit;
	if(hitpoints<=0) {
		dying=true;
		trashbody.setAngularVelocity(-5f);
		 Timer.schedule(new Task(){
             @Override
             public void run() {
              Kill();
     
             }
         }
         ,1    //    (seconds)
     );
	}
	
}
}
