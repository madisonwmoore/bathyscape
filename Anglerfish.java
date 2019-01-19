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
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import box2dLight.PointLight;

public class Anglerfish extends Fish {
	Texture fishsheet;
	boolean killable=false;
	int FRAME_COLS;
	PointLight al;
	int FRAME_ROWS;
	boolean caught=false;
	TextureRegion [] swimframe;
	Animation<TextureRegion> swimanimation;
	SpriteBatch batch;
	Sprite currentframe;
	float statetime;
	int hitpoints =20; 
	SubAbyss game;
	Body anglerfishbody;
	World world;
	String fishtype;
	boolean magnetize=false;
	private Array<Body> bodies = new Array<Body>();
public Anglerfish(SpriteBatch batch, World world, SubAbyss game,  float statetime,float x, float y,String fishtype) {
	this.batch=batch;
	this.game=game;
	this.fishtype=fishtype;
	this.statetime=statetime;
	this.world=world;
		
	fishsheet=game.assman.manager.get("data/anglerfishsheet.png",Texture.class);
	FRAME_COLS=3;
	FRAME_ROWS=2;
	
	TextureRegion[][] tmp = TextureRegion.split(fishsheet, fishsheet.getWidth() / 
			FRAME_COLS, fishsheet.getHeight() / FRAME_ROWS);                                
			        swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
			        int index1 = 0;
			        for (int i = 0; i < FRAME_ROWS; i++) {
			                for (int j = 0; j < FRAME_COLS; j++) {
			                        swimframe[index1++] = tmp[i][j];
			                }
			        }
			       
			        swimanimation = new Animation<TextureRegion>(0.1f, swimframe);
			        			
			        TextureRegion text=swimanimation.getKeyFrame(statetime, true);
			        currentframe = new Sprite(text);   
	
			        
			        BodyDef fishbd=new BodyDef();
			        fishbd.type=BodyType.KinematicBody;
			        fishbd.position.set(x,y);
			        PolygonShape fishbox = new PolygonShape();
			       
			        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/6,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/6);			        			     
			        FixtureDef fishfd = new FixtureDef();
			        fishfd.shape = fishbox;
					fishfd.friction=3f;
					fishfd.density=1f;
					fishfd.restitution = 0f;
					fishfd.isSensor=true;
					this.anglerfishbody =world.createBody(fishbd);
					Fixture fishfix =anglerfishbody.createFixture(fishfd);
					anglerfishbody.setUserData(currentframe);
					fishbox.dispose();				
					anglerfishbody.setLinearVelocity(-3f, 0f);								 
			    	statetime += Gdx.graphics.getDeltaTime(); 			    	
			    	anglerfishbody.setGravityScale(0f);
			    	
			    	al=new PointLight(game.level.rayhandler, 150, Color.SCARLET, 0.5f,0,0);
			    	//al.attachToBody(anglerfishbody,-(currentframe.getWidth()*game.WORLD_TO_BOX)/2,((currentframe.getHeight()-15f)*game.WORLD_TO_BOX)/2);
			        al.setActive(true);
			        al.setXray(true);
}
public void draw() {
	if(!game.level.paused){
		statetime += Gdx.graphics.getDeltaTime();} 
	if(caught) {
		al.setActive(false);
		anglerfishbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		getCurrentFrame().setColor(Color.RED);
		anglerfishbody.setUserData(currentframe);
				
	}
	al.setPosition(anglerfishbody.getPosition().x-64f*game.WORLD_TO_BOX, anglerfishbody.getPosition().y+(2.5f*MathUtils.sin(swimanimation.getKeyFrameIndex(statetime)*0.75f)+32f)*game.WORLD_TO_BOX);
		if(magnetize) {
			anglerfishbody.setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(anglerfishbody.getPosition()).nor().scl(5f));
		}
	
		if(!caught){
		currentframe.setRegion(swimanimation.getKeyFrame(statetime, true));  
		
		swimanimation.setPlayMode(PlayMode.LOOP);
		anglerfishbody.setUserData(currentframe);
		}
		else{
			
			anglerfishbody.setUserData(currentframe);
			
		}
		

				 currentframe.setPosition(anglerfishbody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentframe.getWidth()/2, anglerfishbody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentframe.getHeight()/2);
				   currentframe.setRotation(anglerfishbody.getAngle()*MathUtils.radiansToDegrees);
				   
				   currentframe.draw(batch);
				  // propeller.draw(batch);
			   }  

public Sprite getCurrentFrame(){
	return currentframe;
	
}
public void setPosition(float x,float y){
	anglerfishbody.setTransform(x, y, 0);
}
public void Kill(){
	killable=true;
}
public boolean getKill(){
	return killable;
}
public String getFishType(){
	return fishtype;
}
public void dispose(){
	al.setActive(false);
	al.remove();
	//al.dispose();
}
public Body getBody(){
	return anglerfishbody;
}
public boolean getCaught(){
	return caught;
}
public void setCaught(boolean caught){
	this.caught=caught;
	
}
public void magnetize() {
	magnetize=true;
	
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
		anglerfishbody.setAngularVelocity(-5f);
		 Timer.schedule(new Task(){
             @Override
             public void run() {
              Kill();
     
             }
         }
         ,2    //    (seconds)
     );
	}
	
}
}
