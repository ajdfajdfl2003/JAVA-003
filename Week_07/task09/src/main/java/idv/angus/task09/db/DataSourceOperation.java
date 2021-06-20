package idv.angus.task09.db;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DataSourceOperation {
    WRITE("mainDataSource"),
    READ("replicaDataSource");

    private final String dataSourceName;
}
