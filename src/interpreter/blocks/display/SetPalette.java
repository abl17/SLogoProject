package interpreter.blocks.display;

import java.util.Deque;

import interpreter.IProjectManager;
import interpreter.blocks.FourArgBlock;
import interpreter.blocks.RootBlock;
import interpreter.exceptions.blocks.BlockException;
import interpreter.exceptions.blocks.BlockNumberException;
import javafx.scene.paint.Color;

public class SetPalette extends FourArgBlock {

	public SetPalette(Deque<RootBlock> queue) {
		super(queue);
	}

	@Override
	public double evaluate(IProjectManager pm) throws BlockException {
		int index = (int) getFirstArgument().evaluate(pm);
		int r = (int) getSecondArgument().evaluate(pm);
		int g = (int) getThirdArgument().evaluate(pm);
		int b = (int )getFourthArgument().evaluate(pm);
		
		if (r > 255 || r < 0 ||
				g > 255 || g < 0 ||
				b > 255 || b < 0) {
			throw new BlockNumberException("Incorrect Color Number format", "Color Number format");
		} else {
			Color customColor = Color.rgb(r, g, b);
			pm.setCustomColor(index, customColor);
		}
		return index;
	}

	@Override
	public void print() {
		
	}

}
