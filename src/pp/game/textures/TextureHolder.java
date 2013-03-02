package pp.game.textures;

import java.util.*;

import org.andengine.opengl.texture.*;
import org.andengine.opengl.texture.atlas.bitmap.*;
import org.andengine.opengl.texture.atlas.bitmap.source.*;
import org.andengine.opengl.texture.atlas.buildable.builder.*;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.*;

import pp.game.*;
import pp.game.utils.geometry.*;
import android.content.res.*;

public class TextureHolder implements IDestroyable {
	private static final TextureHolder INSTANCE = new TextureHolder();
	
	private HashMap<SingleTiledTextureType, ITiledTextureRegion> singleTiledTextures;
	private HashMap<MonsterWalkTextureType, ITiledTextureRegion> monsterWalkTiledTextures;
	private HashMap<MonsterDeathTextureType, ITiledTextureRegion> monsterDeathTiledTextures;

	private HashMap<WeaponTextureType, ITextureRegion> weaponTextures;
	private HashMap<PlayerControlTextureType, ITextureRegion> playerControlTextures;
	private HashMap<MenuTextureType, ITextureRegion> menuTextures;
	private HashMap<SingleTextureType, ITextureRegion> singleTextures;
	private HashMap<BonusTextureType, ITextureRegion> bonusTextures;

	private HashMap<BackgroundTextureType, AssetBitmapTextureAtlasSource> backgroundTextures;
	
	private BuildableBitmapTextureAtlas atlas; 
	
	private List<ITextureRegion> texturesToUnload = new ArrayList<ITextureRegion>();
	
	private TextureHolder() {
		int atlasSize = SceneLayoutUtils.IS_HD ? 4096 : 2048; 
		TextureManager textureManager = Game.getGameActivity().getTextureManager();
		AssetManager assets = Game.getGameActivity().getAssets();		
		atlas = new BuildableBitmapTextureAtlas(textureManager, 
				atlasSize, atlasSize, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		singleTiledTextures = new HashMap<SingleTiledTextureType, ITiledTextureRegion>();
		for (SingleTiledTextureType value : SingleTiledTextureType.values()) {
			singleTiledTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(atlas, assets, value.getAssetPath(), 
							value.getColumns(), value.getRows()));
		}
		
		monsterWalkTiledTextures = new HashMap<MonsterWalkTextureType, ITiledTextureRegion>();
		for (MonsterWalkTextureType value : MonsterWalkTextureType.values()) {
			monsterWalkTiledTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(atlas, assets, value.getAssetPath(), 
							value.getColumns(), value.getRows()));
		}
		
		monsterDeathTiledTextures = new HashMap<MonsterDeathTextureType, ITiledTextureRegion>();
		for (MonsterDeathTextureType value : MonsterDeathTextureType.values()) {
			monsterDeathTiledTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createTiledFromAsset(atlas, assets, value.getAssetPath(), 
							value.getColumns(), value.getRows()));
		}
		
		weaponTextures = new HashMap<WeaponTextureType, ITextureRegion>();
		for (WeaponTextureType value : WeaponTextureType.values()) {
			weaponTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, assets, value.getAssetPath()));
		}
		
		playerControlTextures = new HashMap<PlayerControlTextureType, ITextureRegion>();
		for (PlayerControlTextureType value : PlayerControlTextureType.values()) {
			playerControlTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, assets, value.getAssetPath()));
		}
		
		backgroundTextures = new HashMap<BackgroundTextureType, AssetBitmapTextureAtlasSource>();
		for (BackgroundTextureType value : BackgroundTextureType.values()) {
			backgroundTextures.put(value, AssetBitmapTextureAtlasSource.create(
					assets, value.getAssetPath()));
		}
		
		menuTextures = new HashMap<MenuTextureType, ITextureRegion>();
		for (MenuTextureType value : MenuTextureType.values()) {
			menuTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, assets, value.getAssetPath()));
		}
		
		singleTextures = new HashMap<SingleTextureType, ITextureRegion>();
		for (SingleTextureType value : SingleTextureType.values()) {
			singleTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, assets, value.getAssetPath()));
		}
		
		bonusTextures = new HashMap<BonusTextureType, ITextureRegion>();
		for (BonusTextureType value : BonusTextureType.values()) {
			bonusTextures.put(value, BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(atlas, assets, value.getAssetPath()));
		}
		
		try {
			atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
					0, 1, 1));
		} catch (TextureAtlasBuilderException e) {
			throw new RuntimeException(
					"Полный пиздец, ибо текстуры нихуя не загрузились.", e);
		}
		atlas.load();
		
		Game.getGameInstance().addDestroyable(this);
	}
	
	public static TextureHolder getInstance() {
		return INSTANCE;
	}
	
	public AssetBitmapTextureAtlasSource getTexture(BackgroundTextureType type) {
		return backgroundTextures.get(type);
	}
	
	public ITextureRegion getTexture(WeaponTextureType type) {
		return weaponTextures.get(type);
	}
	
	public ITextureRegion getTexture(BonusTextureType type) {
		return bonusTextures.get(type);
	}
	
	public ITextureRegion getTexture(PlayerControlTextureType type) {
		return playerControlTextures.get(type);
	}
	
	public ITextureRegion getTexture(MenuTextureType type) {
		return menuTextures.get(type);
	}
	
	public ITextureRegion getTexture(SingleTextureType type) {
		return singleTextures.get(type);
	}
	
	public ITiledTextureRegion getTiledTexture(MonsterWalkTextureType type) {
		return monsterWalkTiledTextures.get(type);
	}
	
	public ITiledTextureRegion getTiledTexture(MonsterDeathTextureType type) {
		return monsterDeathTiledTextures.get(type);
	}
	
	public ITiledTextureRegion getTiledTexture(SingleTiledTextureType type) {
		return singleTiledTextures.get(type);
	}
	
	public void addTextureToUnload(ITextureRegion texture) {
		texturesToUnload.add(texture);
	}
	
	@Override
	public void destroy() {
//		TextureManager manager = Game.getGameActivity().getTextureManager();
//		
//		for (ITextureRegion texture : singleTiledTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : monsterWalkTiledTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : monsterDeathTiledTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}
//		for (ITextureRegion texture : weaponTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : playerControlTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : menuTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : singleTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		for (ITextureRegion texture : bonusTextures.values()) {
//			manager.unloadTexture(texture.getTexture());
//		}
//		for (ITextureRegion texture : texturesToUnload) {
//			manager.unloadTexture(texture.getTexture());
//		}		
//		
//		atlas.unload();	
	}
	
	@Override
	public Priority getPriority() {
		return Priority.MEDIUM;
	}
}
