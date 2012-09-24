package pp.game.activities;

import pp.game.*;
import android.app.*;
import android.os.*;
import android.view.*;

public class MainMenuActivity extends Activity {
	public MainMenuActivity() {
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu_activity);
	}
	
	public void onNewGameClicked(View view) {
		System.out.println("Hello");
	}
}
