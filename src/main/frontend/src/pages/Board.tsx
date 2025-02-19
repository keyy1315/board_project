import { useEffect, useState } from "react";
import { getBoard } from "../api/BoardApi";
import type { Board } from "../types/Board";
import Popup from "./Popup";
import ReactModal, {} from "react-modal";
import { useNavigate } from "react-router-dom";

export default function Board() {
  const [board, setBoard] = useState<Board | null>();
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const navigate = useNavigate();

  const openPopup = () => {
    setIsPopupOpen(true);
  };

  const closePopup = () => {
    setIsPopupOpen(false);
  };

  const board_no = Number(location.pathname.split("/").pop());
  const fetchBoard = async (board_no: number) => {
    try {
      const data = await getBoard(board_no);
      setBoard(data);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    if (board_no) {
      fetchBoard(board_no);
    }
  }, [board_no]);

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
            <td>{board?.reg_dt}</td>
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
            <td colSpan={3}>{board?.cont}</td>
          </tr>
          <tr>
            <th className="fir">첨부파일</th>
            <td colSpan={3}>
              {board?.file?.map((f) => (
                <span>
                  <a href="">{f.no}</a>
                  <br />
                </span>
              ))}
            </td>
          </tr>
        </tbody>
      </table>

      <div className="btn-box r">
        <a href="#" className="btn btn-green" onClick={openPopup}>
          수정
        </a>
        <a href="#" className="btn btn-red" onClick={openPopup}>
          삭제
        </a>
        <ReactModal isOpen={isPopupOpen} onRequestClose={closePopup} ariaHideApp={false} className="popup-modal">
          <Popup onClose={closePopup} />
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
