package readinglist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
/**
 * 自定义度量信息,需要实现PublicMetrics接口
 * 
 * @author Administrator
 *
 */
public class ApplicationContextMetrics implements PublicMetrics {
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Collection<Metric<?>> metrics() {
		List<Metric<?>> metrics = new ArrayList<>();
		// 记录applicationContext的启动时间，以及记录Bean定义数量和记录控制器类型的Bean数量的数量
		metrics.add(new Metric<Long>("spring.context.startup-date", applicationContext.getStartupDate()));
		metrics.add(new Metric<Integer>("spring.beans.definitions", applicationContext.getBeanDefinitionCount()));
		metrics.add(new Metric<Integer>("spring.beans", applicationContext.getBeanNamesForType(Object.class).length));
		metrics.add(new Metric<Integer>("spring.controllers",
				applicationContext.getBeanNamesForAnnotation(Controller.class).length));
		return metrics;
	}

}
