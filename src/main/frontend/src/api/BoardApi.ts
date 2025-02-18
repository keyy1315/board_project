import axios from "axios";
import { BoardList } from "../types/BoardList";
import { Board } from "../types/Board";

export const getBoardList = async () => {
    const response = await axios.get<BoardList>('/api/board/list?page_no=1&page_size=10');
    return response.data;
}

export const getBoard = async (board_no : number) : Promise<Board> => {
    const response = await axios.get<Board>(`/api/board/${board_no}`)
    return response.data;
}