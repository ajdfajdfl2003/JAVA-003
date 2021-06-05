package idv.angus.task02.xml;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Log4j2
public class BattleField {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");

        final User damon = (User) context.getBean("damon");
        log.info("This is a " + damon.getClass() + " speaking");
        damon.useKeyBoard();

        final User katherine = (User) context.getBean("katherine");
        log.info("This is a " + katherine.getClass() + " speaking");
        katherine.useKeyBoard();
    }
}
