package site.ownw.multpledatasource.core;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.util.StringUtils;
import site.ownw.multpledatasource.config.MultipleDatasourceProperties;

@Slf4j
public class MultipleDatasource implements DataSource {

    private final Map<String, DataSource> dataSources;
    private final DataSource defaultDataSource;

    public MultipleDatasource(MultipleDatasourceProperties multipleDatasourceProperties) {
        log.info("multiple datasource init...");
        if (multipleDatasourceProperties.getDatasource().isEmpty()) {
            throw new IllegalStateException("multiple datasource is empty");
        }
        if (multipleDatasourceProperties.getHikariConfig() == null) {
            multipleDatasourceProperties.setHikariConfig(new HikariConfig());
        }
        this.defaultDataSource =
                createDatasource(
                        multipleDatasourceProperties.getDatasource().getFirst(),
                        multipleDatasourceProperties.getHikariConfig());
        log.info("multiple datasource default datasource inited");
        this.dataSources = new HashMap<>();
        for (int i = 1; i < multipleDatasourceProperties.getDatasource().size(); i++) {
            DataSourceProperties dataSourceProperties =
                    multipleDatasourceProperties.getDatasource().get(i);
            if (!StringUtils.hasText(dataSourceProperties.getName())) {
                throw new IllegalStateException("multiple datasource config properties of name is empty");
            }
            dataSources.put(
                    dataSourceProperties.getName(),
                    createDatasource(dataSourceProperties, multipleDatasourceProperties.getHikariConfig()));
            log.info("multiple datasource {} datasource inited", dataSourceProperties.getName());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getCurrentDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrentDataSource().getConnection(username, password);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getCurrentDataSource().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getCurrentDataSource().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getCurrentDataSource().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getCurrentDataSource().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getCurrentDataSource().getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getCurrentDataSource().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getCurrentDataSource().isWrapperFor(iface);
    }

    private DataSource createDatasource(
            DataSourceProperties dataSourceProperties, HikariConfig hikariConfig) {
        hikariConfig.setPoolName(dataSourceProperties.getName());
        if (dataSourceProperties.getDriverClassName() != null) {
            hikariConfig.setDriverClassName(dataSourceProperties.getDriverClassName());
        }
        hikariConfig.setJdbcUrl(dataSourceProperties.getUrl());
        hikariConfig.setUsername(dataSourceProperties.getUsername());
        hikariConfig.setPassword(dataSourceProperties.getPassword());
        hikariConfig.setDataSourceJNDI(dataSourceProperties.getJndiName());
        return new HikariDataSource(hikariConfig);
    }

    private DataSource getCurrentDataSource() {
        String currentDataSourceName = MultipleDatasourceContext.getCurrentDataSourceName();
        if (!StringUtils.hasText(currentDataSourceName)
                || !dataSources.containsKey(currentDataSourceName)) {
            return defaultDataSource;
        }
        return dataSources.get(currentDataSourceName);
    }
}
