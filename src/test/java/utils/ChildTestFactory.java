package utils;

import model.Child;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class ChildTestFactory {

    private static final AtomicInteger counter = new AtomicInteger();

    public static Child ofAge(int age) {
        return new Child(
                "John_" + age,
                "Doe_" + age,
                LocalDate.now().minusYears(age)
        );
    }

    public static Child random() {
        int i = counter.incrementAndGet();
        return new Child(
                "John_" + i,
                "Doe_" + i,
                LocalDate.now().minusYears(ThreadLocalRandom.current().nextInt(1, 16))
        );
    }

    public static Child withoutBirthDate() {
        return new Child("John", "Doe", null);
    }

    public static Child withId(Long id) {
        Child base = random();
        return new Child(id, base.firstName(), base.lastName(), base.birthDate());
    }
}
