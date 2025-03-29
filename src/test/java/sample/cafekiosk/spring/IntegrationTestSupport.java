package sample.cafekiosk.spring;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import sample.cafekiosk.spring.client.mail.MailSendClient;

@SpringBootTest
@ActiveProfiles("test")
public abstract class IntegrationTestSupport {
    @MockitoBean
    protected MailSendClient mailSendClient;
}
