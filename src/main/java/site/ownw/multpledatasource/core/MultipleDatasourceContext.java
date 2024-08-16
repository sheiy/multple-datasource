package site.ownw.multpledatasource.core;

import org.springframework.util.CollectionUtils;

import java.util.Stack;

public abstract class MultipleDatasourceContext {

    private static final ThreadLocal<Stack<String>> DATA_SOURCE_NAME = new ThreadLocal<>();

    public static String getCurrentDataSourceName() {
        if (CollectionUtils.isEmpty(DATA_SOURCE_NAME.get())) {
            return null;
        }
        return DATA_SOURCE_NAME.get().getLast();
    }

    public static void setCurrentDataSourceName(String dataSourceName) {
        if (CollectionUtils.isEmpty(DATA_SOURCE_NAME.get())) {
            DATA_SOURCE_NAME.set(new Stack<>());
        }
        DATA_SOURCE_NAME.get().push(dataSourceName);
    }

    public static void clearCurrentDataSourceName() {
        if (!CollectionUtils.isEmpty(DATA_SOURCE_NAME.get())) {
            DATA_SOURCE_NAME.get().pop();
        }
        if (CollectionUtils.isEmpty(DATA_SOURCE_NAME.get())) {
            DATA_SOURCE_NAME.remove();
        }
    }
}
