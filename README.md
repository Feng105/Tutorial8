# 159.251 Tutorial 8 - JUnit 5 Testing

Student ID: 25014435

## How to run

```
mvn test
```

`mvn test` compiles the project and executes 9 tests in three test classes:

| Test class | Tests | Pass | Fail (on purpose) |
|---|---|---|---|
| `TestEqualsAndHashcode` | 3 | 2 | 1 |
| `TestMaps` | 4 | 3 | 1 |
| `TestUnmodfiableLists` | 2 | 2 | 0 |

The two failing tests are the deliverable of this tutorial: a good test is a test
that fails, because a failing test proves the presence of a bug. They are
`lecturerEqualsImpliesSameHashCode` and `identityHashMapFindsValueWithEqualKey`.

## Task 1 - the equals / hashCode contract

The contract is the implication `A -> B`:

* `A` = two objects are equal (`equals` returns `true`)
* `B` = both objects have the same `hashCode`

To expose a violation we need `A and not B`. `not A` is of no interest: if the
objects are not equal the contract says nothing, so `not A or B` is trivially
true. In JUnit the honest way to express "this object pair is meaningful for the
test" is an *assumption*, not an assertion:

```java
assumeTrue(lecturer1.equals(lecturer2));            // A - precondition
assertEquals(lecturer1.hashCode(), lecturer2.hashCode());   // B - postcondition
```

### Lecturer (broken, test fails)

`Lecturer.equals` compares `firstName` and `id` only, but `Lecturer.hashCode`
mixes in `firstName`, `id` **and** `name`. Two lecturers with the same id and
first name but a different last name are equal yet hash differently:

```java
new Lecturer(1, "Alan", "Turing")   // hashCode 185094279
new Lecturer(1, "Alan", "Kay")      // hashCode 1963735545
```

`lecturerEqualsIgnoresLastName` passes and documents the root cause; the
contract test `lecturerEqualsImpliesSameHashCode` fails with
`expected: <185094279> but was: <1963735545>`.

### Student (correct, test passes)

`Student.equals` compares `firstName`, `id` and `name`, and `Student.hashCode`
uses exactly the same three fields, so two distinct but equal students also
share a hash code. `studentEqualsImpliesSameHashCode` passes.

## Task 2 - IdentityHashMap vs HashMap

Keys are two distinct but equal `Student` objects (`key1.equals(key2)` is
`true`, `key1 == key2` is `false`), the value is stored under `key1` and looked
up with `key2`.

* `hashMapFindsValueWithEqualKey` - `HashMap` compares keys with `equals`, the
  lookup succeeds (test passes).
* `identityHashMapFindsValueWithEqualKey` - `IdentityHashMap` compares keys with
  `==`, the lookup returns `null`, so `Map`'s contract is violated (test fails
  with `expected: <159.251> but was: <null>`).
* `identityHashMapFindsValueWithIdenticalKey` - looking up with the very same
  reference still works (test passes), which shows the difference is reference
  semantics and not a broken map.
* `keysAreEqualButNotIdentical` - the fixture itself: the keys are equal and not
  identical.

## Task 3 - Collections.unmodifiableList

* `addThrowsUnsupportedOperationException` - `add()` on the unmodifiable view
  throws `UnsupportedOperationException` (verified with `assertThrows`).
* `unmodifiableListCanStillBeIterated` - the view is still an `Iterable`
  (checked with an assumption), so the enhanced for loop visits the same three
  elements.
