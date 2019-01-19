package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.physics.box2d.Fixture;

public class Torpedo {
Sprite torpedosprite;
Sprite bubblesprite,bubblespriteb;
boolean islaunched=false;
Body torpedobody;
float statetime;
Fixture torpedofix;
Batch batch;
SubAbyss game;
AssetManager assman;
TextureRegion[] boomframe;
Animation<TextureRegion> explosionanimation;
Sprite currentFrame;
boolean shouldexplode;
boolean shoulddie=false;
Texture explosionsheet;
long birth;
long explodetime=0L;

public Torpedo(Batch batch, World world,float statetime, AssetManager assman, final SubAbyss game, float x, float y) {
	this.batch=batch;
	this.game=game;
	this.assman=assman;
	this.statetime=statetime;
	birth=TimeUtils.millis();
	torpedosprite=new Sprite(game.assman.manager.get("data/torpedo.png",Texture.class));
	bubblesprite=new Sprite(game.assman.manager.get("data/bubble.png",Texture.class));
	bubblespriteb=new Sprite(game.assman.manager.get("data/bubble.png",Texture.class));
	BodyDef subbd = new BodyDef();
	subbd.type = BodyType.KinematicBody;
	subbd.position.set(x,y);
    PolygonShape torpedobox = new PolygonShape();
  
    torpedobox.setAsBox((torpedosprite.getWidth()/2)*SubAbyss.WORLD_TO_BOX,(torpedosprite.getHeight()/2)*SubAbyss.WORLD_TO_BOX);
    FixtureDef subfd = new FixtureDef();
    subfd.shape = torpedobox;
	subfd.friction=3f;
	subfd.density=0.5f;
	subfd.restitution = 0f;
	subfd.isSensor=true;

	this.torpedobody =world.createBody(subbd);

	torpedofix = torpedobody.createFixture(subfd);
	torpedobody.setUserData(torpedosprite);
	torpedobox.dispose();
	torpedobody.setFixedRotation(true);
	torpedobody.setAngularDamping(3f);
	
	bubblesprite.setScale(0.5f);
	bubblespriteb.setScale(.75f);
	 explosionsheet=	game.assman.manager.get("data/explosion.png",Texture.class);
	 
	 int FRAME_COLS=2;
	int FRAME_ROWS=3;
	
TextureRegion[][] tmp = TextureRegion.split(explosionsheet, explosionsheet.getWidth() / 
FRAME_COLS, explosionsheet.getHeight() / FRAME_ROWS);                                
       boomframe = new TextureRegion[FRAME_COLS * FRAME_ROWS];
       int index1 = 0;
       for (int i = 0; i < FRAME_ROWS; i++) {
               for (int j = 0; j < FRAME_COLS; j++) {
                       boomframe[index1++] = tmp[i][j];
               }
               boomframe[0]=boomframe[1];
       }
	

	
	
}
public void draw() {
	torpedosprite.setPosition(torpedobody.getPosition().x*SubAbyss.BOX_WORLD_TO-torpedosprite.getWidth()/2f,torpedobody.getPosition().y*SubAbyss.BOX_WORLD_TO-torpedosprite.getHeight()/2f);
	   torpedosprite.setRotation(torpedobody.getAngle()*MathUtils.radiansToDegrees);			  
	  if(!shoulddie) {
	   torpedosprite.draw(batch);}
	   
	   if(islaunched) {
			// bubblesprite.setScale(-MathUtils.random(0.2f,0.75f));
			 
			 game.level.bubbles.add(new Bubble(new Sprite(bubblesprite), game, 5, game.level.world, batch, torpedosprite.getX()+torpedosprite.getWidth()/2, torpedosprite.getY()+torpedosprite.getHeight()/2, 10f,true));
			 game.level.bubbles.add(new Bubble(new Sprite(bubblesprite), game, 5, game.level.world, batch, torpedosprite.getX()+torpedosprite.getWidth()/2, torpedosprite.getY()+torpedosprite.getHeight()/2, 10f,true));
	   }
	   if(shouldexplode&&!shoulddie) {
		    explosionanimation = new Animation<TextureRegion>(.02f, boomframe);
			  //     explosionanimation.setPlayMode(PlayMode.NORMAL);
			      currentFrame=new Sprite(explosionanimation.getKeyFrame(statetime,true));
			statetime += Gdx.graphics.getDeltaTime(); 
			explosionanimation.setPlayMode(PlayMode.NORMAL);
			
		currentFrame.setRegion( explosionanimation.getKeyFrame(statetime, true));
		//minebody.setUserData(currentFrame);
		currentFrame.setPosition(torpedobody.getPosition().x*SubAbyss.BOX_WORLD_TO-currentFrame.getWidth()/2, torpedobody.getPosition().y*SubAbyss.BOX_WORLD_TO-currentFrame.getHeight()/2);
		currentFrame.setRotation(torpedobody.getAngle()*MathUtils.radiansToDegrees);
		if(!shoulddie) {
		currentFrame.draw(batch);}
		if(explosionanimation.isAnimationFinished(statetime)){

//			Timer.schedule(new Timer.Task() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				
//			shoulddie=true;
//			}
//			@Override
//			public void cancel() {
//				// TODO Auto-generated method stub
//			}
//			}, .12f);
			
		}
	   }
		if(TimeUtils.timeSinceMillis(birth)>12000L) {
			shoulddie=true;
		}
		if(explodetime!=0L&&TimeUtils.timeSinceMillis(explodetime)>120L) {
			shoulddie=true;
		}
		
}
public void launch() {
	torpedobody.setLinearVelocity(-5f, 0f);
	islaunched=true;
}
public void explode() {
shouldexplode=true;
explodetime=TimeUtils.millis();
}
public void setPosition(float x, float y, float angle) {
torpedobody.setTransform(x, y, angle);
}

public boolean getisLaunched() {
	return islaunched;
}
public void dispose() {
	//torpedo=null;
//	game.level.world.destroyBody(torpedobody);
}
public boolean getKill() {
	return shoulddie;
}



}
