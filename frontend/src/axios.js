import axios from "axios";

// 공통 설정을 적용한 axios 인스턴스 생성
const api = axios.create({
  baseURL: 'http://localhost:8090/api', // 백엔드 기본 주소
  timeout: 5000, // 5초 초과 시 요청 취소
  headers: {
    'Content-Type': 'application/json',
  }
});

export default api;