package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

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
}
