import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czareg.ThumbprintFixerUI;
import com.czareg.utils.ThumbprintGetter;
import com.czareg.utils.ThumbprintMaker;

public class Main {
	static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		if (args.length == 0) {
			ThumbprintFixerUI.main();
		} else {
			System.out.println(makeThumbprint(args[0]));
		}
	}

	private static String makeThumbprint(String userInput) {
		String thumbprint = userInput;
		try {
			thumbprint = ThumbprintGetter.get(userInput);
		} catch (Exception e) {
			LOG.error("Could not get thumbprint from: " + userInput, e);
		}
		return ThumbprintMaker.make(thumbprint);
	}
}