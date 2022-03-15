import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class MainTest {

    @Test
    public void printTime(){
        //исходное время
        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("Atlantic/Azores"));
        // в текущий часовой пояс
        ZonedDateTime withLocalZone = zdt.withZoneSameInstant(ZoneId.systemDefault());
        // без зоны
        LocalDateTime localDateTime = withLocalZone.toLocalDateTime();
        System.out.println(zdt);System.out.println(withLocalZone);
        System.out.println(localDateTime);

        System.out.println(zdt.withZoneSameInstant(ZoneId.of("GMT"))
                .toLocalDateTime());

    }

    private String customToSting(List<?> list){
        return "("+list.toString().substring(1, list.toString().length()-1)+")";
    }

    @Test
    public void toPojo(){

        System.out.println(customToSting(List.of(1,2,3,4,5)));

    }
}
