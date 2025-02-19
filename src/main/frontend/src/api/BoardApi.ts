import axios from "axios";
import { BoardList } from "../types/BoardList";
import { RequestBoardList } from "../types/RequestBoardList";
import { Board } from "../types/Board";

export const getBoardList = async (requestBoardList: RequestBoardList) => {
  const url = `/api/board/?category_cd=${requestBoardList.category_cd}&src_cd=${requestBoardList.src_cd}&search=${requestBoardList.search}&sort_cd=${requestBoardList.sort_cd}&page_no=${requestBoardList.page_no}&page_size=${requestBoardList.page_size}`;
  const response = await axios.get<BoardList>(url);
  return response.data;
};

export const getBoard = async (board_no: number): Promise<Board> => {
  const response = await axios.get<Board>(`/api/board/${board_no}`);
  return response.data;
};
