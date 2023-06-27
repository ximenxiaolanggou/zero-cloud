package cneter.helloworld.zero.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2021/4/1 16:42
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 数据
     */
    private List data;
}
