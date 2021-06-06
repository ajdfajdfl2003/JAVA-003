package idv.angus.task02.annotation;

import idv.angus.task02.annotation.config.AnnotationConfig;
import idv.angus.task02.components.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j2
public class AnnotationBattleField {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AnnotationConfig.class);

        final Joe joe = (Joe) ctx.getBean(User.class);
        joe.useKeyBoard();
    }
}
