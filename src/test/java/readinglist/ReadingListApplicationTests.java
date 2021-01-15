/**
 *  Created by weiping.gong on 2018年5月2日
 */
package readinglist;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ReadingListController test;
	@Test
	public void testService() {
		String reader = "ok";
		Book book = new Book();
		book.setAuthor("1");
		book.setDescription("1");
		book.setId(1L);
		book.setIsbn("1");
		book.setReader(reader);
		book.setTitle("ok");
		String string=test.addToReadingList(reader, book);
		System.out.println("testService result"+string);
	}
	
	@Test
	public void contextLoads() {
		Assert.assertEquals("相等", 19, 19);
		System.out.println("i am ok ========");
	}
}
