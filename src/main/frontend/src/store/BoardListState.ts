import { atom } from "recoil";
import { Board } from "../types/Board";
import { RequestBoardList } from "../types/RequestBoardList";


// export const BoardListState = atom<BoardList>({
//     key: 'BoardListState',
//     default: {
//         boards: [],
//         total: 0,
//     },
// });

export const BoardState = atom<Board | null>({
    key: "BoardState",
    default: null,
})

export const RequestBoardListState = atom<RequestBoardList>({
    key: "RequestBoardList",
    default: {
        category_cd : "",
        src_cd : "",
        search : "",
        sort_cd : "reg_dt",
        page_no : 1,
        page_size : 10
    }
})