/**
 *  Created by weiping.gong on 2018年5月2日
 */
package readinglist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @Author: weiping.gong
 * @Description:
 * @Date: created in 2018年5月2日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@WebAppConfiguration
public class ReadingListApplicationTests {
	@Test
	public void contextLoads() {
		Assert.assertEquals("相等", 19, 19);
	}
}
