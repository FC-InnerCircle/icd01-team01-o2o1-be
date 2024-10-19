# Inner Circle - 1조 O2O 서비스 배달이써
이너서클 1조 Offline To Online 서비스 배달이써 백엔드 입니다.

## 1. 개발 환경
- Kotlin
- JDK21
- SpringBoot 3.3.2
- MongoDB
- Redis

## 2. 빌드 및 실행방법 
```

```

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
