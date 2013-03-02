package pp.game.audio;

import java.io.*;
import java.util.*;

import org.andengine.audio.music.*;
import org.andengine.audio.sound.*;
import org.andengine.ui.activity.*;

import pp.game.*;

public class AudioHolder implements IDestroyable {
	private static final float WEAPON_RELOAD_RATE		= 1.7f;
	private static final float MUSIC_VOLUME 			= 1f;
	private static final float PLAYER_HIT_VOLUME 		= 0.45f;
	
	private static final AudioHolder INSTANCE = new AudioHolder();
	
	private Map<MenuMusicType, Music> menuMusic;
	private Map<GameMusicType, Music> gameMusic;
	private Map<EntityHitSoundType, Sound> entityHitSound;
	private Map<WeaponShotSoundType, Sound> weaponShootSound;
	private Map<WeaponReloadSoundType, Sound> weaponReloadSound;
	private Map<SoundType, Sound> sounds;
	private Music previousMusic;
	
	private AudioHolder() {
		Game.getGameInstance().addDestroyable(this);
	}
	
	public void initialize() {
		previousMusic = null;
		menuMusic = new HashMap<MenuMusicType, Music>();
		gameMusic = new HashMap<GameMusicType, Music>();
		entityHitSound = new HashMap<EntityHitSoundType, Sound>();
		weaponShootSound = new HashMap<WeaponShotSoundType, Sound>();
		weaponReloadSound = new HashMap<WeaponReloadSoundType, Sound>();
		sounds = new HashMap<SoundType, Sound>();
		
		BaseGameActivity context = Game.getGameActivity();
		SoundManager soundManager = context.getEngine().getSoundManager();
		MusicManager musicManager = context.getEngine().getMusicManager();
		
		try {
			Sound sound;
			Music music;
			for (MenuMusicType value : MenuMusicType.values()) {
				menuMusic.put(value, music = MusicFactory.createMusicFromAsset(
						musicManager, context, value.getAssetPath()));
				music.setVolume(MUSIC_VOLUME);
			}
			
			for (GameMusicType value : GameMusicType.values()) {
				gameMusic.put(value, music = MusicFactory.createMusicFromAsset(
						musicManager, context, value.getAssetPath()));
				music.setVolume(MUSIC_VOLUME);
			}
			
			for (EntityHitSoundType value : EntityHitSoundType.values()) {
				entityHitSound.put(value, sound = SoundFactory.createSoundFromAsset(
						soundManager, context, value.getAssetPath()));
				if (value.toString().startsWith("PLAYER")) {
					sound.setVolume(PLAYER_HIT_VOLUME);
				}
			}
			
			for (WeaponShotSoundType value : WeaponShotSoundType.values()) {
				weaponShootSound.put(value, SoundFactory.createSoundFromAsset(
						soundManager, context, value.getAssetPath()));
			}
			
			for (WeaponReloadSoundType value : WeaponReloadSoundType.values()) {
				weaponReloadSound.put(value, sound = SoundFactory.createSoundFromAsset(
						soundManager, context, value.getAssetPath()));
				sound.setRate(WEAPON_RELOAD_RATE);
			}
			
			for (SoundType value : SoundType.values()) {
				sounds.put(value, SoundFactory.createSoundFromAsset(
						soundManager, context, value.getAssetPath()));
			}
		} catch (IOException e) {
			throw new RuntimeException(
					"Полный пиздец, ибо звуки и музыка нихуя не загрузились.", e);
		}
	}
	
	public static AudioHolder getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Priority getPriority() {
		return Priority.LOW;
	}
	
	@Override
	public void destroy() {
		if (previousMusic.isReleased()) {
			previousMusic.stop();
		}
//		for (Music value : menuMusic.values()) {
//			value.release();
//		}
//		
//		for (Music value : gameMusic.values()) {
//			value.release();
//		}
//		
//		for (Sound value : entityHitSound.values()) {
//			value.release();
//		}
//		
//		for (Sound value : weaponShootSound.values()) {
//			value.release();
//		}
//		
//		for (Sound value : weaponReloadSound.values()) {
//			value.release();
//		}
//		
//		for (Sound value : sounds.values()) {
//			value.release();
//		}
	}
	
	private void playMusic(Music music, boolean resume) {
		if (previousMusic != null && !previousMusic.isReleased()) {
			previousMusic.pause();
		}		
		previousMusic = music;
		
		music.setLooping(true); 
		if (!resume) {
			music.seekTo(0);
		}
		music.play();	
	}
	
	public void playMenuMusic(MenuMusicType type) {
		playMusic(menuMusic.get(type), false);
	}
	
	public void playGameMusic(GameMusicType type) {
		playMusic(gameMusic.get(type), false);
	}
	
	public void playGameMusic(GameMusicType type, boolean resume) {
		playMusic(gameMusic.get(type), resume);
	}
	
	public void playEntityHitSound(EntityHitSoundType type) {
		entityHitSound.get(type).play();
	}
	
	public void playWeaponShotSound(WeaponShotSoundType type) {
		weaponShootSound.get(type).play();
	}
	
	public void playWeaponReloadSound(WeaponReloadSoundType type) {
		weaponReloadSound.get(type).play();
	}
	
	public void playSound(SoundType type) {
		sounds.get(type).play();
	}
}
