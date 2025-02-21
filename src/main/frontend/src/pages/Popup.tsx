import { useState } from "react";
import { authBoard, deleteBoard } from "../api/BoardApi";
import { useNavigate } from "react-router-dom";

export default function Popup({
  onClose,
  isDelete,
}: {
  onClose: () => void;
  isDelete: boolean;
}) {
  const navigate = useNavigate();

  const [password, setPassword] = useState("");

  const board_no = Number(location.pathname.split("/").pop());

  const handleCheckPassword = async (e: React.MouseEvent) => {
    e.preventDefault();
    authBoard(board_no, password).then((resp) => {
      if (resp) {
        if (isDelete) {
          if (confirm("게시글을 삭제하시겠습니까?")) {
            deleteBoard(board_no).then((r) => {
              if (r) {
                alert("게시글이 삭제되었습니다.");
              window.location.href = "/";
              }
            });
          }
        } else {
          navigate(`/board/write?n=${board_no}`);
        }
      } else {
        alert("비밀번호가 틀렸습니다.");
      }
    });
  };

  return (
    <div id="pop-wrap">
      <h1 className="pop-tit">비밀번호 확인</h1>
      <div className="pop-con">
        <table className="view">
          <colgroup>
            <col style={{ width: "100px" }} />
            <col />
          </colgroup>
          <tbody>
            <tr>
              <th>비밀번호</th>
              <td>
                <input
                  type="password"
                  className="input"
                  style={{ width: "200px" }}
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <a
                  href=""
                  className="btn btn-red"
                  onClick={handleCheckPassword}
                >
                  확인
                </a>
              </td>
            </tr>
          </tbody>
        </table>

        <div className="btn-box">
          <a
            className="btn btn-default"
            onClick={onClose}
          >
            닫기
          </a>
        </div>
      </div>
    </div>
  );
}
