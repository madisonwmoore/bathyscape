package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.physics.box2d.Fixture;

public class EvilSub {
	Batch batch;
	World world;
	float statetime;
	OrthographicCamera camera;
	Sprite subsprite,propsprite;
	AssetManager assman;
	final SubAbyss game;
	Body evilbody;
	float depth;
	Torpedo torpedo;
	long lastlaunch;
	int missilecount=3;
	Fixture subfix;
	int hitpoints=50;
	Sprite bubblesprite;
	boolean armed=true;
	boolean kill=false;
	public EvilSub(Batch batch, World world,float statetime, OrthographicCamera camera, AssetManager assman, final SubAbyss game, float x, float y){
		this.batch=batch;
		this.world=world;
		this.statetime=statetime;
		this.camera=camera;
		this.assman=assman;
		this.game=game;
		
		lastlaunch=TimeUtils.millis();
		subsprite=new Sprite(game.assman.manager.get("data/evilsub.png",Texture.class));
		bubblesprite=new Sprite(game.assman.manager.get("data/bubble.png",Texture.class));
		bubblesprite.setScale(0.8f);
		propsprite=new Sprite(game.assman.manager.get("data/evilprop.png",Texture.class));
		
		BodyDef subbd = new BodyDef();
		subbd.type = BodyType.DynamicBody;
		subbd.position.set(x,y);
	 //  PolygonShape subbox = new PolygonShape();
	    CircleShape subbox = new CircleShape();
	    subbox.setRadius(subsprite.getWidth()/3*SubAbyss.WORLD_TO_BOX);
	  //  subbox.setAsBox((subsprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(subsprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
	    FixtureDef subfd = new FixtureDef();
	    subfd.shape = subbox;
		subfd.friction=4f;
		subfd.density=0.5f;
		subfd.restitution = 0f;
		

		evilbody =world.createBody(subbd);

		subfix = evilbody.createFixture(subfd);
		evilbody.setUserData("evilsub");
		subbox.dispose();
		evilbody.setFixedRotation(true);
		evilbody.setAngularDamping(3f);
		
		evilbody.setGravityScale(0f);
		evilbody.setLinearVelocity(-3f, 0f);
		
		//torpedo=new Torpedo(batch, world, statetime, assman, game, x, y);
		game.level.torpedos.add(new Torpedo(batch, world, statetime, assman, game, x, y));
		torpedo=game.level.torpedos.first();
		
	}
	public void draw() {
		subsprite.setPosition(evilbody.getPosition().x*SubAbyss.BOX_WORLD_TO-subsprite.getWidth()/2f,evilbody.getPosition().y*SubAbyss.BOX_WORLD_TO-subsprite.getHeight()/2f);
		   subsprite.setRotation(evilbody.getAngle()*MathUtils.radiansToDegrees);	
		   propsprite.setPosition((evilbody.getPosition().x*SubAbyss.BOX_WORLD_TO+80f), evilbody.getPosition().y*SubAbyss.BOX_WORLD_TO+14f);
		   
		   
		   if(hitpoints<=0) {
			   game.level.sub.addScore(150);
			   kill=true;
			   
		   }
		   
		 if(!torpedo.islaunched) {
		   torpedo.setPosition(evilbody.getPosition().x, evilbody.getPosition().y, evilbody.getAngle());}
		   
		   subsprite.draw(batch);
		   propsprite.draw(batch);
		 if(torpedo!=null) {
		   torpedo.draw();}
		   
		   if(Gdx.input.isKeyPressed(Keys.Y)) {reloadTorpedo();}
		   
			if(evilbody.getLinearVelocity().x>0) {
				evilbody.applyForceToCenter(new Vector2(-0.2f*evilbody.getLinearVelocity().x*evilbody.getLinearVelocity().x,0), false);
				
			}
			if(evilbody.getLinearVelocity().x<0) {
				evilbody.applyForceToCenter(new Vector2(0.2f*evilbody.getLinearVelocity().x*evilbody.getLinearVelocity().x,0), false);
			}
			if(evilbody.getLinearVelocity().y<0) {
				evilbody.applyForceToCenter(new Vector2(0,0.2f*evilbody.getLinearVelocity().y*evilbody.getLinearVelocity().y), false);
				 bubblesprite.setScale(-MathUtils.random(0.3f,0.9f));
			game.level.bubbles.add(new Bubble(new Sprite(bubblesprite), game, 5, game.level.world, batch, subsprite.getX()+subsprite.getWidth()/2+45f, subsprite.getY()+subsprite.getHeight()/2+35f, 5f,true));
			}
			if(evilbody.getLinearVelocity().y>0) {
				evilbody.applyForceToCenter(new Vector2(0,-0.2f*evilbody.getLinearVelocity().y*evilbody.getLinearVelocity().y), false);
				bubblesprite.setScale(-MathUtils.random(0.3f,0.9f));
				
				game.level.bubbles.add(new Bubble(new Sprite(bubblesprite), game, 5, game.level.world, batch, subsprite.getX()+subsprite.getWidth()/2+45f, subsprite.getY()+subsprite.getHeight()/2+25f, 5f,true));
			}
			   if(depth<=0) {
				   evilbody.setGravityScale(10f); 
				//   evilbody.setLinearVelocity(evilbody.getLinearVelocity().x, 0);
			   }
			   else {
				   evilbody.setGravityScale(0f); 
				   }
//				if(depth<1f) {
//					if(evilbody.getLinearVelocity().y<0) {
//						evilbody.applyForceToCenter(new Vector2(0,20.2f*evilbody.getLinearVelocity().y*evilbody.getLinearVelocity().y), false);
//					}
			 depth=(evilbody.getPosition().y*-2)+150;
		   System.out.println("Depth "+depth);
		   if(evilbody.getPosition().x<=camera.position.x+(camera.viewportWidth/4)*game.WORLD_TO_BOX) {
			   if(armed&&TimeUtils.timeSinceMillis(lastlaunch)>9000f||((evilbody.getPosition().y-game.level.sub.getBody().getPosition().y<1.0f&&evilbody.getPosition().y-game.level.sub.getBody().getPosition().y>-1f)&&evilbody.getLinearVelocity().y<2.0f&&TimeUtils.timeSinceMillis(lastlaunch)>4000f)) { 
				   			lastlaunch=TimeUtils.millis();
		                   torpedo.launch();
		                   missilecount--;
		                   armed=false;
		                   if(missilecount>0) {
		                   reloadTorpedo();
		                	   }
			   
			   }
		if((evilbody.getPosition().y-game.level.sub.getBody().getPosition().y)<=0.5f&&(evilbody.getPosition().y-game.level.sub.getBody().getPosition().y)>=-0.5f) {
			
			
			if(evilbody.getLinearVelocity().y<0) {
				evilbody.applyForceToCenter(new Vector2(0,2.6f*evilbody.getLinearVelocity().y*evilbody.getLinearVelocity().y), false);
			}
			if(evilbody.getLinearVelocity().y>0) {
				evilbody.applyForceToCenter(new Vector2(0,-2.6f*evilbody.getLinearVelocity().y*evilbody.getLinearVelocity().y), false);
			}
			
			
		}	   
		if(missilecount!=0) {
			   
			   if(game.level.sub.getBody().getPosition().y<evilbody.getPosition().y) {
				   rotatepropdown();			
				   evilbody.setLinearVelocity(0, evilbody.getLinearVelocity().y);
			   if(evilbody.getLinearVelocity().y>-5f) {
			   evilbody.applyForceToCenter(0, -5.5f, false);}
			   else {
				  // evilbody.setLinearVelocity(evilbody.getLinearVelocity().x, -5);
			   }
			   }
			   else {
				   rotatepropup();
				   evilbody.setLinearVelocity(0, evilbody.getLinearVelocity().y);
				   if(evilbody.getLinearVelocity().y<5f) {
				   evilbody.applyForceToCenter(0, 5.5f, false);}
				   else {
					//   evilbody.setLinearVelocity(evilbody.getLinearVelocity().x, 5);
				   }
				   
			   }
		   }
		   else {evilbody.setLinearVelocity(-3f, 0f);}}
		   else {
			   evilbody.setLinearVelocity(-3f, -1f);
			   
		   }
//		   if(evilbody.getLinearVelocity().y>0) {
//			 
//			 rotatepropup();
//			   }
//		   
//		   if(evilbody.getLinearVelocity().y<0){
//			  rotatepropdown();
//		   }
//		
		   if(missilecount<=0) {
			  
			 
			   
			
			   Timer.schedule(new Task(){
		             @Override
		             public void run() {
		            	kill=true;
		            
		             }
		         }
		         , 10f        //    (delay)
		             //    (seconds)
		     );
		   }}
		   
		   
		   
		   
		   
		   
		   
	
	public Torpedo getTorpedo() {
		return torpedo;
	}
	private void rotatepropup() {
		 if(propsprite.getRotation()>-45f){
			   propsprite.rotate(-4f);}
	}
	private void rotatepropdown() {
		if(propsprite.getRotation()<30f){
			   propsprite.rotate(4f);}
		 
	}
	private void reloadTorpedo() {
		
		 Timer.schedule(new Task(){
             @Override
             public void run() {
            
            		torpedo=new Torpedo(batch, world, statetime, assman, game, evilbody.getPosition().x,evilbody.getPosition().y);
            		game.level.torpedos.add(torpedo);
            	
            		armed=true;
            
             }
         }
         , 3f        //    (delay)
             //    (seconds)
     );
		
		
	
	}
	public void dispose() {
		
		
	//	world.destroyBody(evilbody);
		
	}
	public boolean getKill() {
		return kill;
	}
	
	public void hit(int hit) {
		
		
		hitpoints=hitpoints-hit;
		
		
	}
	
}
