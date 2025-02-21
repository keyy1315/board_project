import { atom } from "recoil";
import { Board } from "../types/Board";

export const boardNoState = atom<number | null>({
  key: "boardNo",
  default: null,
});

export const boardState = atom<Board | null>({
  key: "board",
  default: null,
});