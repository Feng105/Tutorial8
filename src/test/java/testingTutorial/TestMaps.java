package testingTutorial;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class TestMaps {

	private Student key1;
	private Student key2;
	private final String value = "159.251";

	@BeforeEach
	public void setUp() {
		// equal keys, but two different references
		key1 = new Student(25014435, "Puze", "Hou");
		key2 = new Student(25014435, "Puze", "Hou");
	}

	@Test
	public void keysAreEqualButNotIdentical() {
		assertNotSame(key1, key2);
		assertEquals(key1, key2);
	}

	@Test
	public void hashMapFindsValueWithEqualKey() {
		Map<Student, String> map = new HashMap<>();
		map.put(key1, value);
		assertEquals(value, map.get(key2));
	}

	@Test
	public void identityHashMapFindsValueWithEqualKey() {
		Map<Student, String> map = new IdentityHashMap<>();
		map.put(key1, value);
		assertEquals(value, map.get(key2));
	}

	@Test
	public void identityHashMapFindsValueWithIdenticalKey() {
		Map<Student, String> map = new IdentityHashMap<>();
		map.put(key1, value);
		assertEquals(value, map.get(key1));
	}
}
