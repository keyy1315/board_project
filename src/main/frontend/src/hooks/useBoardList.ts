import { useQuery } from "@tanstack/react-query";
import { BoardListState } from "../store/BoardListState";
import { useRecoilState } from "recoil";
import { BoardList } from "../types/BoardList";
import { getBoardList } from "../api/BoardApi";
import { useEffect } from "react";

export const useBoardList = () => {
  const [boardList, setBoardList] = useRecoilState(BoardListState);

  const { data, error, isLoading } = useQuery<BoardList, Error>({
    queryKey: ["boardList"],
    queryFn: getBoardList,
  });

  useEffect(() => {
    if (data) setBoardList(data);
  }, [data, setBoardList]);

  if(error) throw error;
  return {boardList, isLoading}
};
