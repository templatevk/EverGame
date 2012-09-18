package pp.game.controls;

import android.content.*;
import android.graphics.*;
import android.graphics.Shader.*;
import android.util.*;
import android.widget.*;

/**
 * Represents main menu items. Provides a TextView with hardcoded
 * gradient foreground and shadow.
 */
public class CustomTextView extends TextView {
	private static final int DARK_GREEN = Color.parseColor("#006400");
	private static final Shader redForeground = new LinearGradient(0, 0, 30, 30,
			new int[] { 
				Color.parseColor("#DD0000"),
				Color.parseColor("#C90000"),
				Color.parseColor("#7A0000")
			}, new float[] { 0.25f, 0.5f, 0.75f }, TileMode.MIRROR);	
	
	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		getPaint().setShadowLayer(44, 0, 0, DARK_GREEN);
		getPaint().setShader(null);
		super.onDraw(canvas);
		
		getPaint().clearShadowLayer();
		getPaint().setShader(redForeground);
		super.onDraw(canvas);
	}
}
