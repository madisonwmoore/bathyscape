package com.madison.Bathyscape.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import box2dLight.PointLight;

public class Projectile {
	SubAbyss game;
	SpriteBatch batch;
	float x,y;
	Sprite projectilesprite;
	Tool launcher;
	boolean killable;
	long birth;
	PointLight laserlight;
	String type;
	Body projectilebody;
public Projectile(SubAbyss game, float x, float y, float angle, String type, Tool launcher) {
	this.game=game;
	this.type=type;
	this.batch=game.level.batch;
	this.launcher=launcher;
	this.x=x;
	this.y=y;
	birth=TimeUtils.millis();
	if(type.equals("Laser")) {
		projectilesprite=new Sprite(game.assman.manager.get("data/laser.png",Texture.class));

	}
	if(type.equals("Potato")) {
		projectilesprite=new Sprite(game.assman.manager.get("data/potato.png",Texture.class));

	}
	
	BodyDef projectilebd = new BodyDef();
	projectilebd.type = BodyType.DynamicBody;

	projectilebd.position.set(x,y);

    PolygonShape projectilebox = new PolygonShape();
    projectilebox.setAsBox((projectilesprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(projectilesprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
    FixtureDef projectilefd = new FixtureDef();
    projectilefd.shape = projectilebox;
	projectilefd.friction=0f;
	projectilefd.density=0.05f;
	if(type.equals("Potato")){
	projectilefd.density=0.03f;}
	projectilefd.restitution = 1f;
	projectilefd.isSensor=false;
	
	projectilebody =game.level.world.createBody(projectilebd);
	projectilebody.setBullet(true);
	if(type.equals("Laser")) {
			projectilebody.setUserData("Laser");
		}
		if(type.equals("Potato")) {
			projectilebody.setUserData("Potato");
		}
	Fixture projectilefix = projectilebody.createFixture(projectilefd);
	
	projectilebox.dispose();

	if(type.equals("Laser")) {
		 laserlight=new PointLight(game.level.rayhandler,5,Color.RED,0.90f,0,0);
		laserlight.setXray(true);
		laserlight.attachToBody(projectilebody);
		
		
	}
	//projectilebody.setFixedRotation(true);
	
	projectilebody.setTransform(x, y, angle);
	if(type.equals("Laser")) {
	projectilebody.setLinearVelocity(10*MathUtils.cos(launcher.getArm2Body().getAngle()),10*MathUtils.sin(launcher.getArm2Body().getAngle()));}
	else {
		projectilebody.setLinearVelocity(8*MathUtils.cos(launcher.getArm2Body().getAngle()),8*MathUtils.sin(launcher.getArm2Body().getAngle()));
	}
}
public void draw() {
	if(TimeUtils.timeSinceMillis(birth)>1500) {
		kill();
	}	
	   projectilesprite.setPosition(projectilebody.getPosition().x*SubAbyss.BOX_WORLD_TO-projectilesprite.getWidth()/2,projectilebody.getPosition().y*SubAbyss.BOX_WORLD_TO-projectilesprite.getHeight()/2);
	   projectilesprite.setRotation(projectilebody.getAngle()*MathUtils.radiansToDegrees);			  
	   projectilesprite.draw(batch);
}
public void kill() {
	if(type.equals("Laser")) {
		laserlight.setActive(false);
		laserlight.remove();
		
	}
	killable=true;
}
public boolean Killable() {

	return killable;
}
}
