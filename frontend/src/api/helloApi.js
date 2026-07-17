// 보안적용 전,
// import axiosInstance from "./axiosInstance";
// 보안적용 후,
import axiosInstance from "./axios";

// ==================================
// HTTP 통신만 담당
// axios, URL, GET/POST, PUT, DELETE
// ==================================

// 조회
export const getHello = () => {
  return axiosInstance.get("/hello");
};

// 등록 
export const createHello = (data) => {
  return axiosInstance.post("/hello", data);
};

// 수정 
export const updateHello = (id, data) => {
  return axiosInstance.put(`/hello/${id}`, data);
};

// 삭제
export const deleteHello = (id) => {
  return axiosInstance.delete(`/hello/${id}`);
};
