package testingTutorial;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestUnmodfiableLists {

	private List<String> unmodifiableList;

	@BeforeEach
	public void setUp() {
		List<String> modifiableList = new ArrayList<>(Arrays.asList("testing", "junit", "159.251"));
		unmodifiableList = Collections.unmodifiableList(modifiableList);
	}

	@Test
	public void addThrowsUnsupportedOperationException() {
		assertThrows(UnsupportedOperationException.class, () -> unmodifiableList.add("extra"));
	}

	@Test
	public void unmodifiableListCanStillBeIterated() {
		assumeTrue(unmodifiableList instanceof Iterable);
		List<String> visited = new ArrayList<>();
		for (String element : unmodifiableList) {
			visited.add(element);
		}
		assertEquals(Arrays.asList("testing", "junit", "159.251"), visited);
	}
}
