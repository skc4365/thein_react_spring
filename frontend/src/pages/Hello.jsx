import { useCallback, useEffect, useState } from "react";
import * as helloService from "../services/helloService";

// ==================================
// GET    /hello           목록 조회
// GET    /hello/{id}      상세 조회
// POST   /hello           등록
// PUT    /hello/{id}      수정
// DELETE /hello/{id}      삭제
// ==================================
export default function Hello() {
  const [list, setList] = useState([]);
  const [message, setMessage] = useState("");

  // 목록 조회
  const loadData = async () => {
    try {
      // const data = await helloService.getHelloList();
      const data = await helloService.getHelloList();
      setList(data);
    } catch (error) {
      console.error(error);
    }
  };

  // 수정 필요할 수 있음~~~
  useEffect(() => {
    loadData();
  }, []);


  // 등록
  const handleSave = async () => {
    try {
      await helloService.createHello({
        message,
      });

      setMessage("");
      loadData();
    } catch (error) {
      console.error(error);
    }
  };

  // 수정
  const handleUpdate = async (id) => {
    try {
      await helloService.updateHello(id, {
        message,
      });

      setMessage("");
      loadData();
    } catch (error) {
      console.error(error);
    }
  };

  // 삭제
  const handleDelete = async (id) => {
    if (!window.confirm("삭제하시겠습니까?")) return;

    try {
      await helloService.deleteHello(id);
      loadData();
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ padding: 30 }}>
      <h2>Hello CRUD</h2>

      <input
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        placeholder="메시지 입력"
      />

      <button onClick={handleSave}>등록</button>

      <hr />

      <table border="1" cellPadding="10">
        <thead>
          <tr>
            <th>ID</th>
            <th>Message</th>
            <th>관리</th>
          </tr>
        </thead>

        <tbody>
          {list.map((item) => (
            <tr key={item.id}>
              <td>{item.id}</td>

              <td>{item.message}</td>

              <td>
                <button
                  onClick={() => {
                    setMessage(item.message);
                  }}
                >
                  선택
                </button>

                <button onClick={() => handleUpdate(item.id)}>
                  수정
                </button>

                <button onClick={() => handleDelete(item.id)}>
                  삭제
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
  // const [message, setMessage] = useState("");
  // const [loading, setLoading] = useState(true);

  // useEffect(() => {
  //   const fetchHello = async () => {
  //     try {
  //       const data = await getHelloList();
  //       setMessage(data);
  //     } catch (error) {
  //       console.error(error);
  //       setMessage("데이터를 불러오지 못했습니다.");
  //     } finally {
  //       setLoading(false);
  //     }
  //   };

  //   fetchHello();
  // }, []);

  // return (
  //   <div style={{ padding: 30 }}>
  //     <h1>React + Spring Boot</h1>
  //     <hr />
  //     <div>{loading ? "Loading..." : message}</div>
  //   </div>
  // );
}
