package bzh.ya2o.java.designwithlambdas;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class StreamLab {

    public static class Person {
        public final String name;
        public final int age;

        public Person(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return this.name; }

        public int getAge() { return this.age; }

        public int ageDifference(Person other) {
            return age - other.age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    private static void printPeople(final String message, final List<Person> people) {
        System.out.println("\n" + message);
        people.forEach(System.out::println);
    }

    private final List<Person> people = Arrays.asList(
            new Person("John", 20),
            new Person("Sara", 21),
            new Person("Jane", 21),
            new Person("Greg", 35)
    );

    /* ----------------- SORT ------------------------------------------------------ */
    @Test
    public void ascendingAgeSort() {
        final List<Person> ascendingAge = people.stream()
                .sorted(Person::ageDifference)
                .collect(toList());
        StreamLab.printPeople("Sorted in ascending order by age:", ascendingAge);
    }

    @Test
    public void descendingAgeSort() {
        final Comparator<Person> compareAscending = ((person, other) -> person.ageDifference(other));
        final Comparator<Person> compareDescending = compareAscending.reversed();

        final List<Person> descendingAge = people.stream()
                .sorted(compareDescending)
                .collect(toList());
        StreamLab.printPeople("Sorted in descending order by age:", descendingAge);
    }

    @Test
    public void ascendingNameSort() {
        final List<Person> ascendingName = people.stream()
                .sorted((person, otherPerson) -> person.name.compareTo(otherPerson.name))
                .collect(toList());
        StreamLab.printPeople("Sorted in ascending order by name:", ascendingName);
    }

    @Test
    public void ascendingNameSort2() {
        final Function<Person, String> byName = person -> {return person.name;};
        final List<Person> ascendingName = people.stream()
                .sorted(comparing(byName))
                .collect(toList());
        StreamLab.printPeople("Sorted in ascending order by name:", ascendingName);
    }

    @Test
    public void ascendingAgeThenName() {
        final Function<Person, Integer> byAge = Person::getAge;
        final Function<Person, String> byName = person -> {return person.name;};
        final List<Person> ascendingName = people.stream()
                .sorted(comparing(byAge).thenComparing(byName))
                .collect(toList());
        StreamLab.printPeople("Sorted in ascending order by age then by name:", ascendingName);
    }

    @Test
    public void youngestPerson() {
        people.stream()
                .min(Person::ageDifference)
                .ifPresent(youngest -> System.out.println("\nYoungest: " + youngest));
    }

    @Test
    public void oldestPerson() {
        people.stream()
                .max(Person::ageDifference)
                .ifPresent(youngest -> System.out.println("\nOldest: " + youngest));
    }

    /* ----------------- COLLECTORS ------------------------------------------------- */
    @Test
    public void groupByAge() {
        final Map<Integer, List<Person>> peopleByAge = people.stream()
                .collect(groupingBy(Person::getAge));
        System.out.println("Grouped by age: " + peopleByAge);
    }

    @Test
    public void namesGroupByAge() {
        final Map<Integer, List<String>> nameOfPeopleByAge = people.stream()
                .collect(
                        groupingBy(
                                Person::getAge,
                                mapping(Person::getName, toList())
                        )
                );
        System.out.println("People grouped by age: " + nameOfPeopleByAge);
    }

    @Test
    public void oldestPersonOfEachLetter() {
        final Map<Character, Optional<Person>> oldestPersonOfEachLetter = people.stream()
                .collect(
                        groupingBy(
                                person -> person.getName().charAt(0),
                                reducing(BinaryOperator.maxBy(comparing(Person::getAge)))
                        )
                );
        System.out.println("Oldest Person Of Each Letter: " + oldestPersonOfEachLetter);
    }

    @Test
    public void oldestPersonOfEachLetter_comparator() {
        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> oldestPersonOfEachLetter =
                people.stream()
                        .collect(
                                groupingBy(
                                        person -> person.getName().charAt(0),
                                        reducing(BinaryOperator.maxBy(byAge))
                                )
                        );
        System.out.println("Oldest person of each letter:");
        System.out.println(oldestPersonOfEachLetter);
    }

    @Test
    public void oldestPersonOfEachLetter_maxBy() {
        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Optional<Person>> oldestPersonOfEachLetter =
                people.stream()
                        .collect(
                                groupingBy(
                                        person -> person.getName().charAt(0),
                                        maxBy(byAge)
                                )
                        );
        System.out.println("Oldest person of each letter:");
        System.out.println(oldestPersonOfEachLetter);
    }

    @Test
    public void oldestPersonOfEachLetter_foldingWithDummyToRemoveOptionalFromResult() {
        Comparator<Person> byAge = Comparator.comparing(Person::getAge);
        Map<Character, Person> oldestPersonOfEachLetter =
                people.stream()
                        .collect(
                                groupingBy(
                                        person -> person.getName().charAt(0),
                                        reducing(new Person("Dummy", -1), BinaryOperator.maxBy(byAge))
                                )
                        );
        System.out.println("Oldest person of each letter:");
        System.out.println(oldestPersonOfEachLetter);
    }


    @Test
    public void oldestPersonOfEachLetter_NoOptionalInResult() {
        final Map<Character, Person> oldestPersonOfEachLetter = people.stream()
                .collect(
                        groupingBy(
                                person -> person.getName().charAt(0),
                                collectingAndThen(
                                        reducing(BinaryOperator.maxBy(comparing(Person::getAge))),
                                        Optional::get
                                )
                        )
                );
        System.out.println("Oldest Person Of Each Letter: " + oldestPersonOfEachLetter);
    }

    /* ----------------- FILES ------------------------------------------------------ */
    @Test
    public void listFiles() throws IOException {
        System.out.println("listFiles");
        Files.list(Paths.get("."))
                .forEach(System.out::println);
    }

    @Test
    public void listDirectories() throws IOException {
        System.out.println("listDirectories");
        Files.list(Paths.get("."))
                .filter(Files::isDirectory)
                .forEach(System.out::println);
    }

    @Test
    public void listFilesWithPattern_oldDirtyWay() throws IOException {
        System.out.println("list files matching pattern");
        final File[] files = new File(".").listFiles(file -> file.toString().endsWith(".xml"));
        for (File file : files) {
            System.out.println(file);
        }
    }

    @Test
    public void listFilesWithPattern() throws IOException {
        System.out.println("list files matching pattern");
        Files.newDirectoryStream(Paths.get("."),
                path -> path.toString().endsWith(".xml"))
                .forEach(System.out::println);
    }

    @Test
    public void listFilesWithProperty() throws IOException {
        System.out.println("list files matching property");
        final File[] files = new File(".").listFiles(File::isHidden);
        for (File file : files) {
            System.out.println(file);
        }
    }

    @Test
    public void listSubDirectories() throws IOException {
        System.out.println("list subdirectories");
        Stream.of(new File(".").listFiles())
                .flatMap(file -> file.listFiles() == null ? Stream.of(file) : Stream.of(file.listFiles()))
                .collect(toList());
    }

    @Test
    public void watchFileChange() throws IOException, InterruptedException {
        final Path path = Paths.get(".");
        final WatchService watchService = path.getFileSystem().newWatchService();

        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        final WatchKey watchKey = watchService.poll(1, TimeUnit.MINUTES);
        if (watchKey != null) {
            watchKey.pollEvents().stream().forEach(event -> System.out.println(event.context()));
        }
    }
}
