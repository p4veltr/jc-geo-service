import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTests {
    public static Stream<Arguments> testSendSource() {
        return Stream.of(
                Arguments.of("172.172.172.1","Добро пожаловать"),
                Arguments.of("96.96.96.1","Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("testSendSource")
    void testSendMessage(String ipAdress, String expectedMessage) {
        // arrange
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location(null, Country.USA, null, 0));

        LocalizationService localizationService = Mockito.spy(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn(expectedMessage);
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn(expectedMessage);

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAdress);

        // act
        String actual = messageSender.send(headers);

        //assert
        Assertions.assertEquals(expectedMessage, actual);
    }

}
