import axios from "axios";
import { BoardList } from "../types/BoardList";
import { RequestBoardList } from "../types/request/RequestBoardList";
import { Board } from "../types/Board";
import { RequestBoard } from "../types/request/requestBoard";

export const getBoardList = async (requestBoardList: RequestBoardList) => {
  const response = await axios.get<BoardList>("/api/board/", {
    params: requestBoardList,
  });
  return response.data;
};

export const getBoard = async (board_no: number): Promise<Board> => {
  console.log("getBoard : ",board_no);
  const response = await axios.get<Board>(`/api/board/${board_no}`);
  return response.data;
};

export const writeBoard = async (board: RequestBoard, files: File[]) => {
  const formData = new FormData();
  if (files) {
    files.forEach((file) => {
      formData.append("uploadFiles", file);
    });
  }
  const blob = new Blob([JSON.stringify(board)], {
    type: "application/json",
  });
  formData.append("dto", blob);
  const response = await axios.post<Board>(`/api/board/`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};

export const updateBoard = async (
  board: RequestBoard,
  board_no: number,
  files: File[],
  deletedFileNos: number[]
) => {
  const formData = new FormData();
  const blob = new Blob([JSON.stringify(board)], { type: "application/json" });
  formData.append("dto", blob);
  if (deletedFileNos) {
    const blob2 = new Blob([JSON.stringify(deletedFileNos)], {
      type: "application/json",
    });
    formData.append("deleteFiles", blob2);
  }

  if (files) {
    files.forEach((file) => {
      formData.append("uploadFiles", file);
    });
  }

  const response = await axios.put<Board>(`/api/board/${board_no}`, formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
  return response.data;
};

export const deleteBoard = async (board_no: number) => {
  const response = await axios.delete<boolean>(`/api/board/${board_no}`);
  return response.data;
};

export const authBoard = async (board_no: number, password: string) => {
  const response = await axios.post<boolean>(
    `/api/board/auth?no=${board_no}&password=${password}`
  );
  return response.data;
};

export const addViewCount = async (board_no: number) => {
  const response = await axios.patch<boolean>(`/api/board/${board_no}`);
  console.log("addViewCount : ", board_no, response.data);
  return response.data;
};
