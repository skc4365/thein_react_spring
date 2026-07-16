
import { Link } from "react-router-dom";
import "./Nav.css";

export default function Nav() {
  return (
    <div>
      {/* 메뉴명 표현 */}
      <nav>
        <ul className="main-menu">
          <li className="float_left">
            <Link to="/menu1">HOME-ABOUT ME (소개)</Link>
          </li>
          <li className="float_left">
            <Link to="/menu2">COURSES</Link>
            <ul className="sub-menu">
              <li>
                <Link to="/menu2/sub1">SpringBoot</Link>
              </li>
              <li>
                <Link to="/menu2/sub2">React</Link>
              </li>
            </ul>
          </li>
          <li className="float_left">
            <Link to="/menu3">CONTACT</Link>
          </li>
          <li className="float_left">
            <Link to="/hello">Hello</Link>
          </li>
        </ul>
      </nav>
      
      <hr />
    </div>
  );
}
