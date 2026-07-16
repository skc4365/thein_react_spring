import axios from 'axios';

const api = axios.create({
  baseURL: '/api',
  withCredentials: true, // refresh token 쿠키 전송을 위해 필수
});

// 요청 인터셉터: accessToken 자동 첨부
api.interceptors.request.use((config) => {
  const token = window.__accessToken; // AuthContext에서 관리하는 값 참조
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;