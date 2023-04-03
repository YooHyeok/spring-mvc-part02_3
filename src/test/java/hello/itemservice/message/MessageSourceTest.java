package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    /**
     * hello 메시지 조회 테스트
     * message.properties
     */
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        //locale정보가 없으면 basename의 기본 이름 메시지 파일인 message.properties를 조회한다.
        assertThat(result).isEqualTo("안녕");
    }

    /**
     * 메시지 조회 오류 테스트
     * assertThatThrownBy() - NoSuchMessageException
     * 예외 발생 테스트
     */
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class); //녹색불이 들어오면 예외발생 (메시지를 찾을 수 없어서 오류가 발생했다.)
    }

    /**
     * 기본 메시지 조회 테스트
     * 메시지가 존재하지 않을때 3번째 매개변수에 지정한 메시지를 반환한다..
     */
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    /**
     * 메시지 파라미터 테스트
     * 파라미터를 받은 ({0}) 메시지는 두번째 매개변수에 파라미터를 담는다.
     * 해당 매개변수는 Object[] 배열 타입으로 담아야한다.
     */
    @Test
    void argumentMessage() {
        String result = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(result).isEqualTo("안녕 Spring");
    }

    /**
     * Locale.KOREA 테스트
     * Locale.ENGLISH가 아니면 default messages를 가져오므로 정상작동.
     */
    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }
}
