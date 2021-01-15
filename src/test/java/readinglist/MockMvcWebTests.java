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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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
		// 这里配置Security认证授权
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(SecurityMockMvcConfigurers.springSecurity()).build();
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

	@Test
	public void homePage_unauthenticatedUser() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "http://localhost/login"));
	}

	@Test
	public void postBook() throws Exception {
		String reader = "ok";
		String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
		HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
		CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
		mockMvc.perform(post("/" + reader).contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.sessionAttr(TOKEN_ATTR_NAME, csrfToken).param(csrfToken.getParameterName(), csrfToken.getToken())
				.param("title", "BOOK TITLE").param("author", "BOOK AUTHOR").param("isbn", "1234567890")
				.param("description", "DESCRIPTION").param("_csrf", "asdfas")).andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", "/" + reader));
		Book expectedBook = new Book();
		expectedBook.setId(1L);
		expectedBook.setReader(reader);
		expectedBook.setTitle("BOOK TITLE");
		expectedBook.setAuthor("BOOK AUTHOR");
		expectedBook.setIsbn("1234567890");
		expectedBook.setDescription("DESCRIPTION");
		mockMvc.perform(get("/" + reader)).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attributeExists("books")).andExpect(model().attribute("books", hasSize(1)))
				.andExpect(model().attribute("books", contains(samePropertyValuesAs(expectedBook))));
	}

	@Test
	@WithMockUser(username="craig",
			password="password",
			roles="READER")
	/**
	 * 有问题的代码测试以后看
	 * @throws Exception
	 */
	public void homePage_authenticatedUser() throws Exception {
		Reader expectedReader = new Reader();
		expectedReader.setUsername("craig");
		expectedReader.setPassword("password");
		expectedReader.setFullname("Craig Walls");
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("readingList"))
				.andExpect(model().attribute("reader", samePropertyValuesAs(expectedReader)))
				.andExpect(model().attribute("books", hasSize(0)));
	}
}
