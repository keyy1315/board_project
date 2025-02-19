import { Outlet, useNavigate } from "react-router-dom";

export default function SideBar() {
  const navigate = useNavigate();

  return (
    <div id="container">
      <div id="lm">
        <h2 className="h2-tit">
          <strong>커뮤니티</strong>
        </h2>
        <ul id="snb">
          <li className="snb1">
            <a
              href="/"
              onClick={() => {
                navigate("/");
              }}
            >
              통합게시판
            </a>
          </li>
        </ul>
      </div>
      <div id="contents">
        <div className="location">
          <span className="ic-home">HOME</span>
          <span>커뮤니티</span>
          <em>통합게시판</em>
        </div>
        <div className="tit-area">
          <h3 className="h3-tit">통합게시판</h3>
        </div>
        {/* <BoardList /> */}
        <Outlet />
      </div>
      <div className="float-right">
          <h2>QUICK MENU</h2>
          <ul>
            <li className="item1">
              <a href="" onClick={() => {
                navigate("/");
              }} >통합게시판</a>
            </li>
          </ul>
        </div>
    </div>
  );
}
// split pagination , element split
