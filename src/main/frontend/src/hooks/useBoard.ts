import { useQuery, useMutation } from "@tanstack/react-query";
import { Board } from "../types/Board";
import { getBoard, addViewCount as addViewCountApi } from "../api/BoardApi";

export const useBoard = (board_no: number) => {
  const { data: board } = useQuery<Board>({
    queryKey: ["board", board_no],
    queryFn: () => getBoard(board_no),
    enabled: !!board_no
  });

  return {board}; 
};

export const useAddViewCount = (board_no: number) => {
  const { mutate: addViewCount } = useMutation<boolean, Error, void>({
    mutationFn: () => addViewCountApi(board_no),
  });
  return { addViewCount };
};
