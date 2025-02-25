import axios from "axios";

export const downloadFile = async (file_no: number) => {
    const response = await axios.get(`/api/file/${file_no}`, {responseType: 'blob'});

    const contentDisposition = response.headers['content-disposition'];
    let fileName = 'download-file=' + file_no;  
        if (contentDisposition) {
            const matches = contentDisposition.match(/filename\*?=["']?([^"']+)["']?/);
            if (matches && matches[1]) {
                fileName = decodeURIComponent(matches[1].replace(/\+/g, '%20'));
            } else {
            fileName = contentDisposition.split('filename=')[1].replace(/"/g, '');
        }
    }

    const blob = new Blob([response.data], {type: 'application/octet-stream'});
    const link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    link.download = fileName;
    link.click();
}


export const deleteFile = async (file_no: number) => {
    const response = await axios.delete(`/api/file/${file_no}`);
    return response.data;
}

export const uploadImage = async (formData: FormData, board_no: number) => {
    const response = await axios.post(`/api/file/img?boardNo=${board_no}`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
    return response.data;
}

