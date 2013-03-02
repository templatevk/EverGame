package pp.game.scene;

import org.andengine.entity.scene.background.*;
import org.andengine.entity.scene.menu.*;
import org.andengine.entity.scene.menu.item.*;
import org.andengine.entity.sprite.*;
import org.andengine.opengl.vbo.*;
import org.andengine.util.debug.*;

import pp.game.*;
import pp.game.textures.*;
import pp.game.utils.geometry.*;

class MainMenuScene extends MenuScene {
	private static final int MAIN_MENU_ITEMS_COUNT = 3;
	
	public MainMenuScene(IOnMenuItemClickListener listener) {
		super(Game.getGameActivity().getEngine().getCamera(), listener);
		
		TextureHolder holder = TextureHolder.getInstance();
		VertexBufferObjectManager manager = Game.getGameActivity().getVertexBufferObjectManager();
		SpriteMenuItem item;

		setBackground(new SpriteBackground(SceneLayoutUtils.adjustSpriteToScreenSize(
				new Sprite(0, 0, holder.getTexture(MenuTextureType.MAIN_MENU_BACKGROUND),
				manager))));
		addMenuItem(item = new SpriteMenuItem(MainScene.MAIN_MENU_NEW_GAME,
				holder.getTexture(MenuTextureType.MAIN_MENU_NEW_GAME),
				manager));
		SceneLayoutUtils.adjustToCenteredListItem(item, 0, MAIN_MENU_ITEMS_COUNT);
		addMenuItem(item = new SpriteMenuItem(MainScene.MAIN_MENU_HIGH_SCORES,
				holder.getTexture(MenuTextureType.MAIN_MENU_HIGH_SCORES),
				manager));
		SceneLayoutUtils.adjustToCenteredListItem(item, 1, MAIN_MENU_ITEMS_COUNT);
		addMenuItem(item = new SpriteMenuItem(MainScene.MAIN_MENU_EXIT,
				holder.getTexture(MenuTextureType.MAIN_MENU_EXIT),
				manager));
		SceneLayoutUtils.adjustToCenteredListItem(item, 2, MAIN_MENU_ITEMS_COUNT);
	}
	
	@Override
	public void back() {
		
	}
}
