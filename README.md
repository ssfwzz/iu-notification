# iu-notification

## 개발 환경 구성

```shell
make clean-devcontainer # 로컬 개발 환경 제거
make init-devcontainer # 로컬 개발 환경 초기화
```

## AWS 테스트

### SQS 메시지 전송

```shell
docker exec -it localstack /bin/bash
awslocal sqs create-queue --queue-name sample-queue
awslocal sqs send-message --queue-url http://localhost:4566/000000000000/SampleQueue --message-body '{"key": "value"}'
```
