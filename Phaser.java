package com.madison.Bathyscape.core;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.TimeUtils;

public class Phaser extends Tool {
	SubAbyss game;
	ArrayList<Projectile> projectiles;
	Sprite arm1sprite;
	Sprite phaserarm2sprite;
	long lastlaunch;
	Body armbody,arm2body;
	Joint armjoint,armjoint2;
	SpriteBatch batch;
	Sound lasersound;
	long birth;
public Phaser(SubAbyss game) {
	this.game=game;
	this.batch=game.level.batch;
	Texture arm1=game.assman.manager.get("data/arm1.png",Texture.class);
	Texture phaserarm2=game.assman.manager.get("data/phaserarm2.png",Texture.class);
	
	 arm1sprite=new Sprite(arm1);
	 phaserarm2sprite=new Sprite(phaserarm2);
}

public Phaser(float x, float y, Body subbody, SubAbyss game) {
	this.batch=game.level.batch;
	this.game=game;
	birth=TimeUtils.millis();
	Texture arm1=game.assman.manager.get("data/arm1.png",Texture.class);
	Texture phaserarm2=game.assman.manager.get("data/phaserarm2.png",Texture.class);
	lasersound=game.assman.manager.get("data/laser.mp3",Sound.class);
	 arm1sprite=new Sprite(arm1);
	 phaserarm2sprite=new Sprite(phaserarm2);
	 
	 BodyDef arm2bd = new BodyDef();
		arm2bd.type = BodyType.DynamicBody;
	    arm2bd.position.set(0*SubAbyss.WORLD_TO_BOX,75);
	    PolygonShape arm2box = new PolygonShape();
	    arm2box.setAsBox((phaserarm2sprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(phaserarm2sprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
	    FixtureDef arm2fd = new FixtureDef();
	    arm2fd.shape = arm2box;
		arm2fd.friction=0f;
		arm2fd.density=1f;
		arm2fd.restitution = 1f;
		arm2fd.isSensor=true;
		arm2body =game.level.world.createBody(arm2bd);
		Fixture arm2fix = arm2body.createFixture(arm2fd);
		arm2body.setUserData(phaserarm2sprite);
		arm2box.dispose();
		//armsound=game.level.armsound;
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
		
		
		RevoluteJointDef subarmswing = new RevoluteJointDef();
		subarmswing.collideConnected=false;
		subarmswing.bodyA=subbody;
		subarmswing.localAnchorA.x=x;
		subarmswing.localAnchorA.y=y;
		subarmswing.bodyB=armbody;
		subarmswing.localAnchorB.x=-20f*SubAbyss.WORLD_TO_BOX;
		
		subarmswing.enableLimit=true;
		subarmswing.lowerAngle=-10f*SubAbyss.WORLD_TO_BOX;
		subarmswing.upperAngle=10*SubAbyss.WORLD_TO_BOX;
		if(game.level.world!=null){
		 armjoint2=game.level.world.createJoint(subarmswing);
		}
		
		RevoluteJointDef  armarm2swing = new RevoluteJointDef();
		armarm2swing.collideConnected=false;
		armarm2swing.bodyA=armbody;
		armarm2swing.localAnchorA.x=40f*SubAbyss.WORLD_TO_BOX;
		armarm2swing.localAnchorA.y=10f*SubAbyss.WORLD_TO_BOX;
		armarm2swing.bodyB=arm2body;
		armarm2swing.localAnchorB.x=-20f*SubAbyss.WORLD_TO_BOX;

		armarm2swing.enableLimit=true;
		armarm2swing.enableMotor=true;
		armarm2swing.maxMotorTorque=.001f;
		armarm2swing.lowerAngle=-05f*SubAbyss.WORLD_TO_BOX;
		armarm2swing.upperAngle=10*SubAbyss.WORLD_TO_BOX;
		if(game.level.world!=null){
		 armjoint=game.level.world.createJoint(armarm2swing);}
		
		projectiles=new ArrayList<Projectile>();
	 
}
public void draw() {

	
	arm1sprite.setPosition(armbody.getPosition().x*SubAbyss.BOX_WORLD_TO-arm1sprite.getWidth()/2,armbody.getPosition().y*SubAbyss.BOX_WORLD_TO-arm1sprite.getHeight()/2);
	   arm1sprite.setRotation(armbody.getAngle()*MathUtils.radiansToDegrees);			  
	   arm1sprite.draw(batch);
	
	
	
	phaserarm2sprite.setPosition(arm2body.getPosition().x*SubAbyss.BOX_WORLD_TO-phaserarm2sprite.getWidth()/2,arm2body.getPosition().y*SubAbyss.BOX_WORLD_TO-phaserarm2sprite.getHeight()/2);
	   phaserarm2sprite.setRotation(arm2body.getAngle()*MathUtils.radiansToDegrees);			  
	   phaserarm2sprite.draw(batch);
	   
	   if(Gdx.input.isKeyPressed(Keys.SPACE)&&TimeUtils.timeSinceMillis(lastlaunch)>100f) {
		  lastlaunch=TimeUtils.millis();
		   projectiles.add(launch());
	   }
	   
	   for(int i =0;i<projectiles.size();i++) {
		   if(projectiles.get(i)!=null) {
		   projectiles.get(i).draw();}
	   }
	   
	   
}
public Body getArm1Body() {
	return armbody;}
public Body getArm2Body() {
	return arm2body;
}
public Sprite getArm2Sprite() {
	return phaserarm2sprite;
}
public Sprite getArm1Sprite() {
	return arm1sprite;
}
public String getToolName() {
	return "Laser Arm"; 
}
public String getType() {
	return "Laser Arm";
}
public Projectile launch() {
	if(game.level.soundonoff) {
	lasersound.play(0.75f);}
	return new Projectile(game, MathUtils.cos(arm2body.getAngle())+arm2body.getPosition().x-(phaserarm2sprite.getWidth()/3.5f)*SubAbyss.WORLD_TO_BOX, MathUtils.sin(arm2body.getAngle())+arm2body.getPosition().y-3f*game.WORLD_TO_BOX,arm2body.getAngle(), "Laser", this);
}
public boolean launch(boolean t) {
	if(TimeUtils.timeSinceMillis(lastlaunch)>100f) {
	  lastlaunch=TimeUtils.millis();
	   projectiles.add(launch());
	   return true;}
	return false;
}
public void clearProjectiles() {
	for(int i =0;i<projectiles.size();i++) {
		if(projectiles.get(i).Killable()) {
			game.level.world.destroyBody(projectiles.get(i).projectilebody);
			projectiles.remove(i);
		}
	}
	
	
}
public boolean isLocked() {

	return game.keeper.isPhaserToolLocked();
}
public int getUnlockCost() {
	return 20000;
}


}
