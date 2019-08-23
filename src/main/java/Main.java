import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.czareg.ThumbprintFixerUI;
import com.czareg.model.ProxyData;
import com.czareg.utils.PropertiesHandler;
import com.czareg.utils.ThumbprintGetter;
import com.czareg.utils.ThumbprintMaker;

public class Main {
	static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		switch (args.length) {
		case 0:
			LOG.info("Starting UI");
			ThumbprintFixerUI.main();
			break;
		case 1:
			System.out.println(makeThumbprint(args[0], null));
			break;
		case 2:
			printHelp();
			break;
		case 3:
			System.out.println(makeThumbprint(args[0], new ProxyData(args[1], args[2])));
			break;
		default:
			printHelp();
			break;
		}
	}

	private static void printHelp() {
		System.out.println("Pass 1 parameter to make thumbprint without proxy (java -jar google.com)");
		System.out.println(
				"Pass 3 parameters to make thumbprint without proxy (java -jar google.com proxyServer proxyPort)");
	}

	private static String makeThumbprint(String userInput, ProxyData proxyData) {
		String thumbprint = userInput;
		try {
			if (proxyData != null) {
				PropertiesHandler.writeProxyData(proxyData);
			}
			thumbprint = ThumbprintGetter.get(userInput);
		} catch (Exception e) {
			LOG.error("Could not create thumbprint for input: " + userInput, e);
		}
		return ThumbprintMaker.make(thumbprint);
	}
}