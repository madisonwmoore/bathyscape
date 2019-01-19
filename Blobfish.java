package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.ConeLight;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Blobfish extends Fish{
	SpriteBatch batch;
	float statetime;
	World world;
	SubAbyss game;
	float x,y;
	Texture blobsheet;
	Sprite currentframe;
	Body blobbody;
	boolean caught=false;
	ConeLight sublight;
	int hitpoints=10;
	
public Blobfish(SpriteBatch batch,World world,float statetime, SubAbyss game, float x, float y) {
	
	this.batch=batch;
	this.statetime=statetime;
	this.world=world;
	this.game=game;
	this.x=x;
	this.y=y;
	
	sublight=game.level.sub.getFrontLight();
	blobsheet=game.assman.manager.get("data/blobfishsheet.png");
	blobsheet.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	FRAME_COLS=5;
	FRAME_ROWS=2;

	TextureRegion[][] tmp = TextureRegion.split(blobsheet, blobsheet.getWidth() / 
			FRAME_COLS, blobsheet.getHeight() / FRAME_ROWS);                                
		swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
		int index1 = 0;
		for (int i = 0; i < FRAME_ROWS; i++) {
		                for (int j = 0; j < FRAME_COLS; j++) {
		                        swimframe[index1++] = tmp[i][j];
		                									 }	                
											}
	swimanimation = new Animation<TextureRegion>(0.1f, swimframe);
	swimanimation.setPlayMode(PlayMode.LOOP);
     TextureRegion text=swimanimation.getKeyFrame(statetime, true);
        currentframe = new Sprite(text);   
	
        BodyDef fishbd=new BodyDef();
        fishbd.type=BodyType.DynamicBody;
        fishbd.position.set(x,y);
        PolygonShape fishbox = new PolygonShape();
       
        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/3,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/6);
        
        
        FixtureDef fishfd = new FixtureDef();
        fishfd.shape = fishbox;
		fishfd.friction=3f;
		fishfd.density=1f;
		fishfd.restitution = 0f;
		fishfd.isSensor=true;
		blobbody =world.createBody(fishbd); 
		blobbody.createFixture(fishfd);
		blobbody.setUserData(currentframe);
		fishbox.dispose();
        blobbody.setLinearVelocity(-2.5f, 0f);
        statetime += Gdx.graphics.getDeltaTime(); 
        blobbody.setGravityScale(0f);
        
	
}
public void draw() {
	blobbody.setLinearVelocity(MathUtils.clamp(-2.5f+-1.5f*MathUtils.cos(statetime*0.5f),-2.5f, -2.5f+-1.5f*MathUtils.cos(statetime*0.5f)), 0f);
	statetime += Gdx.graphics.getDeltaTime();
	currentframe.setRegion(swimanimation.getKeyFrame(statetime, true));
	
	currentframe.setPosition(blobbody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentframe.getWidth()/2, blobbody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentframe.getHeight()/2);
	currentframe.setRotation(blobbody.getAngle()*MathUtils.radiansToDegrees);
	currentframe.draw(batch);
	
	if(caught){
		blobbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		getCurrentFrame().setColor(Color.RED);
		blobbody.setUserData(currentframe);
		
	}
	
//	sublight.getDistance() 
	
}
public String getFishType(){
	return "blobfish";
	
}
public Body getBody() {
	return blobbody;
}
public boolean getCaught(){
	return caught;
}
public void setCaught(boolean caught){
	
	this.caught=caught;
	
}
public Sprite getCurrentFrame(){
	
	return currentframe;
}
public void hit(int hit) {
	Timer.schedule(new Task(){
        @Override
        public void run() {
            currentframe.setColor(Color.RED);
    
            Timer.schedule(new Task(){
                @Override
                public void run() {
                    currentframe.setColor(Color.WHITE);
        
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
		blobbody.setAngularVelocity(-5f);
		 Timer.schedule(new Task(){
             @Override
             public void run() {
              Kill();
     
             }
         }
         ,2    //    (seconds)
     );
	}
}}
