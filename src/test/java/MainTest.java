import com.morena.netMain.logic.entity.NotePosts;
import com.morena.netMain.logic.model.dao.PNotePosts;
import com.morena.netMain.logic.model.builder.PNotePostsBuilder;
import org.junit.jupiter.api.Disabled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Disabled
public class MainTest {

   // @Test
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

    public static List<PNotePosts> toPojoList (List<NotePosts> np){
        return np.stream()
                .map(PNotePostsBuilder::toPojo)
                .collect(Collectors.toList());
    }
   // @Test
    public void toPojo(){

        System.out.println( toPojoList(null));

    }
}
