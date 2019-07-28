import com.czareg.ThumbprintFixerUI;
import com.czareg.model.StringFormat;
import com.czareg.utils.ThumbprintMaker;

public class Main {
	public static void main(String[] args) {
		switch (args.length) {
		case 0:
			ThumbprintFixerUI.main();
			break;
		case 1:
			System.out.println(ThumbprintMaker.make(args[0]));
			break;
		default:
			if (args[1].contains("low") || args[1].contains("l")) {
				ThumbprintMaker.format = StringFormat.LOWERCASE;
			} else if (args[1].contains("mix") || args[1].contains("m")) {
				ThumbprintMaker.format = StringFormat.MIXEDCASE;
			} else {
				ThumbprintMaker.format = StringFormat.UPPERCASE;
			}
			System.out.println(ThumbprintMaker.make(args[0]));
			break;
		}
	}
}