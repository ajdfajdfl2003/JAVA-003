package idv.angus.task02.annotation.config;

import idv.angus.task02.annotation.Backpack;
import idv.angus.task02.annotation.Joe;
import idv.angus.task02.annotation.StrangeKeyboard;
import idv.angus.task02.components.User;
import idv.angus.task02.components.keycaps.A;
import idv.angus.task02.components.keycaps.B;
import idv.angus.task02.components.keycaps.Enter;
import idv.angus.task02.components.keycaps.KeyCap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AnnotationConfig {
    // 詳細在 README
    @Bean
    public List<KeyCap> lefKeyCaps() {
        return Arrays.asList(new A(), new Enter());
    }

    @Bean
    public List<KeyCap> rightKeyCap() {
        return Arrays.asList(new B(), new Enter(), new A());
    }

    @Bean
    public User joe() {
        return new Joe();
    }

    @Bean
    public StrangeKeyboard jeoKeyBoard() {
        return new StrangeKeyboard();
    }

    @Bean(name = "myBackpack")
    public Backpack backpack() {
        return new Backpack();
    }
}
