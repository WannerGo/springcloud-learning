package com.macro.cloud.service.impl;

import com.google.common.collect.Lists;
import com.macro.cloud.domain.Actor;
import com.macro.cloud.domain.User;
import com.macro.cloud.mapper.ActorMapper;
import com.macro.cloud.service.UserService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by macro on 2019/8/29.
 */
@Service
public class UserServiceImpl implements UserService {
    private List<User> userList;

    @Autowired
    ActorMapper actorMapper;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Override
    public void create(User user) {
        userList.add(user);
    }

    @Override
    @Transactional
    public void createActor(Actor user) {
//        actorMapper.insert(user);
        actorMapper.insertBatch(Lists.newArrayList(user));
    }

    @Override
    public int batchCreateActor() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            ActorMapper mapper = sqlSession.getMapper(ActorMapper.class);
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("batch");
            List<Actor> actorList = new ArrayList<>();
            for (int i = 0; i < 100000; i++) {
                Actor actor = new Actor();
                actor.setName("test");
                actor.setUpdateTime(new Date());
                actorList.add(actor);
            }
            List<List<Actor>> partition = Lists.partition(actorList, 1000);
            partition.forEach(mapper::insertBatch);
            stopWatch.stop();
            System.out.println(stopWatch.getLastTaskTimeMillis());
        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            sqlSession.commit();
            sqlSession.close();
        }
        return 0;
    }

    @Override
    public User getUser(Long id) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getId().equals(id)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public void update(User user) {
        userList.stream().filter(userItem -> userItem.getId().equals(user.getId())).forEach(userItem -> {
            userItem.setUsername(user.getUsername());
            userItem.setPassword(user.getPassword());
        });
    }

    @Override
    public void delete(Long id) {
        User user = getUser(id);
        if (user != null) {
            userList.remove(user);
        }
    }

    @Override
    public User getByUsername(String username) {
        List<User> findUserList = userList.stream().filter(userItem -> userItem.getUsername().equals(username)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        }
        return null;
    }

    @Override
    public List<User> getUserByIds(List<Long> ids) {
        return userList.stream().filter(userItem -> ids.contains(userItem.getId())).collect(Collectors.toList());
    }

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        userList.add(new User(1L, "macro", "123456"));
        userList.add(new User(2L, "andy", "123456"));
        userList.add(new User(3L, "mark", "123456"));
    }
}
