package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Fish {
	Texture fishsheet;
	boolean killable=false;
	int FRAME_COLS;
	int FRAME_ROWS;
	boolean caught=false;
	TextureRegion [] swimframe;
	Animation<TextureRegion> swimanimation;
	SpriteBatch batch;
	Sprite currentframe;
	float statetime,depth=100f;
	SubAbyss game;
	Body fishbody;
	World world;
	String fishtype;
	Vector2 tw2;
	int hitpoints;
	boolean magnetize=false;
	boolean dying;
	private Array<Body> bodies = new Array<Body>();
	float randy;
public Fish(){};
	
public Fish(SpriteBatch batch, World world, SubAbyss game,  float statetime,float x, float y,String fishtype){
	this.batch=batch;
	this.game=game;
	this.fishtype=fishtype;
	this.statetime=statetime;
	this.world=world;
	randy=MathUtils.random(game.level.camera.position.y-(game.level.camera.viewportHeight/1.5f)*game.WORLD_TO_BOX, game.level.camera.position.y+(game.level.camera.viewportHeight/1.5f)*game.WORLD_TO_BOX);
	if(fishtype=="tuna"){
		fishsheet=game.assman.manager.get("data/fishsheet.png",Texture.class);
	FRAME_COLS=1;
	FRAME_ROWS=4;
	hitpoints=10;
	}
	if(fishtype=="marlin"){
		fishsheet=game.assman.manager.get("data/marlinsheet.png",Texture.class);
		 FRAME_COLS=2;
		 FRAME_ROWS=3;
		tw2= generatMarlinDestination();
		hitpoints=20;
		}
	if(fishtype=="mahimahi"){
		fishsheet=game.assman.manager.get("data/mahimahi.png",Texture.class);
		 FRAME_COLS=2;
		 FRAME_ROWS=3;
		 hitpoints=10;
	}
	TextureRegion[][] tmp = TextureRegion.split(fishsheet, fishsheet.getWidth() / 
	FRAME_COLS, fishsheet.getHeight() / FRAME_ROWS);                                
	        swimframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
	        int index1 = 0;
	        for (int i = 0; i < FRAME_ROWS; i++) {
	                for (int j = 0; j < FRAME_COLS; j++) {
	                        swimframe[index1++] = tmp[i][j];
	                }
	        }
	        if(fishtype=="tuna"){
	        swimanimation = new Animation<TextureRegion>(0.1f, swimframe);
	        }
	        if(fishtype=="marlin"){
	        swimanimation = new Animation<TextureRegion>(0.07f, swimframe);
	        }
	        if(fishtype=="mahimahi"){
		        swimanimation = new Animation<TextureRegion>(0.07f, swimframe);
		        }
	        
	        
	        TextureRegion text=swimanimation.getKeyFrame(statetime, true);
	        currentframe = new Sprite(text);   
	        
	        
	        
	        
	        
	       // theframe=new Sprite(currentframe);
	        
	        BodyDef fishbd=new BodyDef();
	        fishbd.type=BodyType.KinematicBody;
	        fishbd.position.set(x,y);
	        PolygonShape fishbox = new PolygonShape();
	        if(fishtype=="tuna"){
	        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/4,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/4);
	        }
	        if(fishtype=="marlin"){
		        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/4,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/4);
		        }
	        if(fishtype=="mahimahi"){
		        fishbox.setAsBox((currentframe.getRegionWidth())*SubAbyss.WORLD_TO_BOX/4,(currentframe.getRegionHeight())*SubAbyss.WORLD_TO_BOX/4);
		        }
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
			//fishbody.setFixedRotation(true);
			if(fishtype=="tuna"){	
	        if(game.level.level==0) {
				fishbody.setLinearVelocity(-3f, 0f);}
	        if(game.level.level==1) {
				fishbody.setLinearVelocity(-4f, 0f);}
	        if(game.level.level==2) {
				fishbody.setLinearVelocity(-5f, 0f);}
			}
	        if(fishtype=="marlin"){
	        	fishbody.setLinearVelocity(-5f, 0f);
	        }
	        if(fishtype=="mahimahi"){
	        	if(game.level.level==0) {
					fishbody.setLinearVelocity(-5f, 0f);}
		        if(game.level.level==1) {
					fishbody.setLinearVelocity(-5.5f, 0f);}
		        if(game.level.level==2) {
					fishbody.setLinearVelocity(-6f, 0f);}
				
	        }
	    	statetime += Gdx.graphics.getDeltaTime(); 
	    	
	    	fishbody.setGravityScale(0f);
	    	
	    	
	    	 
	        
	       // swimanimation.setPlayMode(PlayMode.NORMAL);
}
public void draw(){
	if(!game.level.paused){
	statetime += Gdx.graphics.getDeltaTime();} 
	
	if(magnetize) {

	
		fishbody.setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(fishbody.getPosition()).nor().scl(5f));		
		game.level.sub.getArm().setMagnetizing(false);
		if(fishbody.getPosition().dst2(game.level.sub.getArm2Body().getPosition())<0.55f){
			caught=true;
		}
		if(fishbody.getPosition().dst2(game.level.sub.getBody().getPosition())>4.5f){
			caught=false;
			magnetize=false;
		}
		
	}
	if(fishtype.equals("mahimahi")&&!caught&&!magnetize&&!dying){
		if(depth<3) {
			fishbody.setLinearVelocity(-3.3f, -4f);
			fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
		}
		else {
		if(game.level.level==0) {
			fishbody.setLinearVelocity(-3.3f, MathUtils.cos(statetime*-0.25f)*1.2f);}
		if(game.level.level==1) {
			fishbody.setLinearVelocity(-3.9f, MathUtils.cos(statetime*-0.25f)*1.2f);}
		if(game.level.level==2) {
			fishbody.setLinearVelocity(-4.3f, MathUtils.cos(statetime*-0.25f)*1.2f);}
		fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
		}
		}
	if(fishtype.equals("marlin")&&!caught&&!magnetize){
		//Vector2 tw=game.level.fishes.get(0).getBody().getPosition();
	if(depth<10f){
		tw2.y=65f;
		randy=65f;
		fishbody.setLinearVelocity(tw2.sub(fishbody.getPosition()).nor().scl(5f));
		fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
	}else {
		tw2=generatMarlinDestination();
		fishbody.setLinearVelocity(tw2.sub(fishbody.getPosition()).nor().scl(5f));
		fishbody.setTransform(fishbody.getPosition().x, fishbody.getPosition().y, ((fishbody.getLinearVelocity().angle()+180)*MathUtils.degreesToRadians));
		//fishbody.setTransform(fishbody.getPosition().x,fishbody.getPosition().y,fishbody.getAngle());
	}
	}
	if(!caught){
	currentframe.setRegion(swimanimation.getKeyFrame(statetime, true));  
	swimanimation.setPlayMode(PlayMode.LOOP);
	fishbody.setUserData(currentframe);
	}
	
	if(caught){
		if(game.level.sub.getArm().getType().equals("Chopstick Arm")) {
		fishbody.setTransform(game.level.sub.getArm2Body().getPosition().x+1.1f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+1.1f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		}
		else {
		fishbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		}
		getCurrentFrame().setColor(Color.RED);
		fishbody.setUserData(currentframe);
		
	}
	
	world.getBodies(bodies);
	for(Body fishbody : bodies)
		   if (fishbody.getUserData() !=null && fishbody.getUserData() instanceof Sprite &&fishbody.getUserData()==currentframe ){
			   Sprite sprite = (Sprite) fishbody.getUserData();
			   sprite.setPosition(fishbody.getPosition().x*SubAbyss.BOX_WORLD_TO-sprite.getWidth()/2, fishbody.getPosition().y*SubAbyss.BOX_WORLD_TO-sprite.getHeight()/2);
			   sprite.setRotation(fishbody.getAngle()*MathUtils.radiansToDegrees);
			   
			   sprite.draw(batch);
			  // propeller.draw(batch);
		   }  
	  depth=(fishbody.getPosition().y*-2)+150;
}
public Sprite getCurrentFrame(){
	return currentframe;
	
}
public void setPosition(float x,float y){
	fishbody.setTransform(x, y, 0);
}
public void Kill(){
	killable=true;
//	 Timer.schedule(new Timer.Task() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//				killable=true;
//			}
//			@Override
//			public void cancel() {
//				// TODO Auto-generated method stub
//			}
//			}, 1.2f);
	
}
public boolean getKill(){
	return killable;
}
public String getFishType(){
	return fishtype;
}
public void dispose(){
	
}
public Body getBody(){
	return fishbody;
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
private Vector2 generatMarlinDestination() {
	float x=(0-game.level.camera.viewportWidth/2)*game.WORLD_TO_BOX;
	//float y=game.level.fishes.get(0).getBody().getPosition().y;
	float y=randy;
	Vector2 tw2=new Vector2(x,y);
	System.out.println("x,y "+x+","+y);
	return tw2;
}
public boolean isExploded() {
	return false;
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
		fishbody.setAngularVelocity(-5f);
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
