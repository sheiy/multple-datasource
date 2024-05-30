package site.ownw.multpledatasource.core;

abstract class MultipleDatasourceContext {

    private static final ThreadLocal<String> DATA_SOURCE_NAME = new ThreadLocal<>();

    public static String getCurrentDataSourceName() {
        return DATA_SOURCE_NAME.get();
    }

    public static void setCurrentDataSourceName(String dataSourceName) {
        DATA_SOURCE_NAME.set(dataSourceName);
    }

    public static void clearCurrentDataSourceName() {
        DATA_SOURCE_NAME.remove();
    }
}
