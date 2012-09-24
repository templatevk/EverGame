package pp.game.controls;

import java.util.*;

import android.content.*;
import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.text.*;
import android.util.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainMenuItem extends FrameLayout {
	private static final int FIRST_SHADOW_RADIUS = 4;
	private static final int SECOND_SHADOW_RADIUS = 12;
	private static final float SECOND_LAYER_PADDING = 2.5f;
	
	private MainMenuItemHelper firstLayerItem;
	private MainMenuItemHelper secondLayerItem;
	
	public MainMenuItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		int padding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
				SECOND_LAYER_PADDING, getResources().getDisplayMetrics());
		
		secondLayerItem = new MainMenuItemHelper(context, attrs);
		secondLayerItem.setPadding(padding, padding, 0, 0);
		secondLayerItem.setDrawCommands(Arrays.asList(new DrawCommand[] {
				new DrawCommand() {
					private int darkGreen = Color.parseColor("#006400");
					
					@Override
					public void draw(Canvas canvas, TextPaint paint) {
						paint.setShadowLayer(SECOND_SHADOW_RADIUS, 0, 0, darkGreen);
						paint.setShader(null);
					}
				},				
				new DrawCommand() {
					private Shader black = new LinearGradient(0, 0, 1, 1,
							new int[] { Color.BLACK, Color.BLACK },
							new float[] { 0.3f, 0.6f }, TileMode.MIRROR);
					
					@Override
					public void draw(Canvas canvas, TextPaint paint) {
						paint.clearShadowLayer();
						paint.setShader(black);
					}
				}
		}));
		
		firstLayerItem = new MainMenuItemHelper(context, attrs);
		firstLayerItem.setDrawCommands(Arrays.asList(new DrawCommand[] { 
				new DrawCommand() {
					private int darkGreen = Color.parseColor("#006400");
					
					@Override
					public void draw(Canvas canvas, TextPaint paint) {
						paint.setShadowLayer(FIRST_SHADOW_RADIUS, 0, 0, darkGreen);
						paint.setShader(null);
					}
				},
				new DrawCommand() {
					private Shader red = new LinearGradient(0, 0, 30, 30,
							new int[] { Color.parseColor("#DD0000"),
								Color.parseColor("#C90000"),
								Color.parseColor("#7A0000")
							}, new float[] { 0.25f, 0.5f, 0.75f }, 
							TileMode.MIRROR);
					
					@Override
					public void draw(Canvas canvas, TextPaint paint) {
						paint.clearShadowLayer();
						paint.setShader(red);
					}
				}
		}));
		addView(secondLayerItem);
		addView(firstLayerItem);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		secondLayerItem.draw(canvas);
		firstLayerItem.draw(canvas);
	}
}
