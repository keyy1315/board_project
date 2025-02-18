import { atom } from "recoil";
import { Board } from "../types/Board";
import { BoardList } from "../types/BoardList";


export const BoardListState = atom<BoardList>({
    key: 'BoardListState',
    default: {
        boards: [],
        total: 0,
    },
});

export const BoardState = atom<Board | null>({
    key: "BoardState",
    default: null,
})