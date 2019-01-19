package com.madison.Bathyscape.core;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ExtendViewport;



public class Level1 implements Screen {
	public final static Vector2 GRAVITY  = new Vector2(0,-1f);
	boolean armrunning=false;
	int g;
	RayHandler rayhandler;
	ConeLight dl;
	PointLight explodelight;
	FishManager fishmanager;
	float [] verticies;
	TrapManager trapmanager;
	 OrthographicCamera camera;
	 boolean flashon;
	 Mine blowmine;  
	 int level=0;
	 Sound boom,collide;
	 boolean evilsubexists;
	 boolean soundonoff;
	 EvilSub evilsub;
	 Trash trash;
	 boolean dark=false;
	 Texture minetext,chaintext;
	 boolean placeTuna, placeMarlin,placeMine;
	 Box2DDebugRenderer debugRenderer;
	ConeLight fl,fl2,fl3,fl4,fl5,fl6,fl7;
	PointLight pl;
	Equipment equipment;
	Skin skin;
	Sound armsound;
     SpriteBatch batch;
     Stage mainstage;
     Squid squid;
     Sound hit;
     Table table;
     float WIDTH=1366f;
     boolean net,sharkcollision;
     boolean dying;
     InGameMenu ingamemenu;
     int mahimahicount=0,marlincount=0,tunacount=0,anglerfishcountcatch=0,fugucount=0, blobfishcount=0;
     float HEIGHT=768f;
      Array<Fish> fishes = new Array<Fish>();
      Array<Sardine> sardines = new Array<Sardine>();
      Array<Mine> mines = new Array<Mine>();
      Array<Torpedo> torpedos =new Array<Torpedo>();
     private Array<Trap> traps = new Array<Trap>();
     private Array<Trash> trashlist=new Array<Trash>();
     boolean activeJellyfish=false;
     Submersible sub;
     Sprite mine,chainsprite,shipsprite;
     ExtendViewport fv;
     MenuScreen menu;
     Texture sardine,shiptext;
     boolean paused;
     World world;
     float red=20/255f,green=98/255f,blue=120/255f,alpha;
     SubAbyss game;
     ShapeRenderer shaperenderer;
     Rectangle rectangle,skyrectangle;
     float cameraposition=0f;
     float camerapositionx=0f;
     float darkness=0f;
     static Array<Bubble> bubbles=new Array<Bubble>();
     float statetime=0f;
     boolean subhit;
     
	public Level1 (SubAbyss subAbyss,MenuScreen menuscreen){
        this.game = subAbyss;
        this.menu=menuscreen;
       
}
	@Override
	public void render(float delta) {
		if(!game.storage.theme.isPlaying()&&soundonoff){
			game.storage.theme.setVolume(0.5f*(game.keeper.getMusicVolume()/100f));
			game.storage.theme.setLooping(true);
			game.storage.theme.play();
		}
		
		if(game.level.sub.getScore()>100&&game.level.sub.getScore()<=350) {level=1;}
		if(game.level.sub.getScore()>350) {level=2;}
		
		
		if(game.assman.manager.getProgress()==1) {
		 fv.apply(); 
		if(dark){
			 Gdx.gl.glClearColor(0,0,0,1);
		}
		else{
	   Gdx.gl.glClearColor(red,green,blue,1);}
		
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	   if(sub.getDepth()>200) {
		   rayhandler.setAmbientLight(0.1f);
	   }
	   else if(sub.getDepth()>150f&&sub.getDepth()<=200f) {

	   rayhandler.setAmbientLight(-0.008f*sub.getDepth()+1.7f);
	   }
	   else{
	
		rayhandler.setAmbientLight((-0.0033f*sub.getDepth())+1);   
	   }
	 
	   rayhandler.setBlur(true);
	   rayhandler.setBlurNum(3);
	  if(!paused){
	    statetime += Gdx.graphics.getDeltaTime(); 

	  }
	  else{statetime=0f;
	 
	  } 
	  if(sub.getBody().getPosition().y<=camera.position.y-((camera.viewportHeight/2)*0.7f)*game.WORLD_TO_BOX){
			 camera.position.y=sub.getBody().getPosition().y+((camera.viewportHeight/2)*0.7f)*game.WORLD_TO_BOX;
	
		 }
	    if(sub.getBody().getPosition().y>=camera.position.y+((camera.viewportHeight/2)*0.7f)*game.WORLD_TO_BOX){
			 camera.position.y=sub.getBody().getPosition().y-((camera.viewportHeight/2)*0.7f)*game.WORLD_TO_BOX;
	
			
		 }
	
//	  if(Gdx.input.isKeyPressed(Keys.D)&&trashlist.size<3) {
//		  float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
//		  fishes.add(new Trash(game,camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX,y));
//	  }

	    camera.position.x=0;
	    

	   
	    Gdx.gl.glEnable(GL20.GL_BLEND);
	
	    shaperenderer.begin(ShapeType.Filled);
	    shaperenderer.setColor(203f/255f,233f/255f,1,190/255f);
	
	    if(sub.getDepth()<75f) {
	    	//shaperenderer.polygon(vertices);
	    	shaperenderer.rect(camera.position.x-camera.viewportWidth/2,75,camera.viewportWidth,camera.viewportHeight);
	    	
	    }
	 
	 
	   
	   shaperenderer.end();
	    Gdx.gl.glDisable(GL20.GL_BLEND);
	    
	
		    batch.setProjectionMatrix(camera.combined.scl(SubAbyss.WORLD_TO_BOX));
		   
		    rayhandler.setCombinedMatrix(camera.combined);
		    
	    
	    batch.begin();
	    shipsprite.setPosition(camera.position.x-camera.viewportWidth/2-10f, 74f*SubAbyss.BOX_WORLD_TO);
	    shipsprite.translate(0, MathUtils.sin(statetime*1.1f)*10f);
	    shipsprite.draw(batch);
	    sub.draw();
	    equipment.draw();


			for(int i=0;i<trashlist.size;i++) {  
	    	trashlist.get(i).draw();}
	
	    
	 int anglerfishcount=0;
	    for(int i =0;i<fishes.size;i++){
	    	Fish tp =fishes.get(i);
	    	if(tp!=null){
	    		tp.draw();
	    	}
	    	if(tp.getFishType().equals("anglerfish")) {
	    		anglerfishcount++;
	    	}
	    		
	    }
	    fishmanager.setAnglerFishcount(anglerfishcount);
	    for(int i =0;i<sardines.size;i++){
	    	if(sardines.get(i)!=null){
	    		sardines.get(i).draw();
	    	}
	    		
	    }
	   if(squid!=null) squid.draw();
	    for(int i =0;i<mines.size;i++){
	    	
	    for(int l=0;l<20;l++){
	    chainsprite.setPosition(mines.get(i).minebody.getPosition().x*game.BOX_WORLD_TO,(mines.get(i).minebody.getPosition().y*game.BOX_WORLD_TO-chainsprite.getHeight()*2)-chainsprite.getHeight()*l);
		chainsprite.draw(batch);
	    }

	    }
	 
	    for(Trap t:traps){
	    	t.draw();
	    	if( t.getBody().getPosition().y<((camera.position.y-(camera.viewportHeight/2)*game.WORLD_TO_BOX))) {
	    		t.dispose();
	    		
	    		traps.removeValue(t, false);
	    	}
	    }
	    
	   
	    for(int h=0;h<mines.size;h++){
	    	mines.get(h).draw();
	    }
	    
	    
		//EvilSub s = null;
		if(Gdx.input.isKeyPressed(Keys.E)||fishmanager.EvilSub()&&!evilsubexists) {
			evilsubexists=true;
			evilsub=new EvilSub(batch, world, delta, camera, game.assman.manager, game, camera.position.x+(camera.viewportWidth/2)*SubAbyss.WORLD_TO_BOX, sub.getBody().getPosition().y);
			// evilsubs.add(new EvilSub(batch, world, delta, camera, game.assman.manager, game, camera.position.x+(camera.viewportWidth/2)*SubAbyss.WORLD_TO_BOX, sub.getBody().getPosition().y));
			
		}
	
			if(evilsub!=null) {
				evilsub.draw();
				}

		  
	    batch.end();
	    
	    if(trapmanager.Anchor()&&!paused) {
	    	traps.add(new Anchor(game, batch, game.assman.manager, world, MathUtils.random(camera.position.x-camera.viewportWidth/2,camera.position.x+camera.viewportWidth/2)*SubAbyss.WORLD_TO_BOX, camera.position.y+camera.viewportHeight/2*game.WORLD_TO_BOX));
	    }
	    
	   
	    
	    if(placeMine && !paused){
	    	float placex=camera.position.x+(MathUtils.random(((-camera.viewportWidth/2)),(camera.viewportWidth/2))*game.WORLD_TO_BOX);
	    	float placey=camera.position.y-((camera.viewportHeight/2))*game.WORLD_TO_BOX;
	    	mines.add(new Mine(world, batch, mine, chainsprite,placex,placey,statetime,game));	    	
	    	placeMine=false;
	    }
	    
	    alpha=(0f/255f)-sub.getBody().getPosition().y/100;
if(sub.getBody().getPosition().y>0){
	alpha=00/255f;
	
}
if(alpha>229/255f){
	alpha=235/255f;
	dark=true;
}
if(alpha!=229/255f){
	dark=false;
}
if(((camera.position.y*SubAbyss.BOX_WORLD_TO)+(camera.viewportHeight/2))>(75f*SubAbyss.BOX_WORLD_TO)) {
	pl.setActive(true);
	fl.setActive(true);
	fl2.setActive(true);
	fl3.setActive(true);
	fl4.setActive(true);
	pl.setPosition(450f*SubAbyss.WORLD_TO_BOX,camera.position.y+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX);	 

fl.setPosition(450f*SubAbyss.WORLD_TO_BOX,camera.position.y+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX);

fl.setDirection(-115f+MathUtils.sin(statetime)*2f);

fl2.setDirection(-110f+MathUtils.sin(statetime*0.5f)*1.9f);

fl3.setPosition(580f*SubAbyss.WORLD_TO_BOX,camera.position.y+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX);
fl3.setDirection(-100f+MathUtils.sin(statetime*0.7f)*1.9f);

fl4.setPosition(600f*SubAbyss.WORLD_TO_BOX,camera.position.y+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX);
fl4.setDirection(-90f+MathUtils.sin(statetime*0.7f)*1.9f);
}

else if(sub.getDepth()>200) {
	pl.setActive(false);
	fl.setActive(false);
	fl2.setActive(false);
	fl3.setActive(false);
	fl4.setActive(false);
}
else {
	pl.setActive(true);
	fl.setActive(true);
	fl2.setActive(true);
	fl3.setActive(true);
	fl4.setActive(true);
	
	if((camera.position.y*SubAbyss.BOX_WORLD_TO)<=0f) {
		pl.setPosition(450f*SubAbyss.WORLD_TO_BOX,(0f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*1.2f)+MathUtils.sin(statetime)*0.5f);	 
		fl.setPosition(450f*SubAbyss.WORLD_TO_BOX,(0f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);

		fl.setDirection(-115f+MathUtils.sin(statetime)*2f);

		fl2.setPosition(500f*SubAbyss.WORLD_TO_BOX,(0f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
		fl2.setDirection(-110f+MathUtils.sin(statetime*0.5f)*1.9f);

		fl3.setPosition(580f*SubAbyss.WORLD_TO_BOX,(0f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
		fl3.setDirection(-100f+MathUtils.sin(statetime*0.7f)*1.9f);

		fl4.setPosition(600f*SubAbyss.WORLD_TO_BOX,(0f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
		fl4.setDirection(-90f+MathUtils.sin(statetime*0.7f)*1.9f);
	}
	else {
pl.setPosition(450f*SubAbyss.WORLD_TO_BOX,(camera.position.y+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*1.2f)+MathUtils.sin(statetime)*0.5f);	 



fl.setPosition(450f*SubAbyss.WORLD_TO_BOX,(camera.position.y*1.001f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);

fl.setDirection(-115f+MathUtils.sin(statetime)*2f);

fl2.setPosition(500f*SubAbyss.WORLD_TO_BOX,(camera.position.y*1.001f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
fl2.setDirection(-110f+MathUtils.sin(statetime*0.5f)*1.9f);

fl3.setPosition(580f*SubAbyss.WORLD_TO_BOX,(camera.position.y*1.001f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
fl3.setDirection(-100f+MathUtils.sin(statetime*0.7f)*1.9f);

fl4.setPosition(600f*SubAbyss.WORLD_TO_BOX,(camera.position.y*1.001f+((camera.viewportHeight/2))*SubAbyss.WORLD_TO_BOX*2.2f)+MathUtils.sin(statetime)*0.5f);
fl4.setDirection(-90f+MathUtils.sin(statetime*0.7f)*1.9f);
}	    }


	    Gdx.gl.glEnable(GL20.GL_BLEND);
	    shaperenderer.setProjectionMatrix(camera.combined.scl(SubAbyss.BOX_WORLD_TO));

	    shaperenderer.begin(ShapeType.Filled);
	    shaperenderer.setColor(red,green,blue,190/255f);
	

	    if(((camera.position.y*SubAbyss.BOX_WORLD_TO)+(camera.viewportHeight/2))>(75f*SubAbyss.BOX_WORLD_TO)) {
	    	float height2=(((75f*SubAbyss.BOX_WORLD_TO)-(camera.position.y*SubAbyss.BOX_WORLD_TO))+(camera.viewportHeight/2));
	    	 shaperenderer.rect(camera.position.x-((camera.viewportWidth/2)*SubAbyss.WORLD_TO_BOX), camera.position.y-((camera.viewportHeight/2)*SubAbyss.WORLD_TO_BOX), camera.viewportWidth*SubAbyss.WORLD_TO_BOX, height2*SubAbyss.WORLD_TO_BOX);
	    	
	    	 sub.getMenuButton().setColor(Color.LIGHT_GRAY);
	    	 sub.getScoreLabel().setColor(Color.GRAY);
	    	 sub.getHealthLabel().setColor(Color.GRAY);
	

	    }
	    else  {
	    shaperenderer.rect(camera.position.x-(((camera.viewportWidth*1.1f)/2)*SubAbyss.WORLD_TO_BOX), camera.position.y-(((camera.viewportHeight*1.1f)/2)*SubAbyss.WORLD_TO_BOX), camera.viewportWidth*1.1f*SubAbyss.WORLD_TO_BOX, (camera.viewportHeight*1.1f*SubAbyss.WORLD_TO_BOX));
if(!flashon) {
	    sub.getMenuButton().setColor(Color.WHITE);
	    sub.getScoreLabel().setColor(Color.WHITE);
   	 	sub.getHealthLabel().setColor(Color.WHITE);
}
	    
	    }
	   
	    shaperenderer.end();
	    Gdx.gl.glDisable(GL20.GL_BLEND);

	    rayhandler.setCombinedMatrix(camera.combined);
	    rayhandler.updateAndRender(); 
	    
	
	    sub.getStage().act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    sub.getStage().draw();
	    
	    mainstage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    mainstage.draw();
	  
	   
	    if(!paused){ 
	    camera.update();
	    Gdx.graphics.requestRendering();
	    world.step(1.0f/50.0f, 6, 4);
	    }
	    
	    for(int i=0;i<trashlist.size;i++) {
	    	if(trashlist.get(i).getKill()) {
	    		world.destroyBody(trashlist.get(i).getBody());
	    		trashlist.removeIndex(i);
	    	}
	    }

	    
	    for(int p=0;p<fishes.size;p++){
	 Fish tf=fishes.get(p);
	 if(tf.getCaught()==true){
			sub.activateArm(tf);

				}
	    	if(tf!=null && tf.getKill()||(tf.getBody().getPosition().x<camera.position.x-(camera.viewportWidth/2)*game.WORLD_TO_BOX&&!tf.dying)||(tf.getBody().getPosition().y>camera.position.y+(camera.viewportHeight/2)*game.WORLD_TO_BOX*1.25&&!tf.dying)){
	    		if(tf.getKill()){
	    			if(tf.getFishType().equals("marlin")){
	    				marlincount++;
	    				sub.addScore(130);
	    				}
	    			if(tf.getFishType().equals("tuna")){
	    				tunacount++;
	     				sub.addScore(5);
	    				}
	    			if(tf.getFishType().equals("mahimahi")){
	    				mahimahicount++;
	    				sub.addScore(10);
	    				}
	    			if(tf.getFishType().equals("fugu")&&!tf.isExploded()){
	    				fugucount++;
	    				sub.addScore(65);
	    				}
	    			if(tf.getFishType().equals("anglerfish")){
	    				anglerfishcountcatch++;
	    				sub.addScore(140);
	    				}
	    			if(tf.getFishType().equals("blobfish")){
	    				blobfishcount++;
	    				sub.addScore(120);
	    				}
	    			if(tf.getFishType().equals("trash drum")){
	    				flashon=true;
	    				sub.addScore(-50);
	    				Timer.schedule(new Task(){
	    			        @Override
	    			        public void run() {
	    			            sub.getScoreLabel().setColor(Color.RED);
	    			    
	    			            Timer.schedule(new Task(){
	    			                @Override
	    			                public void run() {
	    			                    sub.getScoreLabel().setColor(Color.WHITE);
	    			        
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
	    			    Timer.schedule(new Task(){
			                @Override
			                public void run() {
			                    
			                	flashon=false;
			        
			                }
			            }
			            , 1.1f        //    (delay)
			                //    (seconds)
			        );
	    				}
	    		}
	    		
	    	
	    		world.destroyBody(tf.getBody());
	    		tf.dispose();
	    		fishes.removeIndex(p);
	    	}
	    }
	    
	    for(int i=0;i<sardines.size;i++){
	    	Sardine ts = sardines.get(i);
	    	
	    	if(ts.getCaught()==true){
	    		
	    		sub.activateArm(ts);
	    		for(int j = 0; j<sardines.size;j++){
	    			if(!sardines.get(j).equals(ts)){
	    				sardines.get(j).Scatter();
	    			}
	    		}

	    	}
	    	if(ts.getProjectileHit()==true) {
	    		for(int j = 0; j<sardines.size;j++){
	    			if(!sardines.get(j).equals(ts)){
	    				sardines.get(j).Scatter();
	    			}
	    		}
	    	}
	    	
	    	if(ts!=null && ts.getKill()||ts.getBody().getPosition().x<camera.position.x-(camera.viewportWidth)*SubAbyss.WORLD_TO_BOX){
	    		if(ts.getKill()){
	    			sub.addScore(50);
	    		}
	    		
	    	world.destroyBody(ts.getBody());
	    	ts.dispose();
	    	sardines.removeIndex(i);
	    	}
	    }
	    if(squid!=null) {
	    if(squid.killable) {
	    	if(squid.caught)sub.addScore(500);
	    	world.destroyBody(squid.squidbody);
	    	for (int j=0;j<squid.FRAME_ROWS;j++) {
	    	for(int i=0;i<squid.FRAME_COLS;i++) {
	    	world.destroyBody(squid.tentaclebodies[i][j]);}
	    	}
	    	squid.dispose();
	    	fishmanager.resetSquid();
	    	squid=null;
	    }}

	    	if(evilsub!=null&&evilsub.getKill()) {
	    		world.destroyBody(evilsub.evilbody);
	    		evilsub=null;
	    		evilsubexists=false;
	    		fishmanager.lastevilsub=TimeUtils.millis();
	    	}
	    	for(int i=0;i<torpedos.size;i++) {
	    		if(torpedos.get(i)!=null&&torpedos.get(i).getKill()) {
	    			world.destroyBody(torpedos.get(i).torpedobody);
	    			torpedos.removeIndex(i);
	    		}
	    		torpedos.clear();
	    	}
	    	
	    

	    for(int o=0;o<mines.size;o++){
	    	if(mines.get(o)!=null &&mines.get(o).shoulddie|| mines.get(o).minebody.getPosition().y<camera.position.y-(camera.viewportHeight/2)*SubAbyss.WORLD_TO_BOX){
	    		world.destroyBody(mines.get(o).minebody);
	    		mines.get(o).minebody.setUserData(null);
	    		mines.get(o).minebody=null;
	    		mines.removeIndex(o);
	    			
	    		}}
		sub.getArm().clearProjectiles();

if (Gdx.input.isKeyPressed(Keys.ESCAPE)||Gdx.input.isButtonPressed(Buttons.BACK)||Gdx.input.isKeyPressed(Keys.BACK)){
	if(!paused){    	
	paused=true;}
	
			ingamemenu.showMenu();
		
	    	
	    	
	    	
	    }


//for(int g=0;g<fishes.size;g++){
//	Fish tf=fishes.get(g);
//	if(tf.getCaught()==true){
//		sub.activateArm(tf);
//
//			}
//			}

if(squid!=null&&squid.caught) {
	sub.activateArm(squid);
	
}

//	debugRenderer.render(world, camera.combined);
if(fishmanager.Blobfish()&&!paused) {
	float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
	fishes.add(new Blobfish(batch, world, statetime+MathUtils.random(), game, camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX,y ));
}	

	if(fishmanager.Marlin() && !paused){
		float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
		if(boxtoDepth(y)<200&&boxtoDepth(y)>5) {
		fishes.add(new Fish(batch, world, game,statetime+MathUtils.random(), camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX, y,"marlin"));
		}
	}
	
	if(fishmanager.Shark() && !paused){
		if(sub.getDepth()>2f) {
		fishes.add(new Shark(batch, world, statetime+MathUtils.random(), camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX,sub.getBody().getPosition().y ,null,game));}
		}
	
	if(fishmanager.Fugu()&& !paused){
		float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
		if(boxtoDepth(y)<100&&boxtoDepth(y)>5) {
		fishes.add(new Fugu(batch, world, statetime+MathUtils.random(), game,camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX,y));
		}
	}
	if(fishmanager.AnglerFish()&& !paused){
		float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
	
		fishes.add(new Anglerfish(batch, world, game,statetime+MathUtils.random(), camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX, y,"anglerfish"));
	
	}
	if(fishmanager.Tuna() && !paused){
		float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
		if(boxtoDepth(y)<200&&boxtoDepth(y)>5) {
		fishes.add(new Fish(batch, world,game, statetime+MathUtils.random(), camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX, y,"tuna"));
		}
	}
	if(fishmanager.MahiMahi() && !paused){
		float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
		if(boxtoDepth(y)<80&&boxtoDepth(y)>37) {
		fishes.add(new Fish(batch, world,game, statetime+MathUtils.random(), camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX, y,"mahimahi"));
		
		}}
		if(fishmanager.TrashDrum() && !paused){
			  float y=MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX);
			if(y<(75f)) {
			  fishes.add(new Trash(game,camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX,y));}
	}
	if(fishmanager.Squid()) {
		squid=new Squid(batch, world, statetime, game, camera.position.x+(camera.viewportWidth)*game.WORLD_TO_BOX, MathUtils.random( camera.position.y+((camera.viewportHeight/2)-100f)*game.WORLD_TO_BOX,  camera.position.y-((camera.viewportHeight/2)+100f)*game.WORLD_TO_BOX));
	}

	if(((MathUtils.random(0,1000)==1&&sardines.size==0))&&sub.getDepth()>20&&sub.getDepth()<140f){
		int currentSardinecount=sardines.size;
		while(sardines.size<50){
		if(sardines.size==currentSardinecount){
			
		sardines.add(new Sardine(batch, world, statetime, camera.position.x+(camera.viewportWidth/2)*game.WORLD_TO_BOX, camera.position.y, true, sardine,game));
		sardines.get(currentSardinecount).setLinearVelocity(-1f, MathUtils.sin(statetime*0.7f)*1.1f);
												}
		else{sardines.add(new Sardine(sardines.get(currentSardinecount)));}
	}
	}

		if(sardines.size!=0){
		sardines.get(0).setLinearVelocity(-1f, MathUtils.sin(statetime*0.7f)*1.1f);
		}
		
}
	}

	
private void createCollisionListener() {
    world.setContactListener(new ContactListener() {
    	 
        private long lastanchor;

		@Override
        public void beginContact(Contact contact) {
          Fixture fixtureA = contact.getFixtureA();
         Fixture fixtureB = contact.getFixtureB();
         for(int j=0;j<fishes.size;j++){
         if((fixtureA.getBody().equals(sub.getArm2Body())&& fixtureB.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")&&!sub.getArm().getType().equals("Spud Gun")&&!sub.getArm().getType().equals("Laser Arm")){
        	 fishes.get(j).magnetize=false;
        	
        	 if(!sub.getArm().isArmRunning()&&!dying) {
        	 fishes.get(j).getBody().setLinearVelocity(0, 0);	 
        	 fishes.get(j).setCaught(true);
        	 
        	
        
        	 }
        	 else if((fixtureB.getBody().equals(sub.getArm2Body())&& fixtureA.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")&&!sub.getArm().getType().equals("Spud Gun")&&!sub.getArm().getType().equals("Laser Arm")){
             	 fishes.get(j).magnetize=false; 
             	 
             	 if(!sub.getArm().isArmRunning()&&!dying) {
             	fishes.get(j).getBody().setLinearVelocity(0, 0);	 
             		 
             	 fishes.get(j).setCaught(true);
             
             	 }}
         }
         if((fixtureA.getBody().getUserData().equals("Potato")&& fixtureB.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")){
        	 fishes.get(j).hit(10);
        	 if(soundonoff) {
        	hit.play(.75f);}
        
        	 }
         else if((fixtureB.getBody().getUserData().equals("Potato")&& fixtureA.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")) {
    	 fishes.get(j).hit(10);
    	 if(soundonoff) {
    	 hit.play(.75f);}
         }
         if((fixtureA.getBody().getUserData().equals("Laser")&& fixtureB.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")){
        	 fishes.get(j).hit(10);
        	 if(soundonoff) {
        	 hit.play(.75f);}
        	
        
        	 }
         else if((fixtureB.getBody().getUserData().equals("Laser")&& fixtureA.getBody().equals(fishes.get(j).getBody()))&&!fishes.get(j).getFishType().equals("shark")) {
    	 fishes.get(j).hit(10);
    	 if(soundonoff) {
    	 hit.play(.25f);}
         }

         }
         
         for(int k=0;k<sardines.size;k++) {
         if((fixtureA.getBody().equals(sub.getArm2Body())&&fixtureB.getBody().equals(sardines.get(k).fishbody))&&!sub.getArm().getType().equals("Spud Gun")&&!sub.getArm().getType().equals("Laser Arm")){
        	 sardines.get(k).magnetize=false;
        	if(!sub.getArm().isArmRunning()&&!dying) {
        	 sardines.get(k).getBody().setLinearVelocity(0,0);
        	 sardines.get(k).setCaught(true);
        	
        	}
       
               	 }
         else if((fixtureB.getBody().equals(sub.getArm2Body())&&fixtureA.getBody().equals(sardines.get(k).fishbody))&&!sub.getArm().getType().equals("Spud Gun")&&!sub.getArm().getType().equals("Laser Arm")){
        	 sardines.get(k).magnetize=false;
        	if(!sub.getArm().isArmRunning()&&!dying) {
        	 sardines.get(k).getBody().setLinearVelocity(0,0);
        	 sardines.get(k).setCaught(true);
        	}}
            else if((fixtureB.getBody().getUserData().equals("Potato"))&&fixtureA.getBody().equals(sardines.get(k).getBody())||
            		(fixtureA.getBody().getUserData().equals("Potato"))&&fixtureB.getBody().equals(sardines.get(k).getBody())||
            		(fixtureB.getBody().getUserData().equals("Laser"))&&fixtureA.getBody().equals(sardines.get(k).getBody())||
            		(fixtureA.getBody().getUserData().equals("Laser"))&&fixtureB.getBody().equals(sardines.get(k).getBody())){
           	 sardines.get(k).magnetize=false;
           //	if(!sub.getArm().isArmRunning()) {       
           	 sardines.get(k).setProjectileHit(true);;
          // 	}
       
               	 }
         

         
         
         
         
         
         }
       if(fixtureB.getUserData()!=null) {
         if(fixtureB.getUserData().equals("anchor")&&fixtureA.getBody().equals(sub.getBody())&&TimeUtils.millis()>lastanchor+1000) {
        	 lastanchor=TimeUtils.millis();
        	 sub.hit(20);
        	 if(soundonoff) {
        	 collide.play();}
        	 
        	 if(sub.getHealth()<=0){
         		sub.subhit=true;
         	 sub.sink(); 
         	 sub.getBody().setAngularVelocity(5f);
         	
         	if(!dying) {
         		dying=true;
         	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         			ArrayList<Award> al=game.awardmanager.updateAwards();
         			game.keeper.addCurency(sub.getScore()); 
         		
         			game.keeper.addNumberRuns();
         			game.keeper.addMahiMahiCount(mahimahicount);
         			game.keeper.addMarlinCount(marlincount);
         			game.keeper.addTunaCount(tunacount);
         			game.keeper.addAnglerFishCount(anglerfishcountcatch);
         			game.keeper.addBlobFishCount(blobfishcount);
         			game.keeper.addFuguCount(fugucount);
         			if(sub.getArm().getToolName().equals("Chopstick Arm")) {
         				game.keeper.addChopstickKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(sub.getArm().getToolName().equals("Laser Arm")) {
         				game.keeper.addLaserKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(sub.getArm().getToolName().equals("Spud Gun")) {
         				game.keeper.addSpudGunKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(al.size()>0) {
         			ingamemenu.showAward(al);	
         			game.keeper.writeHighScore(sub.getScore());
         			}
         			else if(game.keeper.writeHighScore(sub.getScore())) {
         				ingamemenu.showMesssage("New High Score!","Score: $"+sub.getScore());
         			}
         			else {
         			ingamemenu.showMesssage("Watch out for anchors!","Score: $"+sub.getScore());}
         			 Timer.schedule(new Task(){
         	                @Override
         	                public void run() {
         	                //	game.keeper.writeHighScore(sub.getScore());
         	                //	game.keeper.addCurency(sub.getScore());       	            
         	                //	game.keeper.addNumberRuns();
         	                //	game.awardmanager.updateAwards();
								try {
         	                	if(game.keeper.getNumberRuns()%4==0){
								game.handler.showAds(true);
									game.handler.showAds(false);
								}}
								catch(Exception e) {
									System.out.println("Ads Unavailable");
								}
         	                	game.level=new Level1(game,menu);
         	                	game.setScreen(game.level);
         	                }
         	            }
         	            , 4f        //    (delay)
         	               //    (seconds)
         	        );
         			
         			}
         			@Override
         			public void cancel() {
         				
         			}
         			}, 2f);
         	 }}
        	 
        	 
        	 
         }
     
       }
       if(evilsub!=null&&fixtureB.getBody().getUserData().equals("evilsub")&&fixtureA.getBody().getUserData().equals("Potato")) {
    	  if(soundonoff) {
    	   hit.play(.75f);}
    	   evilsub.hit(10);
       }
       if(evilsub!=null&&fixtureA.getBody().getUserData().equals("evilsub")&&fixtureB.getBody().getUserData().equals("Potato")) {
    	   if(soundonoff) {
    	   hit.play(.75f);}
    	   evilsub.hit(10);
       }
       if(evilsub!=null&&fixtureB.getBody().getUserData().equals("evilsub")&&fixtureA.getBody().getUserData().equals("Laser")) {
    	   if(soundonoff) {
    	   hit.play(.75f);}
    	   evilsub.hit(20);
       }
       if(evilsub!=null&&fixtureA.getBody().getUserData().equals("evilsub")&&fixtureB.getBody().getUserData().equals("Laser")) {
    	   if(soundonoff) {
    	   hit.play(.75f);}
    	   evilsub.hit(20);
       }
       
       if (evilsub!=null&&evilsub.getTorpedo()!=null&&evilsub.getTorpedo().torpedobody!=null) {
    	   if(fixtureB.getBody().equals(evilsub.getTorpedo().torpedobody)&&fixtureA.getBody().equals(sub.getBody())) {
    		  // boom.play();
    		   evilsub.getTorpedo().explode();
    		  	 explodelight.setPosition(evilsub.getTorpedo().torpedobody.getPosition().x, evilsub.getTorpedo().torpedobody.getPosition().y);
            	 explodelight.setActive(true);
            	 
            	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         		
         				explodelight.setActive(false);
         				
         			
         			}
         			@Override
         			public void cancel() {
         				
         			}
         			}, 0.1f);
            	 sub.subhit=true;
            	 sub.sink();
            	 sub.hit(100);
            	 sub.getBody().setAngularVelocity(5f);
            	 if(soundonoff) {
            	 boom.play();}
            	if(!dying) {
            		dying=true;
            	 Timer.schedule(new Timer.Task() {
            			
            			@Override
            			public void run() {
            				ArrayList<Award> al=game.awardmanager.updateAwards();
                 			game.keeper.addCurency(sub.getScore()); 
                 			game.keeper.addNumberRuns();
                 			game.keeper.addMahiMahiCount(mahimahicount);
                 			game.keeper.addMarlinCount(marlincount);
                 			game.keeper.addTunaCount(tunacount);
                 			game.keeper.addAnglerFishCount(anglerfishcountcatch);
                 			game.keeper.addBlobFishCount(blobfishcount);
                 			game.keeper.addFuguCount(fugucount);
                 			if(sub.getArm().getToolName().equals("Chopstick Arm")) {
                 				game.keeper.addChopstickKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(sub.getArm().getToolName().equals("Laser Arm")) {
                 				game.keeper.addLaserKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(sub.getArm().getToolName().equals("Spud Gun")) {
                 				game.keeper.addSpudGunKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(al.size()>0) {
                 			ingamemenu.showAward(al);	
                 			game.keeper.writeHighScore(sub.getScore());
                 			}
                 			else if(game.keeper.writeHighScore(sub.getScore())) {
                 				ingamemenu.showMesssage("New High Score!","Score: $"+sub.getScore());
                 			}
            				
                 			else {
            			ingamemenu.showMesssage("Torpedo!","Score: $"+sub.getScore());}
            			
            			 Timer.schedule(new Task(){
            	                @Override
            	                public void run() {
									try {
            	                	if(game.keeper.getNumberRuns()%4==0){
										game.handler.showAds(true);}}
									catch(Exception e) {
										System.out.println("Ads Unavailable");
									}
            	                	game.level=new Level1(game,menu);
            	                	game.setScreen(game.level);
            	                }
            	            }
            	            , 4f        //    (delay)
            	               
            	        );
            			}
            			
            			@Override
            			public void cancel() {
            				
            			}
            			}, 2f);
            	
    		  
    	   }}
       }
       
       
       
    if(squid!=null) {
    	   if(fixtureB.getBody().equals(squid.getSquidBody())&&fixtureA.getBody().equals(sub.getArm2Body())&&!fixtureA.getBody().getUserData().equals("Laser")&&!fixtureA.getBody().getUserData().equals("Potato")) {
    		   if(soundonoff) {
    		   collide.play();}
    		   squid.caught=true;
    	   }
   
              if(fixtureA.getUserData()!=null) {
    	   if(fixtureA.getBody().equals(squid.getSquidBody())&&fixtureB.getBody().equals(sub.getArm2Body())&&!fixtureB.getBody().getUserData().equals("Laser")&&!fixtureB.getBody().getUserData().equals("Potato")) {
    		  if(soundonoff) {
    		   collide.play();}
    		   squid.caught=true;
    	   }
              
       }
              
              if((fixtureA.getBody().getUserData().equals("Potato")&& fixtureB.getBody().equals(squid.getSquidBody()))){
            		 squid.hit(10);
             	
             
             	 }
              else if((fixtureB.getBody().getUserData().equals("Potato")&& fixtureA.getBody().equals(squid.getSquidBody()))) {
         	 squid.hit(10);
              }
              if((fixtureA.getBody().getUserData().equals("Laser")&& fixtureB.getBody().equals(squid.getSquidBody()))){
            		 squid.hit(10);
             	
             
             	 }
              else if((fixtureB.getBody().getUserData().equals("Laser")&& fixtureA.getBody().equals(squid.getSquidBody()))) {
            		 squid.hit(10);
              }
    
    
    
    
    
    }
         for(int j=0;j<mines.size;j++){
        	 if(fixtureA.getUserData()!=null&&fixtureB.getUserData()!=null) {
        	 if((fixtureA.getUserData().equals("anchor")||fixtureA.getUserData().equals("mine"))&&(fixtureB.getUserData().equals("mine")||fixtureB.getUserData().equals("anchor"))){
        		 mines.get(j).explode();
            	 explodelight.setPosition(mines.get(j).minebody.getPosition().x, mines.get(j).minebody.getPosition().y);
            	 explodelight.setActive(true);
            	 
            	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         		
         				explodelight.setActive(false);
         				
         			
         			}
         			@Override
         			public void cancel() {
         				
         			}
         			}, 0.1f);
        		if(soundonoff) {
            	 boom.play();}
        		 fixtureA.getBody().setAngularVelocity(20f);
        		 if(fixtureA.getUserData().equals("anchor")) {
        		 fixtureA.getBody().applyForceToCenter(new Vector2(sub.getBody().getPosition().scl(20f)), false);
        		 fixtureA.getBody().setAngularVelocity(20f);
        		 }
        		 else {
        		fixtureB.getBody().applyForceToCenter(new Vector2(sub.getBody().getPosition()).scl(20f), false); 
        		fixtureB.getBody().setAngularVelocity(20f);
        		
        		 }
        	 	}}
        	 
             if((fixtureA.getBody().equals(sub.getBody())||fixtureA.getBody().equals(sub.getArm2Body()))&& fixtureB.getBody().equals(mines.get(j).minebody)){
            	 Mine themine=mines.get(j);
            	
            	 themine.explode();
            	 explodelight.setPosition(themine.minebody.getPosition().x, themine.minebody.getPosition().y);
            	 explodelight.setActive(true);
            	 
            	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         		
         				explodelight.setActive(false);
         				
         			
         			}
         			@Override
         			public void cancel() {
         				
         			}
         			}, 0.1f);
            	 sub.subhit=true;
            	 sub.sink();
            	 sub.hit(100);
            	 sub.getBody().setAngularVelocity(5f);
            	 if(soundonoff) {
            	 boom.play();}
            	if(!dying) {
            		dying=true;
            	 Timer.schedule(new Timer.Task() {
            			
            			@Override
            			public void run() {
            				ArrayList<Award> al=game.awardmanager.updateAwards();
                 			game.keeper.addCurency(sub.getScore()); 
                 			game.keeper.addNumberRuns();
                 			game.keeper.addMahiMahiCount(mahimahicount);
                 			game.keeper.addMarlinCount(marlincount);
                 			game.keeper.addTunaCount(tunacount);
                 			game.keeper.addAnglerFishCount(anglerfishcountcatch);
                 			game.keeper.addBlobFishCount(blobfishcount);
                 			game.keeper.addFuguCount(fugucount);
                 			if(sub.getArm().getToolName().equals("Chopstick Arm")) {
                 				game.keeper.addChopstickKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(sub.getArm().getToolName().equals("Laser Arm")) {
                 				game.keeper.addLaserKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(sub.getArm().getToolName().equals("Spud Gun")) {
                 				game.keeper.addSpudGunKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
                 			}
                 			if(al.size()>0) {
                 			ingamemenu.showAward(al);	
                 			game.keeper.writeHighScore(sub.getScore());
                 			}
                 			else if(game.keeper.writeHighScore(sub.getScore())) {
                 				ingamemenu.showMesssage("New High Score!","Score: $"+sub.getScore());
                 			}
           
                 			else {
                 			ingamemenu.showMesssage("Watch out for mines!","Score: $"+sub.getScore());}
            			
            			 Timer.schedule(new Task(){
            	                @Override
            	                public void run() {
            	                //	game.keeper.writeHighScore(sub.getScore());
            	                //	game.keeper.addCurency(sub.getScore());
            	                //	game.keeper.addNumberRuns();
             	               // 	game.awardmanager.updateAwards();
									try {
            	                	if(game.keeper.getNumberRuns()%4==0){
										game.handler.showAds(true);}}
									catch(Exception e) {
										System.out.println("Ads Unavailable");
									}
            	                	game.level=new Level1(game,menu);
            	                	game.setScreen(game.level);
            	                }
            	            }
            	            , 4f        //    (delay)
            	               
            	        );
            			}
            			
            			@Override
            			public void cancel() {
            				
            			}
            			}, 2f);
            	
 
            	 
             }}
             
             if(fixtureB.getBody().getUserData().equals("Laser")&& fixtureA.getBody().equals(mines.get(j).minebody)) {
            	 Mine themine=mines.get(j);
             	
            	 themine.explode();
            	 explodelight.setPosition(themine.minebody.getPosition().x, themine.minebody.getPosition().y);
            	 explodelight.setActive(true);
            	 
            	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         		
         				explodelight.setActive(false);
         				
         			
         			}
         			@Override
         			public void cancel() {
         				 
         			}
         			}, 0.1f);
            	
            	 if(soundonoff) {
            	 boom.play();}
             }
             if(fixtureA.getBody().getUserData().equals("Laser")&& fixtureB.getBody().equals(mines.get(j).minebody)) {
            	 Mine themine=mines.get(j);
             	
            	 themine.explode();
            	 explodelight.setPosition(themine.minebody.getPosition().x, themine.minebody.getPosition().y);
            	 explodelight.setActive(true);
            	 
            	 Timer.schedule(new Timer.Task() {
         			
         			@Override
         			public void run() {
         		
         				explodelight.setActive(false);
         				
         			
         			}
         			@Override
         			public void cancel() {
         				
         			}
         			}, 0.1f);
            	
            	 if(soundonoff) {
            	 boom.play();}
             }
            
                   	 }
         if(fixtureA.getUserData()!=null) {
         if((fixtureB.getBody().getUserData().equals("Potato")||fixtureB.getBody().getUserData().equals("Laser"))&&fixtureA.getUserData().equals("Shark")) {
  		   fixtureA.getBody().setLinearVelocity(fixtureA.getBody().getLinearVelocity().x*1.2f, MathUtils.random(-3f,-6f));
  	   }
         } 
       if(fixtureB.getUserData()!=null){
    	   if((fixtureA.getBody().getUserData().equals("Potato")||fixtureA.getBody().getUserData().equals("Laser"))&&fixtureB.getUserData().equals("Shark")) {
    		   fixtureB.getBody().setLinearVelocity(fixtureB.getBody().getLinearVelocity().x*1.2f, MathUtils.random(-3f,-6f));
    	   }
    	  
         if((fixtureA.getBody().equals(sub.getBody())||fixtureA.getBody().equals(sub.getArm2Body()))&&fixtureB.getUserData().equals("Shark")&&sharkcollision==false  ){
        	
        	 sharkcollision=true;
        	 fixtureB.getBody().setLinearVelocity(fixtureB.getBody().getLinearVelocity().x*1.2f, MathUtils.random(-3f,3f));
        	 sub.hit(10);
       	 	sub.getBody().applyForceToCenter(fixtureB.getBody().getLinearVelocity().scl(40f),true);
       	  
       	 Timer.schedule(new Task(){
             @Override
             public void run() {
            	 sharkcollision=false;;
             }
         }
         , 5f        
             
     );
        	if(soundonoff) {
         collide.play();}
    	 
    	 if(sub.getHealth()<=0){
    		sub.subhit=true;
    	 sub.sink(); 
    	 sub.getBody().setAngularVelocity(5f);
    	
    	if(!dying) {
    		dying=true;
    	 Timer.schedule(new Timer.Task() {
    			
    			@Override
    			public void run() {
    				
    				ArrayList<Award> al=game.awardmanager.updateAwards();
         			game.keeper.addCurency(sub.getScore()); 
         			game.keeper.addNumberRuns();
         			game.keeper.addMahiMahiCount(mahimahicount);
         			game.keeper.addMarlinCount(marlincount);
         			game.keeper.addTunaCount(tunacount);
         			game.keeper.addAnglerFishCount(anglerfishcountcatch);
         			game.keeper.addBlobFishCount(blobfishcount);
         			game.keeper.addFuguCount(fugucount);
         			if(sub.getArm().getToolName().equals("Chopstick Arm")) {
         				game.keeper.addChopstickKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(sub.getArm().getToolName().equals("Laser Arm")) {
         				game.keeper.addLaserKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(sub.getArm().getToolName().equals("Spud Gun")) {
         				game.keeper.addSpudGunKills(fugucount+marlincount+tunacount+mahimahicount+anglerfishcountcatch);
         			}
         			if(al.size()>0) {
         			ingamemenu.showAward(al);	
         			game.keeper.writeHighScore(sub.getScore());
         			}
         			else if(game.keeper.writeHighScore(sub.getScore())) {
         				ingamemenu.showMesssage("New High Score!","Score: $"+sub.getScore());
         			}
    				else{ingamemenu.showMesssage("Watch out for sharks!","Score: $"+sub.getScore());}
    			//game.keeper.writeHighScore(sub.getScore());	
    		//	ingamemenu.showMesssage("Watch out for sharks!","Score: "+sub.getScore());
    			 Timer.schedule(new Task(){
    	                @Override
    	                public void run() {
    	                	//game.keeper.writeHighScore(sub.getScore());
    	                //	game.keeper.addCurency(sub.getScore());
    	                	//game.keeper.addNumberRuns();
     	                //	game.awardmanager.updateAward();
							try {
    	                	if(game.keeper.getNumberRuns()%4==0){
								game.handler.showAds(true);}}
							catch(Exception e) {
								System.out.println("Ads Unavailable");
							}
    	                	game.level=new Level1(game,menu);
    	                	game.setScreen(game.level);
    	                }
    	            }
    	            , 4f        //    (delay)
    	               //    (seconds)
    	        );
    			
    			}
    			@Override
    			public void cancel() {
    				
    			}
    			}, 2f);
    	 }}}
         
       
       
       }
       
		
		
		
		
		
		
		}

		@Override
		public void endContact(Contact contact) {
			
			
		}

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {
			
			
		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			
			
		}
               	
               	 
               });
                }
      
      

       
       

      
	

	@Override
	public void resize(int width, int height) {
	
	    //  camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
		fv.update(width,height,true);
		sub.getStage().getViewport().update(width, height);
		ingamemenu.stage.getViewport().update(width, height);
	}

	@Override
	public void show() {
		
		Gdx.graphics.setContinuousRendering(false);
	
		camera = new OrthographicCamera();
		
		
	//	camera.position.set(0f, 0f, 0f);
		//camera.position.set(camera.viewportWidth *.5f, camera.viewportHeight * .5f, 0f);
		
		camera.zoom = SubAbyss.WORLD_TO_BOX;
		
		
		float scale=game.keeper.getScale();
		fv=new ExtendViewport(1920/scale,1080/scale,camera);
		camera.viewportHeight=camera.viewportHeight;
		camera.viewportWidth=camera.viewportWidth;
	//	camera.zoom = SubAbyss.WORLD_TO_BOX;
		
		
		
		camera.update();
		
		 rayhandler =new RayHandler(world);
		rayhandler.setCombinedMatrix(camera.combined);
		rayhandler.setShadows(true);
		rayhandler.setAmbientLight(.1f);
		game.assman.loadLevel1Sounds();
		game.assman.manager.finishLoading();
		boom = game.assman.manager.get("data/boom.mp3",Sound.class);
		collide = game.assman.manager.get("data/Underwater Collision.mp3");
		armsound=game.assman.manager.get("data/motor.mp3",Sound.class);
		hit=game.assman.manager.get("data/inflate.mp3",Sound.class);
		
		game.assman.loadLevel1Images();
		game.assman.manager.finishLoading();
		
		mainstage=new Stage();
	
		skin=new Skin();
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		skin.add("default", new BitmapFont());

		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		
		LabelStyle labelstyle = new LabelStyle();
		labelstyle.fontColor =Color.WHITE;
	//	theme=game.assman.manager.get("data/BathyscapeTheme.mp3",Music.class);
		
		skin.add("default", textButtonStyle);
		skin.add("default",labelstyle);

		soundonoff=game.keeper.isSoundOn();
		
		table=new Table();
		table.setFillParent(true);
		mainstage.addActor(table);
			
		minetext=game.assman.manager.get("data/mine.png",Texture.class);
		chaintext=game.assman.manager.get("data/chain.png",Texture.class);
		sardine=game.assman.manager.get("data/sardine.png",Texture.class);
		shiptext=game.assman.manager.get("data/ship.png",Texture.class);
		shipsprite=new Sprite(shiptext);
		
		shiptext.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		mine=new Sprite(minetext);
		chainsprite=new Sprite(chaintext);
		debugRenderer=new Box2DDebugRenderer();
		
		world=new World(GRAVITY,true);
		batch = new SpriteBatch();
		batch.setProjectionMatrix(camera.combined.scl(game.BOX_WORLD_TO));
		sub=game.storage.getChosenSub();
	
		if(sub.getClass().toString().equals("class com.madison.Bathyscape.core.SandwichSubmersible")){
		sub=new SandwichSubmersible(batch,world,statetime,camera, menu.manager, game);
		}
		else if(sub.getClass().toString().equals("class com.madison.Bathyscape.core.AlvinSubmersible")){
			sub=new AlvinSubmersible(batch,world,statetime,camera, menu.manager, game);
		
		}
		else if(sub.getClass().toString().equals("class com.madison.Bathyscape.core.ShinkaiSubmersible")){
			sub=new ShinkaiSubmersible(batch,world,statetime,camera, menu.manager, game);
			
		}
		else if(sub.getClass().toString().equals("class com.madison.Bathyscape.core.MirSubmersible")){
			sub=new MirSubmersible(batch,world,statetime,camera, menu.manager, game);
			
		}
			fishmanager=new FishManager(fishes, sub, game);
			trapmanager = new TrapManager(this);
	
		ingamemenu=new InGameMenu(mainstage,game,sub);
		shaperenderer= new ShapeRenderer();

		rayhandler.setBlurNum(3);
		Color c = new Color(0,0,0,0.9f);
		 fl=new ConeLight(rayhandler,20,c,15f,0f,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX,-100f,4f);
		fl.setXray(true);
		fl.setActive(true);
		
		 fl2=new ConeLight(rayhandler,20,c,15f,0f,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX,-100f,4f);
		fl2.setPosition(150f*SubAbyss.WORLD_TO_BOX,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX);
		fl2.setXray(true);
		fl2.setActive(true);
		
		fl3=new ConeLight(rayhandler, 20,c,15f,0f,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX,-100f,3f);
		fl3.setPosition(630f*SubAbyss.WORLD_TO_BOX,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX);
		fl3.setXray(true);
		fl3.setActive(true);
		
		fl4=new ConeLight(rayhandler, 20,c,15f,0f,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX,-100f,2f);
		fl4.setPosition(630f*SubAbyss.WORLD_TO_BOX,(camera.position.y+camera.viewportHeight)*SubAbyss.WORLD_TO_BOX);
		fl4.setXray(true);
		fl4.setActive(true);	
		pl=new PointLight(rayhandler, 500, Color.WHITE, 10,camera.viewportWidth/4*SubAbyss.WORLD_TO_BOX , camera.viewportHeight*game.WORLD_TO_BOX);
		
		explodelight=new PointLight(rayhandler,20,Color.RED,5,0,0);
		explodelight.setActive(false);
		
		equipment=game.storage.getChosenEquipment();
		if(equipment.getEquipmentName().equals("Santa Hat")) {
			equipment=new Hat("Santa Hat",game);
		}
//		if(theme.isPlaying()) {
//			theme.stop();
//		}
//		if(!theme.isPlaying()&&soundonoff){
//			theme.setVolume(0.5f*(game.keeper.getMusicVolume()/100f));
//			theme.setLooping(true);
//			theme.play();
//		}
Timer.schedule(new Timer.Task() {
	
	@Override
	public void run() {
		
	if(!(sub.getBody().getPosition().y<=camera.position.y-(camera.viewportHeight/2*0.7f)*game.WORLD_TO_BOX)&&sub.getBody().getLinearVelocity().y<0&&sub.getDepth()>5f) {	
	placeMine=true;}
	
	}
	@Override
	public void cancel() {
		
	}
	}, 0.1f,9.5f);
	
		camera.position.y=75f;
		createCollisionListener();
		camera.position.x=0;
	}

	@Override
	public void hide() {
		
		dispose();
	}

	@Override
	public void pause() {
		
		
	}

	@Override
	public void resume() {
		
		
	}

	@Override
	public void dispose() {
		//boom.dispose();
	//	collide.dispose();
		sub.dispose();
		rayhandler.dispose();
		shaperenderer.dispose();
		bubbles.clear();
		fishes.clear();
		traps.clear();
		menu.manager.clear();
	//	menu.manager.update();
		
	}
	
private float boxtoDepth(float boxy) {
	return (boxy*-2)+150;
}
	

}
