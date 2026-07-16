// api/axiosInstance.js
import axios from 'axios';

const axiosInstance = axios.create({
  baseURL: '/api',
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// // 요청 인터셉터 - 토큰 자동 첨부
// axiosInstance.interceptors.request.use((config) => {
//   const token = localStorage.getItem('accessToken');
//   if (token) config.headers.Authorization = `Bearer ${token}`;
//   return config;
// });

// // 응답 인터셉터 - 공통 에러 처리
// axiosInstance.interceptors.response.use(
//   (response) => response,
//   (error) => {
//     if (error.response?.status === 401) {
//       // 로그인 페이지로 리다이렉트 등
//     }
//     return Promise.reject(error);
//   }
// );

export default axiosInstance;