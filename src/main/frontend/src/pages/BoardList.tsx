import Paginate from "../component/Paginate";
import newImage from "../images/new.gif";
import { useBoardList } from "../hooks/useBoardList";
import { useCategory } from "../hooks/useCategory";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { RequestBoardListState } from "../store/BoardListState";
import { useState } from "react";

export default function BoardList() {
  const navigate = useNavigate();
  const [request, setRequest] = useRecoilState(RequestBoardListState);

  const [searchText, setSearchText] = useState<string>(request.search);
  const [searchCode, setsearchCode] = useState<string>(request.src_cd);
  const { boardList } = useBoardList(request);
  const { category } = useCategory();

  const isNew = (reg_dt: string) => {
    const postDate = new Date(reg_dt);
    const now = new Date();

    const three = new Date();
    three.setDate(now.getDate() - 3);

    return postDate >= three;
  };

  const handleSearch = () => {
    if (!searchText.trim() && searchCode !== "all") {
      alert("검색어를 입력하세요");
      return;
    }
    setRequest((p) => ({
      ...p,
      search: searchText,
      src_cd: searchCode,
    }));
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
                  value={request.category_cd}
                  onChange={(e) =>
                    setRequest((p) => ({ ...p, category_cd: e.target.value }))
                  }
                >
                  <option value="" key="all">
                    전체
                  </option>
                  {category.map((c) => (
                    <option value={c.comm_cd} key={c.comm_cd}>
                      {c.comm_cd_nm}
                    </option>
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
                  value={searchCode}
                  onChange={(e) => setsearchCode(e.target.value)}
                >
                  <option value="all" key="all">
                    전체
                  </option>
                  <option value="title" key="title">
                    제목
                  </option>
                  <option value="cont" key="cont">
                    내용
                  </option>
                  <option value="tiCont" key="tiCont">
                    제목 + 내용
                  </option>
                  <option value="writer_nm" key="writer_nm">
                    작성자명
                  </option>
                </select>
                <input
                  type="text"
                  className="input"
                  style={{ width: "300px" }}
                  value={searchText}
                  onChange={(e) => setSearchText(e.target.value)}
                  onKeyDown={(e) => e.key === "Enter" && handleSearch()}
                />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <div className="btn-box btm l">
        <a className="btn btn-red fr" onClick={handleSearch} style={{ cursor: "pointer" }}>
          검색
        </a>
      </div>

      <div className="tbl-hd noBrd mb0">
        <span className="total">
          검색 결과 : <strong>{boardList.total}</strong> 건
        </span>
        <div className="right">
          <span className="spanTitle">정렬 순서 :</span>
          <select id="sortCode" className="select" style={{ width: "120px" }}
          onChange={(e) =>
            setRequest((p) => ({ ...p, sort_cd: e.target.value }))
          }>
            <option key={"reg_dt"} value={"reg_dt"}>최근 작성일</option>
            <option key={"view_cnt"} value={"view_cnt"}>조회수</option>
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
          {boardList.boards.map((b, index) => (
            <tr key={b.board_no}>
              <td>{(request.page_no - 1) * request.page_size + index + 1}</td>
              <td>{b.category_cd}</td>
              <td className="l"
               onClick={(e) => {
                e.preventDefault();
                navigate(`/board/${b.board_no}`);
              }}
              style={{ cursor: "pointer" }}>
                <a>
                  {b.title}
                  {isNew(b.reg_dt) && <img src={newImage} className="new" />}
                </a>
              </td>
              <td>
                {b.files && b.files.length > 0 && (
                  <a className="ic-file">
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
      <Paginate total={boardList.total} />
      <div className="btn-box l mt30">
        <a
          onClick={(e) => {
            e.preventDefault();
            navigate("/board/write");
          }}
          className="btn btn-green fr"
          style={{ cursor: "pointer" }}
        >
          등록
        </a>
      </div>
    </>
  );
}
