import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {
    public static Stream<Arguments> LocalizationServiceSource() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("LocalizationServiceSource")
    void testLocalizationServiceImpl(Country country, String expected) {
        // arrange
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

        // act
        String actual = localizationService.locale(country);

        //assert
        Assertions.assertEquals(expected, actual);
    }
}
