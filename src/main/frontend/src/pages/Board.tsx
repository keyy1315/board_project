import { useState, useEffect, useRef } from "react";
import type { Board } from "../types/Board";
import Popup from "./Popup";
import ReactModal from "react-modal";
import { useNavigate } from "react-router-dom";
import { Viewer } from "@toast-ui/react-editor";
import { downloadFile } from "../api/FileApi";
import { useAddViewCount, useBoard } from "../hooks/useBoard";

export default function Board() {
  const boardNo = Number(location.pathname.split("/").pop());
  const { board } = useBoard(boardNo);
  const hasCalled = useRef(false);
  const navigate = useNavigate();

  const { addViewCount } = useAddViewCount(boardNo);
  useEffect(() => {
    if (!hasCalled.current) {
      addViewCount();
      hasCalled.current = true;
    }
  }, [addViewCount]);

  useEffect(() => {
    if (board === undefined) return;
    if (board === null) {
      navigate('/');
    }
  }, [board, navigate]);

  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [isDelete, setIsDelete] = useState(false);

  const handleOpenPopup = (e: React.MouseEvent) => {
    e.preventDefault();
    setIsPopupOpen(true);
  };

  const handleClosePopup = () => {
    setIsPopupOpen(false);
  };

  const formatDate = (dateString: string) => {
    return dateString.replace("T", " ");
  };

  const handleFileClick = (file_no: number) => {
    downloadFile(file_no);
  };

  if (!board) return null;

  return (
    <div>
      <table className="write">
        <colgroup>
          <col style={{ width: "150px" }} />
          <col />
          <col style={{ width: "150px" }} />
          <col />
        </colgroup>
        <tbody>
          <tr>
            <th className="fir">작성자</th>
            <td>{board?.writer_nm}</td>
            <th className="fir">작성일시</th>
            <td>{board?.reg_dt && formatDate(board.reg_dt)}</td>
          </tr>
          <tr>
            <th className="fir">카테고리</th>
            <td colSpan={3}>{board?.category_cd}</td>
          </tr>
          <tr>
            <th className="fir">제목</th>
            <td colSpan={3}>{board?.title}</td>
          </tr>
          <tr>
            <th className="fir">내용</th>
            <td colSpan={3} style={{ height: "300px", verticalAlign: "top" }}>
              {board?.cont && <Viewer initialValue={board.cont} />}
            </td>
          </tr>
          <tr>
            <th className="fir">첨부파일</th>
            <td colSpan={3}>
              {board?.files &&
                board?.files.length > 0 &&
                board?.files?.map((f) => (
                  <span
                    key={f.file_no}
                    onClick={() => {
                      handleFileClick(f.file_no);
                    }}
                  >
                    <a className="ic-file2" style={{ cursor: "pointer" }}>
                      {f.origin_file_nm}
                    </a>
                    <br />
                  </span>
                ))}
            </td>
          </tr>
        </tbody>
      </table>

      <div className="btn-box r">
        <a
          href=""
          className="btn btn-green"
          onClick={(e) => {
            handleOpenPopup(e);
            setIsDelete(false);
          }}
        >
          수정
        </a>
        <a
          href=""
          className="btn btn-red"
          onClick={(e) => {
            handleOpenPopup(e);
            setIsDelete(true);
          }}
        >
          삭제
        </a>
        <ReactModal
          isOpen={isPopupOpen}
          onRequestClose={handleClosePopup}
          ariaHideApp={false}
          className="popup-modal"
          shouldCloseOnOverlayClick={true}
          shouldCloseOnEsc={true}
        >
          <Popup isDelete={isDelete} onClose={handleClosePopup} />
        </ReactModal>
        <a
          href=""
          className="btn btn-default"
          onClick={(e) => {
            e.preventDefault();
            navigate("/");
          }}
        >
          목록
        </a>
      </div>
    </div>
  );
}
