import "./App.css";
import { Route, Routes } from "react-router-dom";
import Menu1 from "./pages/Menu1";
import Menu2 from "./pages/Menu2";
import SUB1_menu2 from "./pages/SUB1_menu2";
import SUB2_menu2 from "./pages/SUB2_menu2";
import Menu3 from "./pages/Menu3";
import Layout from "./pages/Layout";
import Hello from "./pages/Hello";


function App() {
  
  return (
    <>
      <Routes>
        {/* 공통 layout */}
        <Route path="/" element={<Layout />}>
          <Route path="menu1" element={<Menu1 />} />

          <Route path="menu2" element={<Menu2 />}>
            {/* <Outlet /> */} 
            <Route path="sub1" element={<SUB1_menu2 />} />
            <Route path="sub2" element={<SUB2_menu2 />} />
          </Route>

          <Route path="menu3" element={<Menu3 />} />
          <Route path="hello" element={<Hello />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
