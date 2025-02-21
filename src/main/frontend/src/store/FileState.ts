import { atom } from "recoil";
import { File as CustomFile } from "../types/File";
export const deletedFileNosState = atom<number[]>({
    key: "deletedFileNos",
    default: [],
  });

  export const fileListState = atom<CustomFile[]>({
    key: "fileList",
    default: [],
  });

  export const uploadFileListState = atom<File[]>({
    key: "uploadFileList",
    default: [],
  });
