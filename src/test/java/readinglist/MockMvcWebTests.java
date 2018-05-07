/**
 *  Created by weiping.gong on 2018年5月7日
 */
package readinglist;

/**
 * 引用类中的所有静态方法，确保代码的整洁性
 */
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @Author: weiping.gong
 * @Description:
 * @Date: created in 2018年5月7日
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ReadingListApplication.class)
@WebAppConfiguration
public class MockMvcWebTests {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/**
	 * 改方法含义为。首先向/readingList发起一个GET请求，接下来希望该请求处理返回成功。status().isOk()会判断HTTP
	 * 200响应吗，并且视图的逻辑名称为readingList。而且测试的模型中还要包含一个名为books的属性，该属性的是一个空集合
	 * 
	 * @throws Exception
	 */
	@Test
	public void homePage() throws Exception {
		mockMvc.perform(get("/readingList")).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attributeExists("books")).andExpect(model().attribute("books", is(empty())));
	}
}
