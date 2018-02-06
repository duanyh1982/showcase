package samlen.tsoi.showcase.web.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import samlen.tsoi.showcase.web.entity.ArticleEs;

/**
 * @author samlen_tsoi
 * @date 2018/1/18
 */
@Repository
public interface ArticleEsRepository extends ElasticsearchRepository<ArticleEs, Long> {
}
