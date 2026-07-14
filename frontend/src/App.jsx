import { useEffect, useState } from "react";
import "./App.css";
import axios from "axios";

function App() {
  
  const [message, setMessage] = useState("");

  // 1단계
  // useEffect(() => {
  //   axios
  //     .get("http://localhost:8090/api/hello")
  //     .then((response) => {
  //       setMessage(response.data.message);
  //     })
  //     .catch((error) => {
  //       console.error("데이터 에러:", error);
  //     });
  // }, []);

  // return (
  //   <div>
  //     <h1>React - Spring 연동 테스트</h1>
  //     <p>받은 메시지: {message}</p>
  //   </div>
  // );

  // 2단계
  // proxy server 설정후
  useEffect(() => {

    axios
      .get("/main/main-hello")
      .then((res) => {
        setMessage(res.data.message);
      })
      .catch((err) => console.error(err));

  }, []);

  return (
    <div style={{ padding: "30px" }}>
      <h1>React + Spring Boot 연동</h1>

      <hr />

      <h3>나는 React App 페이지 메시지</h3>

      <div
        style={{
          border: "1px solid #aaa",
          padding: "20px",
          borderRadius: "10px",
          marginTop: "20px",
        }}
      >
        {message || "데이터를 불러오는 중..."}
      </div>
    </div>
  );
}

export default App;
