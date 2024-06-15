package hipravin.samples.faker;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.util.Locale;
import java.util.Random;

public class FakerPlayground {
    public static void main(String[] args) {
        FakeValuesService fakeValuesService = new FakeValuesService(
                new Locale("en-GB"), new RandomService());

        String email = fakeValuesService.bothify("????##@gmail.com");
        System.out.println(email);


        Random random = new Random(0);

        Faker faker = new Faker(random);
        System.out.println(faker.address().city());
        System.out.println(faker.address().country());

        System.out.println(faker.app().author());
        System.out.println(faker.app().name());
        System.out.println(faker.bool().bool());

    }
}
