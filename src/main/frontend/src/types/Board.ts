import {File} from './File'

export interface Board {
    board_no : number,
    category_cd : string,
    title : string,
    cont : string,
    writer_nm : string,
    view_cnt : number,
    reg_dt : string,
    mod_dt : string | null,
    files : File[] | null
}