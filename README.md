# Inner Circle - 1조 O2O 서비스 배달이써
이너서클 1조 Offline To Online 서비스 배달이써 백엔드 입니다.

## 1. 개발 환경
- Kotlin
- JDK21
- SpringBoot 3.3.2
- MongoDB
- Redis

## 2. 빌드 및 실행방법


1. 로컬에서 도커로 서비스 실행 시, 루트 폴더 내 Dockerfile 을 통한 빌드 이후 docker 폴더 내에 있는 docker-compose 파일로 실행
```
docker build -t o2o-backend:latest .
cd docker
docker-compose up -d o2o-be
```
2. CI/CD 구조
   1. free tier 내 최소 비용을 위해, 컨테이너 저장 시 필요한 ECR, AWS CodeSeries를 통한 빌드 등 AWS 통합 서비스를 사용하지 않았습니다.  
   2. CI/CD 의 흐름은  Code push -> Gitlab Actions 에서 빌드 프로세스 이후 S3 에 업로드 / Codedeploy 호출 -> AWS Codedeploy 가 AWS EC2 에 업로드 이후 배포 실행. 배포에 대한 파일은 appspec.yaml 파일에 있습니다. 
   3. appspec.yaml 에 정의된 폴더로 파일을 이동 후, scripts 폴더 내 after-deploy.sh 파일을 실행하여 배포합니다. 

## 3. 아키텍처

- Domain (Order, Member, Store...)
    - presentation
        - api
            - XxxController
        - dto
            - XxxRequest
            - XxxResponse
    - application
        - XxxFacade
    - Domain
        - domain object
        - XxxReader, XxxStore, XxxService
    - infrastructure
        - client
	        - XxxClient
        - repository
            - XxxStorage (impl)
            - XxxRepository (interface)
