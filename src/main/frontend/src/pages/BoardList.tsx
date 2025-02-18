import Paginate from "../component/Paginate";
import newImage from "../images/new.gif";
import { useBoardList } from "../hooks/useBoardList";
import { useCategory } from "../hooks/useCategory";
import { useNavigate } from "react-router-dom";

export default function BoardList() {
  const navigate = useNavigate();
  const { boardList } = useBoardList();
  const { category } = useCategory();

  console.log(category);

  const isNew = (reg_dt: string) => {
    const postDate = new Date(reg_dt);
    const now = new Date();

    const three = new Date();
    three.setDate(now.getDate() - 3);

    return postDate >= three;
  };

  return (
    <>
      <div className="hide-dv mt10" id="hideDv">
        <table className="view">
          <colgroup>
            <col style={{ width: "150px" }} />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th>카테고리</th>
              <td>
                <select
                  id="category_cd"
                  className="select"
                  style={{ width: "150px" }}
                >
                  <option key={"all"}>전체</option>
                  {category.map((c) => (
                    <option key={c.comm_cd}>{c.comm_cd_nm}</option>
                  ))}
                </select>
              </td>
            </tr>
            <tr>
              <th>검색어</th>
              <td>
                <select
                  id="searchCode"
                  className="select"
                  style={{ width: "150px" }}
                >
                  <option key={"all"}>전체</option>
                  <option key={"title"}>제목</option>
                  <option key={"cont"}>내용</option>
                  <option key={"tiCont"}>제목 + 내용</option>
                  <option key={"writer_nm"}>작성자명</option>
                </select>
                <input
                  type="text"
                  className="input"
                  style={{ width: "300px" }}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div className="btn-box btm l">
        <a href="/" className="btn btn-red fr">
          검색
        </a>
      </div>

      <div className="tbl-hd noBrd mb0">
        <span className="total">
          검색 결과 : <strong>{boardList.total}</strong> 건
        </span>
        <div className="right">
          <span className="spanTitle">정렬 순서 :</span>
          <select id="sortCode" className="select" style={{ width: "120px" }}>
            <option key={"reg_dt"}>최근 작성일</option>
            <option key={"view_cnt"}>조회수</option>
          </select>
        </div>
      </div>
      <table className="list default">
        <colgroup>
          <col style={{ width: "60px" }} />
          <col style={{ width: "80px" }} />
          <col style={{ width: "auto" }} />
          <col style={{ width: "80px" }} />
          <col style={{ width: "80px" }} />
          <col style={{ width: "80px" }} />
          <col style={{ width: "120px" }} />
        </colgroup>
        <thead>
          <tr>
            <th>No</th>
            <th>카테고리</th>
            <th>제목</th>
            <th>첨부파일</th>
            <th>작성자</th>
            <th>조회수</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {boardList.boards.map((b) => (
            <tr key={b.board_no}>
              <td>{b.board_no}</td>
              <td>{b.category_cd}</td>
              <td className="l">
                <a href=""
                onClick={() => {
                  navigate(`/board/${b.board_no}`)
                }}
                >
                  {b.title}
                  {isNew(b.reg_dt) && <img src={newImage} className="new" />}
                </a>
              </td>
              <td>
                {b.file && (
                  <a href="#" className="ic-file">
                    파일
                  </a>
                )}
              </td>
              <td>{b.writer_nm}</td>
              <td>{b.view_cnt}</td>
              <td>{new Date(b.reg_dt).toISOString().split("T")[0]}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <Paginate />
      <div className="btn-box l mt30">
        <a href="" 
        onClick={() => {
          navigate("/board/write");
        }}
        className="btn btn-green fr">
          등록
        </a>
      </div>
    </>
  );
}
