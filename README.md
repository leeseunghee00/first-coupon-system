# 선착순 이벤트 시스템

선착순 이벤트 진행 시 발생할 수 있는 동시 접속 문제를 이해하고, 이를 해결하기 위한 방법을 간단한 쿠폰 발급 시스템을 구현하며 학습했습니다. <br />
본 프로젝트 내용은 [노션](https://leeseunghee00.notion.site/1e2889b5fe3d80239cfed09a549246bd?pvs=4)에 기록했습니다.

<br /><br />

> 프로젝트 환경

- Java 17
- Redis 3.x
- Kafka, Zookeeper
- IntelliJ IDEA, Docker

<br /><br />

> 시스템 흐름도

![Image](https://github.com/user-attachments/assets/d070e247-8cf8-4b7a-b184-59ddc89bea40)

<br /><br />

> How to Use Docker Images

Docker 이미지는 [Docker Hub](https://hub.docker.com/_/docker)에서 확인할 수 있습니다.

#### MySQL

```bash
# MySQL 이미지 다운로드
docker image pull mysql:latest

# MySQL 컨테이너 실행
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 --name your-container mysql

# 컨테이너 중지
docker stop your-container
```

#### Redis

```bash
# Redis 이미지 다운로드
docker image pull redis:latest

# Redis 컨테이너 실행
docker run -d -p 6379:6379 --name your-container redis

# 컨테이너 중지
docker stop your-container
```

#### Kafka & Zookeeper

```yaml
# docker-compose.yml
version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    container_name: zookeeper
    ports:
      - "2181:2181"

  kafka:
    image: wurstmeister/kafka:2.12-2.5.0
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_HOST_NAME: 127.0.0.1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```

```bash
# Kafka & Zookeeper 실행
docker-compose up -d
```

#### Kafka Topic, Consumer

```bash
# Topic 생성
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic coupon_create

# Consumer 실행
docker exec -it kafka kafka-console-consumer.sh --topic coupon_create --bootstrap-server localhost:9092 --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"
```