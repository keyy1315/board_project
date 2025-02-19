import { useNavigate } from "react-router-dom";

export default function BoardWrite() {
  const navigate = useNavigate();
  return (
    <div>
      <div id="contents">
        <div className="location">
          <span className="ic-home">HOME</span>
          <span>커뮤니티</span>
          <em>통합게시판</em>
        </div>

        <div className="tit-area">
          <h3 className="h3-tit">통합게시판</h3>
        </div>

        <table className="write">
          <colgroup>
            <col style={{width: "150px"}} />
            <col />
            <col style={{width: "150px"}} />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th className="fir">
                작성자 <i className="req">*</i>
              </th>
              <td>
                <input type="text" className="input block" />
              </td>
              <th className="fir">
                비밀번호 <i className="req">*</i>
              </th>
              <td>
                <input type="password" className="input block" />
              </td>
            </tr>
            <tr>
              <th className="fir">
                카테고리 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <select className="select" style={{width: "150px"}}>
                  <option>전체</option>
                  <option>-</option>
                </select>
              </td>
            </tr>
            <tr>
              <th className="fir">
                제목 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <input type="text" className="input" style={{width:" 100%"}} />
              </td>
            </tr>
            <tr>
              <th className="fir">
                내용 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <textarea style={{width: "100%", height: "300px"}}> </textarea>
              </td>
            </tr>
            <tr>
              <th className="fir">
                첨부파일 1 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <span>
                  <a href="#">상담내역1.xlsx</a>
                  <a href="#" className="ic-del">
                    삭제
                  </a>
                </span>
                <br />
                <input type="file" className="input block mt10" />
              </td>
            </tr>
            <tr>
              <th className="fir">첨부파일 2</th>
              <td colSpan={3}>
                <span>
                  <a href="#">상담내역2.xlsx</a>
                  <a href="#" className="ic-del">
                    삭제
                  </a>
                </span>
                <br />
                <input type="file" className="input block mt10" />
              </td>
            </tr>
            <tr>
              <th className="fir">첨부파일 3</th>
              <td colSpan={3}>
                <input type="file" className="input block mt10" />
              </td>
            </tr>
          </tbody>
        </table>

        <div className="btn-box r">
          <a href="" className="btn btn-red">
            저장
          </a>
          <a 
          href="" 
          className="btn btn-default"
          onClick={(e) => {
            e.preventDefault();
            navigate("/");
          }}
        >
            취소
          </a>
        </div>
      </div>
    </div>
  );
}
