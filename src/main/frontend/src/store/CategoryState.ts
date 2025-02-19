import { atom } from "recoil";
import { Category } from "../types/Category";

export const CategoryState = atom<Category[]>({
    key: 'CategoryState',
    default: []
});
