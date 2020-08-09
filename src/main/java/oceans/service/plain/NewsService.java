package oceans.service.plain;

import oceans.model.News;
import org.springframework.data.domain.Page;

public interface NewsService {
    News selectOne(Integer id);

    Page<News> getPage(Integer num, Integer size);

    void deleteOne(Integer id);

    void insertOne(News news);

    void updateOne(Integer id, News news);


}
