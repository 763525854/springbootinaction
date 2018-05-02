/**
 *  Created by weiping.gong on 2018年5月2日
 */
package readinglist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: weiping.gong
 * @Description:
 * @Date: created in 2018年5月2日
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {
	List<Book> findByReader(String reader);
	
}
