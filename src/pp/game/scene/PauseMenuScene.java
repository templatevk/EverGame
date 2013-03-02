package pp.game.scene;

import org.andengine.entity.scene.background.*;
import org.andengine.entity.scene.menu.*;
import org.andengine.entity.scene.menu.item.*;
import org.andengine.entity.sprite.*;
import org.andengine.opengl.vbo.*;

import pp.game.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;

class PauseMenuScene extends MenuScene {
	private static final int PAUSE_MENU_ITEMS_COUNT = 2;
	
	public PauseMenuScene(IOnMenuItemClickListener listener) {
		super(Game.getGameActivity().getEngine().getCamera(), listener);
		
		TextureHolder holder = TextureHolder.getInstance();
		VertexBufferObjectManager manager = Game.getGameActivity().getVertexBufferObjectManager();
		SpriteMenuItem item;
		
		setBackground(new SpriteBackground(SceneLayoutUtils.adjustSpriteToScreenSize(
				new Sprite(0, 0, holder.getTexture(MenuTextureType.MAIN_MENU_BACKGROUND),
				manager))));
		addMenuItem(item = new SpriteMenuItem(MainScene.PAUSE_MENU_RESUME, 
				holder.getTexture(MenuTextureType.PAUSE_MENU_RESUME), manager));
		SceneLayoutUtils.adjustToCenteredListItem(item, 0, PAUSE_MENU_ITEMS_COUNT);
		addMenuItem(item = new SpriteMenuItem(MainScene.PAUSE_MENU_MAIN_MENU, 
				holder.getTexture(MenuTextureType.PAUSE_MENU_MAIN_MENU), manager));
		SceneLayoutUtils.adjustToCenteredListItem(item, 1, PAUSE_MENU_ITEMS_COUNT);
	}
	
	@Override
	public void back() {
		
	}
}
