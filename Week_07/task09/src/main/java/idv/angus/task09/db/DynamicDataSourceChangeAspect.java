package idv.angus.task09.db;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Log4j2
@Aspect
@Component
@Order(-1)
public class DynamicDataSourceChangeAspect {
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String dsName = targetDataSource.value().getDataSourceName();
        log.info("Datasource now choose:{}", dsName);
        if (!DynamicDataSourceContextHolder.containsDataSource(dsName)) {
            log.error("資料來源[{}]不存在，使用預設資料來源 > {}", targetDataSource.value(), joinPoint.getSignature());
        } else {
            DynamicDataSourceContextHolder.setDataSourceName(targetDataSource.value().getDataSourceName()); //設定到動態資料來源上下文中
        }
    }

    @After("@annotation(targetDataSource)")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        //方法執行完畢之後，銷毀當前資料來源資訊，進行垃圾回收。
        String dsName = targetDataSource.value().getDataSourceName();
        log.info("Datasource times to clear:{}", dsName);
        DynamicDataSourceContextHolder.clearDataSourceName();
    }
}
