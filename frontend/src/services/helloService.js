import * as helloApi from "../api/helloApi";

// ==================================
// API호출 및 데이터 가공.
// 
// ==================================
// export const getHelloList = async() => {
//     const res = await getHello();
//     return res.data.message;
// }

// 조회
export const getHelloList = async () => {
  try {
    const response = await helloApi.getHello();
    //return response.data.message;
    return response.data;
  } catch (error) {
    console.error("Hello 조회 실패:", error);
    throw error;
  }
};

// 등록
export const createHello = async (data) => {
  try {
    const response = await helloApi.createHello(data);
    return response.data;
  } catch (error) {
    console.error("Hello 등록 실패:", error);
    throw error;
  }
};

// 수정
export const updateHello = async (id, data) => {
  try {
    const response = await helloApi.updateHello(id, data);
    return response.data;
  } catch (error) {
    console.error("Hello 수정 실패:", error);
    throw error;
  }
};

// 삭제
export const deleteHello = async (id) => {
  try {
    await helloApi.deleteHello(id);
  } catch (error) {
    console.error("Hello 삭제 실패:", error);
    throw error;
  }
};