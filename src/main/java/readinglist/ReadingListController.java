/**
 *  Created by weiping.gong on 2018年5月2日
 */
package readinglist;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: weiping.gong
 * @Description:
 * @Date: created in 2018年5月2日
 */
@Controller
@RequestMapping("/")
public class ReadingListController {
	@Autowired
	private ReadingListRepository readingListRepository;
	@Autowired
	private CounterService counterService;
	@Autowired
	private GaugeService gaugeService;

	@RequestMapping(value = "/{reader}", method = RequestMethod.GET)
	public String readerBooks(@PathVariable("reader") String reader, Model model) {
		List<Book> readingList = readingListRepository.findByReader(reader);
		if (readingList != null) {
			model.addAttribute("books", readingList);
		}
		return "readingList";
	}

	@RequestMapping(value = "/{reader}", method = RequestMethod.POST)
	public String addToReadingList(@PathVariable("reader") String reader, Book book) {
		book.setReader(reader);
		readingListRepository.save(book);
		//增加记录book.saved方法调用的次数。并记录最后一次book.save的保存时间
		counterService.increment("book.saved");
		gaugeService.submit("books.last.saved", System.currentTimeMillis());
		return "redirect:/{reader}";
	}
}
