package oceans.service.plain.impl;

import oceans.dao.NewsDao;
import oceans.model.News;
import oceans.service.plain.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class NewsServiceImpl implements NewsService {
    @Resource
    private NewsDao newsDao;

    @Override
    public Page<News> getPage(Integer num, Integer size) {
        return newsDao.findAll(
                (root, query, builder) -> builder.equal(root.get("active"), true),
                PageRequest.of(num, size, Sort.by(Sort.Direction.DESC, "date"))
        );
    }

    @Override
    public News selectOne(Integer id) {
        return newsDao.findById(id).orElse(null);
    }

    @Override
    public void deleteOne(Integer id) {
        News news = newsDao.findById(id).orElse(null);
        news.setActive(false);
        newsDao.save(news);
    }

    @Override
    public void insertOne(News news) {
        news.setActive(true);
        newsDao.save(news);
    }

    @Override
    public void updateOne(Integer id, News news) {
        news.setId(id);
        newsDao.save(news);
    }
}
