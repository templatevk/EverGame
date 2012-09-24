package pp.game.activities;

import org.andengine.engine.camera.*;
import org.andengine.engine.options.*;
import org.andengine.engine.options.resolutionpolicy.*;
import org.andengine.entity.scene.*;
import org.andengine.entity.scene.background.*;
import org.andengine.entity.sprite.*;
import org.andengine.opengl.texture.atlas.bitmap.*;
import org.andengine.opengl.texture.region.*;
import org.andengine.ui.activity.*;

import pp.game.gameplay.*;
import android.view.*;

public class GameActivity extends BaseGameActivity {
	private int atlasWidth;
	private int atlasHeight;
	private int displayWidth;
	private int displayHeight;
	
	private Scene scene;
	private BitmapTextureAtlas atlas;
	private TextureRegion backgroundTexture;
	private AbstractLevelFactory levelFactory;
	
	@Override
	public void onPopulateScene(Scene scene, OnPopulateSceneCallback populateSceneCallback)
			throws Exception {
		populateSceneCallback.onPopulateSceneFinished();
	}
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		Display display = getWindowManager().getDefaultDisplay();
		displayWidth = display.getWidth();
		displayHeight = display.getHeight();
		atlasWidth = displayWidth * 3;
		atlasHeight = displayHeight * 3;
		levelFactory = AbstractLevelFactory.getLevel("Survival");
		
		Camera camera = new Camera(0, 0, displayWidth, displayHeight);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(displayWidth, displayHeight), camera);
	}
	
	@Override
	public void onCreateResources(OnCreateResourcesCallback createResourcesCallback)
			throws Exception {		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("assets/textures/");
		
		atlas = new BitmapTextureAtlas(getTextureManager(), atlasWidth, atlasHeight);
		backgroundTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				atlas, this, levelFactory.getBackgroundPicture(), 0, 0);
		atlas.load();
		
		createResourcesCallback.onCreateResourcesFinished();
	}
	
	@Override
	public void onCreateScene(OnCreateSceneCallback createSceneCallback) 
			throws Exception {
		SpriteBackground background = new SpriteBackground(new Sprite(
				backgroundTexture.getWidth() / 2, backgroundTexture.getHeight() / 2,
				backgroundTexture, getVertexBufferObjectManager()));
		
		scene = new Scene();
		scene.setBackground(background);
		createSceneCallback.onCreateSceneFinished(scene);
	}
}
