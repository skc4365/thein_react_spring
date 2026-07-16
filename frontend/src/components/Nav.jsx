
import { Link } from "react-router-dom";
import "./Nav.css";

export default function Nav() {
  return (
    <div>
      {/* 메뉴명 표현 */}
      <nav>
        <ul className="main-menu">
          <li className="float_left">
            <Link to="/menu1">MENU1</Link>
          </li>
          <li className="float_left">
            <Link to="/menu2">MENU2</Link>
            <ul className="sub-menu">
              <li>
                <Link to="/menu2/sub1">SUB1</Link>
              </li>
              <li>
                <Link to="/menu2/sub2">SUB2</Link>
              </li>
            </ul>
          </li>
          <li className="float_left">
            <Link to="/menu3">MENU3</Link>
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
