# React + Vite + Spring Boot 기본 프로젝트

## 구조
```
project/
├─ frontend/         # React + Vite + axios + react-router-dom
│  ├─ src/
│  │  ├─ api/          	# axios 인스턴스 및 API 함수
│  │  ├─ api/axiosInstance.js   # (보안 미적용)axios 인스턴스 및 API 함수
│  │  ├─ api/axios.js          	# (보안 적용)axios 인스턴스 및 API 함수
│  │  ├─ components/   	# 공통 컴포넌트 (NavBar 등)
│  │  ├─ pages/        	# 라우트별 페이지
│  │  ├─ services/      # 데이터 가공 페이지 
│  │  ├─ App.jsx		# Routes
│  │  └─ main.jsx		# BrowserRouter
│  ├─ vite.config.js   	# /api -> localhost:8090 프록시 설정 포함
│  └─ package.json
│
└─ backend/          # Spring Boot (Gradle, JDK 21)
   ├─ src/main/java/com/skc/
   │  ├─ BackendApplication.java
   │  │
   │  ├─ config/SwaggerConfig.java        # swagger 설정-개발운영중 필요.
   │  ├─ controller/HelloController.java  # API 컨트롤러.
   │  ├─ entity/Hello.java            	  # table과 매칭되는 객체.
   │  ├─ repository/HelloRepository.java  # 기본 JpaRepository 객체를 사용.
   │  └─ service/HelloService.java        # 비즈니스 로직 구현.
   │
   ├─ src/main/resources/application.properties	# 환경설정 (개발.운영 분리사용 가능).
   └─ build.gradle								# 프로젝트 의존 리소스 정의.
```

## 실행 방법

### 1. 백엔드 (Spring Boot)
서버는 기본적으로 `http://localhost:8090` 에서 실행됩니다.
`JDK 21`이 설치되어 있어야 하며, `build.gradle`의 toolchain 설정으로 JDK 21을 사용하도록 지정되어 있습니다.

### 2. 프론트엔드 (React + Vite)
```bash
cd frontend
npm install
npm run dev
```
`http://localhost:5173` 에서 실행되며, `/api` 로 시작하는 요청은 `vite.config.js`의 proxy 설정을 통해 자동으로 백엔드(8090)로 전달됩니다.

### 3. 동작 확인
- 프론트엔드 접속 후 Home 화면이 보이면 정상 연결된 것입니다.
- 백엔드 접속 후 Hello메뉴 클릭시, 서브페이지에 Hello CRUD 화면이 보이면 정상 연결된 것입니다.

## 참고
- axios 관련 로직은 `frontend/src/api/axiosInstance.js` 에서 baseURL, 인터셉터 등을 수정하면 됩니다.
- 새로운 페이지 추가 시 `pages/` 에 컴포넌트를 만들고 `router/index.jsx` 에 `<Route>` 를 추가하면 됩니다.
- 새로운 API 추가 시 `controller/` 에 컨트롤러를 만들고, 프론트에서는 `api/` 폴더에 대응하는 함수를 추가하세요.


### 배포전 개발단계
- vite.config.js파일에 proxy 설정.

### 배포할때
- Nginx에 설정.

### 프론트/백엔드를 서로다른Domain 으로 배포시에는
- CORS 설정(WebConfig.java) 이런거 필요함.