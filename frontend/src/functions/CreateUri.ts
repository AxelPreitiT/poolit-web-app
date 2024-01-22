function CreateUri(id: string | undefined, querys: string, base_path: string): string {
    if (id === undefined) {
        return "empty";
    }else{
        return `${base_path}/${id}?${querys}`;
    }
}

export default CreateUri;