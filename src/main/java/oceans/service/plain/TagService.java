package oceans.service.plain;

import oceans.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> selectAllTags();

    void deleteById(Integer id);

    void insertOne(String value);
}
