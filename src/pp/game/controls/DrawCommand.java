package pp.game.controls;

import android.graphics.*;
import android.text.*;

interface DrawCommand {
	void draw(Canvas canvas, TextPaint paint);
}
