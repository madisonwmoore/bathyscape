package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class Sardine {
	Texture sardine;
	Sprite sardinesprite;
	Sardine master;
	SpriteBatch batch;
	World world;
	boolean isMasterSardine;
	boolean magnetize=false;
	boolean projectilehit=false;
	float depth;
	float statetime,x,y;
	boolean controlfish;
	Sprite currentframe;
	int FRAME_COLS, FRAME_ROWS;
	TextureRegion [] swimframe;
	Animation<TextureRegion> swimanimation;
	Body fishbody;
	boolean killable=false;
	//private Array<Body> bodies = new Array<Body>();
	boolean scatter=false;
	boolean caught=false;;
	SubAbyss game;
	
public Sardine(SpriteBatch batch, World world, float statetime,float x, float y,boolean controlfish, Texture sardine,SubAbyss game){
	this.batch=batch;
	this.statetime=statetime;
	this.game=game;
	this.world=world;
	this.sardine=sardine;
	this.x=x;
	this.y=y;
	isMasterSardine=true;
	FRAME_COLS=1;
	FRAME_ROWS=2;
	 sardinesprite=new Sprite(sardine);
	TextureRegion[][] tmp = TextureRegion.split(sardine, sardine.getWidth() / 
			FRAME_COLS, sardine.getHeight() / FRAME_ROWS);                                
			        swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			        int index1 = 0;
			        for (int i = 0; i < FRAME_ROWS; i++) {
			                for (int j = 0; j < FRAME_COLS; j++) {
			                        swimframe[index1++] = tmp[i][j];
			                }
			        }
			     
			        swimanimation = new Animation<TextureRegion>(0.07f, swimframe);
			      
			        TextureRegion text=swimanimation.getKeyFrame(statetime, true);
			        currentframe = new Sprite(text);   
	
			        BodyDef fishbd=new BodyDef();
			        fishbd.type=BodyType.KinematicBody;
			        fishbd.position.set(x,y);
			        PolygonShape fishbox = new PolygonShape();
			    
				    fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/4,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/4);
				       
			        FixtureDef fishfd = new FixtureDef();
			        fishfd.shape = fishbox;
					fishfd.friction=3f;
					fishfd.density=1f;
					fishfd.restitution = 0f;
					fishfd.isSensor=true;
					this.fishbody =world.createBody(fishbd);
					Fixture fishfix = fishbody.createFixture(fishfd);
					fishbody.setUserData(currentframe);
					fishbox.dispose();
			        fishbody.setLinearVelocity(-3f, 0f);
					
			    	statetime += Gdx.graphics.getDeltaTime(); 
			    	
			    	
}

public Sardine(Sardine master){
	this.master=master;
	this.batch=master.batch;
	this.world=master.world;
	this.game=master.game;
	this.sardine=master.sardine;
	this.statetime=master.statetime;
	 sardinesprite=new Sprite(sardine);
	isMasterSardine=false;
	FRAME_COLS=1;
	FRAME_ROWS=2;
	
	TextureRegion[][] tmp = TextureRegion.split(sardine, sardine.getWidth() / 
			FRAME_COLS, sardine.getHeight() / FRAME_ROWS);                                
			        swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			        int index1 = 0;
			        for (int i = 0; i < FRAME_ROWS; i++) {
			                for (int j = 0; j < FRAME_COLS; j++) {
			                        swimframe[index1++] = tmp[i][j];
			                }
			        }
			     
			        swimanimation = new Animation<TextureRegion>(0.7f, swimframe);
			      
			        TextureRegion text=swimanimation.getKeyFrame(statetime, true);
			        currentframe = new Sprite(text);   
	swimanimation.setPlayMode(PlayMode.LOOP);
			        BodyDef fishbd=new BodyDef();
			        fishbd.type=BodyType.KinematicBody;
			        fishbd.position.set(master.x+MathUtils.random(-1.25f,1.25f),master.y+MathUtils.random(-1.25f,1.25f));
			        PolygonShape fishbox = new PolygonShape();
			    
				    fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/4,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/4);
				       
			        FixtureDef fishfd = new FixtureDef();
			        fishfd.shape = fishbox;
					fishfd.friction=3f;
					fishfd.density=1f;
					fishfd.restitution = 0f;
					fishfd.isSensor=true;
					this.fishbody =world.createBody(fishbd);
					Fixture fishfix = fishbody.createFixture(fishfd);
					fishbody.setUserData(currentframe);
					//fishbody.setFixedRotation(false);
					fishbox.dispose();
			    //    fishbody.setLinearVelocity(-3f, 0f);
					
			    	statetime += Gdx.graphics.getDeltaTime(); 
}


public void draw(){
	depth=(fishbody.getPosition().y*-2)+150;
	if(magnetize) {

		
		fishbody.setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(fishbody.getPosition()).nor().scl(5f));
		game.level.sub.getArm().setMagnetizing(false);
		if(fishbody.getPosition().dst2(game.level.sub.getBody().getPosition())<0.55f){
			caught=true;
		}
	}
	if(caught && !game.level.sub.getArm().armrunning) {
		fishbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		currentframe.setColor(Color.RED);
	}
	statetime += Gdx.graphics.getDeltaTime(); 
	

		//  fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
			   currentframe.setPosition(fishbody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentframe.getWidth()/2, fishbody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentframe.getHeight()/2);
			   if(isMasterSardine){
				   currentframe.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);}
			   else{ currentframe.setRotation(master.fishbody.getAngle()*MathUtils.radiansToDegrees);
			  if(!scatter){
				  
				  fishbody.setLinearVelocity(master.fishbody.getLinearVelocity().x,master.fishbody.getLinearVelocity().y);
			   currentframe.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
			  }
			  else{ 
				  currentframe.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
			  
			  }
			  }
			   fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
			   currentframe.draw(batch);
}




public boolean getKill (){
	return killable;
}
public Body getBody(){
	return fishbody;
}
public void dispose(){

}
public void setLinearVelocity(float x, float y){
	if(!scatter){fishbody.setLinearVelocity(x, y);}
}
public void setRotation(float angle){
	fishbody.setTransform(fishbody.localVector, angle);
	
}
public boolean getSardineRank(){
	return isMasterSardine;
}
public void Scatter(){
	scatter=true;
	fishbody.setLinearVelocity(MathUtils.random(-3f,-6f), MathUtils.random());
	fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
	//currentframe.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
}
public void Kill(){
	killable=true;

}
public boolean getCaught() {
	return caught;
}
public void setCaught(boolean caught){
	this.caught=caught;
}
public void setProjectileHit(boolean hit) {
	this.projectilehit=hit;
}
public boolean getProjectileHit() {
	return this.projectilehit;
}

public void magnetize() {
	magnetize=true;
}
}
