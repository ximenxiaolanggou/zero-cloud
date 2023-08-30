package center.helloworld.zero.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhishun.cai
 * @date 2023/6/27 
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
