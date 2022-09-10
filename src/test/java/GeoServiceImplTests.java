import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Location;
import ru.netology.entity.Country;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTests {
    public static Stream<Arguments> geoParamsSource() {
        return Stream.of(
                Arguments.of("127.0.0.1", null),
                Arguments.of("172.1.1.0", Country.RUSSIA),
                Arguments.of("96.1.1.0", Country.USA),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, Country.RUSSIA),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, Country.USA)
        );
    }

    @ParameterizedTest
    @MethodSource("geoParamsSource")
    void testGeoServiceImpl(String ip, Country expected) {
        // arrange
        GeoServiceImpl geoService = new GeoServiceImpl();

        // act
        Location loc = geoService.byIp(ip);

        //assert
        Assertions.assertEquals(expected, loc.getCountry());
    }
}
