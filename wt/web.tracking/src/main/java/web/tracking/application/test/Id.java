package web.tracking.application.test;

import java.util.HashMap;
import java.util.Map;

public class Id {
	public static void main(String[] args) {
		Id id = new Id();

		Map<Id, String> map = new HashMap<Id, String>();

		map.put(id, "Test");

		System.out.println(map.get(id));
	}
}
