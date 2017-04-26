package me.leolin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by leo on 2017/4/26.
 */
@Service
public class JavaRecordService {
    @Autowired
    private JavaRecordDao javaRecordDao;

    @Transactional
    public Long save(String name) {
        return javaRecordDao.save(new JavaRecord(name)).getId();
    }

    @Transactional
    public void update(Long id, String name) {
        System.out.println("update");
        JavaRecord record = javaRecordDao.findOne(id);
        record.setName(name);
        javaRecordDao.save(record);
    }

    @Transactional
    public void update2(Long id, String name) {
        System.out.println("update2");
        JavaRecord record = javaRecordDao.findOne(id);
        record.setName(name);
    }

    public JavaRecord get(Long id) {
        return javaRecordDao.findOne(id);
    }
}
