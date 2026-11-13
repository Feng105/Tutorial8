package testingTutorial;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

public class TestEqualsAndHashcode {

	private Lecturer lecturer1;
	private Lecturer lecturer2;
	private Student student1;
	private Student student2;

	@BeforeEach
	public void setUp() {
		// same id and first name, different last name
		lecturer1 = new Lecturer(1, "Alan", "Turing");
		lecturer2 = new Lecturer(1, "Alan", "Kay");

		// two distinct objects holding the same state
		student1 = new Student(25014435, "Puze", "Hou");
		student2 = new Student(25014435, "Puze", "Hou");
	}

	@Test
	public void lecturerEqualsIgnoresLastName() {
		// passing test: documents the root cause, equals() never compares name
		assertEquals(lecturer1, lecturer2);
	}

	@Test
	public void lecturerEqualsImpliesSameHashCode() {
		assumeTrue(lecturer1.equals(lecturer2));
		assertEquals(lecturer1.hashCode(), lecturer2.hashCode());
	}

	@Test
	public void studentEqualsImpliesSameHashCode() {
		assumeTrue(student1.equals(student2));
		assertEquals(student1.hashCode(), student2.hashCode());
	}
}
