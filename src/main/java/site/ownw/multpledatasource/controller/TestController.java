package site.ownw.multpledatasource.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.ownw.multpledatasource.core.DBSource;
import site.ownw.multpledatasource.mapper.TestMapper;

@DBSource("b")
@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestMapper testMapper;

    @GetMapping
    public Object test(String content) {
        Integer count = testMapper.count();
        System.out.println("count is " + count);
        return Map.of("count", count);
    }
}
