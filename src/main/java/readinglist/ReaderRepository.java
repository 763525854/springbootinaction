/**
 *  Created by weiping.gong on 2018年5月4日
 */
package readinglist;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: weiping.gong
 * @Description:
 * @Date: created in 2018年5月4日
 */
public interface ReaderRepository extends JpaRepository<Reader, String> {

}
