import com.czareg.ThumbprintFixerUI;
import com.czareg.utils.ThumbprintMaker;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) {
			ThumbprintFixerUI.main(args);
		} else {
			System.out.println(ThumbprintMaker.make(args[0]));
		}
	}
}