import { useNavigate } from "react-router-dom";
import { Editor } from "@toast-ui/react-editor";
import { useRef, useState, useEffect, useCallback } from "react";
import { useCategory } from "../hooks/useCategory";
import { writeBoard, updateBoard } from "../api/BoardApi";
import "@toast-ui/editor/dist/toastui-editor.css";
import { RequestBoard } from "../types/request/requestBoard";
import { downloadFile, deleteFile, uploadImage } from "../api/FileApi";
import { useRecoilState } from "recoil";
import {
  uploadFileListState,
  fileListState,
  deletedFileNosState,
} from "../store/FileState.ts";
import { useBoard } from "../hooks/useBoard.ts";

export default function BoardWrite() {
  const navigate = useNavigate();
  const editorRef = useRef<Editor>(null);

  const params = new URLSearchParams(window.location.search);
  const boardNo = params.get("n");
  const { board } = useBoard(Number(boardNo));
  const { category } = useCategory();

  const [fileList, setFileList] = useRecoilState(fileListState);
  const [deletedFileNos, setDeletedFileNos] =
    useRecoilState(deletedFileNosState);
  const [uploadFileList, setUploadFileList] =
    useRecoilState(uploadFileListState);
  const [isModify, setIsModify] = useState(false);

  const [reqBoard, setReqBoard] = useState<RequestBoard>({
    board_no: null,
    category_cd: "",
    title: "",
    cont: "",
    writer_nm: "",
    password: "",
  });

  useEffect(() => {
    if (boardNo && board) {
      setIsModify(true);
      setReqBoard({
        board_no: board.board_no ?? null,
        category_cd: board.category_cd ?? "",
        title: board.title ?? "",
        cont: board.cont ?? "",
        writer_nm: board.writer_nm ?? "",
        password: "",
      });
      setFileList(board.files || []);

      if (editorRef.current) {
        editorRef.current.getInstance().setMarkdown(board.cont ?? "");
      }
    }
  }, [board, boardNo, isModify, setIsModify, setReqBoard, setFileList]);
  const handleSubmit = useCallback(
    async (e: React.MouseEvent, isModify: boolean) => {
      e.preventDefault();

      if (!reqBoard?.password?.trim()) {
        alert("비밀번호를 입력하세요");
        (
          document.querySelector('input[type="password"]') as HTMLInputElement
        )?.focus();
        return;
      }

      if (!reqBoard?.password || reqBoard?.password?.length > 100) {
        alert("비밀번호는 100자를 초과할 수 없습니다");
        (
          document.querySelector('input[type="password"]') as HTMLInputElement
        )?.focus();
        return;
      }

      if (!reqBoard?.category_cd) {
        alert("카테고리를 선택하세요");
        (
          document.querySelector('select[name="category"]') as HTMLSelectElement
        )?.focus();
        return;
      }

      if (!reqBoard?.title?.trim()) {
        alert("제목을 입력하세요");
        (
          document.querySelector('input[name="title"]') as HTMLInputElement
        )?.focus();
        return;
      }

      if (!reqBoard?.title || reqBoard.title.length > 200) {
        alert("제목은 200자를 초과할 수 없습니다");
        (
          document.querySelector('input[name="title"]') as HTMLInputElement
        )?.focus();
        return;
      }

      const editorContent = editorRef.current?.getInstance().getMarkdown();
      if (!editorContent?.trim()) {
        alert("내용을 입력하세요");
        editorRef.current?.getInstance().focus();
        return;
      }

      if (fileList.length === 0 && uploadFileList.length === 0) {
        alert("첨부파일 1개 이상 첨부해주세요.");
        (
          document.querySelector('input[type="file"]') as HTMLInputElement
        )?.focus();
        return;
      }
      // 빈 파일 필터링
      
      setUploadFileList(uploadFileList.filter(file => file.name.trim()));

      // Check total file size
      const totalSize = uploadFileList.reduce(
        (acc, file) => acc + file.size,
        0
      );
      const maxSize = 200 * 1024 * 1024; // 200MB in bytes
      if (totalSize > maxSize) {
        alert("첨부파일의 총 크기가 200MB를 초과할 수 없습니다.");
        return;
      }

      if (isModify) {
        setDeletedFileNos([...new Set(deletedFileNos)]);
        console.log(deletedFileNos);

        if (deletedFileNos.length > 0) {
          const uniqueDeletedFileNos = [...new Set(deletedFileNos)];
          setDeletedFileNos(uniqueDeletedFileNos);
        }
        if (!confirm("게시글을 수정하시겠습니까?")) return;
        if (!reqBoard.board_no) return;

        try {
          const response = await updateBoard(
            reqBoard,
            reqBoard.board_no,
            uploadFileList,
            deletedFileNos
          );
          if (response.board_no != null) {
            alert("게시글이 수정되었습니다.");
            window.location.href = `/board/${response.board_no}`;
            // navigate(`/board/${response.board_no}`);
          }
        } catch (error) {
          alert("게시글 수정에 실패했습니다.");
          console.error(error);
        }
      } else {
        if (!reqBoard?.writer_nm?.trim()) {
          alert("작성자를 입력하세요");
          (
            document.querySelector('input[name="writer"]') as HTMLInputElement
          )?.focus();
          return;
        }

        if (!reqBoard?.writer_nm || reqBoard?.writer_nm?.length > 50) {
          alert("작성자명은 50자를 초과할 수 없습니다");
          (
            document.querySelector('input[name="writer"]') as HTMLInputElement
          )?.focus();
          return;
        }

        if (!confirm("게시글을 저장하시겠습니까?")) return;

        try {
          const response = await writeBoard(reqBoard, uploadFileList);
          if (response) {
            alert("게시글이 등록되었습니다.");
            window.location.href = "/";
          }
        } catch (error) {
          alert("게시글 등록에 실패했습니다.");
          console.error(error);
        }
      }
    },
    [reqBoard, fileList, uploadFileList, deletedFileNos, setDeletedFileNos, setUploadFileList]
  );

  const handleClickFile = useCallback((fileNo: number) => {
    downloadFile(fileNo);
  }, []);

  const handleDeleteFile = useCallback((fileNo: number) => {
    if (!confirm("첨부파일을 삭제하시겠습니까?")) return;
    deleteFile(fileNo).then((r) => {
      if (r) {
        alert("첨부파일이 삭제되었습니다.");
        window.location.reload();
      }
    });
  }, []);

  const handleFileChange = useCallback(
    (
      e: React.ChangeEvent<HTMLInputElement>,
      fileNo: number | null,
      index: number
    ) => {
      const files = e.target.files;
      if (!files) return;

      const file = files[0];
      const maxSize = 200 * 1024 * 1024; // 200MB in bytes

      if (file.size > maxSize) {
        alert("파일 크기가 200MB를 초과할 수 없습니다.");
        e.target.value = "";
        return;
      }

      if (fileNo !== null && fileNo !== undefined) {
        setDeletedFileNos((prev) => [...prev, fileNo]);
      }

      setUploadFileList((prev) => {
        const newList = [...prev];
        while (newList.length < 3) {
          newList.push(new File([], ''));
        }
        newList[index] = file;
        return newList;
      });

      // 파일 입력 상태 업데이트
      const fileInput = e.target;
      const existingDeleteIcon =
        fileInput.parentNode?.querySelector("#input-delete-icon");
      if (existingDeleteIcon) {
        existingDeleteIcon.remove();
      }

      // 새로운 삭제 아이콘 추가
      const deleteIcon = document.createElement("a");
      deleteIcon.className = "ic-del cursor-pointer ml-2";
      deleteIcon.id = "input-delete-icon";
      deleteIcon.style.cursor = "pointer";
      deleteIcon.onclick = () => {
        setUploadFileList((prev) => prev.filter((_, i) => i !== index));
        fileInput.value = ""; // Clear the file input
        deleteIcon.remove();
      };
      fileInput.parentNode?.appendChild(deleteIcon);
    },
    [setUploadFileList, setDeletedFileNos]
  );

  const handleInputChange = useCallback(
    (field: keyof RequestBoard, value: string) => {
      if (field === "cont") {
        // Only allow markdown content for cont field
        const markdownContent =
          editorRef.current?.getInstance().getMarkdown() || "";
        setReqBoard((prev) => ({ ...prev, cont: markdownContent }));
      } else {
        // Check field length limits
        if (field === "writer_nm" && value.length > 50) {
          alert("작성자명은 50자를 초과할 수 없습니다");
          return;
        }
        if (field === "password" && value.length > 100) {
          alert("비밀번호는 100자를 초과할 수 없습니다");
          return;
        }
        if (field === "title" && value.length > 200) {
          alert("제목은 200자를 초과할 수 없습니다");
          return;
        }
        setReqBoard((prev) => ({ ...prev, [field]: value }));
      }
    },
    [editorRef]
  );

  return (
    <div>
      <div id="contents">
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
                  name="writer"
                  className="input block"
                  value={reqBoard.writer_nm}
                  readOnly={isModify}
                  onChange={(e) => handleInputChange("writer_nm", e.target.value)}
                />
              </td>
              <th className="fir">
                비밀번호 <i className="req">*</i>
              </th>
              <td>
                <input
                  type="password"
                  className="input block"
                  value={reqBoard?.password}
                  onChange={(e) =>
                    handleInputChange("password", e.target.value)
                  }
                  // maxLength={100}
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
                  value={reqBoard?.category_cd}
                  onChange={(e) =>
                    handleInputChange("category_cd", e.target.value)
                  }
                >
                  <option value="">선택</option>
                  {category?.map((cat) => (
                    <option key={cat.comm_cd} value={cat.comm_cd_nm}>
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
                  value={reqBoard?.title}
                  onChange={(e) => handleInputChange("title", e.target.value)}
                  // maxLength={200}
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
                  onChange={(e: string) => handleInputChange("cont", e)}
                  hooks={{
                    addImageBlobHook: (blob: Blob, callback: (url: string, alt: string) => void) => {
                      const formData = new FormData();
                      formData.append("image", blob);
                    
                      uploadImage(formData, board?.board_no ?? 0).then((r) => {
                        if(r){
                          callback(r, 'imageURL');
                        }
                      })
                      .catch((err) => {
                        console.error(err);
                      }); 
                    }
                  }}
                />
              </td>
            </tr>
            {isModify
              ? Array.from({ length: 3 }).map((_, index) => (
                  <tr key={index}>
                    <th className="fir">
                      첨부파일 {index + 1}{" "}
                      {index === 0 && <i className="req">*</i>}
                    </th>
                    <td colSpan={3}>
                      {index < fileList.length ? (
                        <>
                          <span>
                            <a
                              className="cursor-pointer mx-2.5"
                              onClick={() =>
                                handleClickFile(fileList[index].file_no)
                              }
                            >
                              {fileList[index].origin_file_nm}
                            </a>
                            <a
                              className="ic-del cursor-pointer ml-2"
                              onClick={() =>
                                handleDeleteFile(fileList[index].file_no)
                              }
                            >
                              삭제
                            </a>
                          </span>
                          <br />
                        </>
                      ) : null}
                      <input
                        type="file"
                        className="input block mt-2"
                        onChange={(e) => {
                          if (fileList.length > 0) {
                            handleFileChange(e, fileList[index].file_no, index);
                          } else {
                            handleFileChange(e, null, index);
                          }
                        }}
                      />
                    </td>
                  </tr>
                ))
              : Array.from({ length: 3 }).map((_, index) => (
                  <tr key={index}>
                    <th className="fir">
                      첨부파일 {index + 1}{" "}
                      {index === 0 && <i className="req">*</i>}
                    </th>
                    <td colSpan={3}>
                      <input
                        type="file"
                        className="input block mt-2"
                        onChange={(e) => handleFileChange(e, null, index)}
                      />
                    </td>
                  </tr>
                ))}
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
