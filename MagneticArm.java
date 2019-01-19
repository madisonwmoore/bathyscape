package com.madison.Bathyscape.core;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.physics.box2d.Fixture;

public class MagneticArm extends Tool {
String type = "Magnetic Arm";
Body armbody,arm2body;
String description="Used to attract and catch marine life";
SpriteBatch batch;
int cost= 30000;
Sprite arm1sprite,magarm2sprite;
SubAbyss game;
Joint armjoint,armjoint2;
boolean armrunning;
RevoluteJointDef armarm2swing,subarmswing;
boolean magnetizing;	
Sound armsound;
boolean isarmplaying;
	public MagneticArm(SubAbyss game) {
		this.game=game;
		Texture arm1=game.assman.manager.get("data/arm1.png",Texture.class);
		Texture magarm2=game.assman.manager.get("data/magarm2.png",Texture.class);
		 arm1sprite=new Sprite(arm1);
		 magarm2sprite=new Sprite(magarm2);
	} 
	
	public MagneticArm(float x, float y, Body subbody, SubAbyss game) {
		this.batch=game.level.batch;
		this.game=game;
		Texture arm1=game.assman.manager.get("data/arm1.png",Texture.class);
		Texture magarm2=game.assman.manager.get("data/magarm2.png",Texture.class);
		 arm1sprite=new Sprite(arm1);
		 magarm2sprite=new Sprite(magarm2);
		
		BodyDef arm2bd = new BodyDef();
		arm2bd.type = BodyType.DynamicBody;
	    arm2bd.position.set(0*SubAbyss.WORLD_TO_BOX,75);
	    PolygonShape arm2box = new PolygonShape();
	    arm2box.setAsBox((magarm2sprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(magarm2sprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
	    FixtureDef arm2fd = new FixtureDef();
	    arm2fd.shape = arm2box;
		arm2fd.friction=0f;
		arm2fd.density=1f;
		arm2fd.restitution = 1f;
		arm2fd.isSensor=true;
		arm2body =game.level.world.createBody(arm2bd);
		Fixture arm2fix = arm2body.createFixture(arm2fd);
		arm2body.setUserData(magarm2sprite);
		arm2box.dispose();
		armsound=game.level.armsound;
		BodyDef arm1bd = new BodyDef();
		arm1bd.type = BodyType.DynamicBody;
	    arm1bd.position.set(0*SubAbyss.WORLD_TO_BOX,75);
	    PolygonShape armbox = new PolygonShape();
	    armbox.setAsBox((arm1sprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(arm1sprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
	    FixtureDef armfd = new FixtureDef();
	    armfd.shape = armbox;
		armfd.friction=0f;
		armfd.density=1f;
		armfd.restitution = 1f;
		armfd.isSensor=true;
		armbody =game.level.world.createBody(arm1bd);
		Fixture armfix = armbody.createFixture(armfd);
		armbody.setUserData(arm1sprite);
		armbox.dispose();
		
		armbody.setGravityScale(0f);
		arm2body.setGravityScale(0f);
		
		
		subarmswing = new RevoluteJointDef();
		subarmswing.collideConnected=false;
		subarmswing.bodyA=subbody;
		subarmswing.localAnchorA.x=x;
		subarmswing.localAnchorA.y=y;
		subarmswing.bodyB=armbody;
		subarmswing.localAnchorB.x=-20f*SubAbyss.WORLD_TO_BOX;
		
		subarmswing.enableLimit=true;
		subarmswing.lowerAngle=-20f*SubAbyss.WORLD_TO_BOX;
		subarmswing.upperAngle=20*SubAbyss.WORLD_TO_BOX;
		if(game.level.world!=null){
		 armjoint2=game.level.world.createJoint(subarmswing);
		}
		
		armarm2swing = new RevoluteJointDef();
		armarm2swing.collideConnected=false;
		armarm2swing.bodyA=armbody;
		armarm2swing.localAnchorA.x=20f*SubAbyss.WORLD_TO_BOX;
		armarm2swing.bodyB=arm2body;
		armarm2swing.localAnchorB.x=-20f*SubAbyss.WORLD_TO_BOX;

		armarm2swing.enableLimit=true;
		armarm2swing.enableMotor=true;
		armarm2swing.maxMotorTorque=.001f;
		armarm2swing.lowerAngle=-20f*SubAbyss.WORLD_TO_BOX;
		armarm2swing.upperAngle=30*SubAbyss.WORLD_TO_BOX;
		if(game.level.world!=null){
		 armjoint=game.level.world.createJoint(armarm2swing);}
		
		
	}
	
	public Body getArm1Body() {
		return armbody;}
	public Body getArm2Body() {
		return arm2body;
	}	
	public void draw() {
		   magarm2sprite.setPosition(arm2body.getPosition().x*SubAbyss.BOX_WORLD_TO-magarm2sprite.getWidth()/2,arm2body.getPosition().y*SubAbyss.BOX_WORLD_TO-magarm2sprite.getHeight()/2);
		   magarm2sprite.setRotation(arm2body.getAngle()*MathUtils.radiansToDegrees);			  
		   magarm2sprite.draw(batch);
		   
		   arm1sprite.setPosition(armbody.getPosition().x*SubAbyss.BOX_WORLD_TO-arm1sprite.getWidth()/2,armbody.getPosition().y*SubAbyss.BOX_WORLD_TO-arm1sprite.getHeight()/2);
		   arm1sprite.setRotation(armbody.getAngle()*MathUtils.radiansToDegrees);			  
		   arm1sprite.draw(batch);

	if(!magnetizing&&!armrunning) {
	for(Fish f: game.level.fishes) {
		if((f.getBody().getPosition().dst2(arm2body.getPosition())<4.5f)&&!magnetizing&&!armrunning&!f.getFishType().equals("marlin")) {
			magnetizing=true;
			f.magnetize();
			
		}
		}
	//}
	
		for(Sardine s: game.level.sardines) {
			if((s.getBody().getPosition().dst2(arm2body.getPosition())<4.5f)&&!magnetizing) {
				magnetizing=true;
				s.magnetize();
			}
		}
		for(int i=0;i<game.level.mines.size;i++) {
			if ((game.level.mines.get(i).minebody.getPosition().dst2(arm2body.getPosition())<4.5f)&&!magnetizing){
				game.level.mines.get(i).magnetize();
			}
		}
		}
		

	
	}
	public void activateArm(final Fish tf){
		
		if(!armrunning) {
			if(game.level.soundonoff) {
				long id=armsound.play();
				armsound.setVolume(id, 0.35f);}	
					
		armrunning=true;	
		magnetizing=false;
		game.level.world.destroyJoint(armjoint);
		armarm2swing.upperAngle=230f*game.WORLD_TO_BOX;
		armarm2swing.maxMotorTorque=6f;
		armarm2swing.motorSpeed=2f;
		armjoint2=game.level.world.createJoint(armarm2swing);
		
		 Timer.schedule(new Timer.Task() {
  			
  			@Override
  			public void run() {

  				tf.Kill();	  
  				
  			}
  			@Override
  			public void cancel() {
  				
  			}
  			}, 1.0f);
		 Timer.schedule(new Timer.Task() {
	  			
	  			@Override
	  			public void run() {
	  				game.level.world.destroyJoint(armjoint2);
	  				armarm2swing.upperAngle=30f*game.WORLD_TO_BOX;
	  				armarm2swing.maxMotorTorque=.001f;
	  				armarm2swing.motorSpeed=0;
	  				armjoint=game.level.world.createJoint(armarm2swing);	  				
	  				armrunning=false;	
	  				
	  			
	  			}
	  			@Override
	  			public void cancel() {
	  				
	  			}
	  			}, 1f);					
		}
	
	
		
		}
	public void activateArm(final Sardine ts){

		if(!armrunning) {
			if(game.level.soundonoff) {
				long id=armsound.play();
				armsound.setVolume(id, 0.35f);}			
					
		armrunning=true;	
		
		game.level.world.destroyJoint(armjoint);
		armarm2swing.upperAngle=230f*game.WORLD_TO_BOX;
		armarm2swing.maxMotorTorque=6f;
		armarm2swing.motorSpeed=2f;
		armjoint2=game.level.world.createJoint(armarm2swing);
		magnetizing=false;
		 Timer.schedule(new Timer.Task() {
  			
  			@Override
  			public void run() {

  				ts.Kill();	  
  				magnetizing=false;
  			}
  			@Override
  			public void cancel() {
  				
  			}
  			}, 1.0f);
		 Timer.schedule(new Timer.Task() {
	  			
	  			@Override
	  			public void run() {
	  				game.level.world.destroyJoint(armjoint2);
	  				armarm2swing.upperAngle=30f*game.WORLD_TO_BOX;
	  				armarm2swing.maxMotorTorque=.001f;
	  				armarm2swing.motorSpeed=0;
	  				armjoint=game.level.world.createJoint(armarm2swing);	  				
	  				armrunning=false;	
	  				magnetizing=false;
	  			
	  			}
	  			@Override
	  			public void cancel() {
	  				
	  			}
	  			}, 1f);					
		}
	
	
		
		}
	public void activateArm(final Squid sq) {
		if(!armrunning) {
			if(game.level.soundonoff) {
				long id=armsound.play();
				armsound.setVolume(id, 0.35f);}		
			armrunning=true;
			
			game.level.world.destroyJoint(armjoint);
			armarm2swing.upperAngle=230f*game.WORLD_TO_BOX;
			armarm2swing.maxMotorTorque=6f;
			armarm2swing.motorSpeed=2f;
			armjoint2=game.level.world.createJoint(armarm2swing);
			
			 Timer.schedule(new Timer.Task() {
	  			
	  			@Override
	  			public void run() {

	  				sq.Kill();	  				  			
	  			}
	  			@Override
	  			public void cancel() {
	  				
	  			}
	  			}, 1.0f);
			 Timer.schedule(new Timer.Task() {
		  			
		  			@Override
		  			public void run() {
		  				game.level.world.destroyJoint(armjoint2);
		  				armarm2swing.upperAngle=30f*game.WORLD_TO_BOX;
		  				armarm2swing.maxMotorTorque=.001f;
		  				armarm2swing.motorSpeed=0;
		  				armjoint=game.level.world.createJoint(armarm2swing);	  				
		  				armrunning=false;	  				  				
		  			
		  			}
		  			@Override
		  			public void cancel() {
		  				
		  			}
		  			}, 1f);					
			
		}
	}
	
	public Sprite getArm2Sprite() {
		return magarm2sprite;
	}
	public Sprite getArm1Sprite() {
		return arm1sprite;
	}
	public void setEquipmentColor(Color color) {
		arm1sprite.setColor(color);
		magarm2sprite.setColor(color);
	}
	public String getToolName() {
		return type;
		
	}
	public String getToolDescription() {
		return description;
		
	}
	public boolean isArmRunning() {
		return armrunning;
	}
	public boolean isMagnetizing() {
		return magnetizing;
	}
	public void setMagnetizing(boolean t) {
	 magnetizing=t;
	}
	public String getType() {
		return type;
	}
	public boolean isLocked() {

		return game.keeper.isMagneticToolLocked();
	}
	public int getUnlockCost() {
		return 15000;
	}

}
