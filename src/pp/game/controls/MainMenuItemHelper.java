package pp.game.controls;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;

class MainMenuItemHelper extends TextView {
	private List<DrawCommand> commands;
	
	public MainMenuItemHelper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		for (DrawCommand command : commands) {
			command.draw(canvas, getPaint());
			super.onDraw(canvas);
		}
	}
	
	public void setDrawCommands(List<DrawCommand> commands) {
		this.commands = commands;
	}
}