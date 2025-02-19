import axios from "axios";
import { BoardList } from "../types/BoardList";
import { RequestBoardList } from "../types/request/RequestBoardList";
import { Board } from "../types/Board";
import { RequestBoard } from "../types/request/requestBoard";

export const getBoardList = async (requestBoardList: RequestBoardList) => {
  const response = await axios.get<BoardList>('/api/board/', {params: requestBoardList});
  return response.data;
};

export const getBoard = async (board_no: number): Promise<Board> => {
  const response = await axios.get<Board>(`/api/board/${board_no}`);
  return response.data;
};

export const writeBoard = async (board: RequestBoard) => {
  const response = await axios.post<Board>(`/api/board/`, board);
  return response.data;
};

export const updateBoard = async (board: RequestBoard , board_no : number) => {
  const response = await axios.put<Board>(`/api/board/${board_no}`, board);
  return response.data;
};

export const deleteBoard = async (board_no: number) => {
  const response = await axios.delete<boolean>(
    `/api/board/${board_no}`
  );
  return response.data;
};

export const authBoard = async (board_no: number, password: string) => {
  const response = await axios.post<boolean>(
    `/api/board/auth?no=${board_no}&password=${password}`
  );
  return response.data;
};
