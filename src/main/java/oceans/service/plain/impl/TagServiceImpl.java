package oceans.service.plain.impl;

import oceans.dao.TagDao;
import oceans.model.Tag;
import oceans.service.plain.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;

    @Override
    public List<Tag> selectAllTags() {
        return tagDao.findAll();
    }
    @Override
    public void deleteById(Integer id) {
        tagDao.deleteById(id);
    }

    @Override
    public void insertOne(String value) {
        tagDao.save(new Tag(value));
    }
}
