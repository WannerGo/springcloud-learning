分布式id的生成

// 压测
ab -n 1 -c 1  -T application/json -p D:/github/springcloud-learning/user-service/src/main/resources/actor.json  http://localhost:8201/user/createActor



分布式锁的生成