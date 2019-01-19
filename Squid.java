package com.madison.Bathyscape.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Squid {
	Body squidbody;
	SubAbyss game;
	World world;
	SpriteBatch batch;
	Sprite squidbodysprite;
	Texture tentaclesheet;
	Sprite[][] tentacles;
	Body[][] tentaclebodies;
	int FRAME_COLS,FRAME_ROWS;
	boolean grabsub;
	float randy;
	boolean killable;
	int hitpoints=30;
	boolean dying;
	boolean caught;
	
public Squid(SpriteBatch batch,World world,float statetime, SubAbyss game, float x, float y) {
	this.world=world;
	this.batch=batch;
	this.game=game;
	randy=MathUtils.random(game.level.camera.position.y-(game.level.camera.viewportHeight/1.5f)*game.WORLD_TO_BOX, game.level.camera.position.y+(game.level.camera.viewportHeight/1.5f)*game.WORLD_TO_BOX);
	tentacles=new Sprite[9][4];
	squidbodysprite = new Sprite(game.assman.manager.get("data/squidbody.png",Texture.class));
	tentaclesheet=game.assman.manager.get("data/tentaclesheet.png",Texture.class);
	tentaclebodies = new Body[9][4];
	FRAME_COLS=9;
	FRAME_ROWS=4;
                               
	float lineheight=tentaclesheet.getHeight()/FRAME_ROWS;
	float linewidth=tentaclesheet.getWidth();

	tentacles[0][0] =new Sprite( new TextureRegion(tentaclesheet,0f,0f,0.1184f,0.33f));
	tentacles[1][0]= new Sprite(new TextureRegion(tentaclesheet,0.1884f,0f,0.2433f,0.33f));
	tentacles[2][0]=	new Sprite(new TextureRegion(tentaclesheet,0.2433f,0f,0.3681f,0.33f));
	tentacles[3][0]=	new Sprite(new TextureRegion(tentaclesheet,0.3681f,0f,0.4912f,0.33f));
	tentacles[4][0]=	new Sprite(new TextureRegion(tentaclesheet,0.4912f,0f,0.5723f,0.33f));
	tentacles[5][0]=	new Sprite(new TextureRegion(tentaclesheet,0.5723f,0f,0.6516f,0.33f));
	tentacles[6][0]=	new Sprite(new TextureRegion(tentaclesheet,0.6516f,0f,0.7285f,0.33f));
	tentacles[7][0]=	new Sprite(new TextureRegion(tentaclesheet,0.7285f,0f,0.7753f,0.33f));
	tentacles[8][0]=	new Sprite(new TextureRegion(tentaclesheet,0.7753f,0f,0.808f,0.33f));
	
	tentacles[0][1] =new Sprite( new TextureRegion(tentaclesheet,0f,0.33f,0.1184f,0.66f));
	tentacles[1][1]= new Sprite(new TextureRegion(tentaclesheet,0.1884f,0.33f,0.2433f,0.66f));
	tentacles[2][1]=	new Sprite(new TextureRegion(tentaclesheet,0.2433f,0.33f,0.3681f,0.66f));
	tentacles[3][1]=	new Sprite(new TextureRegion(tentaclesheet,0.3681f,0.33f,0.4912f,0.66f));
	tentacles[4][1]=	new Sprite(new TextureRegion(tentaclesheet,0.4912f,0.33f,0.5723f,0.66f));
	tentacles[5][1]=	new Sprite(new TextureRegion(tentaclesheet,0.5723f,0.33f,0.6516f,0.66f));
	tentacles[6][1]=	new Sprite(new TextureRegion(tentaclesheet,0.6516f,0.33f,0.7285f,0.66f));
	tentacles[7][1]=	new Sprite(new TextureRegion(tentaclesheet,0.7285f,0.33f,0.7753f,0.66f));
	tentacles[8][1]=	new Sprite(new TextureRegion(tentaclesheet,0.7753f,0.33f,0.808f,0.66f));
	
	tentacles[0][2] =new Sprite( new TextureRegion(tentaclesheet,0f,0f,0.1184f,0.33f));
	tentacles[0][2].flip(false, true);
	tentacles[1][2]= new Sprite(new TextureRegion(tentaclesheet,0.1884f,0f,0.2433f,0.33f));
	tentacles[1][2].flip(false, true);
	tentacles[2][2]=	new Sprite(new TextureRegion(tentaclesheet,0.2433f,0f,0.3681f,0.33f));
	tentacles[2][2].flip(false, true);
	tentacles[3][2]=	new Sprite(new TextureRegion(tentaclesheet,0.3681f,0f,0.4912f,0.33f));
	tentacles[3][2].flip(false, true);
	tentacles[4][2]=	new Sprite(new TextureRegion(tentaclesheet,0.4912f,0f,0.5723f,0.33f));
	tentacles[4][2].flip(false, true);
	tentacles[5][2]=	new Sprite(new TextureRegion(tentaclesheet,0.5723f,0f,0.6516f,0.33f));
	tentacles[5][2].flip(false, true);
	tentacles[6][2]=	new Sprite(new TextureRegion(tentaclesheet,0.6516f,0f,0.7285f,0.33f));
	tentacles[6][2].flip(false, true);
	tentacles[7][2]=	new Sprite(new TextureRegion(tentaclesheet,0.7285f,0f,0.7753f,0.33f));
	tentacles[7][2].flip(false, true);
	tentacles[8][2]=	new Sprite(new TextureRegion(tentaclesheet,0.7753f,0f,0.808f,0.33f));
	tentacles[8][2].flip(false, true);
	
	tentacles[0][3] =new Sprite( new TextureRegion(tentaclesheet,0f,0.66f,0.1184f,1f));
	tentacles[1][3]= new Sprite(new TextureRegion(tentaclesheet,0.1884f,0.66f,0.2433f,1f));
	tentacles[2][3]=	new Sprite(new TextureRegion(tentaclesheet,0.2433f,0.66f,0.3681f,1f));
	tentacles[3][3]=	new Sprite(new TextureRegion(tentaclesheet,0.3681f,0.66f,0.4912f,1f));
	tentacles[4][3]=	new Sprite(new TextureRegion(tentaclesheet,0.4912f,0.66f,0.5723f,1f));
	tentacles[5][3]=	new Sprite(new TextureRegion(tentaclesheet,0.5723f,0.66f,0.6516f,1f));
	tentacles[6][3]=	new Sprite(new TextureRegion(tentaclesheet,0.6516f,0.66f,0.7285f,1f));
	tentacles[7][3]=	new Sprite(new TextureRegion(tentaclesheet,0.7285f,0.66f,0.7753f,1f));
	tentacles[8][3]=	new Sprite(new TextureRegion(tentaclesheet,0.7753f,0.66f,0.808f,1f));
	
	   BodyDef squidbd=new BodyDef();
       squidbd.type=BodyType.DynamicBody;
       squidbd.position.set(x,y);
       PolygonShape squidbox = new PolygonShape();
       squidbox.setAsBox((squidbodysprite.getWidth())*SubAbyss.WORLD_TO_BOX/3,(squidbodysprite.getHeight())*SubAbyss.WORLD_TO_BOX/4);
	
	FixtureDef squidfd = new FixtureDef();
    squidfd.shape = squidbox;
	squidfd.friction=3f;
	squidfd.density=3.5f;
	squidfd.restitution = 0f;
	squidbody =world.createBody(squidbd); 
	Fixture fishfix = squidbody.createFixture(squidfd);
	squidbody.setUserData("squid");
	squidbox.dispose();
    squidbody.setLinearVelocity(-3.5f, 0f);
    statetime += Gdx.graphics.getDeltaTime(); 
    squidbody.setGravityScale(0f);
	squidbody.setAngularDamping(3f);
	BodyDef tentaclebd=new BodyDef();
	tentaclebd.type=BodyType.DynamicBody;
	PolygonShape tentaclebox = new PolygonShape();

	for(int j=0;j<FRAME_ROWS;j++) {
	for(int i =0;i<9;i++) {
		FixtureDef tentaclefix = new FixtureDef();
		tentaclefix.isSensor=true;
		System.out.println("i is "+i);
		tentaclebox.setAsBox(((tentacles[i][j].getRegionWidth()-10f)/2)*SubAbyss.WORLD_TO_BOX, (tentacles[i][j].getRegionHeight()/6)*SubAbyss.WORLD_TO_BOX);
		tentaclefix.shape=tentaclebox;
		tentaclefix.density=2.6f;
		if(i==0){
			 tentaclebd.position.set(squidbody.getPosition().x,y*SubAbyss.WORLD_TO_BOX);
		}else {
		 tentaclebd.position.set(squidbody.getPosition().x,squidbody.getPosition().y);}
	    	Body b=world.createBody(tentaclebd);
	    	b.setUserData(tentacles[i][j]);
	    	b.createFixture(tentaclefix);
	    	b.setGravityScale(0f);	    	
	    	if(j==2) {b.setAngularDamping(-1.4f);}
	    	else{b.setAngularDamping(1.4f);}
	    	b.setLinearDamping(1f);
	    	tentaclebodies[i][j]=(b);
	}
	}
	tentaclebox.dispose();
	
	DistanceJointDef dj = new DistanceJointDef();
	dj.collideConnected=false;
	dj.dampingRatio=1;
for(int j=0;j<FRAME_ROWS;j++) {	
	for(int i=0;i<8;i++) {
		dj.dampingRatio=1;
		
		 dj.bodyA=tentaclebodies[i][j];
    	 dj.localAnchorA.set(((tentacles[i][j].getRegionWidth()-10f)/2)*SubAbyss.WORLD_TO_BOX,0);
    	 dj.localAnchorB.set( -((tentacles[i+1][j].getRegionWidth()-10f)/2)*SubAbyss.WORLD_TO_BOX, 0);
    	 dj.bodyB=tentaclebodies[i+1][j];
    	 dj.length=((2.5f)*SubAbyss.WORLD_TO_BOX);
    	 
    	 world.createJoint(dj);
	}
}

for(int j=0;j<FRAME_ROWS;j++) {
	DistanceJointDef dj2 = new DistanceJointDef();
	dj2.collideConnected=false;
	dj2.dampingRatio=1;
	dj2.bodyA=squidbody;
	if(j==0) {
		dj2.localAnchorA.set((squidbodysprite.getWidth())*SubAbyss.WORLD_TO_BOX/2.5f,(squidbodysprite.getHeight()/5)*SubAbyss.WORLD_TO_BOX);
	}
	if(j==1) {
		dj2.localAnchorA.set((squidbodysprite.getWidth())*SubAbyss.WORLD_TO_BOX/2.5f,(squidbodysprite.getHeight()/5)*0.25f*SubAbyss.WORLD_TO_BOX);
	}
	if(j==2) {
		dj2.localAnchorA.set((squidbodysprite.getWidth())*SubAbyss.WORLD_TO_BOX/2.5f,-(squidbodysprite.getHeight()/5)*SubAbyss.WORLD_TO_BOX);
	}
	if(j==3) {
		dj2.localAnchorA.set((squidbodysprite.getWidth())*SubAbyss.WORLD_TO_BOX/2.5f,0);
	}
	dj2.bodyB=tentaclebodies[0][j];
	dj2.localAnchorB.set(-tentacles[0][j].getWidth()*SubAbyss.WORLD_TO_BOX/2,0);
	dj2.length=(4f)*SubAbyss.WORLD_TO_BOX;
	world.createJoint(dj2);
}

	
	
			        }


public void draw() {
	float gametime=MathUtils.sin(game.level.statetime*5);
	Vector2 tw2=generatSquidDestination();
	if(!grabsub) {
	if(gametime>=0.5){
	//squidbody.setLinearVelocity(-2.1f*gametime, squidbody.getLinearVelocity().y);
		if(!dying) {
		squidbody.setLinearVelocity(tw2.sub(squidbody.getPosition()).nor().scl(gametime*3));}
	}
	else {
		if(!dying) {
		squidbody.setLinearVelocity(tw2.sub(squidbody.getPosition()).nor().scl(2));}
		//squidbody.setLinearVelocity(-3.5f, squidbody.getLinearVelocity().y);
	}}
	for(int j=0;j<FRAME_ROWS;j++) {
	for(int i=0;i<9;i++) {
		Sprite t=(Sprite) tentaclebodies[i][j].getUserData();
	//	batch.draw(t,tentaclebodies.get(i).getPosition().x*game.BOX_WORLD_TO,tentaclebodies.get(i).getPosition().y*game.BOX_WORLD_TO);
	t.setPosition(tentaclebodies[i][j].getPosition().x*game.BOX_WORLD_TO-tentacles[i][j].getWidth()/2,tentaclebodies[i][j].getPosition().y*game.BOX_WORLD_TO-tentacles[i][j].getHeight()/2);
	t.setRotation(MathUtils.radiansToDegrees*tentaclebodies[i][j].getAngle());
	t.draw(batch);
	
	
	
	}
	
	}
	squidbodysprite.setPosition(squidbody.getPosition().x*game.BOX_WORLD_TO-squidbodysprite.getWidth()/2,squidbody.getPosition().y*game.BOX_WORLD_TO-squidbodysprite.getHeight()/2);
	squidbodysprite.setRotation(squidbody.getAngle()*MathUtils.radiansToDegrees);
	squidbodysprite.draw(batch);
	
	if(squidbody.getPosition().x<=(0-2*game.level.camera.viewportWidth/2)*game.WORLD_TO_BOX) {
		Kill();
	}
	if((Gdx.input.isKeyPressed(Keys.G)||game.level.sub.getBody().getPosition().dst2(squidbody.getPosition())<15f)&&!caught) {
		grabsub=true;
		grabSub();
	}
	else {
		grabsub=false;
		ungrabSub();
	}
	
	if(caught) {
		ungrabSub();
		squidbody.setLinearVelocity(0, 0);
		squidbody.setTransform(game.level.sub.getArm2Body().getPosition().x+0.9f*(MathUtils.cos(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getPosition().y+0.9f*(MathUtils.sin(game.level.sub.getArm2Body().getAngle())*(game.level.sub.getArm2Sprite().getWidth()/2)*SubAbyss.WORLD_TO_BOX), game.level.sub.getArm2Body().getAngle()-(MathUtils.PI/2));
		squidbodysprite.setColor(Color.RED);
		for(int j=0;j<FRAME_ROWS;j++) {
			for(int i=0;i<FRAME_COLS;i++) {
				tentacles[i][j].setColor(Color.RED);
			}
		}
	}
	
	//batch.draw(tentacles[0],squidbody.getPosition().x*game.BOX_WORLD_TO,squidbody.getPosition().y*game.BOX_WORLD_TO);
}
private Vector2 generatSquidDestination() {
	float x=(0-2*game.level.camera.viewportWidth/2)*game.WORLD_TO_BOX;
	//float y=game.level.fishes.get(0).getBody().getPosition().y;
	float y=randy;
	Vector2 tw2=new Vector2(x,y);
	System.out.println("x,y "+x+","+y);
	return tw2;
}
public void dispose() {
	
	
}
public boolean getKill() {
	return killable;
	
}

public void Kill() {
	killable=true;
}
public Body getSquidBody() {
	return squidbody;
}
public void grabSub() {
	//squidbody.setLinearVelocity(tw2.sub(squidbody.getPosition()).nor().scl(gametime*3));
	squidbody.applyForceToCenter(game.level.sub.getArm2Body().getPosition().sub(squidbody.getPosition()).nor().scl(-2f),false);

	
	
	tentaclebodies[8][0].getFixtureList().first().setDensity(4f);
	tentaclebodies[8][0].getFixtureList().first().setSensor(false);
	tentaclebodies[8][0].setLinearVelocity(game.level.sub.getBody().getPosition().sub(tentaclebodies[8][0].getPosition()).nor().scl(15f));
	
	tentaclebodies[8][1].getFixtureList().first().setDensity(4f);
	tentaclebodies[8][1].getFixtureList().first().setSensor(false);
	tentaclebodies[8][1].setLinearVelocity(game.level.sub.getBody().getPosition().sub(tentaclebodies[8][1].getPosition()).nor().scl(15f));
	
	tentaclebodies[8][2].getFixtureList().first().setDensity(4f);
	tentaclebodies[8][2].getFixtureList().first().setSensor(false);
	tentaclebodies[8][2].setLinearVelocity(game.level.sub.getArm2Body().getPosition().sub(tentaclebodies[8][2].getPosition()).nor().scl(15f));
	
	tentaclebodies[8][3].getFixtureList().first().setDensity(4f);
	tentaclebodies[8][3].getFixtureList().first().setSensor(false);
	tentaclebodies[8][3].setLinearVelocity(game.level.sub.getBody().getPosition().sub(tentaclebodies[8][3].getPosition()).nor().scl(15f));

//squidbody.setLinearVelocity(squidbody.getPosition().sub(game.level.sub.getBody().getPosition()).nor().scl(0.5f));
//squidbody.setFixedRotation(true);
}
public void ungrabSub() {
	grabsub=false;
	tentaclebodies[8][0].getFixtureList().first().setDensity(2.6f);
	tentaclebodies[8][0].getFixtureList().first().setSensor(true);
	tentaclebodies[8][1].getFixtureList().first().setDensity(2.6f);
	tentaclebodies[8][1].getFixtureList().first().setSensor(true);
	tentaclebodies[8][2].getFixtureList().first().setDensity(2.6f);
	tentaclebodies[8][2].getFixtureList().first().setSensor(true);
	tentaclebodies[8][3].getFixtureList().first().setDensity(2.6f);
	tentaclebodies[8][3].getFixtureList().first().setSensor(true);
}
public void hit(int hit) {
	Timer.schedule(new Task(){
        @Override
        public void run() {
            squidbodysprite.setColor(Color.RED);
    
            Timer.schedule(new Task(){
                @Override
                public void run() {
                    squidbodysprite.setColor(Color.WHITE);
        
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
		squidbody.setAngularVelocity(0.5f);
		squidbody.setLinearVelocity(0, 0);
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
