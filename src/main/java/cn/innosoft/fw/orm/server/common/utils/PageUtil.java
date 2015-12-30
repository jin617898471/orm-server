package cn.innosoft.fw.orm.server.common.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageUtil {
	public static PageRequest buildPageRequest(int pageNumber) {
        Sort sort = new Sort(Direction.DESC, "time");
        return new PageRequest(pageNumber - 1, 10, sort);
    }
}
