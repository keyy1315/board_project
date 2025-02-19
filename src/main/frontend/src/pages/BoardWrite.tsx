import { useNavigate } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useRef, useState, useEffect } from "react";
import { useCategory } from "../hooks/useCategory";
import { writeBoard, getBoard, updateBoard } from "../api/BoardApi";
import "@toast-ui/editor/dist/toastui-editor.css";
import { RequestBoard } from "../types/request/requestBoard";

export default function BoardWrite() {
  const navigate = useNavigate();
  const editorRef = useRef<Editor>(null);
  const { category } = useCategory();
  const [board, setBoard] = useState<RequestBoard>({
    board_no: null,
    category_cd: "",
    title: "",
    cont: "",
    writer_nm: "",
    password: "",
    file: null,
  });
  const [isModify, setIsModify] = useState(false);

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const boardNo = params.get("n");

    if (boardNo) {
      setIsModify(true);
      getBoard(Number(boardNo)).then((data) => {
        setBoard({
          board_no: data.board_no,
          category_cd: data.category_cd,
          title: data.title,
          cont: data.cont,
          writer_nm: data.writer_nm,
          password: "",
          file:
            data.file?.map((f) => ({
              original_file_nm: f.original_file_nm,
              save_file_nm: f.save_file_nm,
              save_path: f.save_path,
              ext: f.ext,
              size: f.size,
            })) || null,
        });
        // 에디터에 내용 설정
        if (editorRef.current) {
          editorRef.current.getInstance().setMarkdown(data.cont);
        }
      });
    }
  }, []);

  const handleSubmit = async (e: React.MouseEvent, isModify: boolean) => {
    e.preventDefault();

    if (!board?.writer_nm?.trim()) {
      alert("작성자를 입력하세요");
      return;
    }
    if (!board?.password?.trim()) {
      alert("비밀번호를 입력하세요");
      return;
    }
    if (!board?.category_cd) {
      alert("카테고리를 선택하세요");
      return;
    }
    if (!board?.title?.trim()) {
      alert("제목을 입력하세요");
      return;
    }

    const editorContent = editorRef.current?.getInstance().getMarkdown();
    if (!editorContent?.trim()) {
      alert("내용을 입력하세요");
      return;
    }

    const updatedBoard = {
      ...board,
      cont: editorContent
    };

    if (isModify) {
      if (!updatedBoard.board_no) return;
      updateBoard(updatedBoard, updatedBoard.board_no)
        .then((r) => {
          if (r.board_no != null) {
            alert("게시글이 수정되었습니다.");
            navigate("/");
          }
        })
        .catch((error) => {
          alert("게시글 수정에 실패했습니다.");
          console.error(error);
        });
    } else {
      writeBoard(updatedBoard)
        .then((r) => {
          if (r) {
            alert("게시글이 등록되었습니다.");
            navigate("/");
          }
        })
        .catch((error) => {
          alert("게시글 등록에 실패했습니다.");
          console.error(error);
        });
    }
  };

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
            <col style={{ width: "150px" }} />
            <col />
            <col style={{ width: "150px" }} />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th className="fir">
                작성자 <i className="req">*</i>
              </th>
              <td>
                <input
                  type="text"
                  className="input block"
                  value={board?.writer_nm}
                  onChange={(e) =>
                    setBoard((p) => ({
                      ...p,
                      writer_nm: e.target.value,
                    }))
                  }
                />
              </td>
              <th className="fir">
                비밀번호 <i className="req">*</i>
              </th>
              <td>
                <input
                  type="password"
                  className="input block"
                  value={board?.password}
                  onChange={(e) =>
                    setBoard((prev) => ({ ...prev, password: e.target.value }))
                  }
                />
              </td>
            </tr>
            <tr>
              <th className="fir">
                카테고리 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <select
                  className="select"
                  style={{ width: "150px" }}
                  value={board?.category_cd}
                  onChange={(e) =>
                    setBoard((prev) => ({
                      ...prev,
                      category_cd: e.target.value,
                    }))
                  }
                >
                  <option value="">선택</option>
                  {category?.map((cat) => (
                    <option key={cat.comm_cd} value={cat.comm_cd}>
                      {cat.comm_cd_nm}
                    </option>
                  ))}
                </select>
              </td>
            </tr>
            <tr>
              <th className="fir">
                제목 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <input
                  type="text"
                  className="input"
                  style={{ width: "100%" }}
                  value={board.title}
                  onChange={(e) =>
                    setBoard((prev) => ({ ...prev, title: e.target.value }))
                  }
                />
              </td>
            </tr>
            <tr>
              <th className="fir">
                내용 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <Editor
                  ref={editorRef}
                  initialValue=" "
                  previewStyle="vertical"
                  height="500px"
                  width="100%"
                  initialEditType="markdown"
                  useCommandShortcut={true}
                />
              </td>
            </tr>
            <tr>
              <th className="fir">
                첨부파일 1 <i className="req">*</i>
              </th>
              <td colSpan={3}>
                <span>
                  <a href="">상담내역1.xlsx</a>
                  <a href="" className="ic-del">
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
                  <a href="">상담내역2.xlsx</a>
                  <a href="" className="ic-del">
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
          <a
            href=""
            className="btn btn-red"
            onClick={(e) => handleSubmit(e, isModify)}
          >
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
