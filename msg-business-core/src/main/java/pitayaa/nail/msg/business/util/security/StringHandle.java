package pitayaa.nail.msg.business.util.security;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringHandle {
	public static List<String> cutName(String name) {
		List<String> list = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(name, " ");
		list.add(token.nextToken());
		list.add(token.toString());
		return list;
	};

}
