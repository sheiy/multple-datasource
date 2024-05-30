package site.ownw.multpledatasource.config;

import com.zaxxer.hikari.HikariConfig;
import java.util.List;
import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "multiple")
public class MultipleDatasourceProperties {
    private List<DataSourceProperties> datasource;
    private HikariConfig hikariConfig;
}
